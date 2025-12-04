package com.hello.android.compose

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.hello.android.ui.theme.HelloDelandingTheme

class ComposeInViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HelloDelandingTheme {
                ComposeInViewScreen()
            }
        }
    }
}

@Composable
fun ComposeInViewScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // 使用 AndroidView 在 Compose 中嵌套 View
        AndroidView(
            factory = { context ->
                LinearLayout(context).apply {
                    orientation = LinearLayout.VERTICAL

                    // 添加标题
                    val titleView = TextView(context).apply {
                        text = "Compose 视图中嵌套 View 视图"
                        textSize = 20f
                        setTextColor(android.graphics.Color.BLACK)
                    }
                    addView(titleView)

                    // 添加分隔线
                    val divider = View(context).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            2
                        ).apply {
                            topMargin = 16
                            bottomMargin = 16
                        }
                        setBackgroundColor(android.graphics.Color.GRAY)
                    }
                    addView(divider)

                    // 添加内容
                    val contentView = TextView(context).apply {
                        text = "这是在 Compose 中通过 AndroidView 嵌套的原生 Android View。\n\n" +
                               "这种方式适合在 Compose 中使用一些还没有 Compose 版本的 Android 组件。"
                        textSize = 16f
                        setTextColor(android.graphics.Color.DKGRAY)
                        setPadding(0, 16, 0, 16)
                    }
                    addView(contentView)
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}