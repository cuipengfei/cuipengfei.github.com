---
title: Head First C# 中文版 图文皆译 第五章 封装 page182
date: 2009-02-06 14:47:00
tags: 我翻译的Head First C#（习作）
---
别担心！这不是你的错。

我们在代码中故意做出一个bug来让你知道对象在使用彼此的字段的时候是多么容易出问题...还有要指出这些错误是多么费劲。

每个选择应该要单独计算

虽然我们根据凯瑟琳说的来计算了所有的数字，但是我们没有考虑只改变一个窗体上的选择的时候会怎么样。

载入窗体的时候，窗体把人数设置为5，把高级装饰选项设置为true。健康选择没有选中，总的花费是350美元。初始的花费是这么计算出来的：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090206/%E6%88%AA%E5%9B%BE00.jpg)

当你把客人数改变之后，程序本应该按照一样的方式来估价。但是它没有：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090206/%E6%88%AA%E5%9B%BE01.jpg)

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

