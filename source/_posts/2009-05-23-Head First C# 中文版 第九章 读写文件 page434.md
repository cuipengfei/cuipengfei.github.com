---
title: Head First C# 中文版 第九章 读写文件 page434
date: 2009-05-23 22:14:00
tags: 我翻译的Head First C#（习作）
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/200
90325/2009-03-25_13-04-01.jpg)

问：为什么我用完  File.ReadAllText  （）和  File.WriteAllText  （）之后不需要用  Close
（）方法来关闭文件？

  

答：  File
类中含有一些很有用的静态方法，它们自动的打开文件，读写数据，然后再自动关闭文件。这些方法都可以自动的打开和关闭流，所以你可以只用一行代码来完成文件操作了。

  

问：  FileStream  已经有读写数据的方法了，我为什么还要用  StreamReader  和  StreamWriter  呢？

  

答：  FileStream  用来读写二进制文件内的字节是很好的。它的读写方法都是处理字节和字节数组的。而有的程序只处理文本，这就用到
StreamWriter  和  StreamReader
了。它们的方法被构建来读写成行的文本。如果没有它们，你要从一个文件中读取一行文本的话，就需要读取一个字节数组出来然后再遍历数组来检查其中哪儿有换行符
\--  看到  StreamWriter  和  StreamReader  是如何减轻你的负担的了吧。

  

问：何时用  File  ，何时用  FileInfo  ？

  

答：它俩的区别就是  File  的方法都是静态的，而  FileInfo  需要以一个文件名来初始化。使用哪个要视情况而定。只做一个文件操作的话，用
File  。做一连串的文件操作的话，用  FileInfo  。

  

问：等一会。为什么“  Eureka  ！”写出的时候一个字符占用一个字节，但是写出希伯来字符的时候一个字符占用两个字节呢？还有开头的“  FF FE
”是什么意思？

  

答：这是两种密切相关的  Unicode  编码。英文字母，数字，普通符号等等字符的  Unicode  编码很低，从  0  到  127  。而用到
Unicode  编码值比较大的字符的时候就比较复杂了。文件需要告诉打开它的程序其中包含的内容是  Unicode
编码值比较大的字符。所以文件开头要有一个“  FF FE  ”。这叫做字节顺序标志。这样一个程序打开该文件的时候就会知道所有的字符都是用两个字节编码的。

  

问：为何叫做字节顺序标志呢？

  

答：还记得字节是怎么被反转的吗？  Shin  的  Unicode  值是  U+05E9  写入文件是  E9 05
。这叫做“小端”。回到把所有字节写入文件的  WriteAllText  （）方法，给它添加第三个参数：
Encoding.BigEndianUnicode  。这告诉该方法不要把字节反转。这会再看写出的文件就是“  05 E9
”了。你看到的字节顺序标志也会不同：“  FE FF  ”。而你的建议文本编辑器足够智能可以把这些都识别出来。

  

如果你要写出的字符串只有Unicode编码的低码值，写出的每个字符占用一个字节。但是如果其中包含高码值的字符，那写出的每个字符占用两个字节。

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

