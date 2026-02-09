package com.vbshkn.dreamboard.presentation.viewmodel

import com.google.common.truth.Truth.assertThat
import com.vbshkn.dreamboard.data.local.model.UserSettings
import com.vbshkn.dreamboard.domain.model.ImageEntity
import com.vbshkn.dreamboard.domain.model.ImageListEntity
import com.vbshkn.dreamboard.domain.usecase.GetImageListUseCase
import com.vbshkn.dreamboard.domain.usecase.GetUserSettingsUseCase
import com.vbshkn.dreamboard.presentation.state.SearchScreenUIState
import com.vbshkn.dreamboard.util.Resource
import io.mockk.Called
import io.mockk.InternalPlatformDsl.toArray
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SearchViewModelTest : BaseViewModelTest() {
    private val getImageListUseCase = mockk<GetImageListUseCase>()
    private val getUserSettingsUseCase = mockk<GetUserSettingsUseCase>()
    private lateinit var viewModel: SearchViewModel

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
    fun `view model's ui state can handle api errors`() = runTest {
        val errorMessage = "error"
        val fakeUserSettings = mockk<UserSettings>(relaxed = true)
        val fakeApiErrorResource = Resource.Error<ImageListEntity>(null, errorMessage)
        every { getUserSettingsUseCase() } returns flowOf(fakeUserSettings)
        every {
            getImageListUseCase(any(), any(), any(),
                any(), any()
            ) } returns flowOf(fakeApiErrorResource)

        viewModel = SearchViewModel(getImageListUseCase, getUserSettingsUseCase)
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect()
        }
        viewModel.onSearchTextChange("something") // имитируем ввод
        advanceUntilIdle()

        val uiState = viewModel.state.value
        assertThat(uiState.isLoading).isFalse()
        assertThat(uiState.imageEntities).isEmpty()
        assertThat(uiState.message).isEqualTo(errorMessage)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `view model doesn't start searching if the request is empty`() = runTest {
        val fakeImageList = mockk<ImageListEntity>(relaxed = true)
        val fakeUserSettings = mockk<UserSettings>(relaxed = true)
        every {
            getImageListUseCase(any(), any(), any(),
                                any(), any()
            ) } returns flowOf(Resource.Success(fakeImageList))
        every { getUserSettingsUseCase() } returns flowOf(fakeUserSettings)

        viewModel = SearchViewModel(getImageListUseCase, getUserSettingsUseCase)
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.searchState.collect()
        }
        advanceUntilIdle()

        val searchingText = viewModel.searchState.value.searchingText
        assertThat(searchingText).isEmpty()
        verify { getImageListUseCase wasNot Called }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `setting the request to empty string cleans the images gata`() = runTest {
        val uiStatesHistory = mutableListOf<SearchScreenUIState>()
        val fakeImagesNumber = 10
        val fakeSettings = mockk<UserSettings>(relaxed = true)
        val fakeImagesResource = Resource.Success(
            ImageListEntity(
                page = 1,
                lastPage = 2,
                perPage = 10,
                totalImages = fakeImagesNumber.toLong(),
                imageEntities = List(fakeImagesNumber) { mockk<ImageEntity>(relaxed = true) }
            )
        )
        every { getUserSettingsUseCase() } returns flowOf(fakeSettings)
        every {
            getImageListUseCase(any(), any(), any(),
                any(), any()
            ) } returns flowOf(fakeImagesResource)

        viewModel = SearchViewModel(getImageListUseCase, getUserSettingsUseCase)
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.searchState.collect()
        }
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state
                .distinctUntilChanged { old, new -> old.imageEntities == new.imageEntities }
                .collect { state -> uiStatesHistory.add(state) }
        }
        viewModel.onSearchTextChange("something")
        advanceUntilIdle()
        viewModel.onSearchTextChange("")
        advanceUntilIdle()

        assertThat(uiStatesHistory).isNotEmpty()
        assertThat(uiStatesHistory).hasSize(3)
        assertThat(uiStatesHistory[0].imageEntities).isEmpty()
        assertThat(uiStatesHistory[1].imageEntities).hasSize(fakeImagesNumber)
        assertThat(uiStatesHistory[2].imageEntities).isEmpty()
    }
}