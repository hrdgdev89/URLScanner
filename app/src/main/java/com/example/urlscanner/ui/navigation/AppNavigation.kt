package com.example.urlscanner.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.urlscanner.presentation.ScanViewModel
import com.example.urlscanner.presentation.ScanViewModelFactory
import com.example.urlscanner.ui.screen.AboutScreen
import com.example.urlscanner.ui.screen.ContactScreen
import com.example.urlscanner.ui.screen.MainScreen

object Routes {
    const val MAIN    = "main"
    const val ABOUT   = "about"
    const val CONTACT = "contact"
}

@Composable
fun AppNavigation(
    initialUrl: String = "",
    navController: NavHostController = rememberNavController()
) {
    val viewModel: ScanViewModel = viewModel(factory = ScanViewModelFactory())

    NavHost(navController = navController, startDestination = Routes.MAIN) {
        composable(Routes.MAIN) {
            MainScreen(
                viewModel          = viewModel,
                initialUrl         = initialUrl,
                onNavigateToAbout   = { navController.navigate(Routes.ABOUT) },
                onNavigateToContact = { navController.navigate(Routes.CONTACT) }
            )
        }
        composable(Routes.ABOUT) {
            AboutScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Routes.CONTACT) {
            ContactScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}
