package com.hello.android.memoryleak

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hello.android.ui.theme.HelloDelandingTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

// 单例对象，持有数据导致内存泄漏（如果只增不减）
object LeakingStore {
    val data = mutableListOf<ByteArray>()
}

class NonPageLeakActivity : ComponentActivity() {
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var count by remember { mutableIntStateOf(LeakingStore.data.size) }

            HelloDelandingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "非页面级内存泄漏演示")
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "静态列表对象数量: $count")
                            Text(text = "预估占用内存: ${count}MB")
                            Spacer(modifier = Modifier.height(16.dp))

                            Button(onClick = {
                                startLeaking { newCount ->
                                    count = newCount
                                }
                            }) {
                                Text("开始增加内存 (每0.5秒 +1MB)")
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(onClick = { stopLeaking() }) {
                                Text("停止增加")
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(onClick = {
                                LeakingStore.data.clear()
                                count = 0
                                System.gc()
                            }) {
                                Text("手动清理内存")
                            }
                        }
                    }
                }
            }
        }
    }

    private fun startLeaking(onUpdate: (Int) -> Unit) {
        if (job?.isActive == true) return
        job = CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                delay(500)
                // 每次增加 1MB
                LeakingStore.data.add(ByteArray(1024 * 1024))
                onUpdate(LeakingStore.data.size)
            }
        }
    }

    private fun stopLeaking() {
        job?.cancel()
        job = null
    }

    override fun onDestroy() {
        super.onDestroy()
        stopLeaking()
        // 注意：这里没有清空 LeakingStore.data，所以内存会一直泄漏，即使退出 Activity
    }
}

