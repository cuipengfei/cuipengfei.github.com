---
title: "格物致知--《Effective C#》读书笔记--验证接口引用指向装箱后的值类型时无需拆箱"
date: 2009-07-29 09:01:00
tags: 格物致知 《Effective C#》读书笔记
---

欲验证的结论：  接口引用指向装箱后的值类型时无需拆箱  。

代码如下：

```
interface SomeInterface

{
    void MethodInInterface();
}

struct OneValueType : SomeInterface

{
    public void MethodInInterface()

    {
        Console.WriteLine("get called");
    }
}

class TestUnBox

{
    public static void Main()

    {
    }

    private static void CastStruct()

    {
        OneValueType ovt = new OneValueType();

        object o = ovt; //Box

        ((OneValueType) o).MethodInInterface();
    }

    private static void CastInterface()

    {
        OneValueType ovt = new OneValueType();

        object o = ovt; //Box

        ((SomeInterface) o).MethodInInterface();
    }
}
```

其中  OneValueType  是值类型，它实现了  SomeInterface  。

两个方法  CastStruct  和  CastInterface  分别把装箱之后的值类型转型为  OneValueType  和
SomeInterface  。预计的结果是第二个方法无需拆箱，查看  IL  来验证：

这是  CastStruct  （）：

![](/images/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090729/2009-07-29_08-47-04.jpg)

这是  CastInterface  （）：

![](/images/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090729/2009-07-29_08-47-18.jpg)

可见，第二个方法中没有  unbox  指令，结论得证。

2009  年  7  月  29  日


