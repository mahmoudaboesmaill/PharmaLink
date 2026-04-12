package com.pharma.link.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pharma.link.app.ui.navigation.PharmaNavHost
import com.pharma.link.app.ui.theme.PharmaLinkTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PharmaLinkTheme {
                // بننادي على الخريطة اللي عملناها فوق
                PharmaNavHost()
            }
        }
    }
}