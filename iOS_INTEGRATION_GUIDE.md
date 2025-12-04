# iOS 集成指南

本指南说明如何在 iOS 应用中集成 Kotlin Compose Multiplatform 的 shared module。

## 项目结构

```
HelloDelanding/
├── androidApp/                   # Android 应用
├── shared/                       # Kotlin Compose Multiplatform Module
├── iosApp/                       # iOS 应用（新增）
│   ├── iosApp/
│   │   ├── iosAppApp.swift       # 应用入口
│   │   ├── ContentView.swift     # 主视图
│   │   └── Assets.xcassets/      # 资源文件
│   ├── iosApp.xcodeproj/         # Xcode 项目
│   ├── build.gradle.kts          # Gradle 配置
│   └── README.md                 # iOS 应用文档
├── build-ios-framework.sh        # iOS Framework 构建脚本
└── iOS_INTEGRATION_GUIDE.md      # 本文件
```

## 前置条件

- Xcode 15.0 或更高版本
- iOS 14.0 或更高版本
- Kotlin 2.0.21 或更高版本
- Gradle 8.13.1 或更高版本

## 构建步骤

### 1. 构建 Shared Framework

首先，需要从 shared module 构建 iOS framework：

```bash
# 使用提供的脚本
chmod +x build-ios-framework.sh
./build-ios-framework.sh

# 或者手动构建
./gradlew :shared:iosX64Binaries      # 模拟器 x64
./gradlew :shared:iosArm64Binaries    # 真机 ARM64
./gradlew :shared:iosSimulatorArm64Binaries  # 模拟器 ARM64
```

构建完成后，framework 将位于：
```
shared/build/xcode-frameworks/
```

### 2. 在 Xcode 中打开 iOS 项目

```bash
open iosApp/iosApp.xcodeproj
```

### 3. 链接 Shared Framework

1. 在 Xcode 中选择 iosApp target
2. 进入 Build Phases
3. 在 Link Binary With Libraries 中添加 Shared.framework
4. 在 Framework Search Paths 中添加：
   ```
   $(SRCROOT)/../shared/build/xcode-frameworks
   ```

### 4. 构建和运行

1. 选择目标设备或模拟器
2. 点击 Run 按钮或按 Cmd+R

## iOS 应用功能

### 主页面 (ContentView)

展示四个功能选项的列表：

1. **Compose视图中嵌套View视图**
   - 展示如何在 Compose 中嵌套原生 View
   - 使用 AndroidView composable

2. **View视图中嵌套Compose视图**
   - 展示如何在 View 中嵌套 Compose
   - 展示混合开发的灵活性

3. **Compose下拉刷新于上拉加载**
   - 实现下拉刷新功能
   - 实现上拉加载更多功能
   - 模拟网络请求

4. **Kotlin Compose Multiplatform**
   - 展示来自 shared module 的 Compose 页面
   - 演示跨平台代码共享

## 代码示例

### 导入 Shared Module

```swift
import Shared

// 使用 shared module 中的 Compose 组件
struct MyView: View {
    var body: some View {
        // 可以直接使用 shared module 中的组件
        Text("Shared Module Integration")
    }
}
```

### 添加新的页面

在 `ContentView.swift` 中：

```swift
struct ContentView: View {
    let items = [
        (id: 0, title: "新功能"),
        // ... 其他项
    ]

    @ViewBuilder
    private func destinationView(for id: Int) -> some View {
        switch id {
        case 0:
            NewFeatureView()
        // ... 其他情况
        default:
            Text("Unknown")
        }
    }
}

struct NewFeatureView: View {
    var body: some View {
        VStack {
            Text("新功能页面")
                .font(.headline)
            Spacer()
        }
        .navigationTitle("新功能")
    }
}
```

## 常见问题

### Q: 如何更新 Shared Framework？

A: 修改 shared module 后，重新运行构建命令：
```bash
./gradlew :shared:iosX64Binaries
```

### Q: Framework 链接失败怎么办？

A: 检查以下几点：
1. Framework Search Paths 是否正确设置
2. Framework 是否已正确构建
3. 在 Build Phases 中是否添加了 framework

### Q: 如何在 iOS 上使用 Compose 组件？

A: shared module 中的 Compose 组件可以通过 SwiftUI 的 interop 层在 iOS 上使用。具体实现取决于 Compose Multiplatform 的 iOS 支持。

### Q: 支持哪些 iOS 版本？

A: 当前配置支持 iOS 14.0 及以上版本。

## 开发工作流

1. **修改 shared module**
   - 编辑 `shared/src/commonMain/` 中的代码
   - 或编辑 `shared/src/iosMain/` 中的 iOS 特定代码

2. **构建 framework**
   ```bash
   ./gradlew :shared:iosX64Binaries
   ```

3. **在 Xcode 中测试**
   - 打开 iosApp.xcodeproj
   - 运行应用

4. **提交代码**
   - 提交 shared module 的更改
   - 提交 iosApp 的更改

## 性能优化建议

1. **使用 Release 构建**
   - 在 Xcode 中选择 Release 配置进行性能测试

2. **优化 Framework 大小**
   - 移除未使用的代码
   - 使用 Link Time Optimization (LTO)

3. **内存管理**
   - 监控内存使用情况
   - 及时释放不需要的资源

## 故障排除

### 构建失败

1. 清除构建缓存：
   ```bash
   ./gradlew clean
   ```

2. 重新构建 framework：
   ```bash
   ./gradlew :shared:iosX64Binaries
   ```

3. 在 Xcode 中清除构建文件夹：
   - Cmd+Shift+K

### 运行时错误

1. 检查 Console 输出
2. 查看 Xcode 的 Debug Navigator
3. 使用 Instruments 进行性能分析

## 相关资源

- [Kotlin Multiplatform Documentation](https://kotlinlang.org/docs/multiplatform.html)
- [Compose Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-multiplatform.html)
- [SwiftUI Documentation](https://developer.apple.com/documentation/swiftui)

## 支持

如有问题，请参考：
- shared module 的 README
- androidApp 的文档
- Kotlin 官方文档

