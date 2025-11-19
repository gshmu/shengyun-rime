<!--
SPDX-FileCopyrightText: 2015 - 2025 Rime community
SPDX-FileCopyrightText: 2025 Shengyun IME

SPDX-License-Identifier: GPL-3.0-or-later
-->

# Shengyun IME (声韵输入法)

> A layered Pinyin input method designed for children learning Chinese phonetics
> Built on [Trime](https://github.com/osfans/trime) (RIME for Android) with [Shengyun Schema](https://github.com/gshmu/shengyun)

![build](https://github.com/gshmu/shengyun-rime/actions/workflows/commit-ci.yml/badge.svg?branch=develop)
[![License: GPL v3](https://img.shields.io/badge/License-GPL%20v3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![GitHub release](https://img.shields.io/github/release/gshmu/shengyun-rime.svg)](https://github.com/gshmu/shengyun-rime/releases)

English | [简体中文](README_sc.md) | [繁體中文](README_tc.md)

---

## 📖 About Shengyun IME

Shengyun Pinyin is an innovative Chinese input method that splits each character's Pinyin into **initial** and **final** layers, requiring only **two taps** to complete a full Pinyin syllable.

> **Note**: This is a **Trime build with Shengyun schema pre-configured**. The Shengyun input schema itself is maintained in a separate repository: [github.com/gshmu/shengyun](https://github.com/gshmu/shengyun)

### ✨ Key Features

- ✅ **Standard-compliant**: Follows Chinese Pinyin initial-final rules
- ✅ **Two-tap input**: Initial + Final, efficient and fast
- ✅ **Tone markers**: Top row displays tone symbols ˉˊˇˋ for learning
- ✅ **Child-friendly**: Large keys, clear colors, pure Pinyin interface
- ✅ **Professional dictionary**: Based on [terra_pinyin](https://github.com/rime/rime-terra-pinyin) with 1.8M words
- ✅ **Open source**: Built on RIME engine and Trime platform

### 🎯 Design Philosophy

**Why layered input?**

Traditional full QWERTY Pinyin keyboards are overwhelming for preschool children with too many keys and high memory load. Shengyun splits Pinyin into:

1. **Step 1**: Select initial (b/p/m/f/d/t/n/l/g/k/h/j/q/x/zh/ch/sh/r/z/c/s/y/w)
2. **Step 2**: Select final (a/o/e/i/u/ü and their combinations)

This layered design helps children:
- Focus on Pinyin's fundamental structure
- Reduce number of keyboard keys
- Reinforce initial-final combination patterns

---

## 📥 Installation

### Method 1: Install APK directly (Recommended)

Visit the [Releases page](https://github.com/gshmu/shengyun-rime/releases) to download and install the latest APK.

Shengyun schema is pre-configured and ready to use after installation.

---

## 📚 Technical Architecture

This project is built on the following open-source components:

- **[RIME](https://rime.im)**: Cross-platform input method engine
- **[Trime](https://github.com/osfans/trime)**: RIME frontend for Android
- **[terra_pinyin](https://github.com/rime/rime-terra-pinyin)**: Terra Pinyin dictionary (1.8M words)
- **[Shengyun Schema](https://github.com/gshmu/shengyun)**: Layered input configuration files

### Configuration Files

```
app/src/main/assets/shared/
├── shengyun.schema.yaml        # Shengyun input schema
├── shengyun.trime.yaml         # Keyboard layout (3750 lines)
├── terra_pinyin.dict.yaml      # Terra Pinyin dictionary (1.8M words)
├── terra_pinyin.schema.yaml    # Terra Pinyin schema
├── default.custom.yaml         # Enable Shengyun by default
└── stroke.schema.yaml          # Stroke auxiliary input
```

---

## 🛠️ Developer Guide

### Requirements

- Android SDK and Android NDK
- JDK (OpenJDK) 17
- Python 3 (for OpenCC dictionary generation)
- Git (with submodule support)

### Clone Project

```sh
git clone git@github.com:gshmu/shengyun-rime.git
cd shengyun-rime
git submodule update --init --recursive --filter=blob:none
```

### Build

#### Debug Version (unsigned)

```sh
# Linux/macOS
make debug

# Windows
.\gradlew assembleDebug
```

#### Release Version (signed)

1. Create `keystore.properties` file:

```properties
storePassword=myStorePassword
keyPassword=mykeyPassword
keyAlias=myKeyAlias
storeFile=myStoreFileLocation
```

2. Build:

```sh
# Linux/macOS
make release

# Windows
.\gradlew assembleRelease
```

### Update Configuration

Shengyun schema configurations are in `app/src/main/assets/shared/`. After modification:

1. Rebuild APK
2. Or use Trime's deploy function to reload (for config changes only)

---

## 📝 Release History

### v1.0.2 (2025-11-18)
- ✅ Removed Luna Pinyin, reduced APK size (~920KB)
- ✅ Updated to latest Shengyun config (3750-line keyboard layout)
- ✅ Optimized terra_pinyin dependencies

### v1.0.1 (2025-11-17)
- ✅ Integrated Shengyun input schema
- ✅ Added terra_pinyin support
- ✅ Child-friendly keyboard layout

### v1.0.0 (2025-11-14)
- 🎉 First release
- ✅ Based on Trime 3.x
- ✅ Layered initial-final input implementation

---

## 🙏 Acknowledgments

### Shengyun IME

- Author: [gshmu](https://github.com/gshmu)
- Inspiration: Designed for daughter's Pinyin learning

### Trime IME

- Developer: [osfans](https://github.com/osfans)
- Contributors: [boboIqiqi](https://github.com/boboIqiqi), [Bambooin](https://github.com/Bambooin), [WhiredPlanck](https://github.com/WhiredPlanck), and others
- Community: [Trime Wiki](https://github.com/osfans/trime/wiki), [QQ Group](https://jq.qq.com/?_wv=1027&k=AXdR80HN), [Telegram](https://t.me/trime_dev)

### Open Source Projects

- [RIME](https://rime.im): Cross-platform IME engine by lotem
- [OpenCC](https://github.com/BYVoid/OpenCC): Traditional-Simplified Chinese conversion
- [terra_pinyin](https://github.com/rime/rime-terra-pinyin): Terra Pinyin dictionary

---

## 📄 License

This project is licensed under [GPL-3.0-or-later](https://www.gnu.org/licenses/gpl-3.0).

Shengyun input schema configuration files also use GPL-3.0 license.

---

## 🔗 Links

- 🏠 Shengyun Schema: [github.com/gshmu/shengyun](https://github.com/gshmu/shengyun)
- 📱 Trime IME: [github.com/osfans/trime](https://github.com/osfans/trime)
- 🔤 RIME IME: [rime.im](https://rime.im)
- 📖 Pinyin Rules: [pinyin.info](https://pinyin.info/rules/initials_finals.html)

---

**Shengyun IME** · Making Pinyin Learning Simple · GPL-3.0 License
