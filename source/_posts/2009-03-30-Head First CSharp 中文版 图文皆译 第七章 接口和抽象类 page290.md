---
title: Head First C# 中文版 图文皆译 第七章 接口和抽象类 page290
date: 2009-03-30 14:02:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090330/2009-03-30_13-45-12.jpg) ![](https://p-blog.csdn.net/images/p_blog_csdn_
net/cuipengfei1/EntryImages/20090330/2009-03-30_13-27-07.jpg)

我们来创建一座房子吧！创建一个房子的模型，用类来表示房间和地点，每个有门的地方都要用一个接口。

①  我们来从这个类模型开始吧

房子里面的每一个房间、地点都用自己的对象表示。内部房间都继承Room，室外地点都继承OutSide，而Room和Outside又都继承Location。Lo
cation有两个字段Name是当前地点的名字，Exits是一个Location数组，保存着当前房间联通的所有房间。所以diningRoom.Name等于“
Dining Room”，DiningRoom.Exits等于数组{LivingRoom，Kitchen}。

\--> 创建一个窗体应用并把Location，Room，Outside这些类写进去。

②  你需要这张蓝图  ![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/Ent
ryImages/20090330/2009-03-30_13-52-54.jpg)

房子有三个房间，还有前院、后院、花园。还有两个门：前门连接前院和起居室，后门连接厨房和后院。

③  有户外门的房间要用IHasExteriorDoor接口

房子里又两个户外门，前门和后门。每个有户外门的地点都要实现IHasExteriorDoor接口。DoorDescruption只读属性包含对门的描述。Doo
rLocation字段含有一个指向门通向哪里的引用。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090330/2009-03-30_13-58-34.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)





