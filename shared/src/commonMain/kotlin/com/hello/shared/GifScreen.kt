package com.hello.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun GifScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 标题
        Text(
            text = "GIF 图片展示 (Coil 3.3.0)",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = MaterialTheme.colorScheme.primary
        )

        // 分隔线
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(Color.LightGray)
                .padding(vertical = 0.5.dp)
        )

        // GIF 图片容器 - 本地资源
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.medium
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "本地 GIF 图片",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // 使用 Coil 3.3.0 加载本地 GIF 文件
                AsyncImage(
                    model = "file:///android_asset/drawable/keeta_design_loading_ip.gif",
                    contentDescription = "Loading GIF",
                    modifier = Modifier.size(200.dp),
                    contentScale = ContentScale.Crop
                )

                // 图片描述
                Text(
                    text = "Keeta Design Loading GIF",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }

        // GIF 图片容器 - 网络资源
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.medium
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "网络 GIF 图片",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // 使用 Coil 3.3.0 加载网络 GIF 文件
                AsyncImage(
                    model = "https://media.giphy.com/media/3o7TKU2VjWzEKTmYGQ/giphy.gif",
                    contentDescription = "Network GIF",
                    modifier = Modifier.size(200.dp),
                    contentScale = ContentScale.Crop
                )

                // 图片描述
                Text(
                    text = "来自网络的 GIF 图片",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }

        // 说明文本
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = MaterialTheme.shapes.medium
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "关于 Coil 3.3.0",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Coil 3.3.0 是最新的图片加载库，专为 Compose 设计。\n\n" +
                            "主要特性：\n" +
                            "• 轻量级，专为 Compose 设计\n" +
                            "• 支持 GIF、WebP、HEIF 等多种格式\n" +
                            "• 自动缓存管理和内存优化\n" +
                            "• 高性能的图片加载和解码\n" +
                            "• 支持本地和网络图片\n" +
                            "• 完整的错误处理和重试机制\n" +
                            "• 跨平台支持（Android、iOS、Desktop）",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        // 使用 Coil 的示例代码
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(
                    color = Color(0xFFF5F5F5),
                    shape = MaterialTheme.shapes.medium
                )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "加载本地 GIF 的代码示例：",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "AsyncImage(\n" +
                            "    model = \"file:///android_asset/drawable/keeta_design_loading_ip.gif\",\n" +
                            "    contentDescription = \"Loading GIF\",\n" +
                            "    modifier = Modifier.size(200.dp),\n" +
                            "    contentScale = ContentScale.Crop\n" +
                            ")",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(8.dp)
                )

                Text(
                    text = "\n加载网络 GIF 的代码示例：",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )

                Text(
                    text = "AsyncImage(\n" +
                            "    model = \"https://media.giphy.com/media/3o7TKU2VjWzEKTmYGQ/giphy.gif\",\n" +
                            "    contentDescription = \"Network GIF\",\n" +
                            "    modifier = Modifier.size(200.dp),\n" +
                            "    contentScale = ContentScale.Crop\n" +
                            ")",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(8.dp)
                )

                Text(
                    text = "\n带加载状态的代码示例：",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )

                Text(
                    text = "AsyncImage(\n" +
                            "    model = \"https://example.com/image.gif\",\n" +
                            "    contentDescription = \"GIF\",\n" +
                            "    modifier = Modifier.size(200.dp),\n" +
                            "    loading = {\n" +
                            "        CircularProgressIndicator()\n" +
                            "    },\n" +
                            "    contentScale = ContentScale.Crop\n" +
                            ")",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        // 官方链接
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = MaterialTheme.shapes.medium
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "官方资源",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "GitHub: https://github.com/coil-kt/coil\n" +
                            "文档: https://coil-kt.github.io/coil/\n" +
                            "版本: 3.3.0",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

