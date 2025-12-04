package com.hello.android.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hello.android.ui.theme.HelloDelandingTheme
import com.hello.shared.SharedDemoScreen

class SharedDemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            HelloDelandingTheme {
                Scaffold { innerPadding ->
                    SharedDemoScreen()
                }
            }
        }
    }
}

