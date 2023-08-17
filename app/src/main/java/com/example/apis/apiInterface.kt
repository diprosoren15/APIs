package com.example.apis

import retrofit2.Call
import retrofit2.http.GET

interface apiInterface {
    @GET("products")
    fun getProductData (): Call<MyData>
}