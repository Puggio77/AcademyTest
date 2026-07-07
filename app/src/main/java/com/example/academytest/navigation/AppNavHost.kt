package com.example.academytest.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.academytest.ViewModels.ItemsListViewModel
import com.example.academytest.views.ItemDetailView
import com.example.academytest.views.ItemsListView
import java.util.UUID

@Composable
fun AppNavHost(
    viewModel: ItemsListViewModel = viewModel()
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            ItemsListView(
                viewModel = viewModel,
                onItemClick = { id -> navController.navigate("detail/$id") }
            )
        }

        composable(
            route = "detail/{itemId}",
            arguments = listOf(navArgument("itemId") { type = NavType.StringType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments
                ?.getString("itemId")
                ?.let { UUID.fromString(it) }

            val items by viewModel.items.collectAsStateWithLifecycle()
            val item = items.firstOrNull { it.id == itemId }

            if (item != null) {
                ItemDetailView(
                    item = item,
                    onToggleFavorite = { viewModel.toggleFavorite(item.id) },
                    onDelete = {
                        viewModel.deleteItem(item.id)
                        navController.popBackStack()
                    },
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}