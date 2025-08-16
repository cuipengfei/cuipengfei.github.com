---
title: "把质量内建进设计：从“测试是负担”到“测试即设计”"
date: 2025-08-10 10:00:00 +0800
tags:
  [Build Quality In, Testability, TDD, BDD, Dependency Inversion, Composition]
---

> 业务复杂 ≠ 代码必然复杂。好代码常常看着普通，关键在于设计和可测试性平衡得好。

## 引言：为什么质量改进总是做不好？

我们都见过这种情况：团队加了各种检查清单、覆盖率红线、扫描工具。

几个月后，流程更复杂了，质量却没怎么变好。

问题不在执行力度，而在于把质量活动放错了时间：设计定好了才让测试介入，只能事后补漏洞。

**核心思路：把质量从"事后检查"变成"设计时就考虑进去"，让测试帮助改进设计。**

---

测试应该验证"设计是否合理"，而不是"代码是否正确"。

理解这一点，才解释得通：为什么很多“质量改进”看似动作频繁却收效甚微。

大量所谓失败的质量改进，其根源是**诊断偏差**：把“认知与方法”的缺口，误判为“态度与能力”的缺陷。

我们缺的往往不是意愿，而是对“质量必须被前置设计进去”的共识。

正因为测试目的被误读，我们才会不断叠加流程——模板、检查项、额外审批——却依旧缺乏演进信心。

把测试前置为设计反馈回路，才能释放团队在结构演化上的创造力。

Build Quality In（内建质量，指从设计、开发到交付各个环节，始终将可度量、可验证的质量目标固化为交付物的内在属性，不依赖于终极测试关卡。这一理念强调风险应前移并在流程中被分解，而不是留到最后集中暴露）

其真正含义是：每一环节交付的中间产物自带“可验证标准”，而不是把风险挤压到最后一轮集中测试。

换句话说：质量是过程属性，是**设计**出来的，而不是**扫**出来的。

![生成融合图像-(1)](<https://cuipengfei.github.io/picx-images-hosting/20250816/生成融合图像-(1).5xaykpra6p.png>)

---

## 职责重塑：测试是谁的事？

| 组织方式     | 测试责任认知       | 结果         |
| ------------ | ------------------ | ------------ |
| QA 负责      | 开发完扔给 QA 测试 | 被动、滞后   |
| 开发补测     | 写完代码再补测试   | 补救、慢反馈 |
| 测试驱动设计 | 测试推动设计       | 主动、预防   |

测试是开发者的责任，不是 QA 的专属。

把测试外包出去，等于把系统演进的控制权也外包了。

团队对测试的认知通常经历三个阶段：

1. 测试是 QA 的事 → 代码能跑就行
2. 测试是开发的事 → 写完代码补测试
3. 测试是设计的事 → 用测试来驱动设计

覆盖率不是质量，"改动后敢不敢部署"才是。

很多 90% 覆盖率的项目，改一行代码还是怕出事，因为测的是执行路径，不是业务行为。

---

## 设计原则：让复杂业务不制造复杂代码

以订单系统为例，如果把支付、库存、通知都耦合在一起，业务再简单代码也会变复杂。

两个关键点：

- **可组合性**：组件能独立变化和复用
- **解耦**：模块边界清晰，依赖关系明确

这两个直接决定了**可测试性**。

代码不好测，通常是设计问题，不是测试技术问题。

![图像生成提示词](https://cuipengfei.github.io/picx-images-hosting/20250816/图像生成提示词.4ub99tvgbv.png)

### 依赖倒置与构造注入

依赖倒置（DIP, Dependency Inversion Principle：让高层模块不依赖具体实现、仅依赖抽象接口，从而提升灵活性与可测试性）

构造注入（Constructor Injection，用显式传参而非内部直接实例化依赖，以便测试和扩展）

先看一个典型“隐式依赖”日常写法，随后用构造注入展示如何让依赖显式、可替换。

反例（内部直接创建依赖 → 紧耦合、难替换、难测）：

```java
public class OrderService {
    private final PaymentProcessor paymentProcessor = new PaymentProcessor();

    public void placeOrder(Order order) {
        paymentProcessor.process(order.getPaymentDetails());
    }
}
```

**改进代码**：

```java
@Service
public class OrderService {
    private final PaymentGateway payment;
    private final Inventory inventory;
    private final Notification notification;

    public OrderService(PaymentGateway payment, Inventory inventory, Notification notification) {
        this.payment = payment;
        this.inventory = inventory;
        this.notification = notification;
    }

    public void processOrder(Order order) {
        payment.charge(order.getAmount());
        inventory.reserve(order.getItems());
        notification.sendConfirmation(order.getEmail());
    }
}
```

为什么这能降复杂？因为：

- 可替换：测试中传入 Mock/Fake，环境可控。
- 可演进：实现可变而接口不变，测试稳定。
- 可理解：依赖图显式化，认知负荷更低。

这三点共同把变动半径压缩到装配层。

把对象创建权上移（构造注入）= 将“变动点”推到最外围；业务核心越“纯”，测试越“薄”。

![中国风概念插画](https://cuipengfei.github.io/picx-images-hosting/20250816/中国风概念插画.2a5ex6vhn1.png)

### 隔离框架：让业务逻辑保持纯粹

完成对象级依赖显式化后，第二类常见耦合源是“框架侵入业务”。策略：让 Controller 仅做“协议/IO → 领域调用”转发，避免领域逻辑渗入框架层，使业务在纯 Java 环境下即可单测。

简例：

```java
@RestController
class OrderController {
    private final OrderApplicationService app;
    OrderController(OrderApplicationService app){this.app = app;}

    @PostMapping("/orders")
    public OrderDTO place(@RequestBody OrderDTO dto){
        return app.place(dto.toDomain()).toDTO();
    }
}

// 纯业务，可直接 new 来测试
public class OrderApplicationService {
    private final OrderService domain;
    public OrderApplicationService(OrderService domain){this.domain = domain;}
    public Order place(Order order){return domain.process(order);}
}
```

### 测试什么：结果而不是过程

脆弱的测试：

```java
verify(paymentClient, times(1)).charge(any());
```

稳定的测试：

```java
orderService.processOrder(order);
assertThat(order.getStatus()).isEqualTo(PROCESSED);
```

测试应该关注业务结果，而不是内部实现细节。

---

## 测试焦点：以对外行为契约取代内部调用序列

- 测什么：组件对外的行为契约（状态、返回值、对外交互语义），而不是它“怎么做”。
- 好处：重构自由度大、测试稳定、设计抽象度更高。
- 经验法则：能用一个 Given-When-Then 讲清楚的场景，就不要写“调用序列监视”。

经验：能用 Given-When-Then 说清楚的，就别用 verify。

```gherkin
Feature: 订单处理
  Scenario: 成功处理有效订单
    Given 用户有一个包含商品的订单
    When 用户提交订单
    Then 订单状态为"已处理"
    And 用户收到确认邮件
```

“行为测试”好比：下单后只看结果——订单状态改变、邮件发送完成；“实现细节测试”好比：盯内部是否“恰好调用 X 一次”，一旦内部重构（拆分、合并、缓存）测试就会挂。

列出该功能对外可观察的 1~3 个结果，测试只断言这些；仅当结果不可直接观察时，再退而使用 `verify`。

这是前文所称“语义覆盖”的具体化——验证承诺，而非执行路径。

![图像生成提示词-(1)](<https://cuipengfei.github.io/picx-images-hosting/20250816/图像生成提示词-(1).1lc5d67ync.png>)

---

## 质量度量：用什么指标

别只看覆盖率，看这些：

- 改完代码敢不敢直接上线？
- 缺陷数在下降吗？
- 出问题多久能发现？

测试金字塔：

- 单元测试：测领域逻辑，最快定位问题
- 集成测试：测边界协作
- 端到端：测关键流程

金字塔倒过来，通常是设计没做好。

### 总结

我们做了什么：

- 测试从后置变前置
- 职责从外包变内聚
- 用依赖倒置 + 组合 + 薄层隔离框架
- 测行为契约，减少 verify
- 用真实指标衡量质量

---

## 几个对照思考

1. 业务复杂 ≠ 代码必须复杂：遇到"看不懂的实现"时，可以先问问"是否把业务决策隐藏在技术细节里"。
2. 主要成本在修改：每次改动如果都需要"开启多人口头同步"才能放心发布，通常说明缺少可验证的自动化回归测试集。测试可以看作是摊平未来修改成本的"前置投资"。
3. 设计是持续活动：每加入一个特性，都值得重新检视"依赖是否最小化""边界是否清晰"以及"关键行为是否能被直接测试"。
4. 测试是一把尺：当你犹豫“是否需要抽象”时，可以问自己——这个抽象是否让关键行为的测试表达更短、更清晰？如果是，那么这个抽象通常是正确的方向。
5. 认知惯性 ≠ 技术难点：很多开发者感觉"写测试浪费时间"，往往是对“前置投入换取未来自由”这一逆直觉收益模型的抗拒。识别到这只是惯性后，可用“小步演示削减回归/调试时间”建立共识。

这些对照共同指向：让测试成为持续设计校准机制，而非末端裁决。

---

![生成融合图像](https://cuipengfei.github.io/picx-images-hosting/20250816/生成融合图像.8adl1x53f7.png)

## 结语

把测试从“末端审判”变成“设计工具”，把质量从“外部检查”变成“内部属性”。

当我们用可组合设计、行为契约测试与清晰验收标准内建质量，复杂业务也能产出“平平无奇”且稳定可演进的代码。

> 当前这次改动，如果我删掉所有 `verify` 调用，只保留对最终结果（状态/返回值/对外交互）的断言，测试是否仍清晰表达业务意图？若否，请回溯：依赖是否显式？行为是否可观察？测试是否覆盖契约而非路径？

这三个对了，质量就在设计里了。
