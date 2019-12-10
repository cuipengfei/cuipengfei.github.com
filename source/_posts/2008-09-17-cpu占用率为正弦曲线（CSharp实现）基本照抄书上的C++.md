---
title: cpu占用率为正弦曲线（C#实现）基本照抄书上的C++
date: 2008-09-17 11:39:00
tags: 《编程之美——微软技术面试心得》的C#实现
---
internal值根据配置调整

  1. using  System; 
  2. using  System.Collections.Generic; 
  3. using  System.Linq; 
  4. using  System.Text; 
  5.   6. namespace  ConsoleApplication1 
  7. { 
  8. class  Program 
  9. { 
  10. static  void  Main(  string  [] args) 
  11. { 
  12. const  double  SPLIT = 0.01; 
  13. const  int  COUNT = 200; 
  14. const  double  PI = 3.14159265; 
  15. const  int  INTERVAL = 100; 
  16.   17.   18. double  [] busySpan =  new  double  [COUNT];  //array of busy times 
  19. double  [] idleSpan =  new  double  [COUNT];  //array of idle times 
  20. int  half = INTERVAL / 2; 
  21. double  radian = 0.0; 
  22. for  (  int  i = 0; i < COUNT; i++) 
  23. { 
  24. busySpan[i] = (  double  )(half + (Math.Sin(PI * radian) * half)); 
  25. idleSpan[i] = INTERVAL - busySpan[i]; 
  26. radian += SPLIT; 
  27. } 
  28. double  startTime = 0; 
  29. int  j = 0; 
  30. while  (  true  ) 
  31. { 
  32. j = j % COUNT; 
  33. startTime = Environment.TickCount; 
  34. while  ((Environment.TickCount - startTime) <= busySpan[j]) ; 
  35. System.Threading.Thread.Sleep((  int  )idleSpan[j]); 
  36. j++; 
  37. } 
  38.   39. } 
  40.   41.   42. } 
  43. } 
  44. 

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

