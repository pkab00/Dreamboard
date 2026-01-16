package com.vbshkn.dreamboard

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.vbshkn.dreamboard.presentation.screen.TestScreen
import com.vbshkn.dreamboard.presentation.theme.DreamboardTheme
import com.vbshkn.dreamboard.presentation.viewmodel.TestViewModel
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DreamboardTheme {
                val viewModel by inject<TestViewModel>()
                TestScreen(viewModel)
            }
        }
    }
}