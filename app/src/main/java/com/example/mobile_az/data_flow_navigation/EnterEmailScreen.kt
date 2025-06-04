package com.example.mobile_az.data_flow_navigation

import android.util.Patterns
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController

@Composable
fun EnterEmailScreen(nav: NavHostController, vm: ForgotPasswordViewModel) {
    val showSummary = remember { mutableStateOf(false) }
    val emailError = remember { mutableStateOf<String?>(null) }

    ConstraintLayout(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        val (logo, input, button, summary, errorText) = createRefs()
        val guildeLine5 = createGuidelineFromTop(0.05f)

        HeaderLogo(
            title = "Forget Password?",
            subtitle = "Enter your Email, we will send you a verification code.",
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(guildeLine5)
                }
        )

        InputField("Your Email", vm.state.email, { vm.state = vm.state.copy(email = it) },
            modifier = Modifier.constrainAs(input) {
                top.linkTo(logo.bottom, margin = 16.dp)
                centerHorizontallyTo(parent)
            }
        )

        ReusableButton(
            text = "Next",
            onClick = {
                val emailTrimmed = vm.state.email.trim()

                when {
                    emailTrimmed.isEmpty() -> {
                        emailError.value = "Vui lòng nhập email"
                    }
                    emailTrimmed.contains(" ") -> {
                        emailError.value = "Email không được chứa khoảng trắng"
                    }
                    !emailTrimmed.contains("@") -> {
                        emailError.value = "Email phải chứa ký tự @"
                    }
                    !Patterns.EMAIL_ADDRESS.matcher(emailTrimmed).matches() -> {
                        emailError.value = "Email không hợp lệ. Vui lòng kiểm tra lại"
                    }
                    else -> {
                        emailError.value = null
                        val code = (10000..99999).random().toString()
                        vm.state = vm.state.copy(email = emailTrimmed, code = code)
                        // Gửi code sang verify qua savedStateHandle
                        nav.currentBackStackEntry?.savedStateHandle?.set("verification_code", code)
                        nav.navigate("verify")
                    }
                }
            },
            modifier = Modifier.constrainAs(button) {
                top.linkTo(input.bottom, margin = 24.dp)
                centerHorizontallyTo(parent)
            }
        )
        if (emailError.value != null) {
            Text(
                text = emailError.value!!,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier
                    .constrainAs(errorText) {
                        top.linkTo(button.bottom, margin = 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }

        if (showSummary.value) {
            Column(
                modifier = Modifier
                    .constrainAs(summary) {
                        top.linkTo(button.bottom, margin = 24.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
            ) {
                Text("Summary:", fontWeight = FontWeight.Bold)
                Text("Email: ${vm.state.email}")
                Text("Code: ${vm.state.code}")
                Text("Password: ${vm.state.password}")
                Text("Confirm: ${vm.state.confirmPassword}")
            }
        }
    }
    LaunchedEffect(nav.currentBackStackEntry) {
        val navBackStackEntry = nav.currentBackStackEntry
        if (navBackStackEntry?.savedStateHandle?.get<Boolean>("showSummary") == true) {
            showSummary.value = true
            navBackStackEntry.savedStateHandle["showSummary"] = false
        }
    }
}
