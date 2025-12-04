package com.hello.android.kotlin

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.hello.android.SimpleItem
import com.hello.android.SimpleList
import com.hello.android.ui.theme.HelloDelandingTheme

class KotlinExperimentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val items = listOf(
            SimpleItem(0, "flow测试")
        )

        setContent {
            HelloDelandingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val context = LocalContext.current
                    SimpleList(
                        items = items,
                        modifier = Modifier.padding(innerPadding),
                        onItemClick = { item ->
                            if (item.title == "flow测试") {
                                val intent = Intent(context, FlowTestActivity::class.java)
                                context.startActivity(intent)
                            }
                        }
                    )
                }
            }
        }
    }
}

