package com.vbshkn.dreamboard.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.vbshkn.dreamboard.R
import com.vbshkn.dreamboard.presentation.navigation.NavBottomBar
import com.vbshkn.dreamboard.presentation.navigation.NavHost
import com.vbshkn.dreamboard.presentation.navigation.NavRoute

@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    val showNavBar = NavRoute.topLevelRoutes.any { routeClass ->
        currentDestination?.hasRoute(routeClass) == true
    }

    Scaffold(
        topBar = {
            TopBar(
                showNavigation = !showNavBar,
                onNavigate = { navController.popBackStack() }
            )
        },
        bottomBar = { if (showNavBar) NavBottomBar(navController) },
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) { paddingValues ->
        Box(Modifier.padding(paddingValues)) {
            NavHost(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    showNavigation: Boolean,
    onNavigate: () -> Unit
) {
    CenterAlignedTopAppBar(
        modifier = Modifier.padding(vertical = 5.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        navigationIcon = {
            if (showNavigation) {
                IconButton(onClick = onNavigate) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = null
                    )
                }
            }
        },
        title = {
            if (!showNavigation) {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    )
}

