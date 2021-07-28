package com.example.androidtestapp.repo

import com.example.androidtestapp.objects.LoginDataResponse
import com.example.androidtestapp.objects.UserDataResponse
import retrofit2.Call
import retrofit2.http.*


interface GetDataService {
    @Headers("Content-Type: application/json")
    @POST("api/v2/people/authenticate")
    fun getCurrentUser(@Body body : HashMap<String, String>): Call<LoginDataResponse?>

    @Headers("Content-Type: application/json")
    @POST("api/v2/people/create")
    fun registerUser(@Body map: java.util.HashMap<String, String>): Call<LoginDataResponse?>

    @GET("api/v2/people/{key}")
    fun fetchCurrentUser(@HeaderMap headers: Map<String, String>, @Path(value = "key", encoded = true) key : String?): Call<UserDataResponse?>
}