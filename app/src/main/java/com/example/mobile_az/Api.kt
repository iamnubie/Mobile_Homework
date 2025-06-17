package com.example.mobile_az

import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("v2/product")
    suspend fun getProduct(): Product

    @GET("api/researchUTH/tasks")
    suspend fun getAllTasks(): TaskResponse

    @GET("api/researchUTH/task/{id}")
    suspend fun getTaskById(@Path("id") id: Int): SingleTaskResponse

    @DELETE("api/researchUTH/task/{id}")
    suspend fun deleteTask(@Path("id") id: Int)
}
