package com.vbshkn.dreamboard.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vbshkn.dreamboard.presentation.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(25.dp),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 10.dp,
                vertical = 5.dp
            )
    ) {
        item {
            Section("Memory") {
                SwitchSectionItem(
                    text = "Enable memory caching",
                    isSwitched = state.memoryCache,
                    onSwitchChanged = viewModel::onSwitchMemoryCaching
                )
                SwitchSectionItem(
                    text = "Enable disc caching",
                    isSwitched = state.discCache,
                    onSwitchChanged = viewModel::onSwitchDickCaching
                )
            }
        }
        item {
            Section("Appearance") {
                SwitchSectionItem(
                    text = "Dark theme",
                    isSwitched = state.darkTheme,
                    onSwitchChanged = viewModel::onSwitchDarkTheme
                )
            }
        }
    }
}

@Composable
private fun Section(
    title: String,
    sectionItems: @Composable () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
        HorizontalDivider(Modifier.padding(vertical = 10.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(7.dp)
        ) { sectionItems() }
    }
}

@Composable
fun SwitchSectionItem(
    text: String,
    isSwitched: Boolean,
    onSwitchChanged: (Boolean) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
        Switch(
            checked = isSwitched,
            onCheckedChange = onSwitchChanged
        )
    }
}