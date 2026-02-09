package com.vbshkn.dreamboard.presentation.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

abstract class BaseViewModelTest {
    private val dispatcher = StandardTestDispatcher()

    @Before
    open fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    open fun tearDown() {
        Dispatchers.resetMain()
    }
}