---
title: Head First C# 中文版 第10章 异常处理 page461
date: 2009-06-02 12:47:00
tags: 我翻译的Head First C#（习作）
---
现在按照下面的步骤调试：

  

①  用上一页的代码来更新  Random Excuse  按钮的事件处理方法。然后在方法中的第一行添加一个断点并调试程序。

  

②  正常运行程序，确保  Random Excuse
按钮在你把程序指向一个包含有正常借口文件的文件夹的时候可以正常工作。调试器会在你设置断点的地方停下来：

  

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090602/2009-06-02_12-37-49.jpg)

③  逐步执行  Random Excuse  按钮事件处理方法中余下的代码，确保它是按照你预期的方式运行的。调试应该执行玩  try  块，跳过
catch  块（因为没有抛出异常），然后执行  finally  块。

  

④  现在把程序的文件夹设置为指向一个含有错误格式文件的文件夹然后点击  Random Excuse  按钮。调试器应该会执行  try
块，然后在异常抛出的时候跳到  catch  块。执行完  catch  块中所有代码之后，会执行  finally  块。

  



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

