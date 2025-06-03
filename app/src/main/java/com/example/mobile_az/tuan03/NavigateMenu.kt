package com.example.mobile_az.tuan03

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.mobile_az.R

@Composable
fun MenuNavigate(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val (imgLogo, tvTitle, tvDesc, btnPush) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = "Logo",
            modifier = Modifier
                .size(200.dp)
                .constrainAs(imgLogo) {
                    top.linkTo(parent.top, margin = 100.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = "Navigation",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.constrainAs(tvTitle) {
                top.linkTo(imgLogo.bottom, margin = 24.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = "is a framework that simplifies the implementation of\n" +
                    "navigation between different UI components\n" +
                    "(activities, fragments, or composables) in an app",
            fontSize = 14.sp,
            color = Color.DarkGray,
            modifier = Modifier.constrainAs(tvDesc) {
                top.linkTo(tvTitle.bottom, margin = 12.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Button(
            onClick = {
                navController.navigate("uiList")
            },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1E90FF),
                contentColor = Color.White
            ),
            modifier = Modifier
                .constrainAs(btnPush) {
                    bottom.linkTo(parent.bottom, margin = 40.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(horizontal = 32.dp)
                .fillMaxWidth(0.6f)
                .height(48.dp)
        ) {
            Text("PUSH")
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun Navigate() {
//    MenuNavigate()
//}