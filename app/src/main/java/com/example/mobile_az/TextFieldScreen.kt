package com.example.mobile_az

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.AsyncImage
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun TextFieldScreen(navController: NavController) {
    var input by remember { mutableStateOf("") }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        val (btnBack, title, textField, label) = createRefs()

        Icon(
            imageVector = Icons.Default.KeyboardArrowLeft,
            contentDescription = "Back",
            tint = Color.Black,
            modifier = Modifier
                .size(32.dp)
                .constrainAs(btnBack) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .clickable { navController.popBackStack() }
        )

        Text(
            text = "TextField",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E90FF),
            modifier = Modifier.constrainAs(title) {
                top.linkTo(btnBack.top)
                bottom.linkTo(btnBack.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            placeholder = { Text("Thông tin nhập") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(textField) {
                    top.linkTo(title.bottom, margin = 48.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = if (input.isBlank()) "Tự động cập nhật dữ liệu theo textfield" else input,
            color = Color.Red,
            fontSize = 14.sp,
            modifier = Modifier.constrainAs(label) {
                top.linkTo(textField.bottom, margin = 24.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}
