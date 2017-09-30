# RedirectStorage

[![](https://jitpack.io/v/Trumeet/RedirectStorage.svg)](https://jitpack.io/#Trumeet/RedirectStorage)

针对第三方 SDK 乱改存储卡和读取用户数据等行为，利用反射方式重定向 SD 卡目录。

# 依賴

* Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

* 查詢最新版本

最新版本：[![](https://jitpack.io/v/Trumeet/RedirectStorage.svg)](https://jitpack.io/#Trumeet/RedirectStorage)

* 添加依賴

```
dependencies {
        compile 'com.github.Trumeet:RedirectStorage:<最新版本號>'
}
```

# Usage

只有一个类，非常简单：

```java

// 启用
enable(PathCallback callback) // 当需要 Hack 路径的时候的 Callback

// 禁用
disable()

// 是否已启用
isEnable()

// 是否已安装
isInstall()

// 获取真实路径，比如需要保存图片
getRealPath()

```

**尚未严格兼容性测试，目前 Android O 模拟器测试通过，理论上支持 KitKat 至 Oreo。有问题请提交 issues**

不支持 KitKat 以下 ROM。（哪位大佬能帮忙想出办法兼容，个人认为主要是 `[getDirectory](https://github.com/android/platform_frameworks_base/blob/jb-release/core/java/android/os/Environment.java#L471)`` 方法，感激不尽。）

# Licenses
使用本項目請確保您遵守 `Apache License 2.0`

**特别感谢 [@heruoxin](https://github.com/heruoxin) （冰箱作者）提供核心代码 ~~，我只是做了个简单封装和提供了几个接口 逃跑~~**

```
Copyright (C) 2017 Trumeet

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
