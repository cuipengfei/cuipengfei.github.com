---
title: Head First C# 中文版 图文皆译 第五章 封装 page187
date: 2009-02-07 23:40:00
tags: 我翻译的Head First C#（习作）
---
但是，realName字段真的被保护起来了吗？

所以，只要克格勃不知道中情局探员的密码，中情局探员的真实姓名就是安全的，对吧？但是realName字段的声明又是怎样的呢:

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90207/%E6%88%AA%E5%9B%BE05.jpg)

Jones  探员可以使用私有字段来把保护自

己的身  ![https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/
20090207/截图06.jpg](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/
EntryImages/20090207/%E6%88%AA%E5%9B%BE06.jpg) 份。一  旦他把  realName字段设置

为private，访问它的唯一方  式是调用对

类的私有部分有访问权的方法。这样克格

勃就被击退了！

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90207/%E6%88%AA%E5%9B%BE07.jpg)

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

