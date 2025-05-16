package com.example.mobile_az

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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

@Composable
fun InputInfo(modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        val horizontalGuideLine30 = createGuidelineFromTop(0.3f)
        val (tvTitle, boxForm, btnCheck, tvResult) = createRefs()

        var name by remember { mutableStateOf("") }
        var age by remember { mutableStateOf("") }
        var result by remember { mutableStateOf("") }

        Text(
            text = "THỰC HÀNH 01",
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
            modifier = Modifier.constrainAs(tvTitle) {
                bottom.linkTo(horizontalGuideLine30, margin = 20.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        Box(
            modifier = Modifier
                .background(color = Color(0xFFEC1EFF), RoundedCornerShape(8.dp))
                .padding(26.dp)
                .constrainAs(boxForm) {
                    top.linkTo(tvTitle.bottom, margin = 30.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            FormContent(
                name = name,
                age = age,
                onNameChange = { name = it },
                onAgeChange = { age = it }
            )
        }
        Button(
            onClick = {
                result = try {
                    val ageValue = age.toInt()
                    when {
                        ageValue > 65 -> "Người già"
                        ageValue in 6..65 -> "Người lớn"
                        ageValue in 2..5 -> "Trẻ em"
                        ageValue in 0..2 -> "Em bé"
                        else -> "Tuổi không hợp lệ"
                    }
                } catch (e: Exception) {
                    "Vui lòng nhập tuổi hợp lệ"
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1E90FF),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)
                .constrainAs(btnCheck) {
                    top.linkTo(tvResult.bottom, margin = 30.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            shape = RoundedCornerShape(5.dp),
        ) {
            Text("Kiểm tra", fontSize = 20.sp,)
        }
        Text(
            text = result,
            fontSize = 16.sp,
            color = Color.Red,
            modifier = Modifier.constrainAs(tvResult) {
                top.linkTo(boxForm.bottom, margin = 20.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}

@Composable
fun FormContent(
    name: String,
    age: String,
    onNameChange: (String) -> Unit,
    onAgeChange: (String) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        val (txtName, txtAge, cName, cAge) = createRefs()
        val verticalGuideLine45 = createGuidelineFromStart(0.35f)
        Text(
            text = "Tuổi",
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
            modifier = Modifier.constrainAs(cAge) {
                start.linkTo(parent.start)
                top.linkTo(txtAge.top)
                bottom.linkTo(txtAge.bottom)
            }
        )
        Text(
            text = "Họ và tên",
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
            modifier = Modifier.constrainAs(cName) {
                start.linkTo(parent.start)
                top.linkTo(txtName.top)
                bottom.linkTo(txtName.bottom)
            }
        )
        TextField(
            value = name,
            onValueChange = onNameChange,
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(9.dp),
            modifier = Modifier
                .width(200.dp)
                .height(50.dp)
                .constrainAs(txtName) {
                    top.linkTo(parent.top)
                    start.linkTo(verticalGuideLine45)
                }
        )
        TextField(
            value = age,
            onValueChange = onAgeChange,
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(9.dp),
            modifier = Modifier
                .width(200.dp)
                .height(50.dp)
                .constrainAs(txtAge) {
                    top.linkTo(txtName.bottom, margin = 18.dp)
                    start.linkTo(verticalGuideLine45)
                }
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NameAge() {
    InputInfo()
}