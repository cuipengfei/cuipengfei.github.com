# Reflections on 15 years of Growing Software Guided by Tests
# 关于15年来测试驱动软件成长的反思

---

## About the experts / 关于专家

### Duncan McGregor / 邓肯·麦格雷戈
Professional software developer based near London, started programming on ZX81 at age 13. I've been lucky enough to ride a wave of demand for developers that started in the 1980s and has continued throughout my career.

专业软件开发人员，工作地点离伦敦足够近以维持生计。我13岁时在ZX81上开始编程。我很幸运地赶上了从1980年代开始并持续至今的开发者需求浪潮。

### Nat Pryce / 纳特·普赖斯
Co-Author of "Growing Object-Oriented Software" (GOOS)
《面向对象软件成长》一书合著者（GOOS）

---

## Introduction / 引言

**Duncan McGregor:** Hello and welcome to "GOTO Unscripted." I'm Duncan McGregor, a professional software developer based close enough to London to make a living. I started programming computers on a ZX81 when I was 13. I've been lucky enough to ride a wave of demand for developers that started in the 1980s and has continued throughout my career.

**邓肯·麦格雷戈：**大家好，欢迎来到"GOTO Unscripted"。我是邓肯·麦格雷戈，一名专业软件开发人员，工作地点离伦敦足够近以维持生计。我13岁时在ZX81上开始编程。我很幸运地赶上了从1980年代开始并持续至今的开发者需求浪潮。

I first met Nat Pryce in the early 2000s when we were both members of the Extreme Tuesday Club, a meet-up for developers practicing extreme programming, one of the precursors to agile development. Nat Pryce and I recently wrote a Kotlin book together, but up until that career highlight, he was best known for the book he wrote with Steve Freeman titled "Growing Object-Oriented Software, Guided by Tests," but colloquially known as GOOS. The book was published in 2009 in the Kent Beck Signature Series and was highly influential in the then relatively-infant agile movement. It is still at number 402,655 in Amazon rankings.

我在2000年代初第一次见到纳特·普赖斯，当时我们都是极限周二俱乐部的成员，这是一个为实践极限编程的开发者举办的聚会，是敏捷开发的前身之一。纳特·普赖斯和我最近合写了一本Kotlin书，但在此之前，他最出名的是与史蒂夫·弗里曼合著的《测试驱动的面向对象软件成长》，俗称GOOS。该书于2009年出版，属于Kent Beck签名系列，在当时相对稚嫩的敏捷运动中产生了重要影响。目前在亚马逊排名402,655位。

**Nat Pryce:** It's that good? / 有那么好吗？

**Duncan McGregor:** I don't know. / 我不知道。

**Nat Pryce:** It's a number. / 就是个数字。

---

## What is GOOS About? / GOOS是关于什么的？

**Nat Pryce:** That makes me feel old. It's a book about test-driven development and was written to encapsulate everything that we had in the Extreme Tuesday Club...so it wasn't just me and Steve inventing it. We were the six...let's talk about the Extreme Tuesday Club and then I can talk about GOOS.

**纳特·普赖斯：**这让我感觉自己老了。这是一本关于测试驱动开发的书，旨在总结我们在极限周二俱乐部中的一切...所以这不只是我和史蒂夫发明的。我们是六个人...让我们先谈谈极限周二俱乐部，然后我可以谈谈GOOS。

The Extreme Tuesday Club was an informal meetup. It's still going to this day, but it started in the early 2000s, people in London who were interested in extreme programming as it was very new when the sort of meetup started. And it was being publicized on the C2 Wiki and people were really quite inspired by this new way of development that was being publicized by Kent Beck, Ward Cunningham, and people like that, Ron Jeffries, and stuff.

极限周二俱乐部是一个非正式聚会。至今仍在继续，但始于2000年代初，当时伦敦对极限编程感兴趣的人们聚在一起，因为极限编程在当时还很新。它在C2 Wiki上被宣传，人们确实被Kent Beck、Ward Cunningham、Ron Jeffries等人宣传的新的开发方式所启发。

So a bunch of interested people just arranged a meetup in a pub in London. It went from pub to pub. It grew over time. It spawned a bunch of conferences that then ended up spreading around the world. And it also spawned a lot of different technical techniques that people learned and shared and sort of developed in their work and then shared in that sort of meetup.

于是一些感兴趣的人就在伦敦的一家酒吧安排了聚会。它从一家酒吧换到另一家酒吧。随着时间的推移，它不断发展壮大。它催生了一系列会议，最终传播到世界各地。它还催生了许多不同的技术技巧，人们在工作中学到并分享，然后在这种聚会上分享。

So GOOS was us writing about test-driven development and the techniques that had been shared and invented within the Extreme Tuesday Club along with...sort of portrayed as by a long-worked example that was an auction-sniping sort of application that would try and buy things on, I think eBay was quite new at the time, so on something like eBay, and how do you write that in a test-driven development way.

所以GOOS是我们写的关于测试驱动开发的书，以及极限周二俱乐部内分享和发明的技术，通过一个长期工作的例子来展示，这是一个拍卖狙击应用，试图在eBay（当时eBay还很新）这样的平台上购买东西，以及如何以测试驱动开发的方式编写它。

---

## The GOOS Approach / GOOS方法

The book showed how you would start from writing a test - writing a test of what the system should do. The system would start off very unstructured because it was very small and didn't need any structure, and then you'd add more and more tests. As the system grew and you wanted to test various bits more thoroughly, you would have to work out how to carve out those parts and mock bits of them while thoroughly testing other parts. Over the narrative arc of the example, you see how the final result is that the hexagon architecture sort of emerges from the continual refactoring and the pressure of the need for testing the application.

这本书展示了如何从编写测试开始，编写系统应该做什么的测试。系统开始时非常无结构，因为它很小，不需要任何结构，然后你会添加越来越多的测试。随着系统的发展，当你想更彻底地测试各个部分时，你必须想办法将这些部分分离出来，并模拟其中的一些部分，同时彻底测试其他部分。在例子的叙述弧线结束时，你会看到最终结果是六边形架构从持续重构和测试应用的需求压力下逐渐显现出来。

---

## Extreme Programming Context / 极限编程背景

**Duncan McGregor:** Extreme programming was one of a few precursors, one of a few ways of writing software that ended up under the umbrella of agile. XP in particular was developer-focused - it told developers how they should be working: pair programming, writing tests first, a bunch of principles and practices. The whole TDD thing was the big difference between XP and other methodologies.

**邓肯·麦格雷戈：**极限编程是几个前身之一，是几种最终被归入敏捷伞下的软件开发方式之一。XP特别是以开发者为中心的，它告诉开发者应该如何工作：结对编程、先写测试、一系列原则和一系列实践。整个TDD（测试驱动开发）是XP与其他方法论之间的主要区别。

**Nat Pryce:** There was a whole bunch of agile methods very early on. All the early agile processes definitely said something about how people should write software. XP was more extreme than others - it definitely had rules and practices that mesh together to allow you to evolve and rapidly change your software. Early on, agile was seen as a holistic thing - the way you should develop software has to involve the developers and the people who want the software working together. It's only later that we ended up with what we see described as agile today, where it's mostly a management process.

**纳特·普赖斯：**早期有一整套敏捷方法。所有早期的敏捷流程都确实说了些什么。XP比其他方法更极端，它确实规定了你遵循这些规则、这些实践，它们相互配合，使你能够演进并快速改变你的软件。在敏捷的早期，它被视为一个整体的事情，你应该如何开发软件必须涉及开发者和想要软件的人一起工作。只是后来我们才得到我们今天所描述的敏捷，它主要是一个管理流程。

---

## Outside-In TDD vs Bottom-Up TDD / 由外而内TDD vs 自下而上TDD

**Duncan McGregor:** The thing that GOOS introduced was a sort of outside-in acceptance test-driven approach. Would you say that was the major innovation?

**邓肯·麦格雷戈：**GOOS向我们介绍的东西是一种由外而内的验收测试驱动方法。你会说这是主要的创新吗？

**Nat Pryce:** I don't know if it was an innovation. It was certainly what we wrote about and it wasn't what Kent Beck really wrote about as much in his book. There was a lot more bottom-up approach in TDD in the first wave. We took a "let's try and capture everything and work from outside in" approach.

**纳特·普赖斯：**我不知道它是否是一种创新。它确实是我们写的东西，而Ken Beck在他的书中并没有真正写那么多。在第一波TDD中，可以说有更多的自下而上的方法。我们采取了一种"让我们尝试捕捉一切，从外向内工作"的方法。

---

## Mock Objects and Testing Techniques / 模拟对象与测试技术

**Duncan McGregor:** GOOS publicized and popularized the technique of mock objects.

**邓肯·麦格雷戈：**GOOS宣传、普及了模拟对象的技术。

**Nat Pryce:** It's more of a technique than a technology. A lot of what we were building was event-driven or message-driven, and it was a very natural fit for object-oriented systems built out of state machines. Those protocols by which objects talk are really the design of the system. You can write an object in your class that implements some roles in these protocols, and then it will plug into the right place. You end up writing code by reorganizing graphs of objects rather than writing procedural code.

**纳特·普赖斯：**我认为它更像是一种技术而不是技术。我们构建的很多是事件驱动或消息驱动的，所以它非常适合面向对象的系统，这些系统由状态机组成。它们交谈的协议实际上是系统的设计。你可以在类中写一个对象，实现这些协议中的一些角色，然后它会插入到正确的位置。你最终通过重新组织对象图来编写代码，而不是用过程代码编写。

---

## Changes in Software Development Over 15 Years / 过去15年软件开发的变化

**Nat Pryce:** Well, machines are a lot faster, and that changes how much you can test in one go. There used to be a very strict distinction between unit tests and integration tests - things that do IO, things that touch hardware. Technology has changed - we have SSDs, fantastic machines many orders of magnitude faster. Now we can run more in our tests at one go, so there's less need to have lots of little unit tests to try and get that fast feedback.

**纳特·普赖斯：**嗯，机器快多了，这改变了你一次可以测试多少。人们曾经非常严格地区分单元测试和集成测试 - 做IO的事情，接触硬件的事情。技术已经改变了，我们现在有SSD，神奇的机器快很多个数量级。所以现在我们可以一次在测试中运行更多，所以不太需要有很多小的单元测试来试图获得快速反馈。

Functional programming: When we wrote GOOS, it was written in Java 6, which didn't really have much support for functional programming. Now modern languages have much better support for transforming data rather than mutating data.

函数式编程：当我们写GOOS时，是用Java 6写的，Java中并没有真正支持函数式编程。但现在现代语言现在都有更好的支持，转换数据而不是改变数据。

---

## Developer-Led vs Management-Led Agile / 开发者主导 vs 管理层主导的敏捷

**Duncan McGregor:** XP had the role of a coach and had the role of a customer and then the role of developers. That felt very different from a Scrum Master who is masterful, telling us what we should be doing, and a product owner who's owning stuff that we're not owning because we're only developers.

**邓肯·麦格雷戈：**XP有教练的角色，有客户的角色，然后是开发者的角色。这对我来说至少感觉非常不同于Scrum Master的角色，你知道，Scrum Master是权威的，告诉我们应该做什么，而产品负责人拥有这些东西，我们不拥有，因为我们只是开发者。

**Nat Pryce:** The most happy XP projects I was on, the developers often had very deep business knowledge. There wasn't such a distinct division of responsibilities. Developers were allowed to say, "Hey, wouldn't it be great if the product did this?" It was much more flexible in the division of responsibilities.

**纳特·普赖斯：**当然，在那些日子里我参与的最快乐的XP项目中，开发者通常有非常深厚的业务知识。没有如此明确的责任分工。开发者被允许说："嘿，如果产品这样做不是很好吗？"在这种责任分工中更加灵活。

---

## Modern Challenges / 现代挑战

**Duncan McGregor:** I feel that we are able to store and access more data, and we're able to be more scalable, but I don't actually feel that we are delivering features any quicker these days.

**邓肯·麦格雷戈：**我觉得我们能够存储和访问更多数据，我们能够更具可扩展性，但我实际上并不觉得我们如今交付功能更快了。

**Nat Pryce:** We expect more from our features. Back in the day, people would put up with a desktop UI created by a programmer. Nowadays, they have people who can actually design user interfaces. We're expected to create websites that work on a wider variety of devices - mobile, desktops, iPads, various operating systems, multiple browsers.

**纳特·普赖斯：**我们对功能的期望更高了。在过去，人们会忍受由程序员创建的桌面用户界面。如今，他们有真正能够设计用户界面的人。我们被期望创建在更广泛的设备上工作的网站 - 移动设备、桌面、iPad、各种操作系统、多个浏览器。

---

## The Essence of GOOS / GOOS的本质

**Duncan McGregor:** The constant focus on "how can I give you value quicker whilst at the same time knowing that I'm going to be able to continue to give you value quickly" - that's the essence of XP, it's the essence of Agile, and I think the essence of what GOOS gave us, which was this guided by the tests. It was this outside-in thing, real focus on delivering to the customer, not doing anything that wasn't focused on delivering value to the customer.

**邓肯·麦格雷戈：**持续关注"我如何能更快地给你价值，同时知道我将能够在下周和下个月等继续快速给你价值"，这是XP的本质，这是敏捷的本质，我认为也是GOOS给我们的本质，这就是由测试引导。这是由外而内的事情，真正专注于交付给客户，不做任何不专注于向客户交付价值的事情。

---

## Watch Next / 接下来观看
- **Crafting Robust Architectures for a Resilient Future** - May 2023 / **为弹性未来打造稳健架构** - 2023年5月
- **Holiday Special 2022** - December 2022 / **2022年节日特辑** - 2022年12月
- **C4 Models as Code** - November 2022 / **代码形式的C4模型** - 2022年11月
- **The Big Friendly Monolith** - November 2017 / **大型友好单体** - 2017年11月