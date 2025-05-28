package com.example.mobile_az

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
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

@Composable
fun ImageDetailScreen(navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        item {
            ConstraintLayout(
                modifier = Modifier
            ) {
                val (btnBack, title, imageUrl, textUrl, imageApp, textApp) = createRefs()

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
                    text = "Images",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color(0xFF1E90FF),
                    modifier = Modifier.constrainAs(title) {
                        top.linkTo(btnBack.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(btnBack.bottom)
                    }
                )

                AsyncImage(
                    model = "https://image.plo.vn/w1000/Uploaded/2025/bpivpuiv/2024_05_18/giao-thong-van-tai-h1-3682.jpg.webp",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .constrainAs(imageUrl) {
                            top.linkTo(title.bottom, margin = 32.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .clip(RoundedCornerShape(12.dp))
                        .fillMaxWidth()
                )

                Text(
                    "https://image.plo.vn/w1000/Uploaded/2025/bpivpuiv/2024_05_18/giao-thong-van-tai-h1-3682.jpg.webp",
                    fontSize = 16.sp,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .constrainAs(textUrl) {
                            top.linkTo(imageUrl.bottom, margin = 12.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                    }
                )

                Image(
                    painter = painterResource(id = R.drawable.school),
                    contentDescription = "In app",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .constrainAs(imageApp) {
                            top.linkTo(textUrl.bottom, margin = 32.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .clip(RoundedCornerShape(12.dp))
                        .fillMaxWidth()
                )

                Text(
                    "In app",
                    fontSize = 16.sp,
                    modifier = Modifier.constrainAs(textApp) {
                        top.linkTo(imageApp.bottom, margin = 12.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
            }
        }
    }
}

