package com.example.mobile_az.tuan03

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController

@Composable
fun TextDetailScreen(navController: NavController) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val (btnBack, title, styledText) = createRefs()

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
            text = "Text Detail",
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

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Blue)) { append("The ") }
                withStyle(style = SpanStyle(textDecoration = TextDecoration.LineThrough)) { append("quick ") }
                withStyle(style = SpanStyle(color = Color(0xFFF44336))) { append("Brown") }
                append("\n")
                append("fox")
                withStyle(style = SpanStyle(letterSpacing = 3.sp)) {
                    append(" jumps ")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("over")
                }
                append("\n")
                withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                    append("the")
                }
                withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                    append(" lazy ")
                }
                append("dog.")
            },
            fontSize = 22.sp,
            lineHeight = 30.sp,
            modifier = Modifier.constrainAs(styledText) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}

