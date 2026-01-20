package com.vbshkn.dreamboard.presentation.navigation

import kotlinx.serialization.Serializable

sealed class NavRoute {
    @Serializable object Home : NavRoute()
    @Serializable object Search : NavRoute()
}