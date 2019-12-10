---
title: Words Via Subtitle改进：视频播放例句、加快生成WPS文档的速度
date: 2009-09-17 21:35:00
tags: undefined
---
Words Via Subtitle  改进：视频播放例句、加快生成  WPS  文档的速度

  

关于  Words Via Subtitle  的介绍：

  

1  [ http://blog.csdn.net/cuipengfei1/archive/2009/09/03/4516588.aspx
](http://blog.csdn.net/cuipengfei1/archive/2009/09/03/4516588.aspx)

2  [ http://blog.csdn.net/cuipengfei1/archive/2009/09/10/4539180.aspx
](http://blog.csdn.net/cuipengfei1/archive/2009/09/10/4539180.aspx)

  

最近给这个小程序添加了视频播放例句的功能，使用方法如下：

  

载入字幕文件并指定了与之对应的视频文件之后，选中某个感觉生疏的单词，然后点击播放例句按钮，该单词所在句子就会以视频的方式开始播放，播完该句子视频暂停。其实这
个功能写起来也挺简单的，就是在字幕中找到一个单词所在句子对应的时间信息，然后依照该时间段播放视频。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090917/%E6%88%AA%E5%9B%BE00.jpg)

加快生成  WPS  文档的速度：

  

原 来是在生成文档的时候才去获取单词的网络解释及其语境。又要访问网络又要读文件然后才可以生成文档，速度就会很慢。现在改变了方式，载入字幕之后就用一个
线程去把单词的解释从网上获取来，把语境从文件中读取来，这样生成文档这个操作所耗的时间就主要在于在文档中找到关键字并把关键字变色了（这个过程还是耗
时不少，不过比原来快多了）。

程序下载地址：  [ http://cuipengfei1.download.csdn.net/
](http://cuipengfei1.download.csdn.net/)

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

