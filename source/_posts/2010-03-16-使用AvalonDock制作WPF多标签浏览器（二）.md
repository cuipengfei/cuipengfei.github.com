---
title: 使用AvalonDock制作WPF多标签浏览器（二）
date: 2010-03-16 21:19:00
tags: WPF
---

闲话少叙，书接上文。

现在我们已经通过  ReStyle  给  DocumentPane  加上了一个加号的按钮，并且可以通过点击该按钮给  DocumentPane  的
Items  添加一个  DocumentContent  了。

不过每个新添加进来的  DocumentContent  内部都是空的，而我们需要的是每个新标签中都有一个  WebBrowser
，要实现这一点很简单，只要给  DocumentContent  的  Content  （  DocumentContent  是
ContentControl  的子类）属性赋值为一个  WebBrowser  的实例就  OK  了。

但是这不应该是  AvalonDock  的默认行为，所以我们要把这部分写到客户端  --  也就是引用  AvalonDock.dll
文件或者直接引用  AvalonDock  工程的  Solution  中去。

如何可以在客户端得知有一个新的  DocumentContent  被添加进  DocumentPane  中去了呢？自然是用事件了。

首先来写一个自定义的  EventArgs  吧：
```
public class NewContentAddedEventArgs : EventArgs
{
    public
        NewContentAddedEventArgs(DocumentContent addedContent)
    {
        AddedContent =
            addedContent;
    }

    public DocumentContent AddedContent { get; private set; }
}
```

这个名为  NewContentAddedEventArgs  的事件参数的构造方法会要求一个  DocumentContent
，其实也就是被添加的那个新标签了。

这样订阅其对应事件的客户代码就可以得到一个指向新添加的标签的引用，当然就可以将其  Content  设置为一个新的  WebBrowser  的实例了。

接下来在  DocumentPane  中定义一个使用刚写好的事件参数的事件：
```
public event EventHandler<NewContentAddedEventArgs> NewContentAdded;
```

接下来，遵照规范写一个  OnNewContentAdded  方法，以免在没人订阅事件的时候试图触发事件而抛异常：
```
    private void OnNewContentAdded(NewContentAddedEventArgs args)
    {
        if
            (NewContentAdded != null)
        {
            NewContentAdded(this, args);
        }
    }
```

再然后在我们之前写的  AddNew  方法中调用  OnNewContentAdded  方法就  OK  了，修改后的  AddNew  方法是这样的：
```
    private void AddNew()
    {
        DocumentContent newContent = new DocumentContent();
        newContent.Title = "new content";
        newContent.IsFloatingAllowed = true;
        Items.Add(newContent);
        OnNewContentAdded(new
            NewContentAddedEventArgs(newContent));
    }
```

然后就可以开始着手写客户代码了。

首先在  XAML  中添加对  AvalonDock  的引用：

```
xmlns:Avalon="clr-namespace:AvalonDock;assembly=AvalonDock"
```

然后再在主体中添加如下代码：

```
<Avalon:DockingManager>
    <Avalon:DocumentPane Name="mainPane"
                         NewContentAdded="DocumentPane_NewContentAdded">
        <Avalon:DocumentContent
                Title="Default Tab" GotFocus="DocumentContent_GotFocus" IsCloseable="False"
                IsFloatingAllowed="True">
            <WebBrowser Name="defaultBrowser"
                        Source="http://www.google.cn/webhp?hl=zh-CN"></WebBrowser>
        </Avalon:DocumentContent>
    </Avalon:DocumentPane>
</Avalon:DockingManager>
```

其中的  DockingManager  是  AvalonDock  中的“总管”，其详细使用方法请看：  [
http://avalondock.codeplex.com/  ](http://avalondock.codeplex.com/)

上面的代码中可以看到，我们给  DocumentPane  新加的  NewContentAdded  事件已经挂到了一个叫做
DocumentPane_NewContentAdded  的方法上。该方法的定义很简单：

```
    private void DocumentPane_NewContentAdded(object sender,
        NewContentAddedEventArgs e)
    {
        DocumentContent newContent = e.AddedContent;
        newContent.Title = "New Tab";
        newContent.GotFocus += DocumentContent_GotFocus;
        newContent.Content = new WebBrowser
        {
            Source = new
                Uri(@"http://www.google.cn")
        };
    }
```

就是把一个打开  Google  首页的  WebBrowser  实例赋值给新添加的标签页的  Content  属性。

里面还有一句：
```
newContent.GotFocus += DocumentContent_GotFocus;
```

其中的  DocumentContent_GotFocus  定义如下：

```
    private void DocumentContent_GotFocus(object sender, RoutedEventArgs e)
    {
        DocumentContent selectedOne = s as DocumentContent;
        if (selectedOne != null)
        {
            WebBrowser browser = selectedOne.Content as WebBrowser;
            if (browser != null &&
                browser.Source != null)
            {
                currentBrowser = browser;
                url.Text =
                    browser.Source.ToString();
            }
        }
    }
```

这是做什么用的呢？

我们用浏览器打开多个标签页的时候程序中就会存在多个  WebBrowser  的实例，这时如果在地址栏中输入一个地址并回车的话，怎么知道到底应该把哪个
WebBrowser  重定向到输入的地址呢？

所以程序中应该有一个  WebBrowser  的引用，假设叫做  currentBrowser  吧，它始终指向当前选中标签中的  WebBrowser
实例。

上面的代码做的就是这个工作，当一个标签页得到焦点的时候，就让  currentBrowser  指向该标签页中的  WebBrowser
实例，并把一个叫做  url  的  TextBox  （也就是地址栏了）的  Text  属性设置为当前  Browser  打开的地址  。

当然，这个叫做  url  的地址栏的  KeyDown  事件的处理方法中应该把  currentBrowser
重定向到输入的网址，这段代码很简单，就不贴了。

现在运行一下，似乎  OK  了，真的完事儿了吗？

呵呵，没有。

试一下打开两个标签，把其中的第二个拖拽到主区域的右侧去，从而将主区域一分为二：

![](http://images.cnblogs.com/cnblogs_com/cuipengfei/2010-03-16_20-50-08.jpg)

在右侧新分化出来的区域中点击加号小按钮新建另一个标签，切换到新标签。

啊哦  ~~~~~

![](http://images.cnblogs.com/cnblogs_com/cuipengfei/2010-03-16_20-51-40.jpg)

新标签是空的，不是明明已经在每个新标签被添加时给其中加上一个  WebBrowser  了吗？咋没有捏？这究竟是哪儿来的  Bug  呢？

再注意观察一下，这个新标签的标题是  new content  而不是  New Tab  ，呵呵，是不是已经猜到了呢？



其实这是和  AvalonDock  中的另一个类  --  DocumentFloatingWindow  有关的问题。



怎么解决这个问题明天再说吧。

Over and out  ！
