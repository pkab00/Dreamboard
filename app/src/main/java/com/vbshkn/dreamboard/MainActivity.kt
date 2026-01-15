package com.vbshkn.dreamboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vbshkn.dreamboard.data.remote.RemoteImage
import com.vbshkn.dreamboard.data.remote.VecteezyApi
import com.vbshkn.dreamboard.presentation.TestScreen
import com.vbshkn.dreamboard.presentation.theme.DreamboardTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import kotlin.getValue

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val api by inject<VecteezyApi>()

        enableEdgeToEdge()
        setContent {
            DreamboardTheme {
                var response by remember { mutableStateOf<RemoteImage?>(null) }
                TestScreen(response)

                LaunchedEffect(true) {
                    response = api.getImage(21848820)
                }
            }
        }
    }
}