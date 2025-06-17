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

data class ForgotPasswordState(
    var email: String = "",
    var code: String = "",
    var password: String = "",
    var confirmPassword: String = ""
)
data class Product(
    val name: String? = null,
    val price: Double? = null,
    val des: String? = null,
    val imgURL: String? = null
)

data class Subtask(
    val id: Int? = null,
    val title: String? = null,
    val isCompleted: Boolean? = null
)
data class Attachment(
    val id: Int? = null,
    val fileName: String? = null,
    val fileUrl: String? = null
)
data class Reminder(
    val id: Int? = null,
    val time: String? = null,
    val type: String? = null
)
data class Task(
    val id: Int? = null,
    val title: String? = null,
    val desImageURL: String? = null,
    val description: String? = null,
    val status: String? = null,
    val priority: String? = null,
    val category: String? = null,
    val dueDate: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val subtasks: List<Subtask>? = null,
    val attachments: List<Attachment>? = null,
    val reminders: List<Reminder>? = null
)
data class TaskResponse(
    val isSuccess: Boolean? = null,
    val message: String? = null,
    val data: List<Task>? = null
)
data class SingleTaskResponse(
    val isSuccess: Boolean? = null,
    val message: String? = null,
    val data: Task? = null
)
data class Coin(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val current_price: Double,
    val price_change_percentage_24h: Double
)


