<!--
SPDX-FileCopyrightText: 2015 - 2025 Rime community
SPDX-FileCopyrightText: 2025 Shengyun IME

SPDX-License-Identifier: GPL-3.0-or-later
-->

# 聲韻輸入法 Shengyun IME

> 專為學拼音的孩子設計的聲韻分層拼音輸入法
> 基於 [Trime](https://github.com/osfans/trime) (Android RIME 輸入法) 和 [聲韻方案](https://github.com/gshmu/shengyun) 構建

![build](https://github.com/gshmu/shengyun-rime/actions/workflows/commit-ci.yml/badge.svg?branch=develop)
[![License: GPL v3](https://img.shields.io/badge/License-GPL%20v3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![GitHub release](https://img.shields.io/github/release/gshmu/shengyun-rime.svg)](https://github.com/gshmu/shengyun-rime/releases)

[English](README.md) | [简体中文](README_sc.md) | 繁體中文

---

## 📖 關於聲韻輸入法

聲韻拼音是一種創新的漢字輸入方法，將每個漢字的拼音分為**聲母**和**韻母**兩層鍵盤，通過**兩次點擊**即可完成一個完整拼音的輸入。

> **說明**：這是一個**預配置聲韻方案的 Trime 構建版本**。聲韻輸入方案本身在獨立倉庫維護：[github.com/gshmu/shengyun](https://github.com/gshmu/shengyun)

### ✨ 核心特性

- ✅ **符合拼音規範**：嚴格遵循漢語拼音聲韻規則
- ✅ **兩步輸入**：聲母 + 韻母，高效快捷
- ✅ **支持聲調提示**：鍵盤頂行顯示聲調符號 ˉˊˇˋ，幫助學習拼音
- ✅ **兒童友好**：大按鍵、清晰配色、純拼音界面
- ✅ **專業詞庫**：基於 [terra_pinyin](https://github.com/rime/rime-terra-pinyin) 180萬詞條
- ✅ **開源免費**：基於 RIME 引擎和 Trime 平台

### 🎯 設計理念

**為什麼要做分層輸入？**

傳統的全鍵盤 QWERTY 拼音鍵盤對學齡前兒童來說過於複雜，按鍵太多、記憶負擔重。聲韻將拼音分為：

1. **第一步**：選擇聲母 (b/p/m/f/d/t/n/l/g/k/h/j/q/x/zh/ch/sh/r/z/c/s/y/w)
2. **第二步**：選擇韻母 (a/o/e/i/u/ü 及其組合)

這種分層設計幫助兒童：
- 專注於拼音的基本結構
- 減少鍵盤按鍵數量
- 強化聲韻組合規律的認知

---

## 📥 安裝方法

### 方式一：直接安裝 APK（推薦）

訪問 [Releases 頁面](https://github.com/gshmu/shengyun-rime/releases) 下載最新的 APK 文件並安裝。

聲韻輸入法已預配置，安裝後即可使用。

---

## 📚 技術架構

本項目基於以下開源組件構建：

- **[RIME](https://rime.im)**：跨平台輸入法引擎
- **[Trime](https://github.com/osfans/trime)**：Android 平台 RIME 前端
- **[terra_pinyin](https://github.com/rime/rime-terra-pinyin)**：地球拼音詞庫（180萬詞）
- **[聲韻方案](https://github.com/gshmu/shengyun)**：聲韻分層配置文件

### 配置文件說明

```
app/src/main/assets/shared/
├── shengyun.schema.yaml        # 聲韻輸入方案配置
├── shengyun.trime.yaml         # 鍵盤佈局配置（3750行）
├── terra_pinyin.dict.yaml      # 地球拼音詞庫（180萬詞）
├── terra_pinyin.schema.yaml    # 地球拼音方案
├── default.custom.yaml         # 默認啟用聲韻方案
└── stroke.schema.yaml          # 筆畫輔助輸入
```

---

## 🛠️ 開發者指南

### 環境要求

- Android SDK 和 Android NDK
- JDK (OpenJDK) 17
- Python 3（OpenCC 字典生成）
- Git（包括子模組支持）

### 克隆項目

```sh
git clone git@github.com:gshmu/shengyun-rime.git
cd shengyun-rime
git submodule update --init --recursive --filter=blob:none
```

### 編譯項目

#### Debug 版本（無簽名）

```sh
# Linux/macOS
make debug

# Windows
.\gradlew assembleDebug
```

#### Release 版本（需簽名）

1. 創建 `keystore.properties` 文件：

```properties
storePassword=myStorePassword
keyPassword=mykeyPassword
keyAlias=myKeyAlias
storeFile=myStoreFileLocation
```

2. 編譯：

```sh
# Linux/macOS
make release

# Windows
.\gradlew assembleRelease
```

### 更新配置文件

聲韻方案配置位於 `app/src/main/assets/shared/`，修改後需要：

1. 重新編譯 APK
2. 或通過 Trime 的重新部署功能加載（僅配置文件變更）

---

## 📝 版本歷史

### v1.0.2 (2025-11-18)
- ✅ 移除 Luna Pinyin，減少 APK 體積（~920KB）
- ✅ 更新到最新聲韻配置（3750行鍵盤佈局）
- ✅ 優化 terra_pinyin 依賴

### v1.0.1 (2025-11-17)
- ✅ 集成聲韻輸入方案
- ✅ 添加 terra_pinyin 支持
- ✅ 兒童友好鍵盤佈局

### v1.0.0 (2025-11-14)
- 🎉 首個發佈版本
- ✅ 基於 Trime 3.x
- ✅ 聲韻分層輸入實現

---

## 🙏 致謝

### 聲韻輸入法

- 作者：[gshmu](https://github.com/gshmu)
- 靈感來源：為女兒學習拼音而設計

### Trime 輸入法

- 開發者：[osfans](https://github.com/osfans)
- 貢獻者：[boboIqiqi](https://github.com/boboIqiqi)、[Bambooin](https://github.com/Bambooin)、[WhiredPlanck](https://github.com/WhiredPlanck) 等
- 社群：[Trime Wiki](https://github.com/osfans/trime/wiki)、[QQ群](https://jq.qq.com/?_wv=1027&k=AXdR80HN)、[Telegram](https://t.me/trime_dev)

### 開源項目

- [RIME](https://rime.im)：佛振開發的跨平台輸入法引擎
- [OpenCC](https://github.com/BYVoid/OpenCC)：繁簡轉換庫
- [terra_pinyin](https://github.com/rime/rime-terra-pinyin)：地球拼音詞庫

---

## 📄 開源許可

本項目採用 [GPL-3.0-or-later](https://www.gnu.org/licenses/gpl-3.0) 許可證。

聲韻輸入方案配置文件同樣採用 GPL-3.0 許可。

---

## 🔗 相關鏈接

- 🏠 聲韻方案倉庫：[github.com/gshmu/shengyun](https://github.com/gshmu/shengyun)
- 📱 Trime 輸入法：[github.com/osfans/trime](https://github.com/osfans/trime)
- 🔤 RIME 輸入法：[rime.im](https://rime.im)
- 📖 拼音規則參考：[pinyin.info](https://pinyin.info/rules/initials_finals.html)

---

**聲韻輸入法** · 讓學拼音變得簡單 · GPL-3.0 License
