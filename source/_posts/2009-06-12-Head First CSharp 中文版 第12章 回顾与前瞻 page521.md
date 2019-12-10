---
title: Head First C# 中文版 第12章 回顾与前瞻 page521
date: 2009-06-12 15:59:00
tags: 我翻译的Head First C#（习作）
---
任何使用了  Point  类型的类文件中都需要添加  using System.Drawing  ；。

  

③创建构造方法

  

Flower  类的构造方法需要接受一个  Point  来指明花朵的位置，还要接受一个  Random
类的实例。你应该可以用这些变量来设置花朵的位置，把年龄设置为  0
，设置花朵为活着的，并且把其花粉量设置为花朵的初始花粉量。由于划分还没有被采集过，所以要把这个变量设置对。最后，算出花朵的寿命。下面一行代码可以帮助你：

  

lifeSpan = random.Next  （  LifeSpanMin  ，  LifeSpanMax + 1  ）；

  

④编写  HarvestNectar  （）方法的代码

  

每次调用这个方法的时候，它都会去检查每个生命周期中采集的花粉量是否大于剩余的花粉量。如果是的话，返回  0
。否则，就应该把在这个生命周期中采集的花粉量从剩余的花粉量中减去，并且返回收集了多少花粉。哦，还有别忘了把这个量加到  NectarHarvested
上去，它记录从一朵花上采集的花粉总量。

提示：我们在这个方法中只会用到  NectarGatheredPerTurn  ，  nectar  和  NectarHarvested  。

  

⑤编写  Go  （）方法的代码

  

这个方法促使花朵变老。假设每次调用这个方法的时候，一个生命周期就会逝去，所以要适当的更新花朵的年龄。你也需要看看年龄是否大于花朵的寿命。如果是的话，花朵死亡
。

  

假设花朵还是活着的，你需要给它添加花在每个生命周期中获得的花粉量。确保去检查花可以容纳的最大花粉量，不要超过那个值。

  

我们最后要把它做成有动画效果的，会有蜜蜂飞来飞去的图片。  Go  （）方法会每一帧被调用一次，而每秒钟会有若干帧。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

