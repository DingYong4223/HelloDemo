package com.hello.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PullRefreshScreen() {
    val items = remember { mutableStateOf<List<ListItem>>(emptyList()) }
    val isRefreshing = remember { mutableStateOf(false) }
    val isLoadingMore = remember { mutableStateOf(false) }
    val currentPage = remember { mutableStateOf(1) }
    val lazyListState = rememberLazyListState()

    // 初始化数据
    LaunchedEffect(Unit) {
        loadFirstPage(items, currentPage)
    }

    // 监听列表滚动，当滚动到底部时加载更多
    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleIndex ->
                if (lastVisibleIndex != null && lastVisibleIndex >= items.value.size - 3) {
                    if (!isLoadingMore.value && currentPage.value < 5) {
                        loadMoreData(items, currentPage, isLoadingMore)
                    }
                }
            }
    }

    // 监听刷新状态
    LaunchedEffect(isRefreshing.value) {
        if (isRefreshing.value) {
            delay(1000) // 模拟网络请求
            loadFirstPage(items, currentPage)
            isRefreshing.value = false
        }
    }

    // 下拉刷新状态
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing.value,
        onRefresh = {
            isRefreshing.value = true
        }
    )

    // 计算页面下移的距离（基于下拉进度）
    // 最大下移距离为 80.dp（RefreshHeaderBox 的高度）
    val offsetY = (pullRefreshState.progress * 80).dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        // 顶部 Loading 区 - 固定在顶部
        RefreshHeaderBox(
            refreshing = isRefreshing.value,
            progress = pullRefreshState.progress,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
        )

        // 列表内容 - 随着下拉而下移
        if (items.value.isEmpty() && !isRefreshing.value) {
            // 空状态
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = offsetY),
                contentAlignment = Alignment.Center
            ) {
                Text("暂无数据")
            }
        } else {
            // 列表 - 直接填满整个页面，并随下拉而下移
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = offsetY),
                state = lazyListState
            ) {
                items(items.value) { item ->
                    PullListItemView(item = item)
                    HorizontalDivider()
                }

                // 加载更多指示器
                if (isLoadingMore.value) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RefreshHeaderBox(
    refreshing: Boolean,
    progress: Float,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(80.dp)
            .background(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            if (refreshing) {
                // 刷新中显示加载动画
                CircularProgressIndicator(
                    modifier = Modifier
                        .height(24.dp)
                        .padding(bottom = 8.dp),
                    strokeWidth = 2.dp
                )
                Text(
                    text = "刷新中...",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            } else if (progress > 0) {
                // 下拉中显示进度
                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .height(24.dp)
                        .padding(bottom = 8.dp),
                    strokeWidth = 2.dp
                )
                Text(
                    text = if (progress >= 1f) "释放刷新" else "下拉刷新",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            } else {
                // 初始状态
                Text(
                    text = "下拉刷新",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun PullListItemView(item: ListItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = item.title,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = item.description,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

// 加载第一页数据
private suspend fun loadFirstPage(
    items: MutableState<List<ListItem>>,
    currentPage: MutableState<Int>
) {
    delay(500) // 模拟网络请求
    val newItems = (1..10).map { index ->
        ListItem(
            id = index,
            title = "列表项 $index",
            description = "这是第 1 页的第 $index 项"
        )
    }
    items.value = newItems
    currentPage.value = 1
}

// 加载更多数据
private suspend fun loadMoreData(
    items: MutableState<List<ListItem>>,
    currentPage: MutableState<Int>,
    isLoadingMore: MutableState<Boolean>
) {
    isLoadingMore.value = true
    delay(1000) // 模拟网络请求

    val nextPage = currentPage.value + 1
    val startIndex = (nextPage - 1) * 10 + 1
    val newItems = (startIndex until startIndex + 10).map { index ->
        ListItem(
            id = index,
            title = "列表项 $index",
            description = "这是第 $nextPage 页的第 ${index - (nextPage - 1) * 10} 项"
        )
    }

    items.value = items.value + newItems
    currentPage.value = nextPage
    isLoadingMore.value = false
}

