package com.example.mobile_az.data_flow_navigation

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OTPInput(
    code: List<String>,
    onCodeChange: (List<String>) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequesters = List(5) { FocusRequester() }

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxWidth() // sử dụng modifier truyền vào
    ) {
        code.forEachIndexed { index, digit ->
            OutlinedTextField(
                value = digit,
                onValueChange = {
                    if (it.length <= 1 && it.all { c -> c.isDigit() }) {
                        val updated = code.toMutableList()
                        updated[index] = it
                        onCodeChange(updated)

                        if (it.isNotEmpty() && index < 4) {
                            focusRequesters[index + 1].requestFocus()
                        }
                    }
                },
                modifier = Modifier
                    .size(56.dp)
                    .focusRequester(focusRequesters[index]),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp
                )
            )
        }
    }
}

@Composable
fun VerifyCodeScreen(nav: NavHostController, vm: ForgotPasswordViewModel) {
    val codeError = remember { mutableStateOf<String?>(null) }
    val navEntry = nav.previousBackStackEntry
    val sentCode = navEntry?.savedStateHandle?.get<String>("verification_code")
    val codeInput = remember { mutableStateOf("") }
    val snackbarHostState = remember { androidx.compose.material3.SnackbarHostState() }
    val otpDigits = remember { mutableStateOf(List(5) { "" }) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        sentCode?.let {
            snackbarHostState.showSnackbar("Mã xác thực là: $it", duration = SnackbarDuration.Long)
        }
    }

    Scaffold(
        snackbarHost = { androidx.compose.material3.SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
    ConstraintLayout(modifier = Modifier.padding(innerPadding).fillMaxSize().padding(16.dp)) {
        val (back, errorText, input, button, logo, resendButton ) = createRefs()
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

//        InputField(
//            label = "Verification Code",
//            value = codeInput.value,
//            onValueChange = { codeInput.value = it },
//            modifier = Modifier.constrainAs(input) {
//                top.linkTo(logo.bottom, margin = 16.dp)
//                centerHorizontallyTo(parent)
//            }
//        )
        OTPInput(
            code = otpDigits.value,
            onCodeChange = { otpDigits.value = it },
            modifier = Modifier.constrainAs(input) {
                top.linkTo(logo.bottom, margin = 16.dp)
                centerHorizontallyTo(parent)
            }
        )

        ReusableButton(
            text = "Next",
            onClick = {
                val codeTrimmed = otpDigits.value.joinToString("").trim()
                val expectedCode = sentCode?.trim() ?: ""

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
                    codeTrimmed != expectedCode -> {
                        codeError.value = "Mã xác thực không chính xác"
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
        Text(
            text = "Gửi lại mã",
            modifier = Modifier
                .padding(top = 16.dp)
                .constrainAs(resendButton) {
                    top.linkTo(button.bottom, margin = 16.dp)
                    centerHorizontallyTo(parent)
                }
                .clickable {
                    val newCode = (10000..99999).random().toString()
                    nav.previousBackStackEntry?.savedStateHandle?.set("verification_code", newCode)

                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Mã xác thực mới: $newCode", duration = SnackbarDuration.Long)
                    }
                },
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
    }
}
