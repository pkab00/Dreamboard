package com.vbshkn.dreamboard.presentation.navigation

import kotlinx.serialization.Serializable

sealed class NavRoute {
    @Serializable object Home : NavRoute()
    @Serializable object Search : NavRoute()
    @Serializable data class ImageDetails(val id: Long) : NavRoute()

    companion object {
        val topLevelRoutes = listOf(
            Home::class,
            Search::class
        )
    }
}