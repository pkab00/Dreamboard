package com.vbshkn.dreamboard.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vbshkn.dreamboard.presentation.theme.DreamboardTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel by viewModels<MainViewModel>()
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            viewModel.isDarkThemeEnabled.value == null
        }

        enableEdgeToEdge()
        setContent {
            val darkTheme by viewModel.isDarkThemeEnabled.collectAsStateWithLifecycle()
            if (darkTheme != null) {
                DreamboardTheme(darkTheme!!) {
                    MainScreen()
                }
            }
        }
    }
}