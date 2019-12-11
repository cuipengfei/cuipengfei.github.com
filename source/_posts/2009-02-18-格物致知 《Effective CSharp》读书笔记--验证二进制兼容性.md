---
title: "格物致知 《Effective C#》读书笔记--验证二进制兼容性"
date: 2009-02-18 13:27:00
tags: 格物致知 《Effective C#》读书笔记
---
昨天读了《Effective C#》的第一个条款“使用属性代替可访问的数据成员”，讲到要把公有字段修改为私有字段，并用公有属性把它封装起来。这一点不难理解，
不过里面提到了一个关于二进制兼容性的问题，很是有趣，今天来验证一下。

验证思路：创建一个类库内有一个public的类，该类内有一个public的字段。另外创建一个WinForm程序去读区该字段并显示。然后修改类库中的字段为属性
。再去运行WinForm，就应该会出错了。出错具体原因请参看《Effective C#》讲解。

开始吧！

创建一个ClassLibrary，叫做TheDLL。代码如下：

```
public class DataHolder
{
    public String Data = "Hey! Hey!You!You!";
}
```


用它生成一个dll。

然后创建一个WindowsFormsApplication，叫做TheForm，上有一个按钮，点击按钮就去读取dll中的数据，把它显示在按钮上。具体代码如下
：

```
public partial class TheForm : Form

{
    public TheForm()

    {
        InitializeComponent();
    }

    private void button1_Click(object sender, EventArgs e)

    {
        TheDLL.DataHolder DH = new TheDLL.DataHolder();

        button1.Text = DH.Data;
    }
}
```


运行，点击按钮，效果如下：


![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090218/2009-02-18_13-07-45.jpg)


然后修改  TheDLL  的代码为如下：

```
public class DataHolder

{
    private String data = "Hey! Hey!You!You!";

    public String Data

    {
        get { return data; }

        set { data = value; }
    }
}
```


重新生成dll，把新生成的dll复制到TheForm的debug文件夹下去覆盖原来的dll文件。然后双击运行TheForm.exe。结果如下：

![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20090218/2009-02-18_13-11-29.jpg)

提示找不到TheDLL.DataHolder.Data。没错!这就是因为访问字段和访问属性的C#代码虽然一样，都是

```
TheDLL.DataHolder DH = new TheDLL.DataHolder();
button1.Text = DH.Data;
```

但是生成的MSIL是不一样的，这就造成了二进制不兼容。

注意，不要去IDE中运行TheForm，因为那样会重编译TheForm，就会看不到二进制不兼容造成的结果了。

另外，即使在TheForm中用try -catch来把访问dll的代码包含起来，如下：

```
public partial class TheForm : Form

{
    public TheForm()

    {
        InitializeComponent();
    }

    private void button1_Click(object sender, EventArgs e)

    {
        try

        {
            TheDLL.DataHolder DH = new TheDLL.DataHolder();

            button1.Text = DH.Data;
        }

        catch (Exception)

        {
        }
    }
}
```

还是会发生上图报错的结果。



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)




