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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.MutableState
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.mobile_az.BuildConfig
import com.example.mobile_az.MainActivity
import com.example.mobile_az.R

fun showLoginNotification(context: Context, title: String, message: String) {
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val channelId = "login_channel"
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            "Login Notifications",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)
    }

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(title)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH) // để hiện popup
        .setAutoCancel(true)

    notificationManager.notify(1, builder.build())
}

/*fun showLoginNotification(context: Context, title: String, message: String) {
    val channelId = "login_channel"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Thông báo đăng nhập"
        val descriptionText = "Kênh thông báo khi đăng nhập thành công"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(title)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true)

    val notificationManager = NotificationManagerCompat.from(context)

    // Kiểm tra permission trước khi gọi notify
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
        ContextCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS)
        == PackageManager.PERMISSION_GRANTED
    ) {
        notificationManager.notify(1001, builder.build())
    } else {
        Log.w("Notification", "Permission POST_NOTIFICATIONS not granted")
    }
}*/
fun getGoogleSignInClient(context: Context): com.google.android.gms.auth.api.signin.GoogleSignInClient {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(BuildConfig.GOOGLE_CLIENT_ID)
        .requestEmail()
        .build()
    return GoogleSignIn.getClient(context, gso)
}
fun handleGoogleSignInResult(
    context: Context,
    result: Intent?,
    loginSuccess: MutableState<Boolean?>,
    loginMessage: MutableState<String?>,
    requestPermissionLauncher: ActivityResultLauncher<String>,
    nav: NavHostController
) {
    val task = GoogleSignIn.getSignedInAccountFromIntent(result)
    try {
        val account = task.getResult(ApiException::class.java)
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    loginSuccess.value = true
                    loginMessage.value = "Đăng nhập thành công!"

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                        ContextCompat.checkSelfPermission(
                            context,
                            android.Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                    } else {
                        showLoginNotification(
                            context,
                            "Đăng nhập thành công",
                            "Chào mừng bạn đến với ứng dụng UTH SMART TASK!"
                        )
                    }

                    nav.navigate("user_profile")
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

@Composable
fun GGLoginScreen(nav: NavHostController) {
    val context = LocalContext.current
    val loginMessage = remember { mutableStateOf<String?>(null) }
    val loginSuccess = remember { mutableStateOf<Boolean?>(null) }
    val googleSignInClient = remember { getGoogleSignInClient(context) }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted && loginSuccess.value == true) {
                showLoginNotification(
                    context,
                    "Đăng nhập thành công",
                    "Chào mừng bạn đến với ứng dụng UTH SMART TASK!"
                )
            }
        }
    )

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        handleGoogleSignInResult(
            context = context,
            result = result.data,
            loginSuccess = loginSuccess,
            loginMessage = loginMessage,
            requestPermissionLauncher = requestPermissionLauncher,
            nav = nav
        )
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        val (logo, intro, wel, resultBox, productBtn, button) = createRefs()
        val guildeLine1 = createGuidelineFromTop(0.1f)
        val guildeLine5 = createGuidelineFromTop(0.5f)

        HeaderLogo2(
            subtitle = "A simple and efficicet to-do app",
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(guildeLine1)
                }
        )
//        val gso = remember {
//            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken("437807276634-jg8jj69n86gsufo45n7h8gt93svks51v.apps.googleusercontent.com")
//                .requestEmail()
//                .build()
//        }
//        val googleSignInClient = remember {
//            GoogleSignIn.getClient(context, gso)
//        }
        Text(
            text = "Welcome",
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(top = 20.dp)
                .constrainAs(wel) {
                    top.linkTo(guildeLine5, margin = 48.dp)
                    centerHorizontallyTo(parent)
                }
        )
        Text(
            text = "Ready to explore? Login in to get start.",
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .constrainAs(intro) {
                    top.linkTo(wel.bottom, margin = 5.dp)
                    centerHorizontallyTo(parent)
                }
        )
        Button(
            onClick = {
                val signInIntent = googleSignInClient.signInIntent
                launcher.launch(signInIntent)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF90CAF9),
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .constrainAs(button) {
                    top.linkTo(intro.bottom, margin = 48.dp)
                    centerHorizontallyTo(parent)
                }
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "Đăng nhập bằng Google",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
        Button(
            onClick = {
                nav.navigate("request_data")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF64B5F6),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .constrainAs(productBtn) {
                    top.linkTo(button.bottom, margin = 16.dp)
                    centerHorizontallyTo(parent)
                }
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Xem sản phẩm", fontWeight = FontWeight.Bold)
        }

        loginMessage.value?.let { msg ->
            Box(
                modifier = Modifier
                    .constrainAs(resultBox) {
                        top.linkTo(logo.bottom, margin = 40.dp)
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
}
