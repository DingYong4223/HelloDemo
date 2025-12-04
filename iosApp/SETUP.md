# iOS 应用设置指南

## 快速开始

### 1. 构建 Shared Framework

在项目根目录运行：

```bash
chmod +x build-ios-framework.sh
./build-ios-framework.sh
```

或者手动构建：

```bash
./gradlew :shared:iosX64Binaries
./gradlew :shared:iosArm64Binaries
./gradlew :shared:iosSimulatorArm64Binaries
```

### 2. 在 Xcode 中打开项目

```bash
open iosApp.xcodeproj
```

### 3. 配置 Framework 链接

#### 方法 1：自动链接（推荐）

1. 在 Xcode 中选择 iosApp target
2. 进入 Build Settings
3. 搜索 "Framework Search Paths"
4. 添加以下路径：
   ```
   $(SRCROOT)/../shared/build/xcode-frameworks
   ```

#### 方法 2：手动链接

1. 在 Xcode 中选择 iosApp target
2. 进入 Build Phases
3. 展开 "Link Binary With Libraries"
4. 点击 "+" 按钮
5. 点击 "Add Other..."
6. 导航到 `shared/build/xcode-frameworks/Shared.framework`
7. 选择并添加

### 4. 构建和运行

1. 选择目标设备或模拟器
2. 按 Cmd+R 或点击 Run 按钮

## 项目结构

```
iosApp/
├── iosApp/                          # 应用源代码
│   ├── iosAppApp.swift              # 应用入口点
│   ├── ContentView.swift            # 主视图和导航
│   └── Assets.xcassets/             # 应用资源
├── iosApp.xcodeproj/                # Xcode 项目配置
│   ├── project.pbxproj              # 项目配置文件
│   ├── project.xcworkspace/         # 工作区配置
│   └── xcshareddata/xcschemes/      # 构建方案
├── build.gradle.kts                 # Gradle 配置
├── README.md                        # 应用文档
└── SETUP.md                         # 本文件
```

## 应用功能

### 主页面 (ContentView)

展示四个功能选项：

1. **Compose视图中嵌套View视图**
   - 演示 Compose 中嵌套原生 View 的方式
   - 展示 AndroidView composable 的用法

2. **View视图中嵌套Compose视图**
   - 演示 View 中嵌套 Compose 的方式
   - 展示混合开发的灵活性

3. **Compose下拉刷新于上拉加载**
   - 实现下拉刷新功能
   - 实现上拉加载更多功能
   - 模拟网络请求延迟

4. **Kotlin Compose Multiplatform**
   - 展示来自 shared module 的 Compose 页面
   - 演示跨平台代码共享

## 开发指南

### 添加新的功能页面

1. 在 `ContentView.swift` 中的 `items` 数组添加新项：

```swift
let items = [
    (id: 0, title: "功能1"),
    (id: 1, title: "功能2"),
    (id: 2, title: "新功能"),  // 新增
]
```

2. 在 `destinationView` 方法中添加对应的视图：

```swift
@ViewBuilder
private func destinationView(for id: Int) -> some View {
    switch id {
    case 0:
        Feature1View()
    case 1:
        Feature2View()
    case 2:
        NewFeatureView()  // 新增
    default:
        Text("Unknown")
    }
}
```

3. 创建新的 SwiftUI View：

```swift
struct NewFeatureView: View {
    var body: some View {
        VStack {
            Text("新功能")
                .font(.headline)
            Spacer()
        }
        .navigationTitle("新功能")
        .navigationBarTitleDisplayMode(.inline)
    }
}
```

### 使用 Shared Module 中的组件

```swift
import Shared

struct MyView: View {
    var body: some View {
        VStack {
            // 使用 shared module 中的 Compose 组件
            Text("Shared Module Integration")
        }
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

### Q: 如何在真机上运行？

A:
1. 连接 iOS 设备
2. 在 Xcode 中选择设备
3. 按 Cmd+R 运行

### Q: 支持哪些 iOS 版本？

A: 当前配置支持 iOS 14.0 及以上版本。

## 故障排除

### 构建失败

1. 清除 Xcode 构建缓存：
   ```bash
   rm -rf ~/Library/Developer/Xcode/DerivedData/*
   ```

2. 重新构建 framework：
   ```bash
   ./gradlew clean :shared:iosX64Binaries
   ```

3. 在 Xcode 中清除构建文件夹：
   - Cmd+Shift+K

### 运行时错误

1. 检查 Xcode Console 输出
2. 查看 Debug Navigator
3. 使用 Instruments 进行性能分析

### Framework 找不到

1. 确认 Framework Search Paths 设置正确
2. 确认 framework 已构建
3. 尝试清除 Xcode 缓存并重新构建

## 性能优化

1. **使用 Release 构建**
   - 在 Xcode 中选择 Release 配置进行性能测试

2. **优化 Framework 大小**
   - 移除未使用的代码
   - 使用 Link Time Optimization (LTO)

3. **内存管理**
   - 监控内存使用情况
   - 及时释放不需要的资源

## 相关文档

- [iOS_INTEGRATION_GUIDE.md](../iOS_INTEGRATION_GUIDE.md) - 详细的集成指南
- [README.md](./README.md) - 应用文档
- [Kotlin Multiplatform Documentation](https://kotlinlang.org/docs/multiplatform.html)
- [SwiftUI Documentation](https://developer.apple.com/documentation/swiftui)

## 支持

如有问题，请参考：
- shared module 的文档
- androidApp 的文档
- Kotlin 官方文档

