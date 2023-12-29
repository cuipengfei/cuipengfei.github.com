---
title: "Spring Integration JDBC分布式锁 - Transactions与Threads"
date: 2023-12-25 11:16:17
tags:
- 分布式
- 锁
- Java
---

# 第一个问题：在多个线程中同时运行隔离级别为serializable的事务而导致的无法重试获取锁的问题

Spring Integration JDBC分布式锁的实现会需要使用一个serializable级别的事务来**获取锁**。

如果多个线程同时尝试获取锁，这些事务之间可能会出现顺序问题。

具体而言，可能会遇到以下错误：

```
org.postgresql.util.PSQLException: ERROR: could not serialize access due to read/write dependencies among transactions
```

发生这样的问题其实也不可怕，因为JDBC锁会进行重试。

然而，当使用JPA Transaction Manager时，由于某些异常类型的原因，JDBC锁无法在发生这种错误的情况下进行重试。

可以在以下GitHub Issue中查看详细信息：
https://github.com/spring-projects/spring-integration/issues/3733

可以使用以下代码重现此问题：
https://github.com/cuipengfei/Spikes/blob/master/jpa/lock-transaction-threads/src/main/java/com/github/spring/example/service/Problem1Service.java

# 使用Data Source Transaction Manager来workaround第一个问题

可以明确指定让JDBC锁不使用JPA Transaction Manager，而是使用Data Source Transaction Manager来绕过此问题。

具体代码请参考：
https://github.com/cuipengfei/Spikes/blob/master/jpa/lock-transaction-threads/src/main/java/com/github/spring/example/configs/CustomJDBCLockConfigs.java

可以执行该代码以观察workaround的效果：
https://github.com/cuipengfei/Spikes/blob/master/jpa/lock-transaction-threads/src/main/java/com/github/spring/example/service/Problem1FixService.java

# 第二个问题：在同一个线程中先使用JpaTransactionManager启动一个事务，然后尝试用DataSourceTransactionManager获取JDBC锁所导致的事务隔离级别变化的问题

该问题的显著特征是：如果在一个方法上标注了@Transactional，然后在该方法内部先执行了一些JPA的SQL操作，然后再尝试获取JDBC分布式锁，就会出现无法更改事务隔离级别的问题。

问题的关键在于并行流（parallel stream）并不总是仅利用其自己线程池中的线程，它也会利用当前线程。
而恰好落在当前线程上的那一次尝试获取JDBC分布式锁的操作就会出现无法更改事务隔离级别的问题。

这是因为我们用来解决第一个问题而引入的DataSourceTransactionManager的文档中提及它具有如下行为：
> Note: The DataSource that this transaction manager operates on needs to return independent Connections. The Connections typically come from a connection pool but the DataSource must not return specifically scoped or constrained Connections. This transaction manager will associate Connections with thread-bound transactions, according to the specified propagation behavior. It assumes that a separate, independent Connection can be obtained even during an ongoing transaction.

可以通过以下代码观察parallel stream的行为：
https://github.com/cuipengfei/Spikes/blob/master/jpa/lock-transaction-threads/src/main/java/com/github/spring/example/TestParallelStreamThreads.java

可以使用以下代码重现该问题：
https://github.com/cuipengfei/Spikes/blob/master/jpa/lock-transaction-threads/src/main/java/com/github/spring/example/service/Problem2Service.java

# 第二个问题的不完善解决方法：强制parallel stream不使用当前线程

在解决该问题的过程中，我尝试了一种不太明智的方法，在这里也记录一下。

我最初的想法是，既然parallel stream会利用当前线程，从而导致落在当前线程上的那一次获取锁的操作失败，那么我干脆强制它不要使用当前线程。然而，这是一种非常简单粗暴的做法。

尽管这样做可以成功获取JDBC锁，但它也会导致一部分SQL游离在事务之外执行。
不仅仅是这个解决方法，**上述的三份代码也都会有这个问题**。

不太理想的解决方法的代码如下：
https://github.com/cuipengfei/Spikes/blob/master/jpa/lock-transaction-threads/src/main/java/com/github/spring/example/service/Problem2BadFixService.java

# 第二个问题的较优解决方法：缩小事务范围，避免将业务操作和获取JDBC锁的操作混合在同一个被@Transactional标注的方法内

**上述四份代码都存在一个共同的缺点**，即@Transactional注解的范围太广。

这容易导致JPA Transaction Manager范畴内与业务相关的SQL操作以及获取JDBC分布式锁的Data Source Transaction Manager范畴内的SQL操作混在一起。

当这两者混在一起时，很容易出现DataSourceTransactionManager试图去改变一个已经被open过的transaction的隔离级别的问题。

第二个问题的较优解决方法的代码请参考：
https://github.com/cuipengfei/Spikes/blob/master/jpa/lock-transaction-threads/src/main/java/com/github/spring/example/service/Problem2GoodFixService.java

# 用图来总结一下

```mermaid
flowchart TD
    
    style dstm fill:lightgreen,stroke:#333,stroke-width:4px
    style nrt fill:#FFCCCB,stroke:#333,stroke-width:4px

    jl[JDBC分布式锁]
    str[Serializable级别的事务]
    mt[多个线程]
    jtm[JPA Transaction Manager]
    dstm[❤️Data Source Transaction Manager❤️]
    nrt[🪳无法重试获取锁🪳]
    se[Serialization Error]
    se2[Serialization Error]

    subgraph 无法重试获取锁
    jl-->|默认使用|str
    mt-->|同时获取|jl
    jl-->|恰好用了|jtm
    str-->|容易撞车而导致|se
    jtm-->|hold不住|se
    se-->|从而导致|nrt
    jl-->|替换成使用|dstm
    dstm-->|可以hold住|se2
    se2-->|从而解决|nrt
    end
```

```mermaid
flowchart TD
    
    style nd fill:lightgreen,stroke:#333,stroke-width:4px
    style ile fill:#FFCCCB,stroke:#333,stroke-width:4px

    st[同一个线程中]
    t[绑在当前线程上的事务]
    t2[事务]
    tm[一个范围很宽的标注了@Transactional的方法]
    js[JPA的SQL操作]
    tl[获取JDBC分布式锁]
    js2[JPA的SQL操作]
    tl2[获取JDBC分布式锁]
    ps[Parallel Stream]
    op[其自己线程池中的线程]
    ct[当前线程]
    ile[🪳无法更改事务隔离级别的问题🪳]
    nd[❤️正确做法应该是缩小@Transactional的范围❤️]

    subgraph 事务隔离级别变化的问题
    ps-->|并不总是仅利用|op
    ps-->|也会利用|ct
    ct-->|那么就会在|st
    st-->|跑|tm
    tm-->|先执行了一些|js
    tm-->|然后再尝试|tl
    js-->|已经open了|t
    tl-->|再去试图更改其隔离等级|t
    t-->|从而导致|ile

    nd-->|可以及时关闭|t2
    t2-->|避免把二者混在一起|js2
    t2-->|避免把二者混在一起|tl2
    js2-->|从而避免|ile
    tl2-->|从而避免|ile
    end
```

# 补充

## Redis

上面的问题都是由于业务代码和获取锁的代码二者同时依赖于同一个数据库。

而Spring Integration的分布式锁除了可以使用JDBC，其实也可以使用Redis或其他底层技术。
如果把上述代码中的JdbcLockRegistry全部替换为RedisLockRegistry，而保持其它代码不变，所有错误都会消失，不会再重现。

因为无论用到了哪一个线程，哪一个DB Transaction，也无论@Transactional标记的宽或者窄，Redis总是不会和JDBC/DB撞车的。

可以通过修改上述代码中的此处来试用Redis： 
https://github.com/cuipengfei/Spikes/blob/c887a6f802bbfffc45ee29cbb91dac731243b7cd/jpa/lock-transaction-threads/src/main/resources/application.properties#L17-L18

## Spring Boot 3

如果升级到Spring Boot 3.1.5 + JDK 17，则Spring Integration JDBC会升到6.1.4(上述代码用的是5.x)，甚至不用替换成Data Source Transaction Manager，上述问题也会消失。

因为这一版本的Spring Integration JDBC的分布式锁实现在acquire lock时不再使用serializable的事务，而是改成了read committed。

这样，自然就规避了第一个问题，不再有serializable事务撞车。

而由于不再需要给锁使用Data Source Transaction Manager，自然也就解决了第二个问题，不再有同一个线程上两个transaction managers打架的问题。