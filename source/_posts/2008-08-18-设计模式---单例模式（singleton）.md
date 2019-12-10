---
title: 设计模式---单例模式（singleton）
date: 2008-08-18 18:44:00
tags: 设计模式读书笔记
---
设计模式---单例模式（singleton）

![c56fe019dbd71a62dbb4bd42.jpg](http://hiphotos.baidu.com/yansuochonglou/pic/i
tem/c56fe019dbd71a62dbb4bd42.jpg)

using System;  
using System.Collections.Generic;  
using System.Text;

namespace ConsoleApplication1  
{  
class Singleton  
{  
private static Singleton s;

private Singleton()  
{  
  
}

public static Singleton GetInstance()  
{  
if (s == null)  
{  
s = new Singleton();  
}  
return s;  
}  
}

class Client  
{  
public static void Main()  
{  
Singleton s1 = Singleton.GetInstance();  
Singleton s2 = Singleton.GetInstance();  
if (object.ReferenceEquals(s1,s2))  
{  
Console.WriteLine("一样的");  
Console.WriteLine(s1);  
}  
Console.Read();  
}  
}  
}

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

