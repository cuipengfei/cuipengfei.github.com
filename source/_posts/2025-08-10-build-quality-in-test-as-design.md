---
title: "把质量内建进设计：从“测试是负担”到“测试即设计”"
date: 2025-08-10 10:00:00 +0800
tags:
  [Build Quality In, Testability, TDD, BDD, Dependency Inversion, Composition]
---

> 业务复杂 ≠ 代码必然复杂。优秀的代码往往“平平无奇”，真正平衡的是设计与可测试性。

## 引言：为何“质量计划”常常徒劳？

你或许经历过——团队引入检查清单、覆盖率红线、扫描工具，三个月后大家疲于应付，质量却无改观。

问题往往不是“大家不努力”，而是“不清楚质量应当在设计阶段就被塑造”，于是测试被动沦为末端拦截。

本文聚焦一条主线：把质量从外部检验迁移为内部设计属性。

换句话说，测试不是在验证代码是否正确，而是在验证“设计是否合理”。

进一步说，很多“失败的质量改进”是**误诊**：把“无知之错”当成“无能之错”。

缺的不是劳动力，而是“质量这一维度的共同认知框架”。

如果我们仍把测试当末端闸门，自然只能不断加流程；当我们把它前移为设计反馈工具，组织能量才会释放。

Build Quality In（内建质量）真正的含义：让每一环交付“自带可验证标准”的成果，而不是把风险挤压到尾端。

一句话：质量是过程属性，被**设计**出来，不是被**扫**出来。

---

## 职责与误解

测试是开发者的职责，不是 QA 的专属。

把测试外包出去，等于放弃对代码质量的管理权。

三段常见的认知演进：

1. 测试是 QA 的事 → 代码能跑就行
2. 测试是开发的事 → 写完代码补测试
3. 质量是设计的事 → 用测试驱动设计（Test drives design）

指标错觉：覆盖率不是质量，“变更信心、缺陷密度、反馈周期”才是更健康的三指标。

覆盖率 < 语义覆盖。很多 90% 覆盖率的项目依旧“改一处怕全局”，因为它们测的是执行路径，而非“业务承诺”。

---

## 设计原则：让复杂业务不制造复杂代码

核心是“可组合性（Composition）”与“解耦（Decoupling）”。

它们直接决定了可测试性（Testability）。

可测试性本身就是优秀设计的试金石——当某段代码“难以被孤立 + 快速断言”，我们常常不是“缺测试技巧”，而是“设计边界模糊 / 依赖隐藏”。

### 依赖倒置（DIP）与构造注入

下面从一个紧耦合的反例切入，再用构造注入展示如何让依赖显式、可替换。

反例：在组件内部直接创建依赖，紧耦合、难替换、难测。

```java
// Anti-pattern: 内部创建依赖，紧耦合
public class OrderService {
    private final PaymentProcessor paymentProcessor = new PaymentProcessor();
    public void placeOrder(Order order) {
        paymentProcessor.process(order.getPaymentDetails());
    }
}
```

更好：让高层依赖抽象、依赖由外注入。

```java
// Better: 构造注入 + 依赖倒置（DIP）
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

把对象创建权上移（构造注入）= 把“变动点”推到最外围；业务核心越“纯”，测试越“薄”。

### 薄回调层，隔离框架复杂度

Controller 只做“转发到业务”的薄层，避免业务深绑框架；业务可在纯代码环境下独立测试。
简例：

```java
// Spring Controller
@RestController
class OrderController {
  private final OrderApplicationService app; // 纯业务服务
  OrderController(OrderApplicationService app){this.app = app;}
  @PostMapping("/orders")
  public OrderDTO place(@RequestBody OrderDTO dto){return app.place(dto.toDomain()).toDTO();}
}

// 纯业务（无框架依赖，可直接 new + 单元测试）
public class OrderApplicationService {
  private final OrderService domain;
  public OrderApplicationService(OrderService domain){this.domain = domain;}
  public Order place(Order order){return domain.process(order);}
}
```

测试时只 new `OrderApplicationService` + Fake 依赖即可，无需启动 Spring。框架层的测试再用少量集成测试覆盖即可。

### 少依赖 verify，多验证结果

“验证是否调用了某内部方法”会把测试绑死在实现细节上，任何重构都易破。

应基于“公共接口的可观察结果/状态变化”断言。

```java
// 错误聚焦：实现细节
verify(paymentClient, times(1)).charge(any());

// 正确聚焦：业务行为
orderService.processOrder(order);
assertThat(order.getStatus()).isEqualTo(PROCESSED);
```

---

## 测试焦点：接口行为 > 内部实现

- 测什么：组件对外的承诺（状态、返回值、对外交互），而不是它“怎么做”。
- 好处：重构自由度大、测试更稳定、设计更抽象。
- 经验法则：能用一个 Given-When-Then 讲清楚的场景，就不要写“调用序列监视”的测试。

示例（BDD/SbE）：

```gherkin
Feature: 订单处理
  Scenario: 成功处理有效订单
    Given 用户有一个包含商品的订单
    When 用户提交订单
    Then 订单状态为“已处理”
    And 用户收到确认邮件
```

- “行为测试”好比：下单后我看结果——订单状态变了、邮件发了。
- “实现细节测试”好比：我盯着服务内部是不是“正好调用了 X 方法一次”。后者一旦内部重构（比如拆成两个方法）就会碎。
  入门策略：先列“这个功能对外可观察的 1~3 个结果”，测试里只断言这些结果；只有当结果无法直接观察时再考虑使用 `verify`。

---

## 质量度量：从数字走向信心

替换“覆盖率崇拜”：

- 变更信心：修改后能否立即部署？
- 缺陷密度：每千行缺陷数随时间下降？
- 反馈周期：提交到发现问题的时间足够短？

这些指标直接反映测试是否在“护航设计”，而非堆数字。

层次补充（避免“一把梭”）：

- 单元测试：纯领域 / 规则（最快，失败定位最精确）。
- 组件 / 集成测试：跨边界协作假设验证（接口契约 + 配置）。
- 端到端冒烟：关键业务旅程“有没有坏”。

测试金字塔倒置（靠系统/端到端堆砌）往往是“可测试性设计缺席”的症状，而不是“尚未补齐测试数量”。

---

## 延伸与反思

几点可内化的“对照提醒”：

1. 业务复杂 ≠ 代码必须复杂：遇到“看不懂的实现”先问“是否把业务决策隐藏在技术细节里”。
2. 主要成本在修改：每次改动如果需要“开启多人口头同步”才能放心发布，说明缺少可验证的自动回归网。测试是摊平未来修改成本的“前置投资”。
3. 设计是持续活动：每加入一个特性，就重新检视一次“依赖是否还最小”“边界是否还能直接被测试观察”。
4. 测试是一把尺：当你犹豫“要不要抽象”时，问——这个抽象是否让关键行为的测试表达更短、更清晰；若是，抽象通常是正向的。
5. 认知阻力 ≠ 技术难点：很多“写测试浪费时间”是对“前置投入换未来变更自由”这一逆直觉收益模型的天然抗拒。识别其是“无知之错”后，用“小步展示节省的回归/调试时间”逐步瓦解。

---

## 结语

把测试从“末端审判”变成“设计工具”，把质量从“外部检查”变成“内部属性”。

当我们用可组合的设计、行为导向的测试和清晰的验收标准把质量内建进去，复杂业务也能产出“平平无奇”的高质量代码。

最后留一个自检问题（供回到代码前快速扫一眼）：

> 当前这次改动，如果我删掉所有 `verify` 只保留结果断言，测试仍然表达业务意图吗？如果答案是否定的，可能我还在测试实现细节，而非设计契约。
