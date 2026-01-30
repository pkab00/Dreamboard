package com.vbshkn.dreamboard.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vbshkn.dreamboard.presentation.screen.HomeScreen
import com.vbshkn.dreamboard.presentation.screen.ImageDetailsScreen
import com.vbshkn.dreamboard.presentation.screen.SearchScreen
import com.vbshkn.dreamboard.presentation.screen.SettingsScreen
import com.vbshkn.dreamboard.presentation.viewmodel.HomeViewModel

@Composable
fun NavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.Home
    ) {
        composable<NavRoute.Home> {
            HomeScreen(hiltViewModel())
        }
        composable<NavRoute.Search> {
            SearchScreen(
                viewModel = hiltViewModel(),
                onImageClick = { navController.navigate(NavRoute.ImageDetails(it)) }
            )
        }
        composable<NavRoute.Settings> {
            SettingsScreen(viewModel = hiltViewModel())
        }
        composable<NavRoute.ImageDetails> {
            ImageDetailsScreen(viewModel = hiltViewModel())
        }
    }
}