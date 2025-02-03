package com.muhammad.memegenerator

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muhammad.memegenerator.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    var memes = ArrayList<MemeDetails>()
    private lateinit var recyclerView: RecyclerView
    lateinit var adapter: Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = binding.recyclerview

        binding.button.setOnClickListener{
            getMemes {
                if (memes.isNotEmpty()) {

                    memes.forEach{meme ->
                        val templateId = meme.id // Use first meme
                        val topText = meme.name
                        val bottomText = "This is bottom text"

                        createMeme(templateId, topText, bottomText)
                    }

                } else {
                    Toast.makeText(this@MainActivity, "No memes available", Toast.LENGTH_SHORT).show()
                }
            }
        }





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
    object RetrofitClient{
        private const val BASE_URL = "https://api.imgflip.com/"
        private val retrofit: Retrofit by lazy {
            Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val service : ApiService by lazy{

            retrofit.create(ApiService::class.java)
        }
        val serve : GetService by lazy{
                retrofit.create(GetService::class.java)
        }

    }
    private fun getMemes(onMemesLoaded: () -> Unit) {
        val call = RetrofitClient.serve.getMemes()
        call.enqueue(object : Callback<Model> {
            override fun onResponse(call: Call<Model>, response: Response<Model>) {
                if (response.isSuccessful && response.body() != null) {
                    memes = response.body()?.data?.memes ?: arrayListOf()
                    updateRecyclerView(memes)
                    onMemesLoaded() // Notify that memes are ready to use
                } else {
                    Log.e("API_ERROR", "Response not successful: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Model>, t: Throwable) {
                Log.e("API_ERROR", "Failed: ${t.message}")
            }
        })
    }
    private fun createMeme(templateId: String, text0: String, text1: String) {
        val call = RetrofitClient.service.createMeme(
            templateId = templateId,
            text0 = text0,
            text1 = text1
        )

        call.enqueue(object : Callback<Meme> {
            override fun onResponse(call: Call<Meme>, response: Response<Meme>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    val memeUrl = response.body()?.data?.url
                    Toast.makeText(this@MainActivity, "Meme created: $memeUrl", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Failed to create meme", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Meme>, t: Throwable) {
                Log.d("onFailure", "Error: ${t.message}")
            }
        })
    }



    // Function to update RecyclerView
    private fun updateRecyclerView(memesList: ArrayList<MemeDetails>) {
        if (::adapter.isInitialized) {
            adapter.updateData(memesList)
        } else {
            adapter = Adapter(memesList)
            recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
            recyclerView.adapter = adapter
        }
    }

}