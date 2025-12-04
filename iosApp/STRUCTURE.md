# iosApp 目录结构说明

## 完整的目录结构

```
iosApp/
├── iosApp/                                    # 应用源代码目录
│   ├── iosAppApp.swift                       # 应用入口点
│   ├── ContentView.swift                     # 主视图和导航
│   ├── Assets.xcassets/                      # 应用资源
│   │   ├── Contents.json
│   │   ├── AppIcon.appiconset/              # 应用图标
│   │   │   └── Contents.json
│   │   └── AccentColor.colorset/            # 强调色
│   │       └── Contents.json
│   └── Preview Content/                      # 预览内容
│       └── Preview Assets.xcassets/
│           └── Contents.json
│
├── iosApp.xcodeproj/                         # Xcode 项目配置
│   ├── project.pbxproj                       # 项目配置文件
│   ├── project.xcworkspace/                  # 工作区配置
│   │   ├── contents.xcworkspacedata
│   │   └── xcuserdata/                       # 用户特定配置
│   │       └── delanding.xcuserdatad/
│   │           └── xcschemes/
│   │               └── xcschememanagement.plist
│   └── xcshareddata/                         # 共享配置
│       └── xcschemes/
│           └── iosApp.xcscheme               # 构建方案
│
├── build.gradle.kts                          # Gradle 配置
├── README.md                                 # 应用文档
├── SETUP.md                                  # 设置指南
└── STRUCTURE.md                              # 本文件
```

## 文件说明

### 源代码文件

#### `iosApp/iosAppApp.swift`
- 应用的入口点
- 使用 `@main` 标记
- 定义应用的主场景

```swift
@main
struct iosAppApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
```

#### `iosApp/ContentView.swift`
- 主视图，包含导航逻辑
- 展示四个功能选项的列表
- 实现各个功能页面的视图

包含的视图：
- `ContentView` - 主列表视图
- `ComposeInViewScreen` - Compose 中嵌套 View
- `ViewInComposeScreen` - View 中嵌套 Compose
- `PullRefreshScreen` - 下拉刷新和上拉加载
- `SharedComposeScreen` - Shared Module 页面

### 资源文件

#### `Assets.xcassets/`
Xcode 资源目录，包含：
- **AppIcon.appiconset/** - 应用图标（各种尺寸）
- **AccentColor.colorset/** - 应用强调色
- **Contents.json** - 资源目录配置

#### `Preview Content/Preview Assets.xcassets/`
SwiftUI 预览使用的资源目录

### 项目配置文件

#### `iosApp.xcodeproj/project.pbxproj`
- Xcode 项目的主配置文件
- 包含构建设置、目标配置等
- 定义源文件、资源、框架等的关联

#### `iosApp.xcodeproj/xcshareddata/xcschemes/iosApp.xcscheme`
- 构建方案配置
- 定义构建、运行、测试、分析、归档等操作

#### `iosApp.xcodeproj/project.xcworkspace/contents.xcworkspacedata`
- 工作区配置文件
- 定义工作区包含的项目

### 配置文件

#### `build.gradle.kts`
- Gradle 构建配置
- 用于从 Gradle 构建 iOS framework

#### `README.md`
- iOS 应用的文档
- 包含功能说明、构建步骤等

#### `SETUP.md`
- iOS 应用的设置指南
- 包含快速开始、常见问题等

## 关键目录说明

### iosApp/ 目录
这是应用的源代码目录，包含：
- Swift 源文件（.swift）
- 资源文件（Assets.xcassets）
- 预览内容

### iosApp.xcodeproj/ 目录
这是 Xcode 项目包，包含：
- 项目配置（project.pbxproj）
- 工作区配置（project.xcworkspace）
- 构建方案（xcschemes）
- 用户特定配置（xcuserdata）

## 文件类型说明

### .swift 文件
Swift 源代码文件，包含应用逻辑和 UI 定义

### .xcassets 目录
Xcode 资源目录，用于管理应用的图片、颜色等资源

### .json 文件
配置文件，定义资源的元数据和属性

### .pbxproj 文件
Xcode 项目配置文件（二进制格式）

### .xcscheme 文件
Xcode 构建方案配置文件（XML 格式）

### .xcworkspacedata 文件
Xcode 工作区配置文件（XML 格式）

### .plist 文件
属性列表文件，用于存储用户特定配置

## 添加新文件

### 添加新的 Swift 文件

1. 在 Xcode 中右键点击 iosApp 文件夹
2. 选择 "New File..."
3. 选择 "Swift File"
4. 输入文件名
5. 确保 "iosApp" target 被选中

### 添加新的资源

1. 在 Xcode 中选择 Assets.xcassets
2. 点击 "+" 按钮
3. 选择资源类型
4. 添加资源文件

## 常见操作

### 查看项目结构
```bash
find iosApp -type f | sort
```

### 列出所有 Swift 文件
```bash
find iosApp -name "*.swift"
```

### 列出所有资源
```bash
find iosApp/iosApp/Assets.xcassets -type f
```

### 在 Xcode 中打开项目
```bash
open iosApp/iosApp.xcodeproj
```

## 注意事项

1. **Assets.xcassets** 必须是一个特殊的目录，不能直接编辑其内容
2. **Preview Content** 用于 SwiftUI 预览，不会包含在最终应用中
3. **xcuserdata** 目录包含用户特定配置，通常不应提交到版本控制
4. **project.pbxproj** 是二进制文件，不应手动编辑

## 相关文档

- [README.md](./README.md) - iOS 应用文档
- [SETUP.md](./SETUP.md) - iOS 应用设置指南
- [../iOS_INTEGRATION_GUIDE.md](../iOS_INTEGRATION_GUIDE.md) - iOS 集成指南
- [../PROJECT_OVERVIEW.md](../PROJECT_OVERVIEW.md) - 项目概览

