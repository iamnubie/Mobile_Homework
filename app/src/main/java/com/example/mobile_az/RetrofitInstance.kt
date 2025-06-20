package com.example.mobile_az

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
//    private const val BASE_URL = "https://mock.apidog.com/m1/890655-872447-default/"
//
//    val api: ApiService by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(ApiService::class.java)
//    }
    private const val BASE_URL = "https://amock.io/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
object RetrofitInstance1 {
    private const val BASE_URL = "https://api.coingecko.com/api/v3/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
//object RetrofitInstance {
//    private fun <T> createRetrofitService(baseUrl: String, serviceClass: Class<T>): T {
//        return Retrofit.Builder()
//            .baseUrl(baseUrl)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(serviceClass)
//    }
//
//    val apiMock: ApiService = createRetrofitService(
//        baseUrl = "https://amock.io/",
//        serviceClass = ApiService::class.java
//    )
//
//    val apiCoinGecko: ApiService = createRetrofitService(
//        baseUrl = "https://api.coingecko.com/api/v3/",
//        serviceClass = ApiService::class.java
//    )
//}
