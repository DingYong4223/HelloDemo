package com.hello.android.memoryleak

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.hello.android.ui.theme.HelloDelandingTheme

class PageLeakActivity : ComponentActivity() {

    // 定义 Handler
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 发送延迟消息
        // 这个 Lambda 表达式会持有 PageLeakActivity 的引用
        // 如果在 20 秒内关闭 Activity，由于 Handler -> Message -> Runnable (Lambda) -> Activity 的引用链
        // Activity 将无法被垃圾回收，直到消息被处理
        handler.postDelayed({
            Log.d("PageLeakActivity", "延迟消息执行了，Activity 依然存活 (或泄漏)")
            // 访问 Activity 的方法证明持有引用
            doSomething()
        }, 20000) // 20秒延迟

        setContent {
            HelloDelandingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "这是页面级内存泄漏演示页面")
                            Text(text = "已发送 20秒 延迟消息")
                            Text(text = "请立即退出页面，Activity 将无法被回收")
                        }
                    }
                }
            }
        }
    }

    private fun doSomething() {
        Log.d("PageLeakActivity", "Activity method called")
    }
}

