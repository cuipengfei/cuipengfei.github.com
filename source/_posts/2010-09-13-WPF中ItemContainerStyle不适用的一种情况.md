---
title: WPF中ItemContainerStyle不适用的一种情况
date: 2010-09-13 23:55:00
tags: 杂7杂8
---
WPF中的ItemsControl定义了  ItemContainerStyle  这一属性，顾名思义，该属性用来给
ItemsControl中包含的每一个Item的容器定义样式  。

比如在ListBox中这个容器就是ListBoxItem，在TabControl中这个容器就是TabItem。

下面是  ItemContainerStyle  的一种简单应用：

XAML：

<Window ......> <StackPanel> <ListBox Name="itemsControl"
ItemsSource="{Binding}"> <ListBox.ItemContainerStyle> <Style
TargetType="ListBoxItem"> <Setter Property="IsSelected" Value="{Binding
IsSelected, Mode=OneTime}"/> </Style> </ListBox.ItemContainerStyle>
<ListBox.ItemTemplate> <DataTemplate> <TextBlock Text="{Binding Text}"/>
</DataTemplate> </ListBox.ItemTemplate> </ListBox> </StackPanel> </Window>

在这段  XAML中  定义了一个ListBox，在其ItemTemplate中有一个TextBlock绑定到数据实体的Text属性上。在其
ItemContainerStyle  中将其每个
Item的IsSelected属性绑定到数据实体的IsSelected上。其数据实体的生成在下面的代码中：

public partial class ComboBoxTest : Window { public ComboBoxTest() {
InitializeComponent(); itemsControl.DataContext = GetData(); } private object
GetData() { Collection<object> data = new Collection<object>(); for (int i =
1; i <= 10; i++) { data.Add(new { Text = i.ToString(), IsSelected = i == 5 });
} return data; } }

为了简单，没有单独定义实体类而是用了匿名对象。一共生成十个匿名实体，其中第五个的IsSelected设置为true，把这十个实体放入一个Collection
中赋值给控件的DataContext，这样XAML中对ItemsSource的绑定就会起效。当然，直接把这个Collection赋值给ItemsSource
也可以。

运行一下，结果和预期的一样，第五项被选中了。

![](http://hi.csdn.net/attachment/201009/13/858_12843928840kEB.jpg)

试试把XAML中的ListBox换成TabControl，更换之后的XAML如下：

<Window ......> <StackPanel> <TabControl Name="itemsControl"
ItemsSource="{Binding}"> <TabControl.ItemContainerStyle> <Style
TargetType="TabItem"> <Setter Property="IsSelected" Value="{Binding
IsSelected, Mode=OneTime}"/> </Style> </TabControl.ItemContainerStyle>
<TabControl.ItemTemplate> <DataTemplate> <TextBlock Text="{Binding Text}"/>
</DataTemplate> </TabControl.ItemTemplate> </TabControl> </StackPanel>
</Window>

仅仅是把ListBox换成了TabControl，把ListBoxItem换成了TabItem而已，C#代码没有改。试着运行一下，结果还是和预期的一样，第五
项会被选中。

![](http://hi.csdn.net/attachment/201009/13/858_12843928849j3S.jpg)

ListBox和TabControl都是间接继承自ItemsControl而直接继承自Selector的，那是不是所有Selector的子类都会有如上的行为
呢？

实际上不是，把Selector的另一个子类ComboBox拿出来试试。

仍然是只改XAML，不改C#代码，改完之后的XAML如下：

<Window ......> <StackPanel> <ComboBox Name="itemsControl"
ItemsSource="{Binding}"> <ComboBox.ItemContainerStyle> <Style
TargetType="ComboBoxItem"> <Setter Property="IsSelected" Value="{Binding
IsSelected, Mode=OneTime}"/> </Style> </ComboBox.ItemContainerStyle>
<ComboBox.ItemTemplate> <DataTemplate> <TextBlock Text="{Binding Text}"/>
</DataTemplate> </ComboBox.ItemTemplate> </ComboBox> </StackPanel> </Window>

运行之后的效果如下：

![](http://hi.csdn.net/attachment/201009/13/858_1284392885MblZ.jpg)

可见启动后没有任何选中项。而只有当用鼠标将ComboBox展开时第五项才会被选中。对这种现象，我的猜测是因为  ItemContainerStyle
只有在所有  Item加载之后才会生效，而ComboBox默认情况下并不会把其Items展示出来，所以直到用鼠标将ComboBox展开时才会有选中效果。

对这种情况有一个不太完美的解决方案，把C#代码中的GetData方法修改如下：

private object GetData() { Collection<object> data = new Collection<object>();
for (int i = 1; i <= 10; i++) { data.Add(new { Text = i.ToString() }); }
return new { Data = data, SelectedData = data[4] }; }

上面的代码中再次应用了匿名对象，把整个实体集合放入新的匿名对象中的Data属性，并把集合的第五项赋值给新的匿名对象的SelectedData属性。

然后修改XAML，把ComboBox的ItemsSource绑定到匿名对象的Data属性，把SelectedValue绑定到匿名对象的SelectedDat
a属性。修改后的XAML如下：

<Window ......> <StackPanel> <ComboBox Name="itemsControl"
ItemsSource="{Binding Data}" SelectedValue="{Binding SelectedData,
Mode=OneTime}"> <ComboBox.ItemTemplate> <DataTemplate> <TextBlock
Text="{Binding Text}"/> </DataTemplate> </ComboBox.ItemTemplate> </ComboBox>
</StackPanel> </Window>

再运行，启动效果如下：

![](http://hi.csdn.net/attachment/201009/13/858_1284393201CUCr.jpg)



[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

