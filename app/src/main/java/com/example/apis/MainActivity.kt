package com.example.apis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.LogPrinter
import android.widget.Adapter
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    lateinit var recyclerview : RecyclerView
    lateinit var thisadapter : MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview = findViewById(R.id.recyclerview)

        val retrofitbuilder = Retrofit.Builder().baseUrl("https://dummyjson.com/").addConverterFactory(GsonConverterFactory.create()).build().create(apiInterface::class.java)
        val retrofitdata = retrofitbuilder.getProductData()

        retrofitdata.enqueue(object : Callback<MyData?> {
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                //if api call is success then use the api data acc to usage
                var responseBody = response.body()
                val responseProducts = responseBody?.products!!

                thisadapter = MyAdapter(this@MainActivity, responseProducts)
                recyclerview.adapter = thisadapter
                recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)

            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                //if api call is a failure then this method executes
                Log.d("Main Activity", "On Failure: "+t.message)
            }
        })
    }
}