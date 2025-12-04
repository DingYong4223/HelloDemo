import SwiftUI

struct ContentView: View {
    @State private var selectedItem: Int? = nil

    let items = [
        (id: 2, title: "Compose下拉刷新于上拉加载"),
        (id: 3, title: "Kotlin Compose Multiplatform")
    ]

    var body: some View {
        NavigationView {
            List(items, id: \.id) { item in
                NavigationLink(destination: destinationView(for: item.id)) {
                    Text(item.title)
                        .font(.body)
                }
            }
            .navigationTitle("Compose实现")
        }
    }

    @ViewBuilder
    private func destinationView(for id: Int) -> some View {
        switch id {
        case 2:
            PullRefreshScreen()
        case 3:
            SharedComposeScreen()
        default:
            Text("Unknown")
        }
    }
}

// MARK: - Pull Refresh Screen
struct PullRefreshScreen: View {
    @State private var items: [String] = (1...10).map { "列表项 \($0)" }
    @State private var isRefreshing = false
    @State private var pageNumber = 1

    var body: some View {
        List {
            ForEach(items, id: \.self) { item in
                Text(item)
            }

            HStack {
                Spacer()
                if isRefreshing {
                    ProgressView()
                } else {
                    Text("上拉加载更多")
                        .foregroundColor(.gray)
                }
                Spacer()
            }
            .onAppear {
                loadMore()
            }
        }
        .navigationTitle("下拉刷新于上拉加载")
        .navigationBarTitleDisplayMode(.inline)
    }

    private func refresh() {
        isRefreshing = true
        DispatchQueue.main.asyncAfter(deadline: .now() + 2.0) {
            pageNumber = 1
            items = (1...10).map { "列表项 \($0)" }
            isRefreshing = false
        }
    }

    private func loadMore() {
        guard !isRefreshing else { return }
        isRefreshing = true
        DispatchQueue.main.asyncAfter(deadline: .now() + 2.0) {
            pageNumber += 1
            let startIndex = (pageNumber - 1) * 10 + 1
            let newItems = (startIndex..<startIndex + 10).map { "列表项 \($0)" }
            items.append(contentsOf: newItems)
            isRefreshing = false
        }
    }
}

// MARK: - Shared Compose Screen
/// 这个视图展示来自 shared 库的 Compose 组件
/// 对应 Android 中的 SharedDemoActivity
struct SharedComposeScreen: View {
    var body: some View {
        SharedComposeView()
    }
}

#Preview {
    ContentView()
}

