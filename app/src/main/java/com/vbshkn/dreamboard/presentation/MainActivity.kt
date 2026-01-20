package com.vbshkn.dreamboard.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.rememberNavController
import com.vbshkn.dreamboard.presentation.navigation.NavHost
import com.vbshkn.dreamboard.presentation.screen.HomeScreen
import com.vbshkn.dreamboard.presentation.theme.DreamboardTheme
import com.vbshkn.dreamboard.presentation.viewmodel.HomeViewModel
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DreamboardTheme {
                MainScreen()
            }
        }
    }
}