---
title: Head First C#中文版  图文皆译  （page20）
date: 2008-10-15 22:43:00
tags: 我翻译的Head First C#（习作）
---
给联系人列表创建数据表  <?xml:namespace prefix = o ns = "urn:schemas-microsoft-
com:office:office" />

我们有一个数据库，现在我们需要往里面存储信息了。但是，实际上，我们的信息需要放进一个数据表里，数据表是数据库用来存储每一个bit数据的数据结构。对于我们的应
用来说，我们来创建一个叫做“People”的数据表来存储所有的联系人信息。

1  向ContactDB数据库里面添加一个数据表。

在数据库浏览器里面右键单击Tables，并且选择Add New Table。这会打开一个让你来定义刚刚创建的数据表中数据列的窗口。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081015/%E6%88%AA%E5%9B%BE04.jpg)

现在我们需要向我们的数据表中添加数据列了。首先，让我们向新建的People数据表中添加一个叫做ContactID的数据列，这样每一个联系人记录就会有一个唯一
的ID了。

2  向People数据表中添加ContactID数据列

在列名字段中键入“ContactID”，并在数据类型下拉列表中选择Int。一定要反选允许为空复选框。

最后，我们把它作为我们数据表的主键。选中你刚刚创建的ContactID数据列，并点击主键按钮。这告诉数据库每一个条目都将会有一个主键条目。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081015/%E6%88%AA%E5%9B%BE05.jpg)

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081015/%E6%88%AA%E5%9B%BE06.jpg)

问：什么是数据列来着？

答：数据列是数据库里面的一个字段。所以在People数据库里面，你可能会有FirstName和LastName数据列。它总会有一个数据类型，比如String
或者Date或者Bool。

问：为什么我们需要ContactID数据列呢？

答：对于绝大多数的数据库来说有一个唯一的ID会大有裨益的。因为我们要为每一个人存储联系信息，所以我们决定给它创建一个数据列，并且称之为ContactID。

问：数据类型里的那个int什么意思？

答：数据类型告诉数据库每一个数据列期待什么类型的信息。Int代表integer，它是一个整数。所以ContactID数据列将会存储整数。

问：东西太多了。我应该要都领会吗？

答：不，你现在还不能全明白也OK。集中精力于基本步骤上，在这本书的后续章节里面我们会花更多的时间在数据库上。如果你现在非常渴望懂得更多，你总是可以拿起Hea
d First SQL来和这本书一起读的。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

