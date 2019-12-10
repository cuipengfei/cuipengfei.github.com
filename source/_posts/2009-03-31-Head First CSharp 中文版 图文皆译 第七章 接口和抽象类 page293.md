---
title: Head First C# 中文版 图文皆译 第七章 接口和抽象类 page293
date: 2009-03-31 13:49:00
tags: 我翻译的Head First C#（习作）
---
⑧  创建一个窗体来浏览房子

创建一个窗体来浏览房子。它需要有一个多行的textbox叫做description，来显示当前的房间的描述。一个叫做exits的列表列出所有与当前房间连通的
地点。有两个按钮：goHere会移动到在列表中选中的地点，goThroughTheDoor按钮只有在有户外门的时候才可见。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90331/2009-03-31_13-24-57.jpg)

⑨  现在你只需要让窗体可以工作

所有细小部件都全了，你只需要把它们组合起来。

★  你的窗体里面需要一个叫做currentLocation的字段来保存当前位置。

★  添加一个MoveToALocation（）方法，它接受一个Location类型的参数。这个方法首先把currentLocation设置为新的地点。然后
清空列表，再用Exits[]数组里面的元素填充列表。最后重新设定列表让它显示第一个元素。

★  让文本框显示当前地点的描述。

★  用is关键字来检查当前地点是否有一个门。如果有，让“Go through the door”按钮可见。否则不可见。

★  如果“Go here”按钮被点击了，就移动到列表中当前选中的地点去。

★  如果“Go through the door”按钮被点击了，就移动到这扇门连接的地点去。

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90331/2009-03-31_13-40-50.jpg)

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

