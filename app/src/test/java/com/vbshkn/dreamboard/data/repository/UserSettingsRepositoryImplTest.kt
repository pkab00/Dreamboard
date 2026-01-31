package com.vbshkn.dreamboard.data.repository

import com.vbshkn.dreamboard.data.local.model.UserSettings
import com.vbshkn.dreamboard.domain.datasource.UserSettingsSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class UserSettingsRepositoryImplTest() {
    private val dataSource = mockk<UserSettingsSource>()
    private val repository = UserSettingsRepositoryImpl(dataSource)

    @Test
    fun `getSettings should emit mapped data from data source`() = runTest {
        val expected  = UserSettings.getDefault()
        every { dataSource.getSettings() } returns flowOf(expected)

        val actual = repository.getSettings().first()

        assert(actual == expected)
        verify(exactly = 1) { dataSource.getSettings() }
    }

    @Test
    fun `updateSettings should update data in the data source`() = runTest {
        val defaultSettings = UserSettings.getDefault()
        val expected = defaultSettings.copy(discCache = true, memoryCache = true)
        every { dataSource.getSettings() } returns flowOf(defaultSettings)
        coEvery { dataSource.updateSettings(any()) } returns Unit

        repository.updateSettings { settings ->
            settings.copy(memoryCache = true, discCache = true)
        }

        coVerify(exactly = 1) { dataSource.updateSettings(expected) }
    }
}