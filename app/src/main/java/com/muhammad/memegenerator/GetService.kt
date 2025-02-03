package com.muhammad.memegenerator

import retrofit2.Call
import retrofit2.http.GET

interface GetService {
    @GET("get_memes")
    fun getMemes(): Call<Model>
}