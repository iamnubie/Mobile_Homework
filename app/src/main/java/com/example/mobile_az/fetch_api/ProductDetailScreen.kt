package com.example.mobile_az.fetch_api

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import com.example.mobile_az.Product
import com.example.mobile_az.R
import com.example.mobile_az.RetrofitInstance

@Composable
fun ProductDetailScreen(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    var product by remember { mutableStateOf<Product?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                product = RetrofitInstance.api.getProduct()
            } catch (e: Exception) {
                error = "Lỗi tải dữ liệu: ${e.message}"
            }
        }
    }

    if (product != null) {
        ProductCard(product!!, navController)
    } else if (error != null) {
        Text(text = error ?: "Lỗi", color = Color.Red, modifier = Modifier.padding(16.dp))
    } else {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun ProductCard(product: Product, nav: NavController) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (title, image, name, price, desc, backButton) = createRefs()

            IconButton(
                onClick = { nav.navigate("google") },
                modifier = Modifier.constrainAs(backButton) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Quay lại",
                    tint = Color.Black
                )
            }

            Text(
                text = "Product detail",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2196F3),
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(backButton.top)
                    bottom.linkTo(backButton.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            AsyncImage(
                model = product.imgURL ?: R.drawable.avatar,
                contentDescription = product.name ?: "Ảnh",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .constrainAs(image) {
                        top.linkTo(title.bottom, margin = 24.dp)
                        centerHorizontallyTo(parent)
                    }
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(20.dp))
            )

            Text(
                text = product.name ?: "Không rõ tên",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(name) {
                    top.linkTo(image.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                }
            )

            Text(
                text = "Giá: %,d₫".format(product.price?.toInt() ?: 0),
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.constrainAs(price) {
                    top.linkTo(name.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                }
            )

            Text(
                text = product.des ?: "Không có mô tả",
                fontSize = 14.sp,
                lineHeight = 20.sp,
                modifier = Modifier
                    .constrainAs(desc) {
                        top.linkTo(price.bottom, margin = 12.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp))
                    .padding(12.dp)
            )
        }
    }
}

