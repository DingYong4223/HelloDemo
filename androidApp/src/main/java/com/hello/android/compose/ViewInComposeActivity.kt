package com.hello.android.compose

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.hello.android.ui.theme.HelloDelandingTheme

class ViewInComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HelloDelandingTheme {
                ViewInComposeScreen()
            }
        }
    }
}

@Composable
fun ViewInComposeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Compose 标题
        Text(
            text = "View 视图中嵌套 Compose 视图",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // 分隔线
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
                .padding(vertical = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Gray)
                    .padding(vertical = 1.dp)
            )
        }

        // 在 Compose 中嵌套 View
        AndroidView(
            factory = { context ->
                LinearLayout(context).apply {
                    orientation = LinearLayout.VERTICAL

                    val viewTitle = TextView(context).apply {
                        text = "这是原生 Android View"
                        textSize = 18f
                        setTextColor(android.graphics.Color.BLACK)
                        setPadding(0, 16, 0, 8)
                    }
                    addView(viewTitle)

                    val viewContent = TextView(context).apply {
                        text = "这是在 Compose 中嵌套的原生 Android View。\n\n" +
                               "通过 AndroidView Composable，我们可以在 Compose 中使用传统的 Android View。\n\n" +
                               "这种混合方式提供了很好的灵活性，让我们可以逐步迁移到 Compose。"
                        textSize = 14f
                        setTextColor(android.graphics.Color.DKGRAY)
                        setPadding(0, 8, 0, 16)
                    }
                    addView(viewContent)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        // 下方的 Compose 内容
        Text(
            text = "下方是 Compose 组件",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 8.dp)
        )

        Text(
            text = "这展示了 View 和 Compose 的完美结合。",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

