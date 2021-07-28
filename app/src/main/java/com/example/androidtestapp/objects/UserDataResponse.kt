package com.example.androidtestapp.objects

import com.example.androidtestapp.objects.Role
import com.google.gson.annotations.SerializedName
import java.util.*

data class UserDataResponse (
    @SerializedName("created_at") val created_at : Date,
    @SerializedName("display_name") val display_name : String,
    @SerializedName("email") val email : String,
    @SerializedName("key") val key : String,
    @SerializedName("role") val role : Role,
    @SerializedName("updated_at") val updated_at : String
)
