package com.hello.android.kotlin

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hello.android.ui.theme.HelloDelandingTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class FlowTestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HelloDelandingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FlowTestScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// 定义网络请求的状态
sealed class NetworkResult {
    object Idle : NetworkResult()
    object Loading : NetworkResult()
    data class Success(val data: String) : NetworkResult()
    data class Error(val error: String) : NetworkResult()
}

@Composable
fun FlowTestScreen(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()

    // 基础 Flow 状态
    var flowValue by remember { mutableStateOf("等待开始...") }
    var isRunning by remember { mutableStateOf(false) }

    // 网络请求 Flow 状态
    var networkState by remember { mutableStateOf<NetworkResult>(NetworkResult.Idle) }

    // 冷流 vs 热流 状态
    var coldLog by remember { mutableStateOf("") }
    var hotLog by remember { mutableStateOf("") }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            // --- 基础 Flow 测试 ---
            Text(text = "基础 Flow 测试")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "当前值: $flowValue")
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (!isRunning) {
                        isRunning = true
                        scope.launch {
                            simpleFlow().collect { value ->
                                flowValue = "接收到: $value"
                            }
                            flowValue = "Flow 完成"
                            isRunning = false
                        }
                    }
                },
                enabled = !isRunning
            ) {
                Text(text = if (isRunning) "运行中..." else "开始倒计时 Flow")
            }

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(24.dp))

            // --- 网络请求 Flow 测试 ---
            Text(text = "网络请求 Flow 模拟")
            Spacer(modifier = Modifier.height(8.dp))

            // 根据状态显示不同 UI
            when (val state = networkState) {
                is NetworkResult.Idle -> Text("点击下方按钮开始请求")
                is NetworkResult.Loading -> CircularProgressIndicator()
                is NetworkResult.Success -> Text("请求成功: ${state.data}", color = Color.Green)
                is NetworkResult.Error -> Text("请求失败: ${state.error}", color = Color.Red)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    scope.launch {
                        // 模拟网络请求 Flow
                        simulateNetworkRequest()
                            .onStart {
                                // 开始时发射 Loading 状态
                                networkState = NetworkResult.Loading
                            }
                            .catch { e ->
                                // 捕获异常并发射 Error 状态
                                networkState = NetworkResult.Error(e.message ?: "未知错误")
                            }
                            .collect { result ->
                                // 收集最终结果
                                networkState = NetworkResult.Success(result)
                            }
                    }
                },
                // 如果正在加载，禁用按钮
                enabled = networkState !is NetworkResult.Loading
            ) {
                Text("发起模拟请求")
            }

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(24.dp))

            // --- 冷流 vs 热流 测试 ---
            Text(text = "冷流 vs 热流 (请查看 Logcat)")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "冷流日志:\n$coldLog", modifier = Modifier.padding(8.dp))
            Text(text = "热流日志:\n$hotLog", modifier = Modifier.padding(8.dp))

            Button(onClick = {
                coldLog = "开始测试...\n"
                hotLog = "开始测试...\n"

                // 1. 冷流测试
                val coldFlow = flow {
                    emit("Cold: ${System.currentTimeMillis()}")
                    delay(100)
                }

                scope.launch {
                    coldFlow.collect { coldLog += "观察者1: $it\n" }
                    delay(500)
                    coldFlow.collect { coldLog += "观察者2: $it\n" }
                }

                // 2. 热流测试 (冷流转热流)
                val hotFlow = flow {
                    emit("Hot: ${System.currentTimeMillis()}")
                    delay(100)
                }.shareIn(
                    scope,
                    started = SharingStarted.Lazily,
                    replay = 1
                )

                scope.launch {
                    hotFlow.collect { hotLog += "观察者1: $it\n" }
                }
                scope.launch {
                    delay(500)
                    hotFlow.collect { hotLog += "观察者2: $it\n" }
                }
            }) {
                Text("对比冷流与热流")
            }

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

fun simpleFlow(): Flow<Int> = flow {
    for (i in 1..5) {
        delay(1000) // 模拟耗时操作
        emit(i) // 发射数据
    }
}

// 模拟网络请求的 Flow
fun simulateNetworkRequest(): Flow<String> = flow {
    // 模拟网络延迟
    delay(2000)

    // 模拟随机成功或失败
    if (System.currentTimeMillis() % 2 == 0L) {
        emit("用户数据 JSON (ID: ${System.currentTimeMillis() % 1000})")
    } else {
        throw RuntimeException("500 Internal Server Error")
    }
}

