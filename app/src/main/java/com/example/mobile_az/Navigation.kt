package com.example.mobile_az

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mobile_az.navigate_oop.OnBoardingScreen
import com.example.mobile_az.navigate_oop.SplashScreen
import com.example.mobile_az.tuan04.BasicUIScreen
import com.example.mobile_az.tuan04.ColumnLayoutScreen
import com.example.mobile_az.tuan04.ImageDetailScreen
import com.example.mobile_az.tuan04.MenuNavigate
import com.example.mobile_az.tuan04.PasswordFieldScreen
import com.example.mobile_az.tuan04.RowLayoutScreen
import com.example.mobile_az.tuan04.TextDetailScreen
import com.example.mobile_az.tuan04.TextFieldScreen
import com.example.mobile_az.tuan04.UIListScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val currentRoute = currentRoute(navController)
    val showBottomBar = currentRoute in listOf("manage", "books", "students")
    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                MainBottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
    NavHost(
        navController = navController,
        startDestination = "manage",
        modifier = Modifier.padding(innerPadding)
    ) {
        composable("splash") {
            SplashScreen(navController)
        }

        composable(
            "onboarding/{page}",
            arguments = listOf(navArgument("page") { type = NavType.IntType })
        ) { backStackEntry ->
            val page = backStackEntry.arguments?.getInt("page") ?: 0
            OnBoardingScreen(navController, page)
        }
        composable("menu") {
            MenuNavigate(navController = navController)
        }
        composable("uiList") {
            UIListScreen(navController = navController)
        }
        composable("textDetail") {
            TextDetailScreen(navController = navController)
        }
        composable("imageDetail") {
            ImageDetailScreen(navController = navController)
        }
        composable("rowLayout") {
            RowLayoutScreen(navController)
        }
        composable("columnLayout") {
            ColumnLayoutScreen(navController)
        }
        composable("textfield") {
            TextFieldScreen(navController)
        }
        composable("passwordfield") {
            PasswordFieldScreen(navController)
        }
        composable("exploreui") {
            BasicUIScreen(navController)
        }
        composable("manage") {
            ManageScreen()
        }
        composable("books") {
            BookListScreen()
        }
        composable("students") {
            StudentListScreen()
        }

    }
        }
}
fun navigateTo(
    navController: NavHostController,
    route: String,
    cleanStack: Boolean = false
) {
    navController.navigate(route) {
        if (cleanStack) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}
