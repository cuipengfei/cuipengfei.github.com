---
title: Scala + Play + Sbt + Protractor = One Build
date: 2014-06-26 09:23:45
tags: 杂7杂8
---
欢迎关注我的新博客地址： [ http://cuipengfei.me/ ](http://cuipengfei.me/)

我所在的项目的技术栈选用的是Play framework做后端API，前端用Angular JS。

因为用了Scala和Play，构建工具很自然用的就是sbt。

而由于前端用了Angular，所以functional test就选用了和Angular结合较好的protractor。

这一切看起来似乎很美好，一个无状态的后端，一个数据和UI双向绑定的前端。What could possibly go wrong？

一开始也确实如此，没什么问题。我们为了让functional test在CI上跑起来，写了一个脚本来把play
dist打出的包部署到CI所在机器上，然后运行protractor。

这个脚本运行还算ok，偶尔有点小问题，修一修也就好了。

不过，这也就是说有两个因素可能会使得我们的CI挂掉，一个是用sbt跑的specs2的测试，一个是protractor的测试。而protractor的测试是基
于我们自己所写的脚本的，与sbt没啥关系。

###  麻烦来了

上周五的show case，我们一个小时后就要给客户演示现有产品的示例，但是CI挂掉了，新的代码没法走到QA和UAT的环境，bug fix也过不去。

最后我们不得不修改jenkins的配置，把sbt test和protractor的test都暂时禁掉，才让最新的代码到了UAT上去。而这一切，是在show
case之前一分钟才解决掉的。

事情总是这样的，出一两次小问题，修修改改就好，我们不会注意到其危害，不会想到其风险。直到琐碎的积累导致了严重的后果，我们才会正视问题的存在。而这个时候问题或
许已经复杂和严重到不可修复或者是要花很大成本修复的地步了。保持一个健康的CI是如此。写代码的每一个细节也是如此。

还好，很幸运，我们的问题还没有那么严重，还来得及修。

在决定要修之后，先

###  定义一下问题是啥

往简单里说，就是CI不稳定，动不动就随便挂。

说的再细一些，就是我们手写脚本去做部署和测试这件事算是重新发明了轮子。而这个轮子不如已有的经过打磨的轮子那么精巧细致，那么稳定好用。以至于我们的CI偶尔就要
出格一次。

Ok，问题定义清楚了，那么想想解决方案吧。

但是，在提出具体的方案之前，先想想，如果把这个现时还未存在的解决方案作用在现有问题之上，会收获一个什么样的结果呢？

###  验证标准

基于以上所述，我想解决这个问题的方案要满足以下3点：

  1. 能让CI重回稳定 
  2. 一条命令行执行整个build 
  3. 不要再自己造轮子了 

第1，2点毋庸赘言，这就是我们问题的核心。关于第3点，是因为我们没有时间精力，也实在没有必要造这个轮子，如果能找到现有的轮子能够解决问题，而且还比我们自己的
木头胶皮轮子好用，那岂不妙哉？

于是，我要开始寻找一个能让CI重归稳定的神圣轮子了！让探险的旅途就此展开吧！

![图](http://static.comicvine.com/uploads/original/7/75497/2189954-aragorn247_s
creen.jpg)

###  开始寻找轮子

我最初的想法是用play的test framework，其中已经集成了selenium，用来做functional
test很是合适。但是由于我们基于protractor的测试数量已经不少了，全部重写成本较高，所以这个轮子就放弃了。

###  残念，再看下一个轮子

再然后我想到的是自定义一个sbt的task，这个task依赖于sbt已有的run。

这样就能在我的task启动之前把play跑起来，而task本身运行protractor的测试，再之后则杀掉正在运行的play app。

看起来不错，但是有问题：

第一，sbt run跑起来后是不会自己退出的，它会维持play一直在待命的状态，这也就是说我自己的task根本就没机会执行。

第二，即便能找到方法让我自己定义的task和run同时跑起来，protractor运行完毕后还要关掉run，免得占用端口。这又是一件麻烦事儿。

于是，这第二个轮子也被我自己给枪毙掉了。

###  再次残念，还有轮子吗？

会有的，总会有的，只要肯去找，还是会有的。

这次我想到，写sbt的task不成，那就写代码。我写个specs2测试，在case里用代码启动sbt
run，然后再启动protractor，最后关闭sbt，总行了吧？

这样，确实是可以work的，而实际上我也把它做出来验证了可以work了，但是缺点很大。

第一，由于我们的specs2测试都是用sbt跑的，而在其中再启动sbt，相当于要开两个jvm，消耗很大。在我本地机器上可以压榨的只剩两位数的内存。

第二，在sbt已经编译好了产品代码和测试代码测试之后，再开始跑另一个sbt run，会导致sbt把代码重新编译一遍。而Scala的代码编译是很慢的。我试了一
下，这两次启动sbt，两次编译所消耗的时间是四分钟左右。时间成本太高。CI的速度会被拉下来，受不了。

基于以上两点原因，我的第三个轮子也被我自己枪毙了。

###  命途多舛啊，三次尝试都失败，以你为我要放弃了吗？哼~~~

最后，我结合第一次的尝试和第三次的尝试找到了一个满意的答案。

来看代码吧：

    
    
    1
    2
    3
    4
    5
    6
    7
    8
    9
    10
    11
    12
    13
    14
    15
    16
    17
    18
    19
    20
    21
    22
    23
    24
    25
    26
    27
    28
    29
    30
    
    
    
    class ProtractorSpec extends Specification with NoTimeConversions {
    
      "my application" should {
    
        "pass the protractor tests" in {
    
          running(TestServer(9000)) {
    
            val resp = Await.result(WS.url("http://localhost:9000").get(), 2 seconds)
            resp.status === 200
    
            runProtractorTests === 0
          }
        }
    
      }
    
      private def runProtractorTests: Int = {
        "protractor functional-test/config/ft.conf.js"
          .run(getProcessIO)
          .exitValue()
      }
    
      private def getProcessIO: ProcessIO = {
        new ProcessIO(_ => (),
          stdout => fromInputStream(stdout).getLines().foreach(println),
          _ => ())
      }
    
    }
    

就只有这么一点点代码。

running和TestServer都是play的test framework所提供的API。顾名思义，其作用就是把play的app跑起来。

然后发一个get请求，assert它的response的status是200，以此来确保play真的是把server运行起来了的。

再然后运行shell脚本，把protractor跑起来。这里Scala会做implicit
conversion，把字符串转换成ProcessBuilder，从而可以调用其run方法。

最后assert，protractor的shell脚本是返回了0的，意味着functional
test跑成功了。如果protractor测试挂掉，返回了1，那么specs2的这个测试也会挂掉，从而挂掉整个build。而这，正是我想要的。

###  这个解决方案合规吗？

检验一下吧。

由于server的启动和关闭都是有play的test framework的API负责的，比自己手写得脚本要稳定，所以符合了重归稳定性这一点。

由于用了specs2的测试，它可以跑在sbt里，所以符合一条命令跑build这一点。

整个解决方案只用了specs2和play的test framework，没有重新发明轮子，所以这一点也符合了。

除了符合最初定下的三条标准之外，还有额外的好处：functional
test所跑到的代码会被纳入到测试覆盖率里面去。因为和其他specs2的测试一样，protractor的测试也在sbt
jacoco:cover的监视下跑的，所以自然就纳入了coverage的范围。

###  Takeaway

在解决这个问题之后，我想我会有三点takeaway：

  1. 多尝试几种方案，不要随便放弃。即便想，也不要。 

  2. 不要屈就于working solution，要相信一定存在你现在还没想到的更好的方式。 

  3. 重复发明轮子总是会显得很诱人，因为它看起来可以非常直接而且准确的解决我们的问题。而实际上它常常是直接而且准确的解决我们的问题的现象。如果能找到现象产生的原因，干死这个原因，问题的解决或许会更彻底。 



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

