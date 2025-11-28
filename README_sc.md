<!--
SPDX-FileCopyrightText: 2015 - 2025 Rime community
SPDX-FileCopyrightText: 2025 Shengyun IME

SPDX-License-Identifier: GPL-3.0-or-later
-->

# 声韵输入法 Shengyun IME

> 专为学拼音的孩子设计的声韵分层拼音输入法
> 基于 [Trime](https://github.com/osfans/trime) (Android RIME 输入法) 和 [声韵方案](https://github.com/gshmu/shengyun) 构建

![build](https://github.com/gshmu/shengyun-rime/actions/workflows/commit-ci.yml/badge.svg?branch=develop)
[![License: GPL v3](https://img.shields.io/badge/License-GPL%20v3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![GitHub release](https://img.shields.io/github/release/gshmu/shengyun-rime.svg)](https://github.com/gshmu/shengyun-rime/releases)

[English](README.md) | 简体中文 | [繁體中文](README_tc.md)

---

## 📖 关于声韵输入法

声韵拼音是一种创新的汉字输入方法，将每个汉字的拼音分为**声母**和**韵母**两层键盘，通过**两次点击**即可完成一个完整拼音的输入。

> **说明**：这是一个**预配置声韵方案的 Trime 构建版本**。声韵输入方案本身在独立仓库维护：[github.com/gshmu/shengyun](https://github.com/gshmu/shengyun)

### ✨ 核心特性

- ✅ **符合拼音规范**：严格遵循汉语拼音声韵规则
- ✅ **两步输入**：声母 + 韵母，高效快捷
- ✅ **支持声调提示**：键盘顶行显示声调符号 ˉˊˇˋ，帮助学习拼音
- ✅ **儿童友好**：大按键、清晰配色、纯拼音界面
- ✅ **专业词库**：基于 [terra_pinyin](https://github.com/rime/rime-terra-pinyin) 180万词条
- ✅ **开源免费**：基于 RIME 引擎和 Trime 平台

### 🎯 设计理念

**为什么要做分层输入？**

传统的全键盘 QWERTY 拼音键盘对学龄前儿童来说过于复杂，按键太多、记忆负担重。声韵将拼音分为：

1. **第一步**：选择声母 (b/p/m/f/d/t/n/l/g/k/h/j/q/x/zh/ch/sh/r/z/c/s/y/w)
2. **第二步**：选择韵母 (a/o/e/i/u/ü 及其组合)

这种分层设计帮助儿童：
- 专注于拼音的基本结构
- 减少键盘按键数量
- 强化声韵组合规律的认知

---

## 📥 安装方法

### 方式一：直接安装 APK（推荐）

访问 [Releases 页面](https://github.com/gshmu/shengyun-rime/releases) 下载最新的 APK 文件并安装。

声韵输入法已预配置，安装后即可使用。

---

## 🎨 键盘布局

### 设计规则

1. **互补分布**：不同声母的韵母层会根据拼音规则显示不同的韵母
   - 例如：n/l 的韵母层显示 ü/ün/üe，而 b/p/m/f 层则不显示
2. **智能过滤**：拼音方案自动过滤无效组合（如 j+a, g+i 等）
3. **输入码规范**：所有 ü 使用 `v` 键输入，符合 RIME 标准

---

## 📚 技术架构

本项目基于以下开源组件构建：

- **[RIME](https://rime.im)**：跨平台输入法引擎
- **[Trime](https://github.com/osfans/trime)**：Android 平台 RIME 前端
- **[terra_pinyin](https://github.com/rime/rime-terra-pinyin)**：地球拼音词库（180万词）
- **[声韵方案](https://github.com/gshmu/shengyun)**：声韵分层配置文件

### 配置文件说明

```
app/src/main/assets/shared/
├── shengyun.schema.yaml        # 声韵输入方案配置
├── shengyun.trime.yaml         # 键盘布局配置（3750行）
├── terra_pinyin.dict.yaml      # 地球拼音词库（180万词）
├── terra_pinyin.schema.yaml    # 地球拼音方案
├── default.custom.yaml         # 默认启用声韵方案
└── stroke.schema.yaml          # 笔画辅助输入
```

---

## 🛠️ 开发者指南

### 环境要求

- Android SDK 和 Android NDK
- JDK (OpenJDK) 17
- Python 3（OpenCC 字典生成）
- Git（包括子模块支持）

### 克隆项目

```sh
git clone git@github.com:gshmu/shengyun-rime.git
cd shengyun-rime
git submodule update --init --recursive --filter=blob:none
```

### 编译项目

#### Debug 版本（无签名）

```sh
# Linux/macOS
make debug

# Windows
.\gradlew assembleDebug
```

#### Release 版本（需签名）

1. 创建 `keystore.properties` 文件：

```properties
storePassword=myStorePassword
keyPassword=mykeyPassword
keyAlias=myKeyAlias
storeFile=myStoreFileLocation
```

2. 编译：

```sh
# Linux/macOS
make release

# Windows
.\gradlew assembleRelease
```

### 更新配置文件

声韵方案配置位于 `app/src/main/assets/shared/`，修改后需要：

1. 重新编译 APK
2. 或通过 Trime 的重新部署功能加载（仅配置文件变更）

---

## 📝 版本历史

### v1.0.2 (2025-11-18)
- ✅ 移除 Luna Pinyin，减少 APK 体积（~920KB）
- ✅ 更新到最新声韵配置（3750行键盘布局）
- ✅ 优化 terra_pinyin 依赖

### v1.0.1 (2025-11-17)
- ✅ 集成声韵输入方案
- ✅ 添加 terra_pinyin 支持
- ✅ 儿童友好键盘布局

### v1.0.0 (2025-11-14)
- 🎉 首个发布版本
- ✅ 基于 Trime 3.x
- ✅ 声韵分层输入实现

---

## 🙏 致谢

### 声韵输入法

- 作者：[gshmu](https://github.com/gshmu)
- 灵感来源：为女儿学习拼音而设计

### Trime 输入法

- 开发者：[osfans](https://github.com/osfans)
- 贡献者：[boboIqiqi](https://github.com/boboIqiqi)、[Bambooin](https://github.com/Bambooin)、[WhiredPlanck](https://github.com/WhiredPlanck) 等
- 社区：[Trime Wiki](https://github.com/osfans/trime/wiki)、[QQ群](https://jq.qq.com/?_wv=1027&k=AXdR80HN)、[Telegram](https://t.me/trime_dev)

### 开源项目

- [RIME](https://rime.im)：佛振开发的跨平台输入法引擎
- [OpenCC](https://github.com/BYVoid/OpenCC)：繁简转换库
- [terra_pinyin](https://github.com/rime/rime-terra-pinyin)：地球拼音词库

---

## 📄 开源许可

本项目采用 [GPL-3.0-or-later](https://www.gnu.org/licenses/gpl-3.0) 许可证。

声韵输入方案配置文件同样采用 GPL-3.0 许可。

---

## 🔗 相关链接

- 🏠 声韵方案仓库：[github.com/gshmu/shengyun](https://github.com/gshmu/shengyun)
- 📱 Trime 输入法：[github.com/osfans/trime](https://github.com/osfans/trime)
- 🔤 RIME 输入法：[rime.im](https://rime.im)
- 📖 拼音规则参考：[pinyin.info](https://pinyin.info/rules/initials_finals.html)

---

**声韵输入法** · 让学拼音变得简单 · GPL-3.0 License
