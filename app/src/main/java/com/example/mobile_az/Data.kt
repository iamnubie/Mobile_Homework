package com.example.mobile_az

import androidx.annotation.DrawableRes

data class Data(
    @DrawableRes val image: Int,
    val name: String,
    val address: String
)
