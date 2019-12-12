---
title: 对Graphic的应用，实现类似九宫格的绘制字符串、制定圆心，半径绘制圆形、用火柴棍拼字[Java ME]
date: 2008-10-03 10:26:00
tags: graphics
---
![](/images/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081003/jietu03.jpg)

![](/images/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081003/jietu02.jpg)

![](/images/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081003/jietu01.jpg)

![](/images/images/p_blog_csdn_net/cuipengfei1/EntryImages/20081003/jietu00.jpg)

```
import  javax.microedition.midlet.*;
import  javax.microedition.lcdui.*;

public class HomeWork extends MIDlet implements CommandListener {
	Display d = Display.getDisplay(this);
	FormattedFloatingDecimal.Form f = new Form("主窗口");
	Command back = new Command("返回", Command.BACK, 1);
	Command exit = new Command("退出", Command.EXIT, 1);
	Command drs = new Command("绘制字符串", Command.OK, 1);
	Command drc = new Command("画圆", Command.OK, 1);
	Command drn = new Command("火柴棍拼字", Command.OK, 1);

	//用于画圆的
	public class CircleCanvas extends Canvas {
		int pointX, pointY, len;

		public void paint(Graphics g) {
			g.setColor(255, 0, 0);
			g.fillArc(pointX, pointY, len, len, 0, 360);
			g.drawRect(pointX, pointY, len, len);  //框住了就代表对了
		}

		public void drawCircle(int x, int y, int r) {
			pointX = x - r;
			pointY = y - r;
			len = 2 * r;
		}
	}

	//用于在指定位置绘制字符串
	public class MyCanvas extends Canvas {
		String printword = "";
		int[] myenum = {
			0, 0, Graphics.LEFT | Graphics.TOP,
			getWidth() / 2, 0, Graphics.HCENTER | Graphics.TOP,
			getWidth(), 0, Graphics.RIGHT | Graphics.TOP,
			0, getHeight() / 2, Graphics.LEFT | Graphics.TOP, getWidth() / 2,
			getHeight() / 2, Graphics.HCENTER | Graphics.TOP,
			getWidth(), getHeight() / 2, Graphics.RIGHT | Graphics.TOP, 0,
			getHeight(), Graphics.LEFT | Graphics.BOTTOM,
			getWidth() / 2,
			getHeight(), Graphics.HCENTER | Graphics.BOTTOM,
			getWidth(), getHeight(), Graphics.RIGHT | Graphics.BOTTOM
		};

		int[] position = new int[3];

		public void paint(Graphics g) {
			g.setColor(255, 0, 0);
			g.drawString(printword, position[0], position[1], position[2]);
		}

		public void DrawMyStrin(String s, int pos) {
			printword = s;
			position[0] = myenum[pos * 3 - 3];
			position[1] = myenum[pos * 3 - 2];
			position[2] = myenum[pos * 3 - 1];
		}
	}

	//用火柴棍拼字
	public class MatchNum extends Canvas {
		int[][] lines =

			{
				{
					100, 100, 120, 100
				},  //0
				{
					100, 120, 120, 120
				},  //1
				{
					100, 140, 120, 140
				},  //2 //三个横
				{
					100, 100, 100, 120
				},  //3
				{
					100, 120, 100, 140
				},  //4//左边的两竖
				{
					120, 100, 120, 120
				},  //5
				{
					120, 120, 120, 140
				}
			};  //6//右边的两竖
		int[][] nums =

			{
				{
					0, 2, 3, 4, 5, 6
				},  //0
				{
					5, 6
				},  //1
				{
					0, 1, 2, 5, 4
				},  //2
				{
					0, 1, 2, 5, 6
				},  //3
				{
					5, 6, 1, 3
				},  //4
				{
					0, 1, 2, 3, 6
				},  //5
				{
					3, 4, 0, 1, 2, 6
				},  //6
				{
					0, 5, 6
				},  //7
				{
					0, 1, 2, 3, 4, 5, 6
				},  //8
				{
					0, 1, 3, 5, 6, 2
				},  //9
			};
		int[] temp;

		public void drawMyNum(int n) {
			temp = nums[n];
		}

		public void paint(Graphics g) {
			g.setColor(255, 0, 0);
			int i = temp.length;
			for (int j = 0; j <= i - 1; j++) {
				int[] a = lines[temp[j]];
				g.drawLine(a[0], a[1], a[2], a[3]);
			}
		}

	}

	public void startApp() {
		f.addCommand(exit);
		f.addCommand(drs);
		f.addCommand(drc);
		f.addCommand(drn);
		f.setCommandListener(this);
		d.setCurrent(f);
	}

	public void commandAction(Command c, Displayable dis) {
		if (c == exit)
			destroyApp(false);
		else if (c == drs) {
			MyCanvas m = new MyCanvas();
			m.addCommand(back);
			m.setCommandListener(this);
			m.DrawMyStrin("字符串", new java.util.Random().nextInt(8) + 1);
			d.setCurrent(m);
		} else if (c == drc) {
			CircleCanvas cc = new CircleCanvas();
			cc.addCommand(back);
			cc.setCommandListener(this);
			cc.drawCircle(60, 60, 40);
			d.setCurrent(cc);
		} else if (c == drn) {
			MatchNum mn = new MatchNum();
			mn.addCommand(back);
			mn.setCommandListener(this);
			mn.drawMyNum(new java.util.Random().nextInt(9));
			d.setCurrent(mn);
		} else if (c == back) {
			d.setCurrent(f);
		}
	}

	public void pauseApp() {
	}

	public void destroyApp(boolean unconditional) {
		notifyDestroyed();
	}
}
```
