package com.example.androidtestapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface GetDataService {
    @Headers("Content-Type: application/json")
    @POST("api/v2/people/authenticate")
    fun getCurrentUser(@Body body : HashMap<String, String>): Call<LoginDataResponse?>

    @Headers("Content-Type: application/json")
    @POST("api/v2/people/create")
    fun registerUser(@Body map: java.util.HashMap<String, String>): Call<LoginDataResponse?>
}