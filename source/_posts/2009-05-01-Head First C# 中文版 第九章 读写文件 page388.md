---
title: Head First C# 中文版 第九章 读写文件 page388
date: 2009-05-01 17:09:00
tags: 我翻译的Head First C#（习作）
---
FlieStream  向文件写入比特

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90501/2009-05-01_16-53-36.jpg)

要向文件写入几行文本需要做很多事情：

  

①  创建一个FileStream对象并让它向文件写入数据。

  

②  FileStream  把自己和一个文件连接起来

  

③  流向文件写入的是字节，所以你需要把需要写入的字符串转换为字节。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90501/2009-05-01_16-59-53.jpg)

④  调用流的Write（）方法，并把字节数组作为参数传递给它。

  

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90501/2009-05-01_17-03-00.jpg)

⑤  关闭流，这样别的程序才可以访问文件

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90501/2009-05-01_17-05-00.jpg)

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

