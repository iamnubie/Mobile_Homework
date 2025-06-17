package com.example.mobile_az

import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("v2/product")
    suspend fun getProduct(): Product

    @GET("api/researchUTH/tasks")
    suspend fun getAllTasks(): TaskResponse

    @GET("api/researchUTH/task/{id}")
    suspend fun getTaskById(@Path("id") id: Int): SingleTaskResponse

    @DELETE("api/researchUTH/task/{id}")
    suspend fun deleteTask(@Path("id") id: Int)

    @GET("coins/markets")
    suspend fun getCoins(
        @Query("vs_currency") vsCurrency: String = "usd",
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") perPage: Int = 10,
        @Query("page") page: Int = 1,
        @Query("sparkline") sparkline: Boolean = false
    ): List<Coin>
}
