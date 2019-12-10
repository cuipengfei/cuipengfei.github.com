---
title: Head First C# 中文版 第12章 回顾与前瞻 page549
date: 2009-06-27 09:13:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090627/2009-06-27_09-02-33.jpg) ![](https://p-blog.csdn.net/images/p_blog_csdn_
net/cuipengfei1/EntryImages/20090627/2009-06-27_08-55-53.jpg) 你的任务就是给
ToolStrip  中的  startSimulation  和  reset  按钮编写事件处理方法。下面是每个按钮需要做的事情：

  

1\.  最开始，第一个按钮应该写有“  Start Simulation  ”，点击它会使得模拟器启动，并且其上的文本改变为“  Pause
Simulation  ”。如果模拟器暂停了，按钮上的文字应该改变为“  Resume Simulation  ”。

  

2\.  第二个按钮应该写有“  Reset  ”。点击它的时候，世界应该被重建。如果  Timer  被暂停了，第一个按钮的文本应该从“  Resume
Simulation  ”改变为“  Start Simulation  ”。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090627/2009-06-27_09-04-07.jpg)

你认为现阶段的模拟器还需要做哪些完善？试着运行程序。把你认为我们需要在去处理图像有关的东西之前做完的事情写下来。

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090627/2009-06-27_09-06-39.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090627/2009-06-27_09-07-04.jpg)

问：我们一直在讲“一轮”这个术语，现在你又在说“一帧”。二者有何区别？

  

答：我们一直在处理轮：一小段时间中，世界中的每一个对象都要各司其职。但是由于我们马上要用到一些很重要的图形相关的东西了，所以我们要用帧这个词，就像一个游戏中
的帧频率一样。

  



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

