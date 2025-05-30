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
fun Bt04(navController: NavController) {
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
                    fontSize = 18.sp,
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
            }
        }
    }
}