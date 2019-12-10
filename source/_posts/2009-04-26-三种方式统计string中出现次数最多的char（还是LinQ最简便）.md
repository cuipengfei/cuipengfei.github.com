---
title: 三种方式统计string中出现次数最多的char（还是LinQ最简便）
date: 2009-04-26 16:28:00
tags: undefined
---
using System;  
using System.Collections.Generic;  
using System.Text;  
using System.Linq;  
using System.Collections;  
  
namespace ConsoleApplication1  
{  
  
class Program  
{  
static void Main(string[] args)  
{  
string str = "aaeaabbebbccececddeddd";  
  
List<char> charList = new List<char>(str.ToCharArray());  
int len = charList.Count;  
  
Dictionary<char, int> charD = new Dictionary<char, int>();  
  
int lastTime = 0;  
  
while (charList.Count != 0)  
{  
int count = CountAChar(charList);  
  
if (count > lastTime)  
{  
charD.Clear();  
charD.Add(charList[0], count);  
lastTime = count;  
}  
  
else if (count == lastTime)  
{  
charD.Add(charList[0], count);  
lastTime = count;  
}  
  
DeleteAChar(count, charList);  
}  
  
foreach (KeyValuePair<char, int> kv in charD)  
{  
Console.WriteLine("{1}有{0}个", kv.Value.ToString(), kv.Key.ToString());  
}  
  
Console.WriteLine("----------------------------------------");  
SecondProgram.SecondWay(str);  
Console.WriteLine("----------------------------------------");  
ThirdClass.ThirdWay(str);  
Console.Read();  
}  
  
static void DeleteAChar(int num, List<char> cl)  
{  
char c = cl[0];  
  
for (int i = 0; i < num; i++)  
{  
cl.Remove(c);  
}  
}  
  
static int CountAChar(List<char> cl)  
{  
int ret = 0;  
  
foreach (char c in cl)  
{  
if (c == cl[0])  
{  
ret++;  
}  
}  
  
return ret;  
}  
}  
  
//-----------------------------------------------------------------  
class SecondProgram  
{  
public static void SecondWay(string str)  
{  
int len = str.Length;  
char[] strArray = str.ToCharArray();  
int[] count = new int[len];  
for (int y = 0; y < len; y++) //将count数组的每个单元初始为0  
count[y] = 0;  
  
for (int y = 0; y < len; y++) //将当前字符与之后的字符进行比较，相同count数组中对应索引处++  
{  
for (int yy = y; yy < len; yy++)  
{  
if (strArray[y].Equals(strArray[yy]))  
{  
count[y]++;  
}  
}  
}  
  
ArrayList maxCountArr = new ArrayList();  
int maxCount = count[0];  
  
for (int y = 0; y < len; y++) //获得出现次数的最大值  
{  
if (count[y] > maxCount)  
{  
maxCount = count[y];  
}  
}  
for (int y = 0; y < len; y++)
//最大值与count数组中的每个数进行比较，相同则出现次数相同，把索引加入到maxCountArr  
{  
if (count[y] == maxCount)  
{  
maxCountArr.Add(y);  
}  
}  
for (int y = 0; y < maxCountArr.Count; y++) //输出  
{  
Console.Write("Str" + (y + 1) + ":" + strArray[(int)maxCountArr[y]] + "/n");  
}  
}  
}  
/// <summary>  
/// 第三种方式  
/// </summary>  
class ThirdClass  
{  
/// <summary>  
/// 还是LinQ最简便  
/// </summary>  
/// <param name="str"></param>  
public static void ThirdWay(string str)  
{  
var resultGroup = from aChar in str.ToCharArray()  
group aChar by aChar;  
  
int max = 0;  
  
foreach (var one in resultGroup)  
{  
if (one.Count() > 0)  
{  
max = one.Count();  
}  
}  
  
foreach (var one in resultGroup)  
{  
if (one.Count() == max)  
{  
Console.WriteLine("{0}字符出现了{1}次", one.Key, max);  
}  
}  
  
}  
}  
}  
  

  * [ 点赞  ](javascript:;)
  * [ 收藏  ](javascript:;)
  * [ 分享 ](javascript:;)

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)

[ 崔鹏飞 ](https://blog.csdn.net/cuipengfei1)

发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

