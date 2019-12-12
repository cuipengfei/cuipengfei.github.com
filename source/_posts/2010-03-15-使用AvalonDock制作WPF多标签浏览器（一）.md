---
title: 使用AvalonDock制作WPF多标签浏览器（一）
date: 2010-03-15 22:31:00
tags: WPF
---

AvalonDock  是  CodePlex  上的一个开源项目，利用它可以很容易的做出类似于  VS  的  UI  效果。

下图是  AvalonDock  源码中自带的一个  Demo  ：
![](http://images.cnblogs.com/cnblogs_com/cuipengfei/2010-03-15_21-01-24.jpg)

我们可以用这款第三方控件为基础来制作多标签浏览器。

下面是最终效果图：

![](http://images.cnblogs.com/cnblogs_com/cuipengfei/2010-03-15_21-46-42.jpg)

甚至可以把其中一个标签拖出主窗体成为一个独立的窗口：

![](http://images.cnblogs.com/cnblogs_com/cuipengfei/2010-03-15_21-49-41.jpg)

是不是很像  VS2010  中新的  TextEditor  啊，呵呵。

但是观察一下常用的浏览器，比如  IE  ：

![](http://images.cnblogs.com/cnblogs_com/cuipengfei/2010-03-15_21-08-15.jpg)

Chrome  ：

![](http://images.cnblogs.com/cnblogs_com/cuipengfei/2010-03-15_21-06-04.jpg)

FireFox  ：

![](http://images.cnblogs.com/cnblogs_com/cuipengfei/2010-03-15_21-06-25.jpg)

它们都有一个添加新标签的按钮，但是  AvalonDock  的  DocumentPane  默认并没有新建一个  DocumentContent的按钮。

DocumentPane  和  DocumentContent  是  AvalonDock  中众多类型中的两个，  DocumentPane  是
DocumentContent  的父级容器，  DocumentContent  中则可以置入任何  UI  元素，比如说一个  WebBrowser
。

所以我们的第一步就从给  AvalonDock  的  DocumentPane  写一个添加新  DocumentContent  的按钮开始吧。

首先  ReStyle  ，从  AvalonDock  的源码中找到  DocumentPaneStyles.xaml  这个文件，定位到

```
<Button x:Name="PART_ShowContextMenuButton" DockPanel.Dock="Right" Width="18"
        Height="18" Style="{StaticResource PaneHeaderCommandStyle}"
        mce_Style="{StaticResource PaneHeaderCommandStyle}"
        Command="ad:DocumentPane.ShowDocumentsListMenuCommand">
    <ad:AlignedImage>
        <Image x:Name="ShowContextMenuIcon" Source="Images/PinMenu.png" Width="13"
               Height="13" Stretch="Uniform"/>
    </ad:AlignedImage>
</Button>
```

这段代码

紧接着这段代码添加如下代码：

```
<Button DockPanel.Dock="Right" Width="18" Height="18" Style="{StaticResource
PaneHeaderCommandStyle}" mce_Style="{StaticResource PaneHeaderCommandStyle}"
        Command="ad:DocumentPane.AddNewCommand">
    <ad:AlignedImage>
        <Image
                Source="Images/add.png" Width="13" Height="13" Stretch="Uniform"/>
    </ad:AlignedImage>
</Button>
```

其中的  add.png  是我从网上随便找的一个加号的图片；

其中的  ad:DocumentPane.AddNewCommand  是紧接下来我们要给  DocumentPane  添加的一个  Command  。

找到  DocumentPane.cs  文件并添加代码：

```
        public static RoutedCommand AddNewCommand = new RoutedCommand();

        private void
            ExecutedAddNewCommand(object sender, ExecutedRoutedEventArgs e)
        {
            AddNew();
        }

        private void AddNew()
        {
            DocumentContent newContent = new DocumentContent();
            newContent.Title = "new content";
            newContent.IsFloatingAllowed = true;
            Items.Add(newContent);
        }

        private void CanExecuteAddNewCommand(object sender,
            CanExecuteRoutedEventArgs e)
        {
            e.CanExecute = true;
        }
```

然后再在其  OnInitialized  方法中添加如下一句：

```
this  .CommandBindings.Add(  new  CommandBinding(AddNewCommand,
ExecutedAddNewCommand, CanExecuteAddNewCommand));
```

这样我们就给  DocumentPane  添加了一个加号按钮并把它和  AddNewCommand  这个命令联系了起来，点击按钮时我们添加的
AddNew  方法就会执行。由于  DocumentPane  是  WPF  中  Selector  的子类，而  Selector  又继承自
ItemsControl  ，所以  DocumentPane  会有一个  Items  属性，我们在  AddNew  方法中做的就是给其  Items
中塞进一个新的  DocumentContent  。

这时再次运行  AvalonDock  自带的  Demo  ，可以看见右侧有一个小加号按钮，点击按钮则可以新建标签页：

![](http://images.cnblogs.com/cnblogs_com/cuipengfei/2010-03-15_21-34-27.jpg)

OK  ！第一步搞定了。

但是每一个标签都是空的，下一步要给每个标签中添加一个  WebBrowser  。

是不是修改一下前面添加的  AddNew  方法，在里面  new up  一个  WebBrowser  呢，不太好，这样感觉不是在修改而是在破坏
AvalonDock  的源码，毕竟  AvalonDock  是一个控件库，添加一个新标签时，其默认行为就应该是让标签为空。

给每个新添加的空标签中置入一个  WebBrowser  应该是在客户代码中做的事儿。

这部分明天再写吧，洗洗去看  10-4 show  了。（挺好看的视频，讲  VS2010  和  .Net 4 new features  的：
[http://channel9.msdn.com/shows/10-4/  ](http://channel9.msdn.com/shows/10-4/)

Over and out  ！  Ciao  ！
