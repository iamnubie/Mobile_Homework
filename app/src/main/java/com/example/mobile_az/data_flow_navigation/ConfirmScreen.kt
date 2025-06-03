package com.example.mobile_az.data_flow_navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController

@Composable
fun ConfirmScreen(vm: ForgotPasswordViewModel, nav: NavHostController) {
    ConstraintLayout(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        val (back, email, code, password, button, logo) = createRefs()
        val guildeLine5 = createGuidelineFromTop(0.05f)

        IconButton(onClick = { nav.popBackStack() }, modifier = Modifier.constrainAs(back) {
            top.linkTo(parent.top, margin = 16.dp)
            start.linkTo(parent.start)
        }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }
        HeaderLogo(
            title = "Confirm",
            subtitle = "We are here to help you!",
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(guildeLine5)
                }
        )

        InputField("Email", vm.state.email, {}, modifier = Modifier.constrainAs(email) {
            top.linkTo(logo.bottom, margin = 12.dp)
            centerHorizontallyTo(parent)
        })

        InputField("Code", vm.state.code, {}, modifier = Modifier.constrainAs(code) {
            top.linkTo(email.bottom, margin = 12.dp)
            centerHorizontallyTo(parent)
        })

        InputField(
            "Password",
            vm.state.password, {},
            isPassword = true,
            modifier = Modifier
                .constrainAs(password) {
                    top.linkTo(code.bottom, margin = 12.dp)
                    centerHorizontallyTo(parent)
                }
        )

        ReusableButton(
            text = "Submit",
            onClick = {
                nav.navigate("enter") {
                    popUpTo("enter") { inclusive = true }
                }
                nav.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set("showSummary", true)
            },
            modifier = Modifier.constrainAs(button) {
                top.linkTo(password.bottom, margin = 24.dp)
                centerHorizontallyTo(parent)
            }
        )
    }
}
