package com.mike.photoapp.api

import com.mike.photoapp.models.PhotoList
import retrofit2.Call
import retrofit2.http.GET

interface PhotoAPI {
    @GET("?key=11938549-0c8e9fe2ff9f0d91e31ab5dee&q=nature&image_type=photo")
    fun getPhotos() : Call<PhotoList>
}