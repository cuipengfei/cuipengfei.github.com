---
title: 对Graphic的应用，实现类似九宫格的绘制字符串、制定圆心，半径绘制圆形、用火柴棍拼字[Java ME]
date: 2008-10-03 10:26:00
tags: 杂7杂8
---
![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081003/%E6%88%AA%E5%9B%BE03.jpg) ![](https://p-blog.csdn.net/images/p_blog_csdn
_net/cuipengfei1/EntryImages/20081003/%E6%88%AA%E5%9B%BE02.jpg) ![](https://p-
blog.csdn.net/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081003/%E6%88%A
A%E5%9B%BE01.jpg) ![](https://p-blog.csdn.net/images/p_blog_csdn_net/cuipengfe
i1/EntryImages/20081003/%E6%88%AA%E5%9B%BE00.jpg)

  1.   2. import  javax.microedition.midlet.*; 
  3. import  javax.microedition.lcdui.*; 
  4.   5. public  class  HomeWork  extends  MIDlet  implements  CommandListener { 
  6.   7. Display d=Display.getDisplay(  this  ); 
  8.   9. Form f=  new  Form(  "主窗口"  ); 
  10.   11. Command back=  new  Command(  "返回"  ,Command.BACK,  1  ); 
  12. Command exit=  new  Command(  "退出"  ,Command.EXIT,  1  ); 
  13. Command drs=  new  Command(  "绘制字符串"  ,Command.OK,  1  ); 
  14. Command drc=  new  Command(  "画圆"  ,Command.OK,  1  ); 
  15. Command drn=  new  Command(  "火柴棍拼字"  ,Command.OK,  1  ); 
  16.   17. //用于画圆的 
  18. public  class  CircleCanvas  extends  Canvas 
  19. { 
  20. int  pointX,pointY,len; 
  21. public  void  paint(Graphics g) 
  22. { 
  23. g.setColor(  255  ,  0  ,  0  ); 
  24. g.fillArc(pointX, pointY, len, len,  0  ,  360  ); 
  25.   26. g.drawRect(pointX, pointY, len, len);  //框住了就代表对了 
  27. } 
  28. public  void  drawCircle(  int  x,  int  y,  int  r) 
  29. { 
  30. pointX=x-r; 
  31. pointY=y-r; 
  32. len=  2  *r; 
  33. } 
  34. } 
  35.   36. //用于在指定位置绘制字符串 
  37. public  class  MyCanvas  extends  Canvas 
  38. { 
  39. String printword=  ""  ; 
  40. int  [] myenum={  0  ,  0  , Graphics.LEFT|Graphics.TOP, 
  41. getWidth()/  2  ,  0  ,Graphics.HCENTER|Graphics.TOP, 
  42. getWidth(),  0  , Graphics.RIGHT|Graphics.TOP, 
  43.   44. 0  , getHeight()/  2  , Graphics.LEFT|Graphics.TOP 
  45. , getWidth()/  2  ,getHeight()/  2  ,  Graphics.HCENTER|Graphics.TOP 
  46. , getWidth(), getHeight()/  2  , Graphics.RIGHT|Graphics.TOP 
  47.   48. ,  0  , getHeight(), Graphics.LEFT|Graphics.BOTTOM 
  49. , getWidth()/  2  ,getHeight(),  Graphics.HCENTER|Graphics.BOTTOM 
  50. , getWidth(), getHeight(), Graphics.RIGHT|Graphics.BOTTOM 
  51. }; 
  52. int  [] position=  new  int  [  3  ]; 
  53. public  void  paint(Graphics g) 
  54. { 
  55. g.setColor(  255  ,  0  ,  0  ); 
  56. g.drawString(printword,position[  0  ],position[  1  ],position[  2  ]); 
  57. } 
  58. public  void  DrawMyStrin(String s,  int  pos) 
  59. { 
  60. printword=s; 
  61. position[  0  ]=myenum[pos*  3  \-  3  ]; 
  62. position[  1  ]=myenum[pos*  3  \-  2  ]; 
  63. position[  2  ]=myenum[pos*  3  \-  1  ]; 
  64. } 
  65. } 
  66.   67. //用火柴棍拼字 
  68. public  class  MatchNum  extends  Canvas 
  69. { 
  70. int  [][] lines={ 
  71. {  100  ,  100  ,  120  ,  100  },  //0 
  72. {  100  ,  120  ,  120  ,  120  },  //1 
  73. {  100  ,  140  ,  120  ,  140  },  //2 //三个横 
  74.   75. {  100  ,  100  ,  100  ,  120  },  //3 
  76. {  100  ,  120  ,  100  ,  140  },  //4//左边的两竖 
  77.   78. {  120  ,  100  ,  120  ,  120  },  //5 
  79. {  120  ,  120  ,  120  ,  140  }};  //6//右边的两竖 
  80.   81. int  [][] nums={ 
  82. {  0  ,  2  ,  3  ,  4  ,  5  ,  6  },  //0 
  83. {  5  ,  6  },  //1 
  84. {  0  ,  1  ,  2  ,  5  ,  4  },  //2 
  85. {  0  ,  1  ,  2  ,  5  ,  6  },  //3 
  86. {  5  ,  6  ,  1  ,  3  },  //4 
  87. {  0  ,  1  ,  2  ,  3  ,  6  },  //5 
  88. {  3  ,  4  ,  0  ,  1  ,  2  ,  6  },  //6 
  89. {  0  ,  5  ,  6  },  //7 
  90. {  0  ,  1  ,  2  ,  3  ,  4  ,  5  ,  6  },  //8 
  91. {  0  ,  1  ,  3  ,  5  ,  6  ,  2  },  //9 
  92. }; 
  93. int  [] temp; 
  94. public  void  drawMyNum(  int  n) 
  95. { 
  96. temp=nums[n]; 
  97. } 
  98. public  void  paint(Graphics g) 
  99. {g.setColor(  255  ,  0  ,  0  ); 
  100. int  i=temp.length; 
  101. for  (  int  j=  0  ;j<=i-  1  ;j++) 
  102. { 
  103. int  [] a=lines[temp[j]]; 
  104. g.drawLine(a[  0  ],a[  1  ],a[  2  ],a[  3  ]); 
  105. } 
  106.   107. } 
  108. } 
  109.   110.   111. public  void  startApp() { 
  112. f.addCommand(exit); 
  113. f.addCommand(drs); 
  114. f.addCommand(drc); 
  115. f.addCommand(drn); 
  116. f.setCommandListener(  this  ); 
  117. d.setCurrent(f); 
  118. } 
  119.   120. public  void  commandAction(Command c,Displayable dis) 
  121. { 
  122. if  (c==exit) 
  123. destroyApp(  false  ); 
  124.   125. else  if  (c==drs) 
  126. {   MyCanvas m=  new  MyCanvas(); 
  127. m.addCommand(back); 
  128. m.setCommandListener(  this  ); 
  129. m.DrawMyStrin(  "字符串"  ,  new  java.util.Random().nextInt(  8  )+  1  ); 
  130. d.setCurrent(m);} 
  131.   132. else  if  (c==drc) 
  133. { 
  134. CircleCanvas cc=  new  CircleCanvas(); 
  135. cc.addCommand(back); 
  136. cc.setCommandListener(  this  ); 
  137. cc.drawCircle(  60  ,  60  ,  40  ); 
  138. d.setCurrent(cc); 
  139. } 
  140.   141. else  if  (c==drn) 
  142. { 
  143. MatchNum mn=  new  MatchNum(); 
  144. mn.addCommand(back); 
  145. mn.setCommandListener(  this  ); 
  146. mn.drawMyNum(  new  java.util.Random().nextInt(  9  )); 
  147. d.setCurrent(mn); 
  148. } 
  149.   150. else  if  (c==back) 
  151. { 
  152. d.setCurrent(f); 
  153. } 
  154. } 
  155.   156. public  void  pauseApp() { 
  157. } 
  158.   159. public  void  destroyApp(  boolean  unconditional) { 
  160. notifyDestroyed(); 
  161. } 
  162. } 
  163. 

[ ![](https://profile.csdnimg.cn/5/2/5/3_cuipengfei1)
![](https://g.csdnimg.cn/static/user-reg-year/1x/11.png)
](https://blog.csdn.net/cuipengfei1)



发布了127 篇原创文章  ·  获赞 8  ·  访问量 74万+

