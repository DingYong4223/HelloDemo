# iosApp - iOS Application

这是一个 iOS 应用，使用 SwiftUI 构建，并集成了 Kotlin Compose Multiplatform 的 shared module。

## 功能

- 展示 Compose 实现的各种功能
- 集成 shared module 中的 Compose 页面
- 支持跨平台 UI 代码共享

## 项目结构

```
iosApp/
├── iosApp/
│   ├── iosAppApp.swift          # 应用入口
│   ├── ContentView.swift         # 主视图
│   └── Assets.xcassets/          # 资源文件
├── iosApp.xcodeproj/             # Xcode 项目配置
└── README.md                      # 本文件
```

## 构建和运行

### 前置条件

- Xcode 15.0 或更高版本
- iOS 14.0 或更高版本
- 已构建的 Shared.framework

### 步骤

1. 首先构建 shared module：
   ```bash
   cd ..
   ./gradlew :shared:iosX64Binaries
   ```

2. 在 Xcode 中打开项目：
   ```bash
   open iosApp.xcodeproj
   ```

3. 选择目标设备或模拟器，然后点击 Run

## 功能说明

### 主页面 (ContentView)
- 展示四个可选项的列表
- 支持导航到不同的功能页面

### 功能页面

1. **Compose视图中嵌套View视图** - 展示如何在 Compose 中嵌套原生 View
2. **View视图中嵌套Compose视图** - 展示如何在 View 中嵌套 Compose
3. **Compose下拉刷新于上拉加载** - 展示下拉刷新和上拉加载功能
4. **Kotlin Compose Multiplatform** - 展示来自 shared module 的 Compose 页面

## 集成 Shared Module

iOS 应用通过导入 `Shared` framework 来使用 shared module 中的 Compose 代码：

```swift
import Shared

// 使用 shared module 中的 Compose 组件
SharedDemoScreen()
```

## 开发指南

### 添加新的页面

1. 在 `ContentView.swift` 中的 `items` 数组中添加新项
2. 在 `destinationView` 方法中添加对应的视图
3. 创建新的 SwiftUI View 结构体

### 与 Shared Module 交互

shared module 提供了跨平台的 Compose UI 组件，可以直接在 iOS 应用中使用。

## 注意事项

- 确保 Shared.framework 已正确构建并链接
- iOS 部署目标为 14.0
- 支持 iPhone 和 iPad

## 许可证

MIT License

