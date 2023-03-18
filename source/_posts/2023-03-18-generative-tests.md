---
title: "当测试代码使用随机生成的输入数据时，该如何去做出断言"
date: 2023-03-18 22:34:00
tags:
- testing
---

# 一份有意思的代码

最近看到了一份有趣的代码，把其大致思路用伪代码描述如下 

## 需要被测的实现代码

```javascript
function calculateSomething(inputData) {
    // 使用inputData来计算结果
    // 假装这里有一些很复杂的逻辑
    return result;
}
```

这是被测的函数，在此不管它算的是什么，总之它接受input，返回result。

## 测试代码的helpers

```javascript
function generateInputData(){
    // 用来生成测试所需的input数据
    // 所生成的数据具有一定的随机性
    return randomlyGeneratedInputData;
}

fuction calculateExpectedResult(inputData){
    // 用来计算assertion所需要的expected值
    return expectedResult;
}
```

这是测试代码的helper函数，一个用来生成测试所需的input，一个用来计算expected的值。

## 测试代码

```javascript
var repeatTimes = 100; //总之是一个较大的数字，不一定非得是100

for (i=0; i<repeatTimes; i++){
    var randomInputData = generateInputData(); //生成具有一定随机性的输入
    var expected = calculatedExpectedResult(randomInputData); //用测试helper算出expected
    var actual = calculateSomething(randomInputData); //用被测方法算出actual

    expect(actual).toEqual(expected); //断言二者相等
}
```

这是测试代码，反复运行多次，每次都生成具有随机性的input，然后把input传递给calculatedExpectedResult和calculateSomething，最后断言二者返回值是一致的。

这份代码和常见的测试不同，它使用的input data不是预先设定好的，而是运行时随机生成的。这也是它有趣的原因。

# 如何解读测试结果

通常来说，当测试通过时，它意味着针对给定的输入，程序给出了**符合预期**的输出。

但是对于这一份代码来说，却并非如此。因为它的expected值是由一个helper函数计算得来，而非是一个已经被验证过是正确的值。

那么，如果上述的测试代码能够执行通过时，它到底意味着什么呢？

* 它意味着```calculatedExpectedResult```这个helper函数和```calculateSomething```这个被测函数之间，具有较高的相似性，二者针对一样的输入，可以给出一样的输出

* 如果我们把```repeatTimes```的数值调到非常高，测试还能通过的话，那就说明```calculatedExpectedResult```这个helper函数和```calculateSomething```这个被测函数之间的相似性非常高，简直可以达到同卵双胞胎甚至是克隆体这种以假乱真的程度

**这是我们需要的吗？**

我们**需要去探寻世界上是否存在那么一个函数，它的行为可以做到和```calculateSomething```极其贴近吗？**

我认为我们是不需要的。

我们需要的是去验证```calculateSomething```的行为是符合预期的。而不是去验证我能写出另一个和它的行为很像的函数来。

这就如同是：如果我去测试洗衣机的话，我希望验证的是某款洗衣机可以把衣服洗涤干净，并且不会损伤衣物。而不是希望验证有另一台洗衣机和我手里这一台表现一样。

要不然的话，我说不定会得到两台洗不干净衣服，还会损伤布料的洗衣机😄

# 当测试代码使用随机生成的输入数据时，该如何去做出断言

想要用随机生成的input数据去做测试其实**并不是一个不合理的想法**。

当我们人工编制的测试数据对于整体样本空间来说显得太小时，用随机数据去作为input数据也是一个不错的补充。

其关键在于，**当我们给input引入了随机性的时候，我们该如何去assert其output是符合预期的？**

如果我们还是想要和常规测试一样，严格地去assert输出的值和预期**相等**，那么就会陷入上述代码的误区里。

但是如果思路换一下，不一定非得强求能够严格地去assert输出的值和预期**相等**，而是去assert输出值符合一定的规则。这样，就无需在测试代码里重复去实现一遍，而只需要描述规则。

# Property Based Testing

而这，恰好就是Property Based Testing。

Property Based Testing是一种基于属性规约的测试方法，通过使用随机输入数据来验证程序的行为是否符合预期的属性规约。

在 Property Based Testing 中，测试用例不需要手动编写，而是基于属性规约自动生成。

Property Based Testing 的基本流程如下：

* 定义属性规约：定义程序的行为应该满足的属性规约，这些规约通常是**通用的、可重用的、抽象**的，而**不是特定的测试用例**。

* 生成随机数据：通过随机数据生成器生成随机数据，并将随机数据输入到程序中。

* 检查属性规约：将实际输出与定义的属性规约进行比较，如果程序的输出符合属性规约，则测试通过，否则测试失败。

* 修复代码：如果测试失败，则需要对程序进行修复，直到程序能够符合所有属性规约。

Property Based Testing 的优点是可以有效减少测试用例的数量，提高测试覆盖面，发现程序中隐藏的错误，并提高程序的可读性和可维护性。但是，Property Based Testing 也需要一定的技能和经验，特别是在定义属性规约和设计随机数据生成器方面需要花费更多的时间和精力。

一些常用的 Property Based Testing 框架包括 QuickCheck、Hypothesis、ScalaCheck、fast-check 等。

下面是一段使用Property Based Testing的样例代码：

```javascript
const fc = require('fast-check');

// Property-Based Testing，测试加法函数
test('加法满足交换律', () => {
    fc.assert(fc.property(fc.integer(), fc.integer(), (x, y) => {
        return add(x, y) === add(y, x);
    }));
});

test('加0不影响结果', () => {
    fc.assert(fc.property(fc.integer(), (x) => {
        return add(x, 0) === x;
    }));
});

test('正数加负数，结果小于原数', () => {
    fc.assert(fc.property(fc.integer(-1000, -1), fc.integer(1, 1000), (x, y) => {
        return add(x, y) < x;
    }));
});

test('负数加正数，结果大于原数', () => {
    fc.assert(fc.property(fc.integer(-1000, -1), fc.integer(1, 1000), (x, y) => {
        return add(x, y) > x;
    }));
});

test('负数加负数，结果小于原数', () => {
    fc.assert(fc.property(fc.integer(-1000, -1), fc.integer(-1000, -1), (x, y) => {
        return add(x, y) < x;
    }));
});

test('正数加正数，结果大于原数', () => {
    fc.assert(fc.property(fc.integer(1, 1000), fc.integer(1, 1000), (x, y) => {
        return add(x, y) > x;
    }));
});

test('任何数加自己，结果是两倍', () => {
    fc.assert(fc.property(fc.integer(), (x) => {
        return add(x, x) === x * 2;
    }));
});
```

代码中所使用的fast-check(fc)会帮助我们生成大量的具有随机性的输入数据，但是我们并没有去assert add的返回等于某个具体的数字，而是去判断add的返回值符合我们定义的规律。
