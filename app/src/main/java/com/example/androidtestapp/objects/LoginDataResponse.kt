package com.example.androidtestapp.objects

import com.google.gson.annotations.SerializedName

data class LoginDataResponse(
@SerializedName("authentication_token") val authentication_token : String?,
@SerializedName("person") val person : Person?
)

data class Person (
    @SerializedName("key") val key : String,
    @SerializedName("display_name") val display_name : String,
    @SerializedName("role") val role : Role
)


data class Role (
    @SerializedName("key") val key : String,
    @SerializedName("rank") val rank : Int
)
