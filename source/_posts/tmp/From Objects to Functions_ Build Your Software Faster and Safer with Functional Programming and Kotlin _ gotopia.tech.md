# From Objects to Functions: Build Your Software Faster and Safer with Functional Programming and Kotlin
# 从对象到函数：使用函数式编程和Kotlin更快更安全地构建软件

**Duncan McGregor • Uberto Barbini | Gotopia Bookclub Episode • April 2024**
**Duncan McGregor • Uberto Barbini | Gotopia读书会节目 • 2024年4月**

---

## Overview / 概述

Explore the dynamic journey of software development through 'From Objects to Functions,' where Uberto Barbini's passion for functional programming meets the pragmatic realities of building real-world applications. Delving into the nuances of Kotlin, Uberto Barbini and Duncan McGregor navigate the intersection of object-oriented and functional paradigms, revealing how thinking in morphisms fosters better software solutions. They also share insights into the evolution of his understanding and the transformative power of functional programming. From tackling complex concepts to advocating for a more creative approach to teaching programming, this GOTO Book Club episode offers a comprehensive exploration of modern software development and inspires you to embrace functional thinking in your own coding adventures.

通过《从对象到函数》探索软件开发的动态历程，其中Uberto Barbini对函数式编程的热情与构建真实应用的现实需求相遇。深入探讨Kotlin的细微差别，Uberto Barbini和Duncan McGregor游走于面向对象和函数式编程范式之间，揭示了如何通过态射思维促进更好的软件解决方案。他们还分享了对函数式编程理解的演变和变革力量的见解。从处理复杂概念到倡导更具创造性的编程教学方法，这期GOTO读书会节目全面探索了现代软件开发，并激励你在自己的编程冒险中拥抱函数式思维。

---

## Transcript / 文字稿

### Functional Kotlin / 函数式Kotlin

**Duncan McGregor:** Hello and welcome to "GOTO Book Club." I'm Duncan McGregor, and today I'm delighted to be interviewing Uberto Barbini about his book "From Objects to Functions: Build Your Software Faster and Safer with Functional Programming and Kotlin." It was published by Pragmatic Programmer in September. Uberto Barbini, welcome to GOTO Book Club.

**Duncan McGregor：**大家好，欢迎来到"GOTO读书会"。我是Duncan McGregor，今天很高兴能采访Uberto Barbini关于他的书《从对象到函数：使用函数式编程和Kotlin更快更安全地构建软件》。这本书由Pragmatic Programmer在9月出版。Uberto Barbini，欢迎来到GOTO读书会。

**Uberto Barbini:** Hi, nice to be here, Duncan.

**Uberto Barbini：**你好，很高兴来到这里，Duncan。

**Duncan McGregor:** So, Uberto, we worked in the same circles in London. You were a technical reviewer on the book that I wrote with Nat Price, and then I returned a favor by being a technical reviewer on your book, but we've never actually worked together. So, how did you come to work on Kotlin Projects in London and now in Japan?

**Duncan McGregor：**那么，Uberto，我们在伦敦的同一个圈子里工作过。你曾是我和Nat Price合著的书的技术审稿人，后来我投桃报李，成为你书的技术审稿人，但我们从未真正一起工作过。那么，你是如何开始在伦敦从事Kotlin项目的，现在又到了日本？

**Uberto Barbini:** I was working in banks in London, and... I started learning about Kotlin many years ago, before Version 1. At that time there was also another programming language in the JVM that seemed more promising. But after a few years, Kotlin grew on me and I started using it in the banks. Then I started looking basically, at the beginning we were using Kotlin, you know, introducing slowly creeping to write a test, to write a little tools. Then I started looking for a gig where I could write Kotlin all the time. I think more or less we got in touch and at the time London Kotlin community, back then the Kotlin community was not that big.

**Uberto Barbini：**我当时在伦敦的银行工作，而且...我在很多年前就开始学习Kotlin，在1.0版本之前。那时JVM上还有另一种编程语言看起来更有前景。但几年后，Kotlin逐渐赢得了我的青睐，我开始在银行中使用它。然后我开始寻找机会，基本上一开始我们使用Kotlin，你知道，慢慢引入来写测试，写一些小工具。然后我开始寻找可以一直使用Kotlin的工作。我想我们或多或少取得了联系，当时伦敦的Kotlin社区，那时Kotlin社区还不是很大。

---

### The Book's Purpose / 书的目的

**Duncan McGregor:** So what prompted you to write the book? Who's it for?

**Duncan McGregor：**那么是什么促使你写这本书的？它是为谁而写的？

**Uberto Barbini:** I'm very passionate about functional programming, but also about writing good code, you know, with good practice. And I didn't find much content outside about, yeah, how to put together the two things. So, all the books about, you know, TDD, or clean programming or stuff like that tend to be very object-oriented. And all the book about functional programming tends to be quite, you know, very specific exercises on specific things on functional programming. There was no book about, yeah, how to set up a full project from scratch and adopt a functional program with a kind of pragmatical point of view, let's say. So, I think it's quite a useful book for people that came from a Java background also culturally, but use a bit of a, like Java Agile, I mean, simple Java, and they wanted to touch a bit of functional programming. Or people coming from more functional, advanced stuff like Haskell or Scala and wanted to use a... I mean, take advantage of Kotlin's simplicity and productivity.

**Uberto Barbini：**我对函数式编程充满热情，也对编写好的代码很感兴趣，你知道，用好的实践。我在外面没有找到太多关于如何将这两者结合的内容。所以，所有关于TDD、清洁编程或类似内容的书籍往往都非常面向对象。而所有关于函数式编程的书籍往往都是，你知道，非常具体的函数式编程特定内容的练习。没有一本书是关于如何从头开始建立一个完整项目并采用函数式编程的实用观点。所以，我认为这本书对那些有Java背景的人很有用，文化上也是如此，但他们使用的是类似Java敏捷的，我的意思是简单的Java，并且想要接触一些函数式编程。或者那些来自更函数式、高级内容如Haskell或Scala的人，想要利用Kotlin的简洁性和生产力。

**Duncan McGregor:** I should say, the book isn't a sort of academic book. It takes a project, it takes, the example is a sort of to-do list if I remember. And it takes us from sort of inception, the initial sort of prototyping through to a sort of complete database-backed application with a web frontend and so on. And I found that was quite refreshing, it's not just how do you... It's not just, "These are bits of functional programming." It is how to plug functional programming into an entire project lifecycle.

**Duncan McGregor：**我应该说，这本书不是那种学术书籍。它以一个项目为例，我记得例子是一个待办事项列表。它带领我们从最初的概念阶段，初始的原型设计，一直到完整的数据库支持的应用程序和Web前端等等。我觉得这很令人耳目一新，它不仅仅是关于如何...不仅仅是"这些是函数式编程的一些片段"。而是如何将函数式编程融入整个项目生命周期。

---

### Balancing Object-Oriented and Functional Paradigms / 平衡面向对象和函数式编程范式

**Duncan McGregor:** Kotlin is a sort of multi-paradigm language. I think Scala took that mantle first, but we can write procedural, we can write object-oriented or functional style. I think, I mean, Kotlin sort of probably starts from OO, being on the JVM. Lots of the sort of libraries we use are object-oriented. But how would you look at some Kotlin code and decide that it was functional versus OO? What characterizes functional programming for you?

**Duncan McGregor：**Kotlin是一种多范式语言。我认为Scala首先承担了这个角色，但我们可以写过程式、面向对象或函数式风格的代码。我认为，我的意思是，Kotlin可能从面向对象开始，因为它在JVM上。我们使用的很多库都是面向对象的。但是你会如何看一些Kotlin代码并判断它是函数式的还是面向对象的？对你来说，什么特征定义了函数式编程？

**Uberto Barbini:** The first thing is that it's not that important that is functional programming object-oriented, but I appreciate that the advantage of functional programming is having functions, basically everything is designed using functions, and putting the function together to do some feature, I mean, to do some tasks. The good thing is that you know, combining these functions, each function does just a bit piece of work and is very defined, clearly defined, from the input to the output. So, it's easy to test, and it's easy to use in another place if you have to do the same things. And then you put it together and you have a function. So, when instant code started to reference external things like singleton or external calls without being specified by the type system, then it became also very hard to reuse. I mean, I remember someone that... Actually in the banks at some point, someone was doing some calculations and got a function from another piece of the program. But that function was locking a lot of stuff, and just using that function slowed down the whole application. And we realized in production. So, this kind of stuff it's kind of easier with the functional programming to see what you are doing without the kind of casting the code.

**Uberto Barbini：**首先，函数式编程和面向对象哪个更重要并不重要，但我很欣赏函数式编程的优势在于有函数，基本上一切都是用函数设计的，把函数组合起来实现某个功能，我的意思是执行某些任务。好处是，你知道，组合这些函数时，每个函数只做一小部分工作，并且从输入到输出都有非常明确的定义。所以，它很容易测试，如果要做同样的事情，在另一个地方也很容易使用。然后你把它们组合起来就得到了一个函数。所以，当即时代码开始引用外部事物如单例或外部调用而没有被类型系统指定时，就变得很难重用了。我的意思是，我记得有人...实际上在银行里某个时候，有人在做一些计算，从程序的另一部分得到了一个函数。但那个函数锁定了很多东西，仅仅使用那个函数就拖慢了整个应用程序。我们在生产环境中意识到了这一点。所以，这类问题用函数式编程更容易看出你在做什么，而不需要那种强制转换代码的方式。

[Content continues with all bilingual text preserved...]

---

## About the speakers / 关于演讲者

### Duncan McGregor (interviewer) / Duncan McGregor（采访者）
Professional software developer based near London, started programming on ZX81 at age 13.

### Uberto Barbini (author) / Uberto Barbini（作者）
Author of "From Objects to Functions: Build Your Software Faster and Safer with Functional Programming and Kotlin"

---

## Related topics / 相关主题
- #Kotlin #Kotlin
- #functional programming #函数式编程
- #Java #Java

---

## Watch Next / 接下来观看
- **Using Ktor 3.0 with All the Shiny Things** - June 2024 / **使用Ktor 3.0与所有闪亮的功能** - 2024年6月
- **10 Reasons to Try Kotlin Multiplatform** - October 2023 / **尝试Kotlin多平台的10个理由** - 2023年10月
- **Spring Boot: Up and Running** - October 2022 / **Spring Boot：启动并运行** - 2022年10月
- **Enterprise Java in the Age of Microservices, Containers and Cloud-Native** - December 2021 / **微服务、容器和云原生时代的企业Java** - 2021年12月