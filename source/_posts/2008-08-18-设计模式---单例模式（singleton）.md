---
title: 设计模式---单例模式（singleton）
date: 2008-08-18 18:44:00
tags: 设计模式读书笔记
---
设计模式---单例模式（singleton）


![c56fe019dbd71a62dbb4bd42.jpg](http://hiphotos.baidu.com/yansuochonglou/pic/item/c56fe019dbd71a62dbb4bd42.jpg)

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



