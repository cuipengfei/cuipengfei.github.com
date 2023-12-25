---
title: "Spring Integration JDBC分布式锁 - transaction与threads"
date: 2023-12-25 11:16:17
tags:
- 分布式
- 锁
- Java
---

# 第一个问题:在多个线程中跑隔离等级为serializable的Transaction

Spring Integration jdbc分布式锁在试图获取锁的过程中，需要一个serializable的transaction。

如果有多个线程同时试图我去获取锁，那这些transition之间可能会有顺序的问题。

具体来说是：

```
org.postgresql.util.PSQLException: ERROR: could not serialize access due to read/write dependencies among transactions
```

发生这样的问题其实也不可怕，因为jdbc lock会重试

但是由于某些异常类型的原因jdbc lock在使用jpa transaction manager的时候，无法在发生这种错误的情况下重试。

具体可以查看这个github issue: https://github.com/spring-projects/spring-integration/issues/3733

可以用这份代码来重现这个问题: https://github.com/cuipengfei/Spikes/blob/master/jpa/lock-transaction-threads/src/main/java/com/github/spring/example/service/Problem1Service.java

# 用data source transaction manager来workaround第一个问题

可以特意指定让jdbc lock不去使用jpa transaction manager，而是使用一个data source transaction manager来绕过这个问题。

具体代码： https://github.com/cuipengfei/Spikes/blob/master/jpa/lock-transaction-threads/src/main/java/com/github/spring/example/configs/CustomJDBCLockConfigs.java

workaround的效果可以执行这个代码来观察到： https://github.com/cuipengfei/Spikes/blob/master/jpa/lock-transaction-threads/src/main/java/com/github/spring/example/service/Problem1FixService.java

# 第二个问题：在同一个线程中先用jpa启动一个transaction，然后试图获取jdbc锁所导致的改变transaction isolation level的问题

这个问题的显著特征是：如果在一个方法上标注了transitional然后在这个方法内先执行了某些jpa的SQL操作，后再试图去获取jdbc分布式锁，就会出现无法改变transaction isolation level的问题。

这里的关键在于parallel stream并不总是只利用它自己线程池里面的线程，他也会利用当前线程。

而恰好落在当前线程上的那一次试图获取jdbc分布式锁的操作就会出现无法改变transaction isolation level的问题。

可以通过这代码来观察parallel stream的行为： https://github.com/cuipengfei/Spikes/blob/master/jpa/lock-transaction-threads/src/main/java/com/github/spring/example/TestParallelStreamThreads.java

可以用这个代码来重现该问题：https://github.com/cuipengfei/Spikes/blob/master/jpa/lock-transaction-threads/src/main/java/com/github/spring/example/service/Problem2Service.java

# 第二个问题的不完善解法：强制parallel stream不要使用当前线程

在这个解决问题的过程中，我先尝试了一个。事后发现很不明智的做法，在这里也记录一下。

我一开始的思路是既然parallel stream会利用当前县城，从而导致落在当前线程上的那一次获取锁的操作失败，那我干脆就强制它不要使用当前线程。当然，这是一个非常简单粗暴的做法。

这样做虽然可以成功的获取到Jdbc锁，但是他也会导致有一部分SQL游离在在transaction外执行。不只是这个做法，上面的三份代码也都会有这个问题。

个不太好的解法的代码如下：https://github.com/cuipengfei/Spikes/blob/master/jpa/lock-transaction-threads/src/main/java/com/github/spring/example/service/Problem2BadFixService.java

# 第二个问题的较优解法：缩小transaction的范围、避免把业务和获取jdbc锁的操作混在同一个标注了transactional的方法内

上面的四份代码都具有一些共同的缺点，那就是transitional annotation标注的范围太广。

这就容易把有jpa transaction manager范畴内的和业务相关的SQL，以及获取jdbc分布式锁的，有data source transaction manager范畴内的SQL混在一起。

而当这二者混在一起时，就很容易出现问题。

第二个问题的较优解法的代码: https://github.com/cuipengfei/Spikes/blob/master/jpa/lock-transaction-threads/src/main/java/com/github/spring/example/service/Problem2GoodFixService.java