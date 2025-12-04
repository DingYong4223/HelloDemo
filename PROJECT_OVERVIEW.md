# HelloDelanding 项目概览

这是一个跨平台移动应用项目，使用 Kotlin Compose Multiplatform 实现 Android 和 iOS 的代码共享。

## 项目结构

```
HelloDelanding/
├── androidApp/                      # Android 应用
│   ├── src/main/
│   │   ├── java/com/hello/android/
│   │   │   ├── compose/            # Compose 相关页面
│   │   │   ├── kotlin/             # Kotlin 实验
│   │   │   └── memoryleak/         # 内存泄漏演示
│   │   ├── res/                    # 资源文件
│   │   └── AndroidManifest.xml
│   ├── build.gradle.kts
│   └── proguard-rules.pro
│
├── iosApp/                          # iOS 应用（新增）
│   ├── iosApp/
│   │   ├── iosAppApp.swift         # 应用入口
│   │   ├── ContentView.swift       # 主视图
│   │   └── Assets.xcassets/        # 资源文件
│   ├── iosApp.xcodeproj/           # Xcode 项目
│   ├── build.gradle.kts
│   ├── README.md
│   └── SETUP.md
│
├── shared/                          # Kotlin Compose Multiplatform Module
│   ├── src/
│   │   ├── commonMain/kotlin/com/hello/shared/
│   │   │   ├── SharedScreen.kt     # 跨平台 Compose UI
│   │   │   └── Platform.kt         # 平台接口
│   │   ├── androidMain/kotlin/com/hello/shared/
│   │   │   └── Platform.android.kt # Android 实现
│   │   └── iosMain/kotlin/com/hello/shared/
│   │       └── Platform.ios.kt     # iOS 实现
│   └── build.gradle.kts
│
├── gradle/
│   ├── wrapper/
│   └── libs.versions.toml          # 依赖版本管理
│
├── build.gradle.kts                # 根项目配置
├── settings.gradle.kts             # 项目设置
├── build-ios-framework.sh          # iOS Framework 构建脚本
├── iOS_INTEGRATION_GUIDE.md        # iOS 集成指南
├── PROJECT_OVERVIEW.md             # 本文件
└── README.md                       # 项目说明
```

## 核心功能

### 1. Android 应用 (androidApp)

#### 主要功能
- **内存泄漏演示** - 展示常见的内存泄漏问题
- **Kotlin 实验** - Kotlin 语言特性演示
- **Compose 实现** - 多个 Compose 功能演示

#### Compose 功能
1. **Compose视图中嵌套View视图** - 使用 AndroidView 嵌套原生 View
2. **View视图中嵌套Compose视图** - 在 View 中使用 Compose
3. **Compose下拉刷新于上拉加载** - 实现下拉刷新和上拉加载
4. **Kotlin Compose Multiplatform** - 展示跨平台 Compose 页面

### 2. iOS 应用 (iosApp)

#### 主要功能
- 使用 SwiftUI 构建用户界面
- 集成 Kotlin Compose Multiplatform 的 shared module
- 展示与 Android 应用相同的功能

#### 页面结构
- **主页面** - 功能列表导航
- **功能页面** - 对应各个功能的详细页面
- **Shared 页面** - 展示来自 shared module 的 Compose 页面

### 3. Shared Module (shared)

#### 特点
- 使用 Kotlin Compose Multiplatform
- 支持 Android、iOS（x64、arm64、simulator）
- 提供跨平台的 Compose UI 组件

#### 内容
- `SharedScreen.kt` - 跨平台 Compose UI 实现
- `Platform.kt` - 平台相关接口定义
- 平台特定实现（Android、iOS）

## 技术栈

### 共享技术
- **Kotlin** 2.0.21
- **Compose Multiplatform** 1.6.10
- **Gradle** 8.13.1

### Android 特定
- **Android SDK** 36
- **Compose Material3** 最新版本
- **AndroidX** 库

### iOS 特定
- **Swift** 5.0
- **SwiftUI** 框架
- **iOS** 14.0+

## 构建和运行

### Android 应用

```bash
# 构建 Debug APK
./gradlew :androidApp:assembleDebug

# 构建 Release APK
./gradlew :androidApp:assembleRelease

# 运行应用
./gradlew :androidApp:installDebug
```

### iOS 应用

```bash
# 构建 Shared Framework
./gradlew :shared:iosX64Binaries
./gradlew :shared:iosArm64Binaries
./gradlew :shared:iosSimulatorArm64Binaries

# 或使用脚本
chmod +x build-ios-framework.sh
./build-ios-framework.sh

# 在 Xcode 中打开项目
open iosApp/iosApp.xcodeproj
```

## 开发工作流

### 修改 Shared Module

1. 编辑 `shared/src/commonMain/` 中的代码
2. 构建 framework：
   ```bash
   ./gradlew :shared:iosX64Binaries
   ```
3. 在 Android 中测试：
   ```bash
   ./gradlew :androidApp:assembleDebug
   ```
4. 在 iOS 中测试：
   - 在 Xcode 中运行应用

### 添加新的 Android 功能

1. 在 `androidApp/src/main/java/com/hello/android/` 中创建新的 Activity
2. 在 `AndroidManifest.xml` 中注册
3. 在相应的列表中添加导航项

### 添加新的 iOS 功能

1. 在 `iosApp/iosApp/` 中创建新的 SwiftUI View
2. 在 `ContentView.swift` 中添加导航项
3. 在 `destinationView` 方法中添加对应的视图

## 依赖管理

所有依赖版本在 `gradle/libs.versions.toml` 中集中管理：

```toml
[versions]
kotlin = "2.0.21"
composeBom = "2024.09.00"
composeMultiplatform = "1.6.10"
# ... 其他版本
```

## 常见任务

### 清除构建缓存

```bash
./gradlew clean
```

### 更新依赖

```bash
./gradlew :shared:dependencies
./gradlew :androidApp:dependencies
```

### 运行测试

```bash
./gradlew test
```

### 生成文档

```bash
./gradlew dokka
```

## 项目配置

### Gradle 配置
- **AGP** (Android Gradle Plugin): 8.13.1
- **Kotlin Plugin**: 2.0.21
- **Compose Plugin**: 1.6.10

### Android 配置
- **Min SDK**: 24
- **Target SDK**: 36
- **Compile SDK**: 36

### iOS 配置
- **Deployment Target**: iOS 14.0
- **Swift Version**: 5.0

## 文档

- [iOS_INTEGRATION_GUIDE.md](./iOS_INTEGRATION_GUIDE.md) - iOS 集成详细指南
- [iosApp/README.md](./iosApp/README.md) - iOS 应用文档
- [iosApp/SETUP.md](./iosApp/SETUP.md) - iOS 应用设置指南
- [shared/README.md](./shared/README.md) - Shared Module 文档（如果存在）

## 故障排除

### 构建失败

1. 清除缓存：
   ```bash
   ./gradlew clean
   ```

2. 重新构建：
   ```bash
   ./gradlew build
   ```

### Framework 链接问题

1. 重新构建 framework：
   ```bash
   ./gradlew :shared:iosX64Binaries
   ```

2. 在 Xcode 中清除缓存：
   - Cmd+Shift+K

### 版本不兼容

1. 检查 `gradle/libs.versions.toml` 中的版本
2. 更新到兼容的版本
3. 运行 `./gradlew clean build`

## 贡献指南

1. 创建新分支
2. 进行更改
3. 测试 Android 和 iOS 应用
4. 提交 Pull Request

## 许可证

MIT License

## 联系方式

如有问题或建议，请提交 Issue 或 Pull Request。

## 相关资源

- [Kotlin 官方文档](https://kotlinlang.org/docs/)
- [Compose Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-multiplatform.html)
- [Android 开发文档](https://developer.android.com/docs)
- [iOS 开发文档](https://developer.apple.com/documentation/)
- [SwiftUI 教程](https://developer.apple.com/tutorials/swiftui)

