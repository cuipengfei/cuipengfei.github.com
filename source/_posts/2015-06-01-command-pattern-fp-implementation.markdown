---
layout: post
title: 命令模式的不爽就像用指甲刀刮胡子
date: '2015-06-01 21:51'
comments: true
categories: null
---

# 命令模式
> 在面向对象程式设计的范畴中，命令模式是一种设计模式，它尝试以物件来代表实际行动。命令物件可以把行动(action) 及其参数封装起来，于是这些行动可以被：
  - 重复多次
  - 取消（如果该物件有实作的话）
  - 取消后又再重做

以上是wiki对命令模式的定义（术语像是台湾的）。

下面是来自《Head first design patterns》的一个例子：

假设你有很多家用电器：电灯泡，电视，音响，还有一个水疗浴盆。

每个家用电器都有自己的开关装置，处于不同的位置。如果你想把它们都开启，需要一个一个地去按按钮。

现在你想要有一个遥控器，一键完成所有电器的开启和关闭。

或者是一键完成任意的电器组合操作。

每个电器的接口都是不同的，但是又想要和同一个遥控器集成，于是呢，肯定要有一个统一的接口了。

于是就有了下面的代码。

# Java

```java
public class Light {
    String location;

    public Light(String location) {
        this.location = location;
    }

    public void on() {
        System.out.println("Light is on");
    }

    public void off() {
        System.out.println("Light is off");
    }

}

public class TV {
    String location;
    int channel;

    public TV(String location) {
        this.location = location;
    }

    public void on() {
        System.out.println(location + " TV is on");
    }

    public void off() {
        System.out.println(location + " TV is off");
    }

    public void setInputChannel() {
        this.channel = 3;
        System.out.println(location + " TV channel is set for DVD");
    }
}

public class Stereo {
    String location;

    public Stereo(String location) {
        this.location = location;
    }

    public void on() {
        System.out.println(location + " stereo is on");
    }

    public void off() {
        System.out.println(location + " stereo is off");
    }
}

public class Hottub {
    boolean on;
    int temperature;

    public Hottub() {
    }

    public void on() {
        on = true;
    }

    public void off() {
        on = false;
    }

    public void circulate() {
        if (on) {
            System.out.println("Hottub is bubbling!");
        }
    }

    public void setTemperature(int temperature) {
        if (temperature > this.temperature) {
            System.out.println("Hottub is heating to a steaming " + temperature + " degrees");
        } else {
            System.out.println("Hottub is cooling to " + temperature + " degrees");
        }
        this.temperature = temperature;
    }
}
```

首先是有四大件家用电器。各自之间没有什么关系。

这里面的代码都有点傻，不过没关系，我们就想象这都是些很复杂的硬件通信之类的代码就好了。

```java
public interface Command {
    void execute();
}
```

然后，定义一个Command接口，其中只有一个execute()方法。

之后我们会用它的实现类来造作各种电器。

```java
public class LightOnCommand implements Command {
    Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    public void execute() {
        light.on();
    }
}

public class LightOffCommand implements Command {
    Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    public void execute() {
        light.off();
    }
}

public class TVOnCommand implements Command {
    TV tv;

    public TVOnCommand(TV tv) {
        this.tv = tv;
    }

    public void execute() {
        tv.on();
        tv.setInputChannel();
    }
}

public class TVOffCommand implements Command {
    TV tv;

    public TVOffCommand(TV tv) {
        this.tv = tv;
    }

    public void execute() {
        tv.off();
    }
}

public class StereoOnCommand implements Command {
    Stereo stereo;

    public StereoOnCommand(Stereo stereo) {
        this.stereo = stereo;
    }

    public void execute() {
        stereo.on();
    }
}

public class StereoOffCommand implements Command {
    Stereo stereo;

    public StereoOffCommand(Stereo stereo) {
        this.stereo = stereo;
    }

    public void execute() {
        stereo.off();
    }
}

public class HottubOnCommand implements Command {
    Hottub hottub;

    public HottubOnCommand(Hottub hottub) {
        this.hottub = hottub;
    }

    public void execute() {
        hottub.on();
        hottub.setTemperature(104);
        hottub.circulate();
    }

}

public class HottubOffCommand implements Command {
    Hottub hottub;

    public HottubOffCommand(Hottub hottub) {
        this.hottub = hottub;
    }

    public void execute() {
        hottub.setTemperature(98);
        hottub.off();
    }
}
```

这一大坨，就是Command的实现了。

四大件电器，于是便有八个Command，分别负责每个电器的开启和关闭。

有些电器的开启和关闭比别的要复杂一些，不过这没有关系，因为它们的细节都被封装在Command的实现类里面了，我们接下来的代码只要和Command这个接口打交道就好了。

```java
public class MacroCommand implements Command {
    Command[] commands;

    public MacroCommand(Command[] commands) {
        this.commands = commands;
    }

    public void execute() {
        for (int i = 0; i < commands.length; i++) {
            commands[i].execute();
        }
    }
}
```

还有一个宏命令，用来组合其他命令。

```java
public class RemoteControl {
    Command[] onCommands;
    Command[] offCommands;

    public RemoteControl() {
        onCommands = new Command[7];
        offCommands = new Command[7];
    }

    public void setCommand(int slot, Command onCommand, Command offCommand) {
        onCommands[slot] = onCommand;
        offCommands[slot] = offCommand;
    }

    public void onButtonWasPushed(int slot) {
        onCommands[slot].execute();
    }

    public void offButtonWasPushed(int slot) {
        offCommands[slot].execute();
    }
}
```

可以实现遥控器了。

[http://elisabethrobson.com/wp-content/uploads/2014/07/Command.jpg](http://elisabethrobson.com/wp-content/uploads/2014/07/Command.jpg)

这个遥控器上的按钮都是空白的，我们可以给它置入人以我们想要的命令。

```java
public class RemoteLoader {
    public static void main(String[] args) {
        RemoteControl remoteControl = new RemoteControl();

        Light light = new Light("Living Room");
        TV tv = new TV("Living Room");
        Stereo stereo = new Stereo("Living Room");
        Hottub hottub = new Hottub();

        Command[] partyOn = {
                new LightOnCommand(light), new StereoOnCommand(stereo),
                new TVOnCommand(tv), new HottubOnCommand(hottub)};

        Command[] partyOff = {new LightOffCommand(light), new StereoOffCommand(stereo),
                new TVOffCommand(tv), new HottubOffCommand(hottub)};

        remoteControl.setCommand(0, new MacroCommand(partyOn), new MacroCommand(partyOff));

        System.out.println("--- Pushing Macro On---");
        remoteControl.onButtonWasPushed(0);
        System.out.println("--- Pushing Macro Off---");
        remoteControl.offButtonWasPushed(0);
    }
}
```

终于可以写一个main函数了：
* 把家用电器和其对应的Command联系起来
* 把各种Command组合成开启和关闭两个宏命令
* 把宏命令置入遥控器

然后，只要按一个按钮，就可以开启所有电器，享受资产阶级奢靡的生活了。

享受够了之后只要再按一个按钮就可以把所有电器关闭掉。

```scala
case class Light(location: String) {
  def on() = println(s"$location light is on")

  def off() = println(s"$location light is off")
}

case class TV(location: String) {
  private var channel: Int = 0

  def on() = println(location + " TV is on")

  def off() = println(location + " TV is off")

  def setInputChannel() = {
    this.channel = 3
    println(location + " TV channel is set for DVD")
  }
}

case class Stereo(location: String) {
  def on() = println(s"$location stereo is on")

  def off() = println(s"$location stereo is off")
}

case class Hottub(var isOn: Boolean = false) {
  private var temperature: Int = 0

  def on() = isOn = true

  def off() = isOn = false

  def circulate() = if (isOn) println("Hottub is bubbling!")

  def setTemperature(temperature: Int) = {
    if (temperature > this.temperature) {
      println("Hottub is heating to a steaming " + temperature + " degrees")
    }
    else {
      println("Hottub is cooling to " + temperature + " degrees")
    }
    this.temperature = temperature
  }
}
```

```scala
object Commands {
  type Command = () => Unit

  def lightOn(light: Light): Command = () => light.on()

  def lightOff(light: Light): Command = () => light.off()

  def tvOn(tv: TV): Command = () => {
    tv.on()
    tv.setInputChannel()
  }

  def tvOff(tv: TV): Command = () => tv.off()

  def stereoOn(stereo: Stereo): Command = () => stereo.on()

  def stereoOff(stereo: Stereo): Command = () => stereo.off()

  def hottubOn(hottub: Hottub): Command = () => {
    hottub.on()
    hottub.setTemperature(104)
    hottub.circulate()
  }

  def hottubOff(hottub: Hottub): Command = () => {
    hottub.setTemperature(98)
    hottub.off()
  }

  def macroCommand(commands: Command*): Command = () =>
    commands.foreach(command => command())
}
```

```scala
case class RemoteControl(onCommands: Seq[Command], offCommands: Seq[Command]) {
  def pushOnButton(slot: Int) = onCommands(slot)()

  def pushOffButton(slot: Int) = offCommands(slot)()
}

object RemoteLoader {
  def main(args: Array[String]) {
    val light = Light("living room")
    val tv = TV("living room")
    val stereo = Stereo("living room")
    val hottub = Hottub()

    val on = macroCommand(lightOn(light),
      stereoOn(stereo), tvOn(tv), hottubOn(hottub))

    val off = macroCommand(lightOff(light),
      stereoOff(stereo), tvOff(tv), hottubOff(hottub))

    val remoteControl = RemoteControl(Seq(on), Seq(off))

    println("--- Pushing Macro On---")
    remoteControl.pushOnButton(0)
    println("--- Pushing Macro Off---")
    remoteControl.pushOffButton(0)
  }
}
```

103 247
