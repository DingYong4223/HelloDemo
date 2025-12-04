package com.hello.android.memoryleak

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

class MemoryLeakActivity : ComponentActivity() {

    companion object {
        const val TAG = "MemoryLeakActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val items = listOf(
            SimpleItem(0, "页面级内存泄漏"),
            SimpleItem(1, "非页面级内存泄漏"),
        )

        setContent {
            HelloDelandingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val context = LocalContext.current
                    SimpleList(
                        items = items,
                        modifier = Modifier.padding(innerPadding),
                        onItemClick = { item ->
                            Log.i(TAG, "click: ${item.id}")
                            if (item.id == 0) {
                                val intent = Intent(context, PageLeakActivity::class.java)
                                context.startActivity(intent)
                            } else if (item.id == 1) {
                                val intent = Intent(context, NonPageLeakActivity::class.java)
                                context.startActivity(intent)
                            }
                        }
                    )
                }
            }
        }
    }
}

