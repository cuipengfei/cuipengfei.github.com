---
layout: post
title: 命令模式的不爽就像用指甲刀刮胡子
date: '2015-06-01 21:51'
comments: true
tags:
- Scala
- OODP
---

# 命令模式
> 在面向对象程式设计的范畴中，命令模式是一种设计模式，它尝试以物件来代表实际行动。命令物件可以把行动(action) 及其参数封装起来，于是这些行动可以被：

> * 重复多次
> * 取消（如果该物件有实作的话）
> * 取消后又再重做

以上是wiki对命令模式的定义（术语像是台湾的）。

下面是来自《Head first design patterns》的一个例子：

假设你有很多家用电器：电灯泡，电视，音响，还有一个水疗浴缸。（就是没有手电筒）

每个家用电器都有自己的开关装置，处于不同的位置。如果你想把它们都开启，需要一个一个地去按按钮。

现在你想要有一个遥控器，一键开启所有电器，一键关闭所有电器。

或者是一键完成任意的电器操作组合。

每个电器的接口都是不同的，但是又需要和同一个遥控器集成，于是呢，肯定要有一个统一的接口了。

于是就有了下面命令模式的实现代码。

# Java

<script src="https://emgithub.com/embed.js?target=https%3A%2F%2Fgithub.com%2Fcuipengfei%2FBlogCode%2Fblob%2Fmaster%2FOODPFP%2Fsrc%2Fmain%2Fjava%2Fcommand%2Fappliances%2FLight.java&style=hybrid&showBorder=on&showFileMeta=on&showCopy=on"></script>

<script src="https://emgithub.com/embed.js?target=https%3A%2F%2Fgithub.com%2Fcuipengfei%2FBlogCode%2Fblob%2Fmaster%2FOODPFP%2Fsrc%2Fmain%2Fjava%2Fcommand%2Fappliances%2FTV.java&style=hybrid&showBorder=on&showFileMeta=on&showCopy=on"></script>

<script src="https://emgithub.com/embed.js?target=https%3A%2F%2Fgithub.com%2Fcuipengfei%2FBlogCode%2Fblob%2Fmaster%2FOODPFP%2Fsrc%2Fmain%2Fjava%2Fcommand%2Fappliances%2FStereo.java&style=hybrid&showBorder=on&showFileMeta=on&showCopy=on"></script>

<script src="https://emgithub.com/embed.js?target=https%3A%2F%2Fgithub.com%2Fcuipengfei%2FBlogCode%2Fblob%2Fmaster%2FOODPFP%2Fsrc%2Fmain%2Fjava%2Fcommand%2Fappliances%2FHottub.java&style=hybrid&showBorder=on&showFileMeta=on&showCopy=on"></script>

首先是有四大件家用电器。各自之间没有什么关系。

这里面的代码都有点傻，不过没关系，我们就想象这都是些很复杂的硬件通信之类的代码就好了。

<script src="https://emgithub.com/embed.js?target=https%3A%2F%2Fgithub.com%2Fcuipengfei%2FBlogCode%2Fblob%2Fmaster%2FOODPFP%2Fsrc%2Fmain%2Fjava%2Fcommand%2FCommand.java&style=hybrid&showBorder=on&showFileMeta=on&showCopy=on"></script>

然后，定义一个Command接口，其中只有一个execute()方法。

之后我们会用它的实现类来操作各种电器。

<script src="https://emgithub.com/embed.js?target=https%3A%2F%2Fgithub.com%2Fcuipengfei%2FBlogCode%2Fblob%2Fmaster%2FOODPFP%2Fsrc%2Fmain%2Fjava%2Fcommand%2Fcommands%2FLightOnCommand.java&style=hybrid&showBorder=on&showFileMeta=on&showCopy=on"></script>

<script src="https://emgithub.com/embed.js?target=https%3A%2F%2Fgithub.com%2Fcuipengfei%2FBlogCode%2Fblob%2Fmaster%2FOODPFP%2Fsrc%2Fmain%2Fjava%2Fcommand%2Fcommands%2FLightOffCommand.java&style=hybrid&showBorder=on&showFileMeta=on&showCopy=on"></script>

<script src="https://emgithub.com/embed.js?target=https%3A%2F%2Fgithub.com%2Fcuipengfei%2FBlogCode%2Fblob%2Fmaster%2FOODPFP%2Fsrc%2Fmain%2Fjava%2Fcommand%2Fcommands%2FTVOnCommand.java&style=hybrid&showBorder=on&showFileMeta=on&showCopy=on"></script>

<script src="https://emgithub.com/embed.js?target=https%3A%2F%2Fgithub.com%2Fcuipengfei%2FBlogCode%2Fblob%2Fmaster%2FOODPFP%2Fsrc%2Fmain%2Fjava%2Fcommand%2Fcommands%2FTVOffCommand.java&style=hybrid&showBorder=on&showFileMeta=on&showCopy=on"></script>

<script src="https://emgithub.com/embed.js?target=https%3A%2F%2Fgithub.com%2Fcuipengfei%2FBlogCode%2Fblob%2Fmaster%2FOODPFP%2Fsrc%2Fmain%2Fjava%2Fcommand%2Fcommands%2FStereoOnCommand.java&style=hybrid&showBorder=on&showFileMeta=on&showCopy=on"></script>

<script src="https://emgithub.com/embed.js?target=https%3A%2F%2Fgithub.com%2Fcuipengfei%2FBlogCode%2Fblob%2Fmaster%2FOODPFP%2Fsrc%2Fmain%2Fjava%2Fcommand%2Fcommands%2FStereoOffCommand.java&style=hybrid&showBorder=on&showFileMeta=on&showCopy=on"></script>

<script src="https://emgithub.com/embed.js?target=https%3A%2F%2Fgithub.com%2Fcuipengfei%2FBlogCode%2Fblob%2Fmaster%2FOODPFP%2Fsrc%2Fmain%2Fjava%2Fcommand%2Fcommands%2FHottubOnCommand.java&style=hybrid&showBorder=on&showFileMeta=on&showCopy=on"></script>

<script src="https://emgithub.com/embed.js?target=https%3A%2F%2Fgithub.com%2Fcuipengfei%2FBlogCode%2Fblob%2Fmaster%2FOODPFP%2Fsrc%2Fmain%2Fjava%2Fcommand%2Fcommands%2FHottubOffCommand.java&style=hybrid&showBorder=on&showFileMeta=on&showCopy=on"></script>

这一大坨，就是Command的实现了。

四大件电器，于是便有八个Command，分别负责每个电器的开启和关闭。

有些电器的开启和关闭比别的要复杂一些，不过这没有关系，因为它们的细节都被封装在Command的实现类里面了，我们接下来的代码只要和Command这个接口打交道就好了。
<script src="https://emgithub.com/embed.js?target=https%3A%2F%2Fgithub.com%2Fcuipengfei%2FBlogCode%2Fblob%2Fmaster%2FOODPFP%2Fsrc%2Fmain%2Fjava%2Fcommand%2FMacroCommand.java&style=hybrid&showBorder=on&showFileMeta=on&showCopy=on"></script>

还有一个宏命令，用来组合其他命令。
<script src="https://emgithub.com/embed.js?target=https%3A%2F%2Fgithub.com%2Fcuipengfei%2FBlogCode%2Fblob%2Fmaster%2FOODPFP%2Fsrc%2Fmain%2Fjava%2Fcommand%2Frunner%2FRemoteControl.java&style=hybrid&showBorder=on&showFileMeta=on&showCopy=on"></script>

可以实现遥控器了。

![http://elisabethrobson.com/wp-content/uploads/2014/07/Command.jpg](http://elisabethrobson.com/wp-content/uploads/2014/07/Command.jpg)

这个遥控器上的按钮都是空白的，我们可以给它置入任意我们想要的命令。

<script src="https://emgithub.com/embed.js?target=https%3A%2F%2Fgithub.com%2Fcuipengfei%2FBlogCode%2Fblob%2Fmaster%2FOODPFP%2Fsrc%2Fmain%2Fjava%2Fcommand%2Frunner%2FRemoteLoader.java&style=hybrid&showBorder=on&showFileMeta=on&showCopy=on"></script>

终于可以写一个main函数了：

* 把家用电器和其对应的Command联系起来
* 把各种Command组合成开启和关闭两个宏命令
* 把宏命令置入遥控器

然后，只要按一个按钮，就可以开启所有电器，享受资产阶级奢靡的生活了。

享受够了之后只要再按一个按钮就可以把所有电器关闭掉。

如果再有别的电器，只需要实现几个新的Command，把新的Command组合入宏命令，继续使用遥控器就好了。

换句话说，因为遥控器和电器之间通过Command解耦了，增加新的电器和新的Command对于遥控器没有影响，遥控器的代码是稳定的。这也就是所谓的对扩展开放，对修改关闭。

很好，很符合良好的设计原则，看着就舒服对吧？

##  不过再想一下

电灯的开启和关闭这两个命令仅仅是对电灯的两个方法的简单代理。

音响的开启和关闭这两个命令仅仅是对音响的两个方法的简单代理。

电视机的关闭也是简单的代理。

这些命令类是否看起来太单薄了呢？它们的方法异常瘦弱，营养不良。

它们除了持有一个需要操作的电器的实例之外，基本没有什么实例级状态。

（电视开机还好，由于需要选择频道，好歹调用了两个方法。
水疗浴缸操作比较复杂，需要调节温度，所以也还稍微好一些。）

每次看到这种贫血的类，我就怀疑它们存在的必要性。

如果我们只是想要给家用电器内的方法构造一个统一个的对外接口，是不是可以用函数式来实现呢？

# functions

来试试用Scala实现：
<script src="https://emgithub.com/embed.js?target=https%3A%2F%2Fgithub.com%2Fcuipengfei%2FBlogCode%2Fblob%2Fmaster%2FOODPFP%2Fsrc%2Fmain%2Fscala%2FcommandFP%2Fappliances%2FLight.scala&style=hybrid&showBorder=on&showFileMeta=on&showCopy=on"></script>

<script src="https://emgithub.com/embed.js?target=https%3A%2F%2Fgithub.com%2Fcuipengfei%2FBlogCode%2Fblob%2Fmaster%2FOODPFP%2Fsrc%2Fmain%2Fscala%2FcommandFP%2Fappliances%2FTV.scala&style=hybrid&showBorder=on&showFileMeta=on&showCopy=on"></script>

<script src="https://emgithub.com/embed.js?target=https%3A%2F%2Fgithub.com%2Fcuipengfei%2FBlogCode%2Fblob%2Fmaster%2FOODPFP%2Fsrc%2Fmain%2Fscala%2FcommandFP%2Fappliances%2FStereo.scala&style=hybrid&showBorder=on&showFileMeta=on&showCopy=on"></script>

<script src="https://emgithub.com/embed.js?target=https%3A%2F%2Fgithub.com%2Fcuipengfei%2FBlogCode%2Fblob%2Fmaster%2FOODPFP%2Fsrc%2Fmain%2Fscala%2FcommandFP%2Fappliances%2FHottub.scala&style=hybrid&showBorder=on&showFileMeta=on&showCopy=on"></script>

首先是有四大件家用电器，这部分和Java的代码等价。

<script src="https://emgithub.com/embed.js?target=https%3A%2F%2Fgithub.com%2Fcuipengfei%2FBlogCode%2Fblob%2Fmaster%2FOODPFP%2Fsrc%2Fmain%2Fscala%2FcommandFP%2FCommands.scala&style=hybrid&showBorder=on&showFileMeta=on&showCopy=on"></script>

这一段用来定义各种命令的代码就不同了。

我们对家用电器的各种方法的调用都是只期待其副作用，不期待任何返回值的。所以可以定义一个函数签名Command来涵盖所有这类操作。

和上面的Java代码类似，这里也有一个宏命令，只不过实现简单一些。

电视的开启，水疗浴缸的开和关都有对应的方法来把家用电器的实例封入闭包中。

咦？电灯的开关，音响的开关，以及电视的关闭都跑哪儿去了呢？

由于这几个操作都只涉及到一个方法的调用，它们直接就符合Command的函数签名，所以不用再封入任何闭包了。这一点看下面的代码就明白了。

<script src="https://emgithub.com/embed.js?target=https%3A%2F%2Fgithub.com%2Fcuipengfei%2FBlogCode%2Fblob%2Fmaster%2FOODPFP%2Fsrc%2Fmain%2Fscala%2FcommandFP%2FRemoteControl.scala&style=hybrid&showBorder=on&showFileMeta=on&showCopy=on"></script>

<script src="https://emgithub.com/embed.js?target=https%3A%2F%2Fgithub.com%2Fcuipengfei%2FBlogCode%2Fblob%2Fmaster%2FOODPFP%2Fsrc%2Fmain%2Fscala%2FcommandFP%2FRemoteLoader.scala&style=hybrid&showBorder=on&showFileMeta=on&showCopy=on"></script>

我们可以定义一个遥控器。其中有开启，和关闭两排按钮。

最后，可以写一个main函数，其中所做的事情和之前Java代码main函数所做的事情是一样的。

只不过，不需要创建各种Command的实例。

而且light.on，stereo.on，light.off，stereo.off，tv.off这几个方法由于符合Command的签名，是可以直接拿来当Command用的。（注意方法名后面没有()，不是调用，而是函数传递）

前后两版代码是等价的。只不过：

* 247行代码变成了93行代码
* 16个实体变成了7个

作为一个多按几个按钮都嫌麻烦的好逸恶劳的资产阶级，这个结果是我所乐于见到的。

更少，更紧凑的代码。更少的实体。我终于可以用更小的成本来享受我昂贵的家用电器了。

# 指甲刀刮胡子

最后回到标题上去：指甲刀刮胡子，意即用不合适的工具解决问题。

命令模式想要做到的事情其实就是给各种不同的操作寻找一个统一的接口，从而实现调用者（遥控器）和被调用者（家用电器）之间的解耦。

给不同的操作寻找一个统一的接口这件事可以通过接口来做，但是我们同时要承担写一堆贫血类的代价。

而如果直接用函数来做的话，则可以得到更紧凑简洁的代码（就像object Commands这个实体内的代码一样）。

该模式提出的时候FP并不如今日盛行，其作者选用了可能会导致贫血类泛滥的解决方案，这无可厚非。传播了解耦和开闭等良好设计的原则也实为功德。

不过今天我们有了剃须刀，就无需一定要用指甲刀来刮胡子了。
