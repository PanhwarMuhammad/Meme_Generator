package com.muhammad.memegenerator

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("caption_image")
    fun createMeme(
        @Field("template_id") templateId: String,
        @Field("username") username: String = "Muhammad93",
        @Field("password") password: String = "Panhwar93",
        @Field("text0") text0: String,
        @Field("text1") text1: String
    ): Call<Meme>
}