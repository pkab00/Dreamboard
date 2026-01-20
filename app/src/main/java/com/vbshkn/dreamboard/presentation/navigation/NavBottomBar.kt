package com.vbshkn.dreamboard.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ImageSearch
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController

@Composable
fun NavBottomBar(navController: NavHostController) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val barItems = listOf(
        BarItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = NavRoute.Home
        ),
        BarItem(
            title = "Search",
            selectedIcon = Icons.Filled.ImageSearch,
            unselectedIcon = Icons.Outlined.ImageSearch,
            route = NavRoute.Search
        )
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
       barItems.forEachIndexed { index, item ->
           val selected = selectedIndex == index
           NavigationBarItem(
               onClick = {
                   selectedIndex = index
                   navController.navigate(item.route)
               },
               icon = {
                   Icon(
                       imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                       contentDescription = null
                   )
               },
               label = {
                   Text(
                       text = item.title,
                       style = MaterialTheme.typography.labelSmall,
                       color = MaterialTheme.colorScheme.onPrimary,
                       textAlign = TextAlign.Center
                   )
               },
               colors = NavigationBarItemDefaults.colors(
                   indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                   unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                   unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                   selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                   selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer
               ),
               selected = selected,
               alwaysShowLabel = selected
           )
       }
    }
}