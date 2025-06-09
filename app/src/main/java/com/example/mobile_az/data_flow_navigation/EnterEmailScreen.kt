package com.example.mobile_az.data_flow_navigation

import android.util.Patterns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun EnterEmailScreen(nav: NavHostController, vm: ForgotPasswordViewModel) {
    val showSummary = remember { mutableStateOf(false) }
    val emailError = remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val loginMessage = remember { mutableStateOf<String?>(null) }
    val loginSuccess = remember { mutableStateOf<Boolean?>(null) } // true/false/null

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        loginSuccess.value = true
                        loginMessage.value = "Đăng nhập thành công!"
                    } else {
                        loginSuccess.value = false
                        loginMessage.value = "Đăng nhập thất bại!"
                    }
                }
        } catch (e: ApiException) {
            loginSuccess.value = false
            loginMessage.value = "Lỗi đăng nhập: ${e.localizedMessage}"
        }
    }


    ConstraintLayout(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        val (logo, input, button, summary, errorText, resultBox) = createRefs()
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
        val gso = remember {
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("437807276634-jg8jj69n86gsufo45n7h8gt93svks51v.apps.googleusercontent.com")
                .requestEmail()
                .build()
        }
        val googleSignInClient = remember {
            GoogleSignIn.getClient(context, gso)
        }

        Text(
            text = "Đăng nhập bằng Google",
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(top = 24.dp)
                .clickable {
                    val signInIntent = googleSignInClient.signInIntent
                    launcher.launch(signInIntent)
                }
                .constrainAs(createRef()) {
                    top.linkTo(button.bottom, margin = 48.dp)
                    centerHorizontallyTo(parent)
                }
        )
        loginMessage.value?.let { msg ->
            Box(
                modifier = Modifier
                    .constrainAs(resultBox) {
                        top.linkTo(button.bottom, margin = 120.dp) // hoặc linkTo google text nếu bạn đã dùng constrainAs
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(
                        color = if (loginSuccess.value == true) Color(0xFFDFF0D8) else Color(0xFFF2DEDE),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = msg,
                    color = if (loginSuccess.value == true) Color(0xFF3C763D) else Color(0xFFA94442),
                    fontSize = 16.sp
                )
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
