package com.vbshkn.dreamboard.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vbshkn.dreamboard.presentation.screen.HomeScreen
import com.vbshkn.dreamboard.presentation.screen.ImageDetailsScreen
import com.vbshkn.dreamboard.presentation.screen.SearchScreen
import com.vbshkn.dreamboard.presentation.viewmodel.HomeViewModel
import com.vbshkn.dreamboard.presentation.viewmodel.ImageDetailsViewModel
import com.vbshkn.dreamboard.presentation.viewmodel.SearchViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.Home
    ) {
        composable<NavRoute.Home> {
            HomeScreen(viewModel = koinViewModel<HomeViewModel>())
        }
        composable<NavRoute.Search> {
            SearchScreen(
                viewModel = koinViewModel<SearchViewModel>(),
                onImageClick = { navController.navigate(NavRoute.ImageDetails(it)) }
            )
        }
        composable<NavRoute.ImageDetails> {
            ImageDetailsScreen(viewModel = koinViewModel<ImageDetailsViewModel>())
        }
    }
}