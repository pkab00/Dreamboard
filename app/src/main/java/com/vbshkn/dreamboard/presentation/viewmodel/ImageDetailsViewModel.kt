package com.vbshkn.dreamboard.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.vbshkn.dreamboard.presentation.navigation.NavRoute

class ImageDetailsViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val args = savedStateHandle.toRoute<NavRoute.ImageDetails>()
    val id = args.id
}