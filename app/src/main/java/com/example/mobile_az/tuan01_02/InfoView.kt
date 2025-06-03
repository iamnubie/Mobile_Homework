package com.example.mobile_az.tuan01_02

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.mobile_az.ui.theme.Mobile_AZTheme
import androidx.compose.ui.text.style.TextAlign
import com.example.mobile_az.Data
import com.example.mobile_az.R

@Composable
fun MyInfo(
    data: Data,
    modifier: Modifier = Modifier
){
    val backgroundColor = Color(0xFFFEF9E4)
    ConstraintLayout(
        modifier = modifier
            .background(color = backgroundColor)
            .fillMaxSize()
    ){
        val horizontalGuideLine30 = createGuidelineFromTop(0.3f)
        val horizontalGuideLine12 = createGuidelineFromTop(0.12f)
        val (imgIcon, tvName, tvAddress, imgBack, imgNote) = createRefs()
        val borderColor = Color(0xFF00BCD4).copy(alpha = 0.7f)
        Image(
            painter = painterResource(id = data.image),
            contentDescription = "",
            modifier = Modifier
                .clip(CircleShape)
                .size(150.dp)
                .constrainAs(imgIcon) {
                    top.linkTo(horizontalGuideLine30)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            contentScale = ContentScale.Crop
        )
        Text(
            text = data.name,
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
            modifier = Modifier.constrainAs(tvName) {
                top.linkTo(imgIcon.bottom, margin = 20.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        Text(
            text = data.address,
            textAlign = TextAlign.Justify,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp).constrainAs(tvAddress) {
                top.linkTo(tvName.bottom, margin = 10.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        Box(
            modifier = Modifier
                .clickable {  }
                .border(1.dp, borderColor, RoundedCornerShape(6.dp))
                .padding(15.dp)
                .constrainAs(imgBack) {
                    bottom.linkTo(horizontalGuideLine12)
                    start.linkTo(parent.start, margin = 20.dp)
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
        }
        Box(
            modifier = Modifier
                .clickable {  }
                .border(1.dp, borderColor, RoundedCornerShape(6.dp))
                .padding(15.dp)
                .constrainAs(imgNote) {
                    bottom.linkTo(horizontalGuideLine12)
                    end.linkTo(parent.end, margin = 20.dp)
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.note),
                contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
        }

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    Mobile_AZTheme {
        MyInfo(
            data = Data(
                image = R.drawable.avatar,
                name = "Nguyen Hong Minh - CN22H",
                address = "Trải nghiệm kiến thức mới, hoàn thiện chỉ tiêu của môn học, và trong quá trình đó sẽ đánh giá xem em có thích với hướng đi này không."
            ),
        )
    }
}