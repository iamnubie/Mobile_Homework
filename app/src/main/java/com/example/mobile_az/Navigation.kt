package com.example.mobile_az

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mobile_az.data_flow_navigation.ConfirmScreen
import com.example.mobile_az.data_flow_navigation.EnterEmailScreen
import com.example.mobile_az.data_flow_navigation.ForgotPasswordViewModel
import com.example.mobile_az.data_flow_navigation.GGLoginScreen
import com.example.mobile_az.data_flow_navigation.ResetPasswordScreen
import com.example.mobile_az.data_flow_navigation.VerifyCodeScreen
import com.example.mobile_az.fetch_api.ProductDetailScreen
import com.example.mobile_az.fetch_api.UserProfileScreen
import com.example.mobile_az.library_management.BookListScreen
import com.example.mobile_az.library_management.MainBottomBar
import com.example.mobile_az.library_management.ManageScreen
import com.example.mobile_az.library_management.StudentListScreen
import com.example.mobile_az.navigate_oop.OnBoardingScreen
import com.example.mobile_az.navigate_oop.SplashScreen
import com.example.mobile_az.tuan03.BasicUIScreen
import com.example.mobile_az.tuan03.ColumnLayoutScreen
import com.example.mobile_az.tuan03.ImageDetailScreen
import com.example.mobile_az.tuan03.MenuNavigate
import com.example.mobile_az.tuan03.PasswordFieldScreen
import com.example.mobile_az.tuan03.RowLayoutScreen
import com.example.mobile_az.tuan03.TextDetailScreen
import com.example.mobile_az.tuan03.TextFieldScreen
import com.example.mobile_az.tuan03.UIListScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val currentRoute = currentRoute(navController)
    val showBottomBar = currentRoute in listOf("manage", "books", "students")
    val vm: ForgotPasswordViewModel = viewModel()

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                MainBottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        val sharedViewModel: TaskViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = "tasklist", //splash,manage,enter, google
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
        composable("enter") { EnterEmailScreen(navController, vm) }
        composable("verify") { VerifyCodeScreen(navController, vm) }
        composable("reset") { ResetPasswordScreen(navController, vm) }
        composable("confirm") { ConfirmScreen(vm, navController) }
        composable("google") { GGLoginScreen(navController) }
        composable("user_profile") {
            UserProfileScreen(navController)
        }
        composable("request_data") {
            ProductDetailScreen(navController)
        }
        composable("tasklist") { TaskListScreen(navController) }
        composable(
            "task_detail/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId")
            if (taskId != null) {
                TaskDetailScreen(navController, taskId)
            }
        }
        composable("add_task_list") {
            AddTaskListScreen(navController, sharedViewModel)
        }
        composable("add_new_task") {
            AddNewTaskScreen(navController, sharedViewModel)
        }
    }
        }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
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
