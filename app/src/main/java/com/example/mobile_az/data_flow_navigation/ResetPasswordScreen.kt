package com.example.mobile_az.data_flow_navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController

@Composable
fun ResetPasswordScreen(nav: NavHostController, vm: ForgotPasswordViewModel) {
    val passwordError = remember { mutableStateOf<String?>(null) }
    val confirmError = remember { mutableStateOf<String?>(null) }

    ConstraintLayout(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        val (back, logo, pass, confirm, button, errorPass, errorConfirm) = createRefs()
        val guildeLine5 = createGuidelineFromTop(0.05f)

        IconButton(onClick = { nav.popBackStack() }, modifier = Modifier.constrainAs(back) {
            top.linkTo(parent.top, margin = 16.dp)
            start.linkTo(parent.start)
        }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }
        HeaderLogo(
            title = "Create new password",
            subtitle = "Your new password must be different form previously used password",
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(guildeLine5)
                }
        )

        InputField("Password", vm.state.password, { vm.state = vm.state.copy(password = it) },
            modifier = Modifier.constrainAs(pass) {
                top.linkTo(logo.bottom, margin = 16.dp)
                centerHorizontallyTo(parent)
            })

        InputField("Confirm Password", vm.state.confirmPassword, { vm.state = vm.state.copy(confirmPassword = it) },
            modifier = Modifier.constrainAs(confirm) {
                top.linkTo(pass.bottom, margin = 16.dp)
                centerHorizontallyTo(parent)
            })

        ReusableButton(
            text = "Next",
            onClick = {
                val password = vm.state.password.trim()
                val confirm = vm.state.confirmPassword.trim()

                when {
                    password.isEmpty() -> {
                        passwordError.value = "Vui lòng nhập mật khẩu"
                    }
                    password.length < 8 -> {
                        passwordError.value = "Mật khẩu phải có ít nhất 8 ký tự"
                    }
                    !password.any { it.isUpperCase() } -> {
                        passwordError.value = "Mật khẩu phải chứa ít nhất 1 chữ hoa"
                    }
                    !password.any { it.isLowerCase() } -> {
                        passwordError.value = "Mật khẩu phải chứa ít nhất 1 chữ thường"
                    }
                    !password.any { it.isDigit() } -> {
                        passwordError.value = "Mật khẩu phải chứa ít nhất 1 số"
                    }
                    !password.any { "!@#\$%^&*()_+{}[]|:;<>,.?/~`-=\\\"'".contains(it) } -> {
                        passwordError.value = "Mật khẩu phải chứa ít nhất 1 ký tự đặc biệt"
                    }
                    confirm != password -> {
                        passwordError.value = null
                        confirmError.value = "Mật khẩu nhập lại không khớp"
                    }
                    else -> {
                        passwordError.value = null
                        confirmError.value = null
                        nav.navigate("confirm")
                    }
                }
            },
            modifier = Modifier.constrainAs(button) {
                top.linkTo(confirm.bottom, margin = 24.dp)
                centerHorizontallyTo(parent)
            }
        )
        if (passwordError.value != null) {
            Text(
                text = passwordError.value!!,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier
                    .constrainAs(errorPass) {
                        top.linkTo(button.bottom, margin = 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }
        if (confirmError.value != null) {
            Text(
                text = confirmError.value!!,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier
                    .constrainAs(errorConfirm) {
                        top.linkTo(button.bottom, margin = 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }


    }
}
