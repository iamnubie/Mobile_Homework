package com.example.mobile_az.data_flow_navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import com.example.mobile_az.R

@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
    )
}

@Composable
fun ReusableButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        enabled = enabled
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun HeaderLogo(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        val (logo, text, titleText,  subtitleText) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.school_logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(120.dp)
                .constrainAs(logo) {
                    top.linkTo(parent.top)
                    centerHorizontallyTo(parent)
                }
        )

        Text(
            text = "SmartTasks",
            modifier = Modifier.constrainAs(text) {
                top.linkTo(logo.bottom)
                centerHorizontallyTo(parent)
            },
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.righteous)),
                fontSize = 26.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Magenta
            )
        )

        Text(
            text = title,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(titleText) {
                top.linkTo(text.bottom, margin = 32.dp)
                centerHorizontallyTo(parent)
            }
        )

        Text(
            text = subtitle,
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .constrainAs(subtitleText) {
                    top.linkTo(titleText.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    centerHorizontallyTo(parent)
                }
                .fillMaxWidth()
        )
    }
}
