package com.muhammad.memegenerator

data class Meme(
    val success: Boolean,
    val data: MemeData?
)

data class MemeData(
    val url: String,
    val pageUrl: String
)

