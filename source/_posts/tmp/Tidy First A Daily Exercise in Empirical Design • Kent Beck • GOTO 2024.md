# Tidy First? A Daily Exercise in Empirical Design
# 先整理？经验设计的日常练习
## Kent Beck • GOTO 2024

---

## Introduction / 引言

Thank you so much for that introduction. I'm not just a signatory of the Agile Manifesto - I am the first signatory alphabetically.

非常感谢您的介绍，我不仅仅是敏捷宣言的签署者，我是按字母顺序排列的第一个签署者。

What a pleasure it's been to be here so far. I've been having great conversations with people, learning lots from different people's perspectives, and it's great to be among a group of geeks. I tend to talk to more business people these days, and it feels great to be home where I can talk a little bit about some math and don't need to worry about scaring anybody.

到目前为止能在这里真是荣幸，我一直在与人们进行很好的交流，从不同人的观点中学习很多，能和一群极客在一起真是太好了。我最近更多地与商业人士交谈，在这里感觉很棒，我可以稍微谈一些数学，我不需要担心吓到任何人。

---

## The Three Billion Seconds / 三十亿秒

In the Bible, it talks about the lifespan of a person being three score years and 10 - 70 years. But with medical advances, we've extended the lifespan, so I like to think of it in terms of three billion seconds. Three billion seconds is about 92 years. Two-thirds of mine are gone; for most of you, one-third of yours is gone.

在圣经中谈到一个人的寿命是七十岁，但随着医学的进步，我们当然延长了寿命，所以我喜欢用三十亿秒来计算，我们都是三十亿秒大约是92年，我的三分之二已经过去了，对你们大多数人来说三分之一你的时间已经过去了。

But there's something that's driven my whole career: I hate wasting time. Each second is a precious opportunity, and giving it up to activities that create no value, that aren't enjoyable, is an opportunity never to be returned.

但有一件事一直驱动着我的整个职业生涯，那就是我讨厌浪费时间，每一秒都是宝贵的机会，把它放弃给那些不创造价值的活动，那些不愉快的价值，是一个永远不会回来的机会。

---

## The Motivation Behind Extreme Programming / 极限编程背后的动机

Early in my career, I saw software development activities that were very clearly a waste of time. When I tell the kids these days about documentation measured in meters, they don't believe me, but there really were meters of documentation on a shelf that were then never looked at again.

在我职业生涯早期，我看到了软件开发活动，那些非常非常明确是浪费时间。很久以前，当我告诉现在的孩子们关于文档测量在米时，他们不相信我，但确实有米长的文档在架子上，然后就再也没有看过了。

When you optimize, you don't maximize - there's always some tradeoff. We can't try to waste zero seconds, but we sure can try and waste fewer of them. That was the motivation behind extreme programming: what if we stripped software development back to its essence and did the stuff that we absolutely knew absolutely mattered, and did as little of the other stuff as possible?

当你优化时不要最大化，总是有一些权衡，所以我们不能试图浪费零秒，但我们当然可以尝试浪费更少的秒数。这就是极限编程背后的动机：如果我们把软件开发剥离到其本质，我们做那些我们绝对知道绝对重要的东西，我们尽可能少地做其他的事情？

---

## The Journey to Structured Design / 结构化设计的历程

In 2005, I got to meet two of my heroes at OOPSLA celebrating the 25th anniversary of the publication of the book "Structured Design." This green book had been my college textbook, and I got to meet the authors Larry Constantine and Ed Jordan.

在2005年我遇到了其中两个我的英雄，我当时在oopsa的一个小组讨论会上庆祝25周年结构化设计这本书的出版，结构化设计是一本绿色的书，我把它作为大学教材，我得以见到作者们拉里·康斯坦丁和埃德·乔丹。

These two guys back in the early days of computing had laid out the equivalent of Newton's Laws of Motion for software development - here's why software development costs so much and here's what you can do about it. The examples were dated, but the principles were absolutely dead on.

这两个人在计算的早期就制定了相当于软件开发的牛顿运动定律，他们制定了这里为什么软件开发如此昂贵，以及你能对此做些什么。但这些例子过时了，至少可以说，原则是绝对正确的。

---

## Tidy First: The Book / 《整洁第一》：这本书

I published a book last year called "Tidy First?" (note: don't put punctuation in book titles - it's a logistical pain).

我去年出版了一本书，叫做《整洁第一？》（注意：不要在书名中放标点符号，这是一个后勤问题）。

The book boils down to this moment that every programmer encounters 20 times a day: there's some code I need to change, it's a mess - do I tidy first? That is a software design moment - the smallest quantum of software design that you can do. Sometimes yes, sometimes no, probably yes, but don't get carried away.

这本书将其简化为这个时刻，每个程序员都会遇到这个时刻一天20次：有一些代码我需要改变它，它一团糟，我先整理一下？那是一个软件设计时刻，它是最小的软件设计量子，你能做的。有时是，有时不是，可能是是，但不要被冲昏头脑。

---

## The Trilogy / 三部曲

I'm working on a trilogy:

我正在写一个三部曲：

1. **Tidy First** - about your relationship with yourself as a programmer / **整洁第一** - 关于你与自己的关系，你是一个程序员
2. **Tidy Together** - about teams of programmers working together (interpersonal relationships) / **一起整洁** - 关于程序员团队一起工作的（人际关系）
3. **Third part** - about the relationship between technology and business (architectural level) / **第三部分** - 关于技术与商业的关系（架构层面）

---

## Software Development at the Highest Level / 软件开发在最高层面

Software development looks like this at the highest level:
1. Somebody has an idea for how the software is going to change
2. We implement some feature
3. The magic of software: the more features we implement, the more ideas we get

这就是这就是软件开发在最高层面上的样子：
1. 有人对软件将如何改变有一个想法
2. 然后我们实现一些功能
3. 软件魔法的一部分：我们实现的功能越多，我们得到的想法就越多

But there's another layer under the water: we don't just implement features, we implement features in the presence of some structure of the system, and the structure profoundly affects how expensive those features will be to implement.

但在水下还有另一层：我们不仅仅实现功能，我们在一些系统结构的存在下实现功能，系统的结构深刻影响着这些功能实现的费用。

---

## The Conflict / 冲突

Features are very visible to everybody who's affected by the software. The structure is only visible to the programmer who's walking the path - nobody else can see that.

功能可见于受软件影响的每个人，结构只是可见于走在路径上的程序员，其他人都看不到。

The intuition I hear from programmers is: we're not investing anywhere near enough in the structure of the system (technical debt). Part of the reason is that structure is only indirectly observable.

我从程序员那里反复听到的直觉是我们没有在系统结构上的投资远远不够（技术债务）。部分原因是结构只是间接地可观察的。

---

## Empirical Software Design / 实证软件设计

The goal of empirical software design is to bring balance to the investment in features and in structure.

实证软件或设计的目标是为功能和结构的投资带来平衡。

---

## The Gusto Example / Gusto案例

I worked at a payroll company called Gusto for three years. We accidentally implemented two payroll calculators. Payroll calculation in the US is really expensive and complicated (8,700 tax authorities), and we had two copies of it.

我在一家叫做Gusto的薪资公司工作了三年。我们意外地实现了两个薪资计算器。现在薪资计算在美国真的昂贵复杂（美国有8700个税务机关），我们有两个副本。

Eventually we unified the two payroll calculators. Then somebody said: "Since now it's easy to call the payroll calculator, let's embed it in our homepage as part of our marketing." It became a very popular feature. We would never have implemented this marketing feature if it hadn't been cheap and if somebody hadn't known that using a payroll calculator was cheap today (where it was expensive 3 months ago).

最终我们统一了两个薪资计算器。然后有人说："哦，你知道吗，既然现在很容易调用薪资计算器，让我们把薪资计算器嵌入到我们的主页中作为我们的营销。"它成为了一个非常受欢迎的功能。如果它不便宜，如果有人不知道使用薪资计算器今天很便宜（而在那里它三个月前很昂贵），我们永远不会实现这个营销功能。

---

## The Jackpot Payoffs / 头奖回报

I want to work a little bit different for the chance for those jackpot payoffs. When I say jackpot payoffs, it might be money, it might be satisfaction, it might be societal impact.

我在这里追求的，我不希望事情只是一点点，我不希望为了得到一点点更多价值而稍微努力一点，我想要稍微不同地工作，为了那些头奖回报。当我说头奖回报时，它可能不是金钱，可能是满足感，可能是社会的影响。

---

## Constraints on Software Design / 软件设计的约束

### 1. Social Constraints / 社会约束

We have to get along, we have to build and maintain trust, we have to repair relationships when they've been damaged.

我们必须相处，我们必须建立和维护信任，当关系被破坏时我们必须修复它们。

### 2. Incentives / 激励

Everybody's incentives are not the same. There are people who just love designing software and don't particularly like designing features, and vice versa. Neither of those people is an idiot - there are different perspectives.

每个人的激励是不同的。有些人只是喜欢设计软件，而不太喜欢设计功能，反之亦然。那些人不是白痴，有不同的观点。

### 3. Social Fabric Changes / 社会结构变化

The social fabric is going to change over time: juniors don't stay juniors forever, seniors don't stay seniors forever, people come, people go, markets change, technology changes.

社会结构会随着时间改变：初级不会永远是初级，高级不会说高级永远，人们来来去去，市场变化，技术变化。

---

## Medium Constraints / 媒介约束

Software is different than other mediums for engineering. What we do is genuinely engineering, but the medium we work with is different.

软件与其他工程媒介不同。我们所做的确实是工程，但我们工作的媒介是不同的。

### Cost Structure / 成本结构

**Constantine's equivalence**: The cost of software is mostly in the cost of changing the software, not in writing it first.

**康斯坦丁等价**：软件的成本主要在于改变软件的成本，而不是首先编写它。

---

## Economic Constraints / 经济约束

### 1. Survival / 生存
Software starts in a default failure mode. Only when you cross the threshold of sustainability does the default become "the software is going to last quite a while."

软件开始于一个默认的失败模式。只有当你跨越可持续性的阈值时，默认才变成"软件将持续相当长时间"。

### 2. Net Present Value / 净现值
Time is money. Earning sooner is exactly the same as earning more. Spending later is exactly economically equivalent to spending less.

时间就是金钱。更早赚到钱正是与赚更多钱相同。以后花钱正是经济上等同于花更少的钱。

### 3. Optionality / 选择性
What can I do with this software in the future? The more changes you can make in more different directions, the more valuable the work you've done is.

我能在未来用这个软件做什么？你能在更多不同的方向上做出的变化越多，你所做的工作就越有价值。

---

## Implications / 推论

### 1. Grow Like a Tree / 像树一样成长
Most of design is going to be differentiation - make a thing, split it into parts, expand parts, eventually split into more parts.

大部分设计将是分化 - 做一个东西，把它分成部分，扩展部分，最终分裂成更多部分。

### 2. Design is an Activity / 设计是一个活动
Design is not a phase - it's an activity that takes place periodically as we go along. This requires interlacing feature and design back and forth.

设计不是一个阶段，它是一个在我们进行时定期发生的活动。这需要功能和设计来回交织。

### 3. Interruptible Design / 可中断的设计
The key technical skill is working in parallels - having old and new designs exist together for a time, making design activities always interruptible.

关键技术技能是并行工作 - 让旧设计和新设计共存一段时间，使设计活动总是可中断的。

---

## Leadership Insight / 领导力见解

If you want to lead a software project and get the benefits I'm talking about - relationships that strengthen over time, features that improve over time, structure that improves over time - you're never going to get that with pressure.

如果你想领导一个软件项目，你想要我谈论的那种好处 - 随着时间推移而加强的关系，随着时间推移而改进的功能，随着时间推移而改进的结构 - 你永远不会通过压力得到这些。

"Could you just stay a couple extra hours? Could you work a little harder?" is never going to achieve that because software design is a creative activity. Creative activities need space but they also need impetus. Leading with purpose instead of with pressure creates that combination.

"只是多待几个小时，你能更努力工作吗？"永远不会实现这一点，因为软件设计是一种创造性的活动。创造性的活动需要空间，但它们也需要动力。以目标为导向而不是以压力为导向创造了这种组合。

---

## Conclusion / 结论

This remarkable medium that we get to work in has the potential for our own satisfaction, the satisfaction of the users whose lives we affect with the decisions we make every day, investors, and eventually society as a whole.

这种我们工作的非凡媒介具有我们自身满足感的潜力，我们通过每天做出的决策影响其生活的用户的满足感，投资者，最终作为社会的整体的潜力。

Thank you very much. / 非常感谢。