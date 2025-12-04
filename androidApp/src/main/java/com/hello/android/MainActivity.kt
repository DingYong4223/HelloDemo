package com.hello.android

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hello.android.compose.ComposeListActivity
import com.hello.android.kotlin.KotlinExperimentActivity
import com.hello.android.memoryleak.MemoryLeakActivity
import com.hello.android.ui.theme.HelloDelandingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 自定义每一项的文案
        val items = listOf(
            SimpleItem(0, "内存泄漏"),
            SimpleItem(1, "KOTLIN实验"),
            SimpleItem(2, "Compose实现"),
        )

        setContent {
            HelloDelandingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val context = LocalContext.current
                    SimpleList(
                        items = items,
                        modifier = Modifier.padding(innerPadding),
                        onItemClick = { item ->
                            when (item.id) {
                                0 -> {
                                    val intent = Intent(context, MemoryLeakActivity::class.java)
                                    context.startActivity(intent)
                                }
                                1 -> {
                                    val intent = Intent(context, KotlinExperimentActivity::class.java)
                                    context.startActivity(intent)
                                }
                                2 -> {
                                    val intent = Intent(context, ComposeListActivity::class.java)
                                    context.startActivity(intent)
                                }
                                else -> {
                                    // 点击时的自定义逻辑，这里演示弹出一个Toast
                                    Toast.makeText(context, "点击了: ${item.title}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

data class SimpleItem(val id: Int, val title: String)

@Composable
fun SimpleList(
    items: List<SimpleItem>,
    modifier: Modifier = Modifier,
    onItemClick: (SimpleItem) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(items) { item ->
            SimpleListItem(item = item, onClick = { onItemClick(item) })
            HorizontalDivider()
        }
    }
}

@Composable
fun SimpleListItem(
    item: SimpleItem,
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

@Preview(showBackground = true)
@Composable
fun ListPreview() {
    HelloDelandingTheme {
        SimpleList(
            items = List(5) { SimpleItem(it, "预览项 $it") },
            onItemClick = {}
        )
    }
}