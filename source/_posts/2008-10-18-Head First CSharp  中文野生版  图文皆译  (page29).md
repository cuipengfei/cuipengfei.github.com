---
title: Head First C#  中文野生版  图文皆译  (page29)
date: 2008-10-18 12:26:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081018/%E6%88%AA%E5%9B%BE08.jpg)

3  一旦你键入了所有六条记录，再次从文件菜单选择全部保存。那将会把所有记录保存到数据库。

<?xml:namespace prefix = o ns = "urn:schemas-microsoft-com:office:office" />

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081018/%E6%88%AA%E5%9B%BE09.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081018/%E6%88%AA%E5%9B%BE10.jpg)

问：我输入完的数据怎么样了？它去哪儿了？

答：IDE自动把你键入的数据存储进数据库的People数据表。数据表，它的列，数据类型，和里面的所有数据都存储在SQL Server
Express的ContactDB.mdf文件里。这个文件被作为项目的一部分存储，并且像你的代码文件一样，IDE会在你修改它的时候更新它。

问：好，我输入了列条记录。它们会永远作为我的程序的一部分吗？

答：对，它们就像你写的代码和正在创建的窗体一样，是你的程序的一部分。不同的是，他们不被编译为可执行文件，而是被复制并且和可执行文件一起存储。当你的程序需要访
问数据时，它读写程序的输出目录下的ContactDB.mdf。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081018/%E6%88%AA%E5%9B%BE00.jpg)

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

