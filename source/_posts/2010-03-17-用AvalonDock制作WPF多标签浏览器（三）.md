---
title: 用AvalonDock制作WPF多标签浏览器（三）
date: 2010-03-17 22:37:00
tags: 杂7杂8
---

 昨天遇到了一个  Bug  ，如果在浏览器中打开多个标签，并把其中一个标签拖拽到主窗口的一侧来划分出独立的一个区域，然后在新区域中通过点击加号键添加的新标签内不会被添加上  WebBrowser  。  

 说得好绕嘴啊，截张图吧：  

 ![](http://images.cnblogs.com/cnblogs_com/cuipengfei/2010-03-17_21-21-44.jpg)

 而且没有加上  WebBrowser  的标签的标题是  new content  （我们在  AvalonDock  中给新标签的默认标题）而不是  New Tab  （客户代码中重新赋的值）。  

 OK  ，问题明了了，是不是新添加的标签的  Got_Focus  没能够挂到客户代码中的方法上去呢？  

 的确是这样，当我们把一个标签（  DocumentContent  ）拖拽到一侧从而划分出一个新区域的时候，  AvalonDock  会创建一个新的  DocumentPane  来代表这个新区域。  

 我们浏览器中默认的  DocumentPane  是在  XAML  中声明的，声明时其  NewContentAdded  

 事件就挂到了事件响应方法DocumentPane_NewContentAdded上。  

 而这个新区域中的  DocumentPane  是在  AvalonDock  内部的  DocumentFloatingWindow类中创建的，其事件自然没有挂上来。  

 知道了这些，解决方案自然就有了。  

 在DocumentFloatingWindow中添加如下事件：  

 public static event EventHandler<NewDocumentPaneAddedByMouseEventArgs> NewDocumentPaneAddedByMouse;

 其中用到的事件参数定义如下：  

     public class NewDocumentPaneAddedByMouseEventArgs : EventArgs {
        public NewDocumentPaneAddedByMouseEventArgs(DocumentPane addedPane) { AddedPane = addedPane; }
        public DocumentPane AddedPane { get; private set; }
      }

 依然尊规范在DocumentFloatingWindow中定义如下方法来触发事件：  

 private void OnNewDocumentPaneAddedByMouse(NewDocumentPaneAddedByMouseEventArgs args) { if (NewDocumentPaneAddedByMouse != null) { NewDocumentPaneAddedByMouse(this, args); } }

 并在DocumentFloatingWindow中的ClonePane方法（鼠标拖拽时创建新  DocumentPane  的工作就是在这个方法中做的）中调用该方法来触发事件，修改后的  ClonePane方法是这样的：  

 public override Pane ClonePane() { DocumentPane paneToAnchor = new DocumentPane(); OnNewDocumentPaneAddedByMouse(new NewDocumentPaneAddedByMouseEventArgs(paneToAnchor)); ResizingPanel.SetEffectiveSize(paneToAnchor, new Size(Width, Height)); while (HostedPane.Items.Count > 0) { paneToAnchor.Items.Add( HostedPane.RemoveContent(0)); } return paneToAnchor; }

 我们只关心其中的OnNewDocumentPaneAddedByMouse(new NewDocumentPaneAddedByMouseEventArgs(paneToAnchor));  这一句就OK了。  

 好了，现在每当因鼠标拖拽而创建出一个新的  DocumentPane  时，都有一个事件会被触发，而且其传递的事件参数中还含有对新添加的  DocumentPane  实例的引用。这样订阅事件的地方（比如说我们的客户代码中）就可以通过该引用来把新添加的  DocumentPane  的  NewContentAdded事件挂到某个方法上了（当然就是我们的DocumentPane_NewContentAdded方法了）。  

 接下来修改客户代码吧：  

 在浏览器窗口的构造方法中添加下面一句：  

 DocumentFloatingWindow.NewDocumentPaneAddedByMouse += (object sender, NewDocumentPaneAddedByMouseEventArgs e) => { e.AddedPane.NewContentAdded += DocumentPane_NewContentAdded; };

 用了  lambda  表达式，有点长，不过的确还只是一句啊。  

 这样每个通过鼠标拖拽出来的  DocumentPane  就和我们在  XAML  中声明的  DocumentPane  没什么两样了，它们的  NewContentAdded 事件都挂到了DocumentPane_NewContentAdded方法上，这个方法做什么的来着？它做的就是给每一个新标签中置入一个新的  WebBrowser  。  

 好了，现在再运行一下，之前的问题不见了。  

 另外，如果你在使用  Win7  的话，把某个新标签拖拽出窗口，右击，选择  Floating  

 ![](http://images.cnblogs.com/cnblogs_com/cuipengfei/2010-03-17_21-58-17.jpg)

 然后就可以把这个拖拽出来的标签  Dock  到屏幕的一侧了  

 ![](http://images.cnblogs.com/cnblogs_com/cuipengfei/2010-03-17_21-59-16.jpg)

 那个玻璃化的框框好漂亮啊，呵呵。  

 好了，到现在为止我们的多标签浏览器基本就运转起来了。如果您发现其中隐含的  Bug  或者不妥之处请不吝赐教哈！  

 另外，  AvalonDock  有两套  Theme  ，我们之前的  Restyle  只修改了  DocumentPaneStyles.xaml  ，要在  Win7  下看到想要的效果还要对  aero.normalcolor.xaml  做同样的修改。  

 好了，  Over and out  ！  

代码下载： [ http://download.csdn.net/source/2137819
](http://download.csdn.net/source/2137819)
