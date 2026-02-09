package com.vbshkn.dreamboard.presentation.viewmodel

import coil3.request.CachePolicy
import com.google.common.truth.Truth.assertThat
import com.vbshkn.dreamboard.data.local.model.UserSettings
import com.vbshkn.dreamboard.domain.model.ImageListEntity
import com.vbshkn.dreamboard.domain.usecase.GetImageListUseCase
import com.vbshkn.dreamboard.domain.usecase.GetUserSettingsUseCase
import com.vbshkn.dreamboard.util.Resource
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class HomeViewModelTest : BaseViewModelTest() {
    private val getImageListUseCase = mockk<GetImageListUseCase>()
    private val getUserSettingsUseCase = mockk<GetUserSettingsUseCase>()
    private lateinit var viewModel: HomeViewModel

    @Before
    override fun setUp() {
        super.setUp()
    }

    @After
    override fun tearDown() {
        super.tearDown()
        clearAllMocks()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `view model is mapping cache policies correctly`() = runTest {
        val fakeUserSettings = UserSettings(
            memoryCache = true,
            discCache = false,
            darkTheme = true,
            disableAi = true
        )
        val fakeImageList = mockk<ImageListEntity>(relaxed = true)
        val expectedMemoryPolicy = CachePolicy.ENABLED
        val expectedDiscPolicy = CachePolicy.DISABLED
        every {
            getImageListUseCase(any(), any(), any(),
                                any(), any())
        } returns flowOf(Resource.Success(fakeImageList))
        every { getUserSettingsUseCase() } returns flowOf(fakeUserSettings)

        viewModel = HomeViewModel(getImageListUseCase, getUserSettingsUseCase)
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.memoryCachePolicy.collect()
            viewModel.discCachePolicy.collect()
        }
        advanceUntilIdle()
        val actualMemoryPolicy = viewModel.memoryCachePolicy.value
        val actualDiscPolicy = viewModel.discCachePolicy.value

        assertThat(actualMemoryPolicy).isEqualTo(expectedMemoryPolicy)
        assertThat(actualDiscPolicy).isEqualTo(expectedDiscPolicy)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `view model handles an error when unable to load images`() = runTest {
        val fakeErrorResource = Resource.Error<ImageListEntity>(null, "error")
        val fakeSettings = mockk<UserSettings>(relaxed = true)
        every {
            getImageListUseCase(any(), any(), any(),
                                any(), any())
        } returns flowOf(fakeErrorResource)
        every { getUserSettingsUseCase() } returns flowOf(fakeSettings)

        viewModel = HomeViewModel(getImageListUseCase, getUserSettingsUseCase)
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.imageMap.collect()
        }
        advanceUntilIdle()

        viewModel.imageMap.value.values.forEach { entities ->
            assertThat(entities).isEmpty()
        }
    }
}