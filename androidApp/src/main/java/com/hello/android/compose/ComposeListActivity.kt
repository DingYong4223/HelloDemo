package com.hello.android.compose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.hello.android.ui.theme.HelloDelandingTheme

class ComposeListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val items = listOf(
            ComposeItem(0, "Compose视图中嵌套View视图"),
            ComposeItem(1, "View视图中嵌套Compose视图"),
            ComposeItem(2, "Compose下拉刷新于上拉加载"),
            ComposeItem(3, "CMP Demo Page"),
            ComposeItem(4, "CMP列表下拉刷新")
        )

        setContent {
            HelloDelandingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val context = LocalContext.current
                    ComposeItemList(
                        items = items,
                        modifier = Modifier.padding(innerPadding),
                        onItemClick = { item ->
                            when (item.id) {
                                0 -> {
                                    val intent = Intent(context, ComposeInViewActivity::class.java)
                                    context.startActivity(intent)
                                }
                                1 -> {
                                    val intent = Intent(context, ViewInComposeActivity::class.java)
                                    context.startActivity(intent)
                                }
                                2 -> {
                                    val intent = Intent(context, PullRefreshActivity::class.java)
                                    context.startActivity(intent)
                                }
                                3 -> {
                                    val intent = Intent(context, SharedDemoActivity::class.java)
                                    context.startActivity(intent)
                                }
                                4 -> {
                                    val intent = Intent(context, CmpListActivity::class.java)
                                    context.startActivity(intent)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

data class ComposeItem(val id: Int, val title: String)

@Composable
fun ComposeItemList(
    items: List<ComposeItem>,
    modifier: Modifier = Modifier,
    onItemClick: (ComposeItem) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(items) { item ->
            ComposeListItemView(item = item, onClick = { onItemClick(item) })
            HorizontalDivider()
        }
    }
}

@Composable
fun ComposeListItemView(
    item: ComposeItem,
    onClick: () -> Unit
) {
    Text(
        text = item.title,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        style = MaterialTheme.typography.bodyLarge
    )
}

