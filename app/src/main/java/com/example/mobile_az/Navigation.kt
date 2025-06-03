package com.example.mobile_az

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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

    NavHost(
        navController = navController,
        startDestination = "menu",
        modifier = modifier
    ) {
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
    }
}
