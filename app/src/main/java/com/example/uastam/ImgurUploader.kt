package com.example.uastam

import android.content.Context
import android.net.Uri
import android.util.Base64
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

object ImgurUploader {
    private const val CLIENT_ID = "Client-ID 06db70966b90761" // Ganti ini!

    fun uploadImage(context: Context, imageUri: Uri, callback: (String?) -> Unit) {
        val inputStream = context.contentResolver.openInputStream(imageUri)
        val imageBytes = inputStream?.readBytes()
        inputStream?.close()

        if (imageBytes == null) {
            callback(null)
            return
        }

        val base64Image = Base64.encodeToString(imageBytes, Base64.DEFAULT)
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("image", base64Image)
            .build()

        val request = Request.Builder()
            .url("https://api.imgur.com/3/image")
            .addHeader("Authorization", CLIENT_ID)
            .post(requestBody)
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    callback(null)
                    return
                }

                val responseBody = response.body?.string()
                val json = JSONObject(responseBody ?: "")
                val imageUrl = json.getJSONObject("data").getString("link")
                callback(imageUrl)
            }
        })
    }
}
