package com.hello.android.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hello.android.ui.theme.HelloDelandingTheme
import kotlinx.coroutines.delay

class PullRefreshActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            HelloDelandingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PullRefreshScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PullRefreshScreen(modifier: Modifier = Modifier) {
    var items by remember { mutableStateOf(generateInitialItems(1)) }
    var isRefreshing by remember { mutableStateOf(false) }
    var isLoadingMore by remember { mutableStateOf(false) }
    var pageNumber by remember { mutableIntStateOf(1) }
    val lazyListState = rememberLazyListState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
        }
    )

    // 检查是否滚动到底部
    val isAtBottom by remember {
        derivedStateOf {
            val lastVisibleIndex = lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleIndex >= items.size - 2
        }
    }

    // 处理下拉刷新
    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            delay(2000) // 模拟网络请求
            pageNumber = 1
            items = generateInitialItems(1)
            isRefreshing = false
        }
    }

    // 处理上拉加载更多
    LaunchedEffect(isAtBottom) {
        if (isAtBottom && !isLoadingMore && !isRefreshing && items.isNotEmpty()) {
            isLoadingMore = true
            delay(2000) // 模拟网络请求
            pageNumber++
            items = items + generateInitialItems(pageNumber)
            isLoadingMore = false
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            state = lazyListState
        ) {
            items(items) { item ->
                PullRefreshListItem(item = item)
                HorizontalDivider()
            }

            // 上拉加载更多指示器
            item {
                if (isLoadingMore) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "上拉加载更多",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        // 下拉刷新指示器
        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        )
    }
}

@Composable
fun PullRefreshListItem(item: String) {
    Text(
        text = item,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        style = MaterialTheme.typography.bodyLarge
    )
}

fun generateInitialItems(pageNumber: Int): List<String> {
    val startIndex = (pageNumber - 1) * 10 + 1
    return (startIndex until startIndex + 10).map { "列表项 $it" }
}

