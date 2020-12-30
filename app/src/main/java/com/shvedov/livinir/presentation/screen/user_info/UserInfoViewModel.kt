package com.shvedov.livinir.presentation.screen.user_info

import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import com.shvedov.livinir.data.repository.UserRepository
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.File
import java.io.IOException
import javax.inject.Inject

class UserInfoViewModel @Inject constructor(

    private val userRepository: UserRepository

) : ViewModel(), UploadRequestBody.UploadCallback {

    val progressValue: ObservableInt = ObservableInt(0)

    fun uploadAvatar(avatarFile: File) {
        progressValue.set(0)

        val postBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "image",
                "androidFlask.jpg",
                UploadRequestBody(avatarFile, "image/*jpg", this)
            ).build()

        postRequest("http://192.168.1.185/index", postBody)
    }

    private fun postRequest(postUrl: String, postBody: RequestBody) {

        val client = OkHttpClient()

        val request = Request.Builder()
            .url(postUrl)
            .post(postBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val a = 2
            }

            override fun onResponse(call: Call, response: Response) {
                progressValue.set(100)
            }
        })
    }

    override fun onProgressUpdate(percentage: Int) {
        progressValue.set(percentage)
    }
}