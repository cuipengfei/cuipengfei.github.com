---
layout: post
title: "利用CouchBase为弱网环境构建云同步Android应用"
date: 2016-03-10 10:26
comments: true
tags:
---

#背景

Wifi，4G，3G，这些我们习以为常的东西，未必对所有人来说都是随时可用的。

以我当前所在项目为例，应用场景是某欠发达地区医疗服务机构的药品库存管理。

所谓欠发达，具体怎样呢？

* 没有台式电脑
* 没有笔记本
* 只能使用低端的安卓平板
* 4G，3G信号不要想
* 我们去过现场的一位同事甚至要爬到树上去，才能勉强收到2G信号
![tree](http://img2.cache.netease.com/cnews/2009/2/2/20090202093425b68f3.jpg)
* 即便是2G信号，也是时断时续，非常不稳定

因此，需要随时保持连通的BS结构基本不可行，我们选择了重度依赖移动端设备本地存储的CS结构（胖客户端）。

网络不可用时，库存变动存储在安卓本地，何时网络可用，再将数据与服务器同步。

#问题

以上描述的解决方案似乎合情合理，但是真实实施中还是遇到不少问题：

* 本地schema与服务器schema不一致，中间涉及数据转换与回转
* 本地到服务器的同步数据流动链条过长（本地orm->本地Json serialization->服务器Json deserialization->服务器orm），链条中任何一环都有出差池的可能性。
换句话说，导致数据健全性受损的可能性分散在了太多的点上，一旦出错，难以定位
* 服务器到本地的数据同步，上一条中所描述的链条的逆向，同样是链条太长，潜在的出现错误的点太多
* 服务器端所掌握的数据只是客户端真实数据的一个变体，并且还未必是最新的，这样就导致当移动端应用因其本地数据而出错的时候，我们只能对着服务器干着急

以上描述的问题并不是偶发性的，它不像这里有个bug今天修了，明天那里有个bug再修一次就好。

只要我们仍然要在弱网环境中运行应用，我们就需要重度依赖本地存储，就需要持续的在移动端和服务端进行双向数据同步，以上的问题就将会一直存在。

这是自然环境限制与技术选择所带来的固有的内在的问题。

#解决方案

上面提到：

> 这是自然环境限制与技术选择所带来的固有的内在的问题。

这句话再解释明白一些，自然环境限制指的是很差的网络可用性，技术选择指的是服务器端提供REST API，移动端利用该API进行通信。

以上这二者相结合导致了上述情况成为了固有的内在的问题。

自然环境的限制我们无法突破，我们不能把基站部署过去，让大家打电话之前不用再爬到树上去。

但是技术选择是完全受我们控制的，是有做文章的空间的。

这就引出了文章标题提到的CouchBase。

#CouchBase

关于CouchBase是一个怎样的DB，请大家自行搜索。

我们主要关注它推出的CouchBase-Lite（android和iOS均有对应版本）。

![replicate](https://camo.githubusercontent.com/c1aa705fde3eb12245c06730d850c23e5a84ad8d/687474703a2f2f746c657964656e2d6d6973632e73332e616d617a6f6e6177732e636f6d2f636f756368626173652d6c6974652f636f756368626173652d6c6974652d6172636869746563747572652e706e67)

左边的绿色方框是移动端应用，它通过蓝色标示的Sync Gateway与CouchBase Server通信。

请注意图中的箭头都是双向的，任何一方对本地数据库的写操作，都会导致对方的更新。任何一方的网络暂时中断也没有关系，在网络恢复的时候将会自动重试。

这样一来，数据同步的思路就变了，不再是在服务器端定义上传下载的API，移动端进行调用。而是利用DB自有的replication机制进行数据同步。

这就意味着我们在移动端只需要关注建立领域特定的模型，并将其存储入移动端本地的CouchBase即可，至于后面的序列化、网络通信等等问题就不需要我们去担心了。

关注点中很大一部分就这样被分离了出去，交由infrastructure去完成。

至于DB自有的replication机制的可靠性，应该可以比较安全的做出假设，认为一个有商用场景的DB厂商的通用数据备份机制不会比我们自己拼凑出来的更差。

#一个原型

[https://github.com/cuipengfei/Spikes/tree/master/android/sync-prototype](https://github.com/cuipengfei/Spikes/tree/master/android/sync-prototype)

上面的链接是一个基本可用的购物清单应用。全部代码都在，供参考。

下面谈如何把玩它。

##第一步

下载CouchBase Server： [http://www.couchbase.com/nosql-databases/downloads#](http://www.couchbase.com/nosql-databases/downloads#)，安装，配置管理员账户，不赘述。

在CouchBase Server的Admin console(默认地址： http://127.0.0.1:8091/index.html)中创建一个bucket，命名为demodb。

##第二步

安装sync_gateway，Mac用户可以：
	brew install sync_gateway

以上github代码克隆下来后，sync-gateway路径下有个名为start_sync_gateway_server.sh的脚本，运行它来启动sync gateway。

##第三步

运行同一个路径下的create_user.sh，来创建一个名为user1的用户，然后运行create_session.sh，为该用户创建一个session。

create_session.sh脚本有类似如下的输出：

	{"session_id":"a469f18027647e4957ffd1743e2ea33ce0386dbc","expires":"2016-02-21T17:51:43.071175586+08:00","cookie_name":"SyncGatewaySession"}

把其中的session id记下备用。

（注：这里的用户和session都是sync gateway需要的，与CouchBase Server无直接关系）

## 第四步

找到代码中的MainActivity类，在startSync方法中加入session id：

```java
//......
Replication pullReplication = database.createPullReplication(syncUrl);
pullReplication.setCookie("SyncGatewaySession", "a469f18027647e4957ffd1743e2ea33ce0386dbc", null, 86400000000000L, false, false);
pullReplication.setChannels(asList("user1"));
pullReplication.setContinuous(true);

Replication pushReplication = database.createPushReplication(syncUrl);
pushReplication.setCookie("SyncGatewaySession", "a469f18027647e4957ffd1743e2ea33ce0386dbc", null, 86400000000000L, false, false);
pushReplication.setChannels(asList("user1"));
pushReplication.setContinuous(true);
```

这段代码负责启动replication，双向同步从此而起。

找到createGroceryItem，为其中创建document的代码指定其所归属的用户：

```java
//......
Document document = database.createDocument();

Map<String, Object> properties = new HashMap<String, Object>();
properties.put("channels", asList("user1"));
```

这几行代码可以保证各个移动端用户之间的数据不会混杂在一起。

##第五步

在genymotion中启动android虚拟机（如果使用其他虚拟设备或者真机，请注意修改代码中的服务器ip地址）。

在购物清单中创建几条记录，然后清空移动端本机数据，重启应用，可以看到刚刚被清空的购物清单会从服务器上同步回来。

也可以尝试把虚拟机的网络连接断掉，创建或者修改几条记录，稍后重新连通网络，可以发现数据仍然可以上传到服务器。

还可以尝试用第三步中提到的脚本多创建几个用户，在不同的android虚拟机中使用不同用户，可以发现它们对彼此的数据是没有访问权的。

#总结

以上第五步提到的双向同步，离线操作，不同用户之间的数据隔离，都不需要我们写任何特殊的代码来实现。

我们移动端的代码与CouchBase的集成基本就只涉及到第四步中提到的启动replication和创建document，那这样移动端剩下的工作就只有构建业务逻辑了。

如果你的移动端应用也需要在弱网环境下进行离线操作，在网络恢复时与服务器同步数据的话，不妨尝试一下CouchBase。