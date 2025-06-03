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
fun VerifyCodeScreen(nav: NavHostController, vm: ForgotPasswordViewModel) {
    val codeError = remember { mutableStateOf<String?>(null) }

    ConstraintLayout(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        val (back, errorText, input, button, logo) = createRefs()
        val guildeLine5 = createGuidelineFromTop(0.05f)

        IconButton(onClick = { nav.popBackStack() }, modifier = Modifier.constrainAs(back) {
            top.linkTo(parent.top, margin = 16.dp)
            start.linkTo(parent.start)
        }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }
        HeaderLogo(
            title = "Verify Code",
            subtitle = "Enter the the code we just sent you on your registered Email",
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(guildeLine5)
                }
        )

        InputField("Verification Code", vm.state.code, { vm.state = vm.state.copy(code = it) },
            modifier = Modifier.constrainAs(input) {
                top.linkTo(logo.bottom, margin = 16.dp)
                centerHorizontallyTo(parent)
            })

        ReusableButton(
            text = "Next",
            onClick = {
                val codeTrimmed = vm.state.code.trim()

                when {
                    codeTrimmed.isEmpty() -> {
                        codeError.value = "Vui lòng nhập mã xác thực"
                    }
                    !codeTrimmed.all { it.isDigit() } -> {
                        codeError.value = "Mã xác thực chỉ được chứa số"
                    }
                    codeTrimmed.length != 5 -> {
                        codeError.value = "Mã xác thực phải gồm đúng 5 chữ số"
                    }
                    else -> {
                        codeError.value = null
                        vm.state = vm.state.copy(code = codeTrimmed)
                        nav.navigate("reset")
                    }
                }
            },
            modifier = Modifier.constrainAs(button) {
                top.linkTo(input.bottom, margin = 24.dp)
                centerHorizontallyTo(parent)
            }
        )
        if (codeError.value != null) {
            Text(
                text = codeError.value!!,
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

    }
}
