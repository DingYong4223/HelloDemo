package com.hello.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

data class ListItem(val id: Int, val title: String, val description: String)

@Composable
fun CmpListScreen() {
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

    Box(modifier = Modifier.fillMaxSize()) {
        if (items.value.isEmpty() && !isRefreshing.value) {
            // 空状态
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("暂无数据")
            }
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                // 列表
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    state = lazyListState
                ) {
                    items(items.value) { item ->
                        ListItemView(item = item)
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

                // 刷新指示器
                if (isRefreshing.value) {
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

        // 下拉刷新按钮（简化实现）
        if (!isRefreshing.value && items.value.isNotEmpty()) {
            Text(
                text = "下拉刷新",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
                    .background(Color.LightGray)
                    .padding(8.dp),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Composable
fun ListItemView(item: ListItem) {
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
    items: androidx.compose.runtime.MutableState<List<ListItem>>,
    currentPage: androidx.compose.runtime.MutableState<Int>
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
    items: androidx.compose.runtime.MutableState<List<ListItem>>,
    currentPage: androidx.compose.runtime.MutableState<Int>,
    isLoadingMore: androidx.compose.runtime.MutableState<Boolean>
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

