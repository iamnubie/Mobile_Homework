package com.example.mobile_az

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController

@Composable
fun UIListScreen(navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (
                    title, displayLabel, textItem, imageItem,
                    inputLabel, textFieldItem, passwordFieldItem,
                    layoutLabel, columnItem, rowItem,
                    exploreBox
                ) = createRefs()

                Text(
                    text = "UI Components List",
                    color = Color(0xFF1E90FF),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.constrainAs(title) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )

                Text(
                    "Display",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.constrainAs(displayLabel) {
                        top.linkTo(title.bottom, margin = 24.dp)
                        start.linkTo(parent.start)
                    }
                )
                UIItem("Text", "Displays text", Modifier.constrainAs(textItem) {
                    top.linkTo(displayLabel.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                    navController.navigate("textDetail")
                }

                UIItem("Image", "Displays an image", Modifier.constrainAs(imageItem) {
                    top.linkTo(textItem.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                    navController.navigate("imageDetail")
                }

                Text(
                    "Input",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.constrainAs(inputLabel) {
                        top.linkTo(imageItem.bottom, margin = 24.dp)
                        start.linkTo(parent.start)
                    }
                )
                UIItem("TextField", "Input field for text", Modifier.constrainAs(textFieldItem) {
                    top.linkTo(inputLabel.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
                UIItem("PasswordField", "Input field for passwords", Modifier.constrainAs(passwordFieldItem) {
                    top.linkTo(textFieldItem.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

                Text(
                    "Layout",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.constrainAs(layoutLabel) {
                        top.linkTo(passwordFieldItem.bottom, margin = 24.dp)
                        start.linkTo(parent.start)
                    }
                )
                UIItem("Column", "Arranges elements vertically", Modifier.constrainAs(columnItem) {
                    top.linkTo(layoutLabel.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
                UIItem("Row", "Arranges elements horizontally", Modifier.constrainAs(rowItem) {
                    top.linkTo(columnItem.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

                Column(
                    modifier = Modifier
                        .constrainAs(exploreBox) {
                            top.linkTo(rowItem.bottom, margin = 24.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom, margin = 24.dp)
                        }
                        .fillMaxWidth()
                        .background(Color(0xFFFFA084), RoundedCornerShape(8.dp))
                        .clickable { }
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Tự tìm hiểu",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "Tìm ra tất cả các thành phần UI Cơ bản",
                        fontSize = 12.sp,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}

@Composable
fun UIItem(
    title: String,
    desc: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFEF41FF), RoundedCornerShape(8.dp))
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(12.dp)
    ) {
        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Text(text = desc, fontSize = 12.sp)
    }
}
