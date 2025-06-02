package com.createfuture.takehome.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.createfuture.takehome.ui.commonui.AppNavigationBar
import com.createfuture.takehome.ui.theme.TakeHomeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TakeHomeTheme {
                AppNavigationBar()
            }
        }
    }
}
