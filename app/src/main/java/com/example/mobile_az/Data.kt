package com.example.mobile_az

import androidx.annotation.DrawableRes
import androidx.annotation.Keep
import java.util.UUID

data class Data(
    @DrawableRes val image: Int,
    val name: String,
    val address: String
)

@Keep
data class Book(
    val id: String? = UUID.randomUUID().toString(),
    val title: String? = null
)

@Keep
data class Student(
    val id: String? = UUID.randomUUID().toString(),
    val name: String? = null,
    val borrowedBooks: List<String>? = null
)