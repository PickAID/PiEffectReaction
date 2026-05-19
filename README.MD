# PiEffectReaction

[English](README_EN.md) | 中文

PiEffectReaction 是 Pi 系列里负责“效果触发反应”的桥接模组。它的目标不是替代 `PiDataGraph`，也不是把实体状态塞进数据图里，而是把两边稳定接起来：

- `PiEntityFX` 或后续实体运行时保存真实状态：effect、meter、counter、mark、cooldown、signal。
- `PiDataGraph` 保存可数据包修改的 reaction plan：条件、分支、延迟、执行预算、动作编排。
- PiEffectReaction 位于中间，把 effect signal 交给有上界的 reaction 执行，并把执行结果回写到实体 runtime 或同步表现 cue。

## 为什么不是纯 Java lib

这个库的核心场景离不开 Minecraft：

- actor 通常是 `LivingEntity`；
- reaction 数据来自 datapack；
- 运行结果要影响 effect state、targeting、damage、render cue 或网络同步；
- 诊断也需要能指向世界、实体、数据包路径和 mod id。

所以 PiEffectReaction 应该作为 Minecraft mod 发布。但内部结构会尽量保持薄依赖：规则定义和编译接口放在 `api`，Forge 事件、reload、sync、entity 接线放在 `runtime` 和 `bridge`。

## P0 范围

当前版本提供最小 reaction contract 和服务端 runtime 基础：

- `PiReactions.on(signalType)` 注册 typed signal reaction。
- `PiReactionBudget` 限制每 tick signal 数和递归深度。
- `PiReactionRuntime` 编译注册表并按 priority 执行匹配 reaction。
- `PiReactionTrace` 记录 emitted signal id 和被 budget/cycle guard 拦截的原因。
- `PiThresholdReaction` 提供阈值触发的基础形状。

P0 不拥有实体 effect 真相，不直接修改世界，不替代 PiEngine 状态，也不把 PiDataGraph 变成全局 signal loop。

包结构：

- `org.pickaid.pieffectreaction.api`：稳定公开契约。
- `org.pickaid.pieffectreaction.runtime`：服务端权威 reaction queue、guard、cooldown、diagnostics。
- `org.pickaid.pieffectreaction.bridge`：到 `Pibrary`、`PiDataGraph`、`PiEngine`、视觉 pack 的桥。

## 构建

```bash
./gradlew build
```
