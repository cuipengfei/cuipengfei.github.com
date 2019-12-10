---
title: 送给所有需要学英语的朋友—Words Via Subtitle
date: 2010-10-06 16:50:00
tags: 杂7杂8
---
从名字就可以看出来，Words Via Subtitle是用来通过字幕学单词的辅助工具。它通过解析美剧或者电影的字幕文件把其中的生词取出，用网络上提供的开放
API或者本地词库解释生词。另外，程序还可以提供单词读音（机器发音），可以播放一个单词在剧中出现的语境，可以把所有生词串起来像幻灯片一样连续播放，也就是说显
示一个单词及其解释，然后用机器发音读该单词，然后播放该单词在剧中出现的句子，然后切换到下一个生词再重复这一过程。

这个程序去年就写过一个雏形，最近断断续续的重写了。这次主要的改进是将字幕的分析和单词的解释从主程序中抽出来做成插件，也就是说主程序部署到一台机器之后如果想要
增加对一种字幕格式的支持就只需要写一个插件拷到部署的机器上去就ok了，无需把整个程序重新编译、重新部署。同样，如果想要增加一种单词解释器也可以通过添加一个插
件完成，比如说现在只有一个很小的本地词库和一个网络释义，之后有人想给这个程序增加一个Google词典的解释器就可以写一个插件来完成。这次重写用了WPF、ME
F，以及Ribbon Controls等最近接触的新东西，不过在这儿暂时不介绍程序是怎么写，也不说字幕解析插件和解释提供器插件如何编写，先简单介绍一下各功能
的使用吧。对代码感兴趣的朋友可以去CodePlex上去搜索WVS（Words Via Subtitle的简写），我把这个程序作为一个开源项目上传到那儿了
，用的MS-PL协议，完全开源哈。

下面就开始说一下大概怎么用这个程序吧。

运行程序，用左上角的Open按钮（ [ ![2010-10-24_15-15-25](http://hi.csdn.net/attachment/20101
0/24/0_1287907696T90H.gif)
](http://hi.csdn.net/attachment/201010/24/0_12879076954nrG.gif)
）打开一个字幕文件（现在可以支持srt和ass格式的，当然以后可以通过插件来添加对更多种类的字幕的支持）。

[ ![2010-10-24_15-12-10](http://hi.csdn.net/attachment/201010/24/0_1287907704b
dDE.gif) ](http://hi.csdn.net/attachment/201010/24/0_1287907700gCeG.gif)

上图中右下角可选的文件格式会随着插件的增多而增多。

加载字幕文件之后程序会把字幕中出现的生词列到一个列表中（如何判断一个单词是不是生词呢？程序需要通过一个已知单词列表来比对，这个列表需要使用者自己慢慢的“养”
。第一次运行程序会把字幕中出现的所有单词，无论难易、常见还是生僻统统的列出来，这时需要使用者自己把认识的单词标记一下，这样这个单词在下次打开别的字幕文件时就
不会再蹦出来烦您了），程序看起来是这样的：

[ ![2010-10-24_15-01-29](http://hi.csdn.net/attachment/201010/24/0_1287907713G
tId.gif) ](http://hi.csdn.net/attachment/201010/24/0_1287907709mQwJ.gif)

在左侧的列表里切换单词，右侧区域的解释就会跟着切换。右侧区域的解释可以在多个解释提供器间切换（可切换的解释提供器的数量取决于插件的数量），现在有两个选择：D
ict.cn的解释和一个很小的本地词库，上图中用的是Dict.cn，切换到本地词库是这样的：

[ ![2010-10-24_15-05-11](http://hi.csdn.net/attachment/201010/24/0_12879077190
vv0.gif) ](http://hi.csdn.net/attachment/201010/24/0_128790771673Eq.gif)

标有Choose Language的Ribbon Group（ [ ![2010-10-24_15-08-55](http://hi.csdn.net/at
tachment/201010/24/0_12879077217ns1.gif)
](http://hi.csdn.net/attachment/201010/24/0_1287907720Hs1z.gif)
）中的按钮用于切换翻译提供器，其数量会随着插件的增多而增多。

顺着Ribbon菜单向右走，标为Operations的Ribbon Group里有两个按钮（ [ ![2010-10-24_15-21-50](http:/
/hi.csdn.net/attachment/201010/24/0_1287907722PB1b.gif)
](http://hi.csdn.net/attachment/201010/24/0_1287907721nkeH.gif) ），Known用于把一个单词
标记为已知的，这样该单词就会被记录到已知单词列表中，像前文中说的那样，被“养”起来了。Misspelled用于把字幕中拼错的单词从列表中踢出去，这样在使用S
lide Show功能（这个稍后介绍）的时候就不会受到错误单词的干扰了。

菜单中再向右是标为Video的Ribbon Group（ [ ![2010-10-24_15-24-16](http://hi.csdn.net/attac
hment/201010/24/0_1287907723rx99.gif)
](http://hi.csdn.net/attachment/201010/24/0_12879077225gx7.gif)
），第一个按钮用于选择和当前字幕对应的视频，第二个用于播放当前在列表中选中的单词在剧中出现的语境。播放时界面看起来是这样的：

[ ![2010-10-24_15-27-43](http://hi.csdn.net/attachment/201010/24/0_1287907732b
khG.gif) ](http://hi.csdn.net/attachment/201010/24/0_1287907727EHMF.gif)

能够支持的视频格式取决于部署机器上装过的解码器，应该是Windows Media Player能播放什么这儿就能播放什么。播放完之后自动切换回单词解释界面。

继续向右走，是用于发音的两个按钮（ [ ![2010-10-24_15-31-56](http://hi.csdn.net/attachment/20101
0/24/0_12879077336SN1.gif)
](http://hi.csdn.net/attachment/201010/24/0_12879077331c6W.gif)
），很明显左侧的用于读出当前单词，右侧的列表用于选择发音风格（其中选项的多少取决于部署机安装过语音引擎，Win7默认会有两个，其他系统没试过）。

最右侧的就是这次重写后我自己最喜欢的功能了，Slide Show（ [ ![2010-10-24_15-36-42](http://hi.csdn.net/
attachment/201010/24/0_1287907734druS.gif)
](http://hi.csdn.net/attachment/201010/24/0_12879077331mu7.gif) ）。它用于把列表中所有的生词
串联成幻灯片一样播放。点击该按钮之后，程序会切换到全屏，显示一个单词及其解释，读出该单词的读音，播放该单词对应的语境，然后切换到下一个生词再重复上述过程。这
一步就不截图了，我把一些播放Slides Show的过程录下来放到了VeryCD上了（ [
http://www.verycd.com/topics/2859418/ ](http://www.verycd.com/topics/2859418/)
）。

程序现有的功能就是这些了，如果您有好主意、建议请留言哈。

对程序代码有兴趣的朋友可以去CodePlex看看： [ http://wvs.codeplex.com/
](http://wvs.codeplex.com/) ，如果您发现代码中有什么不妥的地方还请不吝赐教哈。

想要用这个程序的朋友可以去这几个地址看看：

1 [ http://blog.csdn.net/cuipengfei1/archive/2009/09/03/4516588.aspx
](http://blog.csdn.net/cuipengfei1/archive/2009/09/03/4516588.aspx)

2 [ http://blog.csdn.net/cuipengfei1/archive/2009/09/10/4539180.aspx
](http://blog.csdn.net/cuipengfei1/archive/2009/09/10/4539180.aspx)

3 [ http://blog.csdn.net/cuipengfei1/archive/2009/09/17/4564389.aspx
](http://blog.csdn.net/cuipengfei1/archive/2009/09/17/4564389.aspx)

上面的三个链接地址是我去年写的那个雏形，功能大致和本文介绍的新版一致，但是只需要.NET 2.0就可以运行，新版的是一个发烧友式的开源项目，target
framework是.NET 4的。

程序使用介绍就到这里，字幕解析插件和翻译提供器插件的编写下次再写。

祝您背单词顺利！

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

