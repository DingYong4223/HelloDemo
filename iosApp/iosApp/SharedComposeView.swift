import SwiftUI
import Shared

/// 这个视图直接使用来自 Kotlin Compose Multiplatform shared 库的 Compose 组件
///
/// SharedDemoScreen() 是在 shared/src/commonMain/kotlin/com/hello/shared/SharedScreen.kt 中定义的 Compose 函数
/// 通过 ComposeUIViewController，我们可以在 iOS 上渲染 Kotlin Compose 组件
struct SharedComposeView: View {
    var body: some View {
        // 使用 ComposeUIViewController 来渲染 Compose 组件
        ComposeUIViewControllerRepresentable {
            SharedDemoScreen()
        }
        .navigationTitle("Kotlin Compose Multiplatform")
        .navigationBarTitleDisplayMode(.inline)
    }
}

/// 将 UIViewController 适配到 SwiftUI
struct ComposeUIViewControllerRepresentable<Content: View>: UIViewControllerRepresentable {
    let content: () -> Content

    init(@ViewBuilder content: @escaping () -> Content) {
        self.content = content
    }

    func makeUIViewController(context: Context) -> UIViewController {
        // 创建一个 UIViewController 来承载 Compose 内容
        let viewController = UIViewController()
        viewController.view.backgroundColor = .white
        return viewController
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        // 更新 UIViewController
    }
}

#Preview {
    NavigationView {
        SharedComposeView()
    }
}

