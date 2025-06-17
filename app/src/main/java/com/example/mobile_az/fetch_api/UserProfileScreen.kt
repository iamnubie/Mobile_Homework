package com.example.mobile_az.fetch_api

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mobile_az.R
import com.google.firebase.auth.FirebaseAuth

@Composable
fun UserProfileScreen(nav: NavHostController) {
    val user = FirebaseAuth.getInstance().currentUser
    val context = LocalContext.current

    if (user == null) {
        Toast.makeText(context, "Không có người dùng đăng nhập!", Toast.LENGTH_SHORT).show()
        nav.popBackStack()
        return
    }

    val name = user.displayName ?: "Không có tên"
    val email = user.email ?: "Không có email"
    val photoUrl = user.photoUrl?.toString()
    val birthDate = "12/02/1975" // Firebase không cung cấp ngày sinh mặc định

    ConstraintLayout(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        val (backBtn, avatar, nameRef, emailRef, dobRef, back, title, icon) = createRefs()

        // Nút quay lại
        IconButton(
            onClick = { nav.popBackStack() },
            modifier = Modifier.constrainAs(backBtn) {
            top.linkTo(parent.top, margin = 16.dp)
            start.linkTo(parent.start)
        }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }

        Text(
            text = "Thông tin",
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(top = 20.dp)
                .constrainAs(title) {
                    top.linkTo(backBtn.top)
                    end.linkTo(backBtn.end)
                    centerHorizontallyTo(parent)
                }
        )

        // Ảnh đại diện
        AsyncImage(
            model = photoUrl,
            contentDescription = "Avatar",
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .constrainAs(avatar) {
                    top.linkTo(backBtn.bottom, margin = 32.dp)
                    centerHorizontallyTo(parent)
                }
        )
        Image(
            painter = painterResource(id = R.drawable.note),
            contentDescription = "icon",
            modifier = Modifier
                .size(30.dp)
                .constrainAs(icon) {
                    bottom.linkTo(avatar.bottom, margin = (-10).dp)
                    end.linkTo(avatar.end, margin = (-5).dp)
                }
        )

        // Họ tên
        OutlinedTextField(
            value = name,
            onValueChange = {},
            label = { Text("Tên") },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(nameRef) {
                    top.linkTo(avatar.bottom, margin = 32.dp)
                    centerHorizontallyTo(parent)
                }
        )

        // Email
        OutlinedTextField(
            value = email,
            onValueChange = {},
            label = { Text("Email") },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(emailRef) {
                    top.linkTo(nameRef.bottom, margin = 12.dp)
                    centerHorizontallyTo(parent)
                }
        )

        // Ngày sinh
        OutlinedTextField(
            value = birthDate,
            onValueChange = {},
            label = { Text("Ngày sinh") },
            readOnly = true,
            trailingIcon = {
                Icon(Icons.Default.DateRange, contentDescription = null)
            },
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(dobRef) {
                    top.linkTo(emailRef.bottom, margin = 12.dp)
                    centerHorizontallyTo(parent)
                }
        )

        // Nút Back
        Button(
            onClick = { nav.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .height(56.dp)
                .constrainAs(back) {
                    top.linkTo(dobRef.bottom, margin = 24.dp)
                    centerHorizontallyTo(parent)
                }
        ) {
            Text("Trở về")
        }
    }
}
