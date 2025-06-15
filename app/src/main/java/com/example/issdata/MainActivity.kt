package com.example.issdata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.issdata.ISSLightstreamerClient.floatValue
import com.example.issdata.ui.theme.ISSDataTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ISSDataTheme {
                ISSLightstreamerClient.connect()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PissPercentage(floatValue)
                }
            }
        }
    }

}


