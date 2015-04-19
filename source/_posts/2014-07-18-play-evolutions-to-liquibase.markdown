---
layout: post
title: "在使用play framework的evolutions？需要支持SQL Server？用Liquibase吧"
date: 2014-07-18 15:11
comments: true
keywords: scala, play, play framework, evolution, sql server, liquibase
categories:
---

我所在的项目在用Scala + Play framework做一个web app。

Play自带的evolutions是一个DB Migration工具，从一开始我们就在用它来做所有阶段的数据迁移工作。

运行自动化测试时它可以帮每个测试用例在H2中创建数据（H2是Play默认的内存数据库）。
在下一个测试用例运行时evolutions则会创建一份和上次完全相同的新数据，这样我们的测试可以获得独立性而不用担心之前的测试遗留的副作用。也不用担心会给下一个测试遗留下什么脏数据。

在测试或者部署环境中运行时它也可以针对Postgres做数据迁移。

这一切看起来都挺好，我们就差喊evolutions是我们忠实的好伙伴了。

但是，快到给终端客户部署时，某一家客户提出他们一定要使用SQL Server，我们最初提出的使用Postgres他们不接受了。这时我们才发现evolutions的设计初衷就是在开发和测试阶段提供便利性，它根本就没想成为一个production ready的东西。

这样看来我们必须得寻找一个正经的DB Migration的工具了。而且这个DB Migration工具一定要满足以下几点：

1. 能够在运行自动化测试时和H2结合使用（因为我们已经有很多测试在依赖于H2跑了，要换掉成本较高）
2. 能支持多种数据库（今天有人要SQL Server的支持，明天说不定还会有人要其他的）
3. 在支持多种数据库时不需要我们写不同风格的SQL脚本（要写出让各个DB都不挑剔的SQL实在是太费劲了）

我最先想到的就是Flyway，之前用过，而且TW的tech radar也提到过它。

但是它并没有入选，原因在于上面的第三点。Flyway要求使用者自己提供执行所需的SQL脚本。
这就意味着我们写SQL时需要同时兼顾H2，Postgres，SQL Server的异同。而且还无法预知未来的其他数据库会对我们现在写出的SQL脚本产生什么样的影响。

最后我们选择了Liquibase，我们可以通过JSON，YAML，或者XML来定义数据。Liquibase自己负责把我们定义的数据翻译给各种不同的数据库。

这样，通过一层中间语言。我们就隔离了数据库的差异对我们开发工作可能会造成的影响。

Ok，要用Liquibase这个大方向就确定了。但是具体怎么把它跑起来呢？在什么时机跑它呢？

用脚本跑？

Liquibase确实提供了Standalone，我们可以用脚本来调用它。

但是这怎么和build结合起来呀？在测试时调用它？在app启动时调用它？

那H2运行的端口每次都未必是一样的，这怎么办啊？

这个方案想想就费劲。

把它做成sbt的一个task？

这样确实比直接用脚本要稍微距离我们的build近一点，但是还是会有类似的问题。我们需要显式地去调用它，还要选择合适的时机去调用它。实现起来也会很麻烦。

而实际上，Play自己是支持plug in的。我们想要控制执行时机，而有谁比Play自己更了解它的运行时机呢？

而且已经有人做了liquibase play plug in。我把它fork了一份，更新了liquibase和play的版本，提高了log的level。并且部署到了sonatype去。

由于是Play自己的plug in，不是我们试图插入的生硬的脚本或者sbt task。Play自己知道该在什么合适的时机去执行它。

下面说一下如何应用它吧。

* 在所有的conf文件中删掉所有和evolutions有关的配置

这两个东西不能一起用，要不然我们需要同时维护两种DB Migration的脚本。

* 在dependencies中加入这一项：

"com.github.cuipengfei" % "play-liquibase_2.11" % "1.1"

很明显，这是用来引入这个plugin的。

* 在conf目录下创建一个名为play.plugins的文件，在其中写入：

400:com.github.cuipengfei.LiquibasePlugin

冒号前的400用来定义plugin的执行优先级，Play会由此决定何时执行该plugin。

冒号后是plugin的完全限定名。

* 在你需要的conf文件中加入两行：

liquibaseplugin=enabled

applyLiquibase.default=true

这样用来启用该plugin。

* 在conf/liquibase/default/下创建一个modules.xml。

在其中写入你的数据定义。（具体如何写，liquibase的官网有详细的介绍）

如果你用的数据库名字不是default，相应的替换就ok了。


这样，就大功告成了。

当你用sbt运行自动化测试时，liquibase会帮你创建数据。

当你在本地调试运行时，liquibase会帮你set up数据库。

当应用被部署到生产环境下去的时候，liquibase也可以帮你在第一次运行时进行数据的初创。
