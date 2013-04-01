---
layout: post
title: "用豆瓣读书Chrome插件，简单购买电子书"
date: 2013-04-01 16:09
comments: true
categories: 
---

##为什么要做这款插件？

在豆瓣上查看一本书的时候，页面的右侧会显示哪些网站可以购买该书以及各自的价格。
比如这本《乡关何处》，页面右侧显示了亚马逊，京东，当当等网站的购买链接。

![乡关何处](http://farm9.staticflickr.com/8264/8609485540_026e013035_b.jpg)

但是豆瓣只会提供纸质书的购买链接，不提供电子书的。除非该书豆瓣自己有售。
所以我写了个Chrome的插件来解决这个问题。

##这款插件怎么用？

这款插件会在每个图书页面上添加正版电子书的购买链接及其价格。您只需点击链接去购买就好了。

在Chrome中加载这个插件之后，再访问豆瓣的《乡关何处》的页面，右侧会多出三个链接：

![乡关何处](http://farm9.staticflickr.com/8532/8609505800_fbd41bdc60_b.jpg)

淘宝，多看和亚马逊中文站都卖这本书的电子版。
看，纸质书要卖19.2，豆瓣电子书要卖11，多看只要6块钱，噢耶！节省纸张又省钱。

很多英文原版书在国内卖得很贵，所以这款插件也支持一些国外网站。
比如这本《The Pragmatic Programmer》：

![The Pragmatic Programmer](http://farm9.staticflickr.com/8247/8608418739_e4fe0b1ebb_b.jpg)

原版纸质书亚马逊要卖351，澜瑞外文要卖487，吃人啊！亚马逊英文站的纸质版的售价折合成人民币也要180多，再加运费......

而电子版则要便宜很多，看截图上的红圈，最便宜的折合人民币只要130左右，嗯，还是有点贵，不过如果想读原版的话，这个kindle版是最好的选择了。

这款插件现在支持以下的电子书销售商：

* 多看
* 唐茶
* 亚马逊kindle中文站
* 淘宝
* 京东
* 亚马逊kindle美国站
* kobo
* nook

##如何实现的？

是用JavaScript写的。

基本实现思路是这样的：

* 获取当前图书的ISBN号码
* 根据ISBN去上面罗列的几家网站上搜索是否有这本书
* 如果有的话，显示在右侧

例外情况：

* 有些网站不支持ISBN搜索，对于这些网站直接用书名和作者名搜索的，所以结果有时不太准。
* 有时一本书的纸质版的ISBN和电子版的ISBN不同，所以实现中用到了google books的API来获取电子版的ISBN，然后再搜索。

主要技术：
knockoutjs和jQuery。

##插件下载和源码

插件下载地址：[chrome store](https://chrome.google.com/webstore/detail/ebook-price-for-douban/ppbnlfplpcjhdphaejdfhbojmjifdjgd?hl=zh-CN)

源码：[github](https://github.com/cuipengfei/JavaScript-Practice-Code/tree/master/EBookPriceForDouBan)

##Disclaimer

插件的实现中违反了豆瓣API的协议（豆瓣不允许其API的使用者利用豆瓣的数据产生其他网站的购买信息）。如果您对这点很敏感，请慎用。