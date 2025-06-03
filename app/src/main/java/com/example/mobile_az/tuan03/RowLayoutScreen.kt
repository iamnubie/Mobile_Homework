package com.example.mobile_az.tuan03

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController

@Composable
fun RowLayoutScreen(navController: NavController) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val (btnBack, title, content) = createRefs()

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
            text = "Row Layout",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color(0xFF1E90FF),
            modifier = Modifier.constrainAs(title) {
                top.linkTo(btnBack.top)
                bottom.linkTo(btnBack.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        // Nội dung (dùng Column bọc Row)
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .constrainAs(content) {
                    top.linkTo(title.bottom, margin = 32.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            repeat(3) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    repeat(3) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(2f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    color = if (it == 1) Color(0xFFE042FF)
                                    else Color(0xFF3399FF).copy(alpha = 0.4f)
                                )
                        )
                    }
                }
            }
        }
    }
}
