package com.example.mobile_az

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.mobile_az.ui.theme.Mobile_AZTheme

@Composable
fun Outline(modifier: Modifier = Modifier){
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
    ) {
        val horizontalGuideLine40 = createGuidelineFromTop(0.4f)
        val (tvTitle, txtEmail, tvResult, btnCheck) = createRefs()

        var email by remember { mutableStateOf("") }
        var result by remember { mutableStateOf("") }

        Text(
            text = "Thực hành 02",
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
            modifier = Modifier.constrainAs(tvTitle) {
                bottom.linkTo(horizontalGuideLine40, margin = 20.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
            label = { Text("Email") },
            placeholder = { Text("Nhập email") },
            colors = TextFieldDefaults.colors(
                cursorColor = Color.Black,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            modifier = Modifier
                .constrainAs(txtEmail) {
                    top.linkTo(tvTitle.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(horizontal = 16.dp)
        )

        Text(
            text = result,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Red,
            modifier = Modifier
                .constrainAs(tvResult) {
                    top.linkTo(txtEmail.bottom, margin = 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Button(
            onClick = {
                result = when {
                    email.isBlank() -> "Email không hợp lệ"
                    !email.contains("@") -> "Email không đúng định dạng"
                    else -> "Bạn đã nhập email hợp lệ"
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1E90FF), // Màu xanh giống hình
                contentColor = Color.White
            ),
            modifier = Modifier
                .constrainAs(btnCheck) {
                    top.linkTo(tvResult.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Text("Kiểm tra")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Email() {
    Outline()
}