package com.example.okhttp_android_demo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private lateinit var getButton: Button
    private lateinit var postButton: Button
    private lateinit var text: TextView

    private lateinit var client: OkHttpClient

    private var getUrl: String = "https://reqres.in/api/users/2"
    private var postUrl: String = "https://reqres.in/api/users"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeComponents()

        initializeOkHttpClient()

        getButton.setOnClickListener {
            getCall()
        }

        postButton.setOnClickListener{
            postCall()
        }
    }

    private fun initializeComponents() {
        setContentView(R.layout.activity_main)

        getButton = findViewById(R.id.getButton)
        postButton = findViewById(R.id.postButton)
        text = findViewById(R.id.viewText)
    }

    private fun initializeOkHttpClient() {
        client = OkHttpClient()
    }

    @Throws(IOException::class)
    fun getCall() {
        val request: Request = Request.Builder().url(getUrl).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                call.cancel()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val myResponse = response.body!!.string()
                runOnUiThread { text.text = myResponse }
            }
        })
    }

    @Throws(IOException::class)
    fun postCall() {
        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("name", "Wanindu Hasaranga")
            .addFormDataPart("job", "Sri Lanka Cricketer")
            .build()

        val request: Request = Request.Builder().url(postUrl).post(requestBody).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                call.cancel()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val myResponse = response.body!!.string()
                runOnUiThread { text.text = myResponse }
            }
        })
    }
}