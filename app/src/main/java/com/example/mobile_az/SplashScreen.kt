package com.example.mobile_az

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mobile_az.ui.theme.Mobile_AZTheme
import kotlinx.coroutines.delay
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon

@Composable
fun SplashScreen(navController: NavHostController) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (text, logo) = createRefs()
        val guildeLine30 = createGuidelineFromTop(0.3f)
        Image(
            painter = painterResource(id = R.drawable.school_logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(140.dp)
                .constrainAs(logo) {
                    top.linkTo(guildeLine30)
                    centerHorizontallyTo(parent)
                }
        )

        Text(
            text = "UTH SmartTasks",
            modifier = Modifier.constrainAs(text) {
                top.linkTo(logo.bottom, margin = 16.dp)
                centerHorizontallyTo(parent)
            },
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.righteous)),
                fontSize = 32.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Magenta
            )
        )

        //Delay điều hướng sang onboarding
        LaunchedEffect(true) {
            delay(2000)
            navigateTo(navController, "onboarding/0", cleanStack = true)
        }
    }
}

@Composable
fun OnBoardingScreen(
    navController: NavHostController,
    pageIndex: Int
) {
    val pages = listOf(OnBoardingPage.Page1, OnBoardingPage.Page2, OnBoardingPage.Page3)
    val page = pages[pageIndex]

    ConstraintLayout(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        val (image, title, desc) = createRefs()
        val guildeLine15 = createGuidelineFromTop(0.15f)

        Image(painter = painterResource(id = page.imageRes), contentDescription = null,
            modifier = Modifier
                .size(300.dp)
                .constrainAs(image) {
                top.linkTo(guildeLine15)
                centerHorizontallyTo(parent)
            })

        Text(
            text = page.title,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier
                .constrainAs(title) {
            top.linkTo(image.bottom)
            centerHorizontallyTo(parent)
        })

        Text(text = page.description, modifier = Modifier.constrainAs(desc) {
            top.linkTo(title.bottom, margin = 8.dp)
            centerHorizontallyTo(parent)
        })

        val (buttonRow) = createRefs()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .constrainAs(buttonRow) {
                    bottom.linkTo(parent.bottom)
                    centerHorizontallyTo(parent)
                }
        ) {
            if (pageIndex > 0) {
                Button(
                    onClick = {
                        navigateTo(navController, "onboarding/${pageIndex - 1}")
                    },
                    shape = RoundedCornerShape(50),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .height(50.dp)
                        .aspectRatio(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(18.dp))
            }

            Button(
                onClick = {
                    if (pageIndex < 2) navigateTo(navController, "onboarding/${pageIndex + 1}")
                    else navigateTo(navController, "splash")
                },
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f)
            ) {
                Text(
                    text = if (pageIndex < 2) "Next" else "Get Started",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Color.White
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    val fakeNavController = rememberNavController()

    Mobile_AZTheme {
        SplashScreen(navController = fakeNavController)
    }
}
