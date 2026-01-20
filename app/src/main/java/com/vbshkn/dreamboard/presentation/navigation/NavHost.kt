package com.vbshkn.dreamboard.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vbshkn.dreamboard.presentation.screen.HomeScreen
import com.vbshkn.dreamboard.presentation.viewmodel.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.Home
    ) {
        composable<NavRoute.Home> {
            val viewModel = koinViewModel<HomeViewModel>()
            HomeScreen(viewModel)
        }
        composable<NavRoute.Search> {
            // TODO
        }
    }
}