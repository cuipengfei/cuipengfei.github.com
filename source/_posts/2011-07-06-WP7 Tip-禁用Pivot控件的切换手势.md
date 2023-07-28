---
title: "WP7 Tip: 禁用Pivot控件的切换手势"
date: 2011-07-06 13:26:26
tags: C#
---
原文地址： [ http://www.imaginativeuniversal.com/blog/post/2010/12/09/WP7-Tip-disabling-the-Pivot-Control-swipe-gesture.aspx](http://www.imaginativeuniversal.com/blog/post/2010/12/09/WP7-Tip-disabling-the-Pivot-Control-swipe-gesture.aspx)

原文作者： [ James Ashley](http://www.imaginativeuniversal.com/blog/page/about.aspx)

在WP7社区中一个经常被问到的问题就是：在Pivot中放置了可以接受滑动手势的控件（比如说一个Slider）时，如何禁用Pivot控件本身内置的“用手指滑动
来切换视图”的功能呢？

对此问题，微软标准的答案是：你不应该这么做。这是“不好的做法”（Bad Practice），会造成用户体验的混淆。这种说法的前提是假设用户不会自己根据上下文
去思考，而总是预期“滑动”这一手势会在任何页面中都有一样的作用。这种答案听起来还不错，而且对于Pivot中内置Slider这种例子来说也很合理。况且，我们还
是可以把Slider纵向的放置在Pivot内的，那这个答案就显得更有道理了。

话又说回来，在WP7的TextBox中，我们可以用“按住并滑动”这一手势来操作光标在文本框内的位置。那么在Pivot控件中放置TextBox算不算是造成了不
好的用户体验呢？算不算是“不好的做法”（Bad
Practice）呢？我是不是应该想办法把TextBox也纵向放置呢？还有，ToggleSwitch控件（此控件来自于Silverlight for
Windows Phone Toolkit ）又该怎么办呢？

滑动这一手势对于手机来说是很常用的。很多针对WP7的新控件都会用到它。如果所有这些即将面世的新控件都不能放置在Pivot控件中的话，那就太可惜了。

本文接下来讲解如何在Pivot中放置一个横向放置的Slider，并让它正常运行。

简单来说，做成这件事的关键就是使用Pivot的IsHitTestVisible属性来禁用它的滑动手势。然后还要使用Touch类型的FrameReported
事件来决定何时去重新启用Pivot的滑动手势。

创建一个新工程，在页面中加入一个含有两个PivotItem的Pivot控件，并在其中一个PivotItem中加入一个Slider。

现在运行的话，你会发现用手指拨动Slider时，Slider中的Thumb可以被拖动，但是同时Pivot也会移动，甚至会切换到另一个视图去。

要解决这个问题，我们需要处理Slider的ManipulationStarted事件，在其中把Pivot的IsHitTestVisible这一属性设置为fa
lse，这样可以确保当手指在Slider上滑动时Pivot是被禁用掉的。

当滑动手势结束之后，我们需要重新启用Pivot。这件事不能在MouseLeftButtonUp事件的Handler里面做，因为当一个容器的IsHitTest
Visible被设为false时，它所包含的所有其他控件都无法触发MouseLeftButtonUp这一事件。我们可以把它放在ManipulationCom
pleted事件的Handler里面做，但是那样做却会导致一些不一致的行为。

排除上面两种方案之后，我们可以使用比较底层的Touch API，当“抬起”这个手势发生在Slider上的时候，把Pivot的IsHitTestVisible
属性设回为true。这个事件的Handler可以在页面的构造函数中挂上：



    Touch.FrameReported += (s, e) =>


    {


        if (e.GetPrimaryTouchPoint(slider1).Action == TouchAction.Up)


        {


            pivot1.IsHitTestVisible = true;


        }


    };

下面是相关的XAML代码：



    <!--ContentPanel - place additional content here-->


    <Grid x:Name="ContentPanel" Grid.Row="1" Margin="12,0,12,0">


        <controls:Pivot  HorizontalAlignment="Stretch" Margin="6,6,0,0"


                            Name="pivot1" Title="pivot"


                            VerticalAlignment="Top" Height="595">


            <controls:PivotItem Header="item1">


                <Grid>


                    <Slider  Height="107" HorizontalAlignment="Left"


                                Margin="-4,109,0,0" Name="slider1"


                                VerticalAlignment="Top" Width="460"


                                SmallChange="1"


                                Maximum="100"


                                Value="30"


                ManipulationStarted="slider1_ManipulationStarted" />


                </Grid>


            </controls:PivotItem>


            <controls:PivotItem Header="item2">


                <Grid />


            </controls:PivotItem>


        </controls:Pivot>


    </Grid>

下面是所需的后台代码：



    public MainPage()


    {


        InitializeComponent();


        Touch.FrameReported += (s, e) =>


        {


            if (e.GetPrimaryTouchPoint(slider1).Action == TouchAction.Up)


            {


                pivot1.IsHitTestVisible = true;


            }


        };


    }


     


    private void slider1_ManipulationStarted(object sender


        , ManipulationStartedEventArgs e)


    {


        pivot1.IsHitTestVisible = false;


    }

  * [ 点赞  10  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)






