---
title: cpu占用率为正弦曲线（C#实现）基本照抄书上的C++
date: 2008-09-17 11:39:00
tags: 《编程之美——微软技术面试心得》的C#实现
---
internal值根据配置调整

```
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ConsoleApplication1
{
    class Program
    {
        static void Main(string[] args)
        {
            const double SPLIT = 0.01;
            const int COUNT = 200;
            const double PI = 3.14159265;
            const int INTERVAL = 100;
            double[] busySpan = new double [COUNT]; //array of busy times
            double[] idleSpan = new double [COUNT]; //array of idle times
            int half = INTERVAL / 2;
            double radian = 0.0;
            for (int i = 0; i < COUNT; i++)
            {
                busySpan[i] = (double) (half + (Math.Sin(PI * radian) * half));
                idleSpan[i] = INTERVAL - busySpan[i];
                radian += SPLIT;
            }

            double startTime = 0;
            int j = 0;
            while (true)
            {
                j = j % COUNT;
                startTime = Environment.TickCount;
                while ((Environment.TickCount - startTime) <= busySpan[j]) ;
                System.Threading.Thread.Sleep((int) idleSpan[j]);
                j++;
            }
        }
    }
}
```

