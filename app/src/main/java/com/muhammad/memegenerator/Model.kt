package com.muhammad.memegenerator

data class Model( val success: Boolean, // Success status
                  val data: Data // Data object containing memes
)

data class Data(
    val memes: ArrayList<MemeDetails> // List of meme objects
)

data class MemeDetails(
    val id: String, // Meme ID
    val name: String, // Meme name
    val url: String, // URL of the meme image
    val width: Int, // Width of the meme image
    val height: Int, // Height of the meme image
    val boxCount: Int // Number of boxes on the meme
     )
