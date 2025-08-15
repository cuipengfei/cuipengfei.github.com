---
title: "把质量内建进设计：从“测试是负担”到“测试即设计”"
date: 2025-08-10 10:00:00 +0800
tags:
  [Build Quality In, Testability, TDD, BDD, Dependency Inversion, Composition]
---

> 业务复杂 ≠ 代码必然复杂。优秀的代码往往“平平无奇”，真正平衡的是设计与可测试性。

## 引言：为何“质量改进”举步维艰？

我们或许都经历过类似的场景：团队引入了详尽的检查清单、严格的覆盖率红线和先进的扫描工具，但几个月后，大家在疲于应付流程，而质量本身却未见实质性提升。

问题往往不在于“执行不力”，而在于我们将质量活动放在了错误的位置——当设计已经完成，测试才被动介入，沦为问题拦截的最后一道防线。

本文聚焦一条主线：将质量从“外部检验”转变为“内部设计属性”。

换句话说，测试的核心目的，是验证“设计是否合理”，而不仅仅是“代码是否正确”。

进一步说，很多“失败的质量改进”源于**诊断偏差**：把“认知与方法”的问题，错误归因于“态度与能力”。

我们缺的不是执行的意愿，而是“将质量融入设计”的共同认知框架。

如果我们仍将测试视为末端闸门，自然会陷入不断增加流程的循环；而当我们把它前置为设计的反馈工具时，团队的创造力才能真正被释放。

Build Quality In（内建质量）的真正含义是：让软件开发的每一环都交付“自带可验证标准”的成果，而不是将所有风险都积压到最终测试阶段。

一句话：质量是过程属性，被**设计**出来，不是被**扫**出来。

---

## 职责与误解

测试是开发者的职责，不是 QA 的专属。

当我们把测试外包出去，在某种程度上等于放弃对代码质量的直接管理权。

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

可测试性本身可以看作是优秀设计的一个重要标志——当某段代码"难以被孤立 + 快速断言"时，我们常常面临的不是"缺测试技巧"的问题，而是"设计边界模糊 / 依赖隐藏"的挑战。

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

“验证是否调用了某内部方法”的测试，会将测试用例与实现细节紧紧绑死，任何内部重构都可能导致测试失效。

正确的做法是，基于“公共接口的可观察结果或状态变化”进行断言。

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

测试金字塔倒置（过度依赖系统/端到端测试堆砌）通常反映的是"可测试性设计缺席"的现象，而不仅仅是"尚未补齐测试数量"的问题。

---

## 延伸与反思

几点可内化的"对照思考"：

1. 业务复杂 ≠ 代码必须复杂：遇到"看不懂的实现"时，可以先问问"是否把业务决策隐藏在技术细节里"。
2. 主要成本在修改：每次改动如果都需要"开启多人口头同步"才能放心发布，通常说明缺少可验证的自动化回归测试集。测试可以看作是摊平未来修改成本的"前置投资"。
3. 设计是持续活动：每加入一个特性，都值得重新检视"依赖是否最小化""边界是否清晰"以及"关键行为是否能被直接测试"。
4. 测试是一把尺：当你犹豫“是否需要抽象”时，可以问自己——这个抽象是否让关键行为的测试表达更短、更清晰？如果是，那么这个抽象通常是正确的方向。
5. 认知惯性 ≠ 技术难点：很多开发者感觉"写测试浪费时间"，这可能源于对"前置投入换取未来变更自由"这一逆直觉收益模型的天然抗拒。识别到这是"认知惯性"而非"技术局限"后，可以通过"小步展示节省的回归与调试时间"来逐步建立共识。

---

## 结语

把测试从“末端审判”变成“设计工具”，把质量从“外部检查”变成“内部属性”。

当我们用可组合的设计、行为导向的测试和清晰的验收标准把质量内建进去，复杂业务也能产出“平平无奇”的高质量代码。

最后留一个自检问题（供回到代码前快速扫一眼）：

> 当前这次改动，如果我删掉所有 `verify` 调用，只保留对最终结果的断言，测试是否仍然能够清晰地表达业务意图？如果答案是否定的，那么我可能仍旧在测试实现细节，而非设计契约。
