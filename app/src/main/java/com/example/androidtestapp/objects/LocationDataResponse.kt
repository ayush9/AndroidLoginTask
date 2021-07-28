package com.example.androidtestapp.objects

import com.google.gson.annotations.SerializedName

data class LocationDataResponse (
    @SerializedName("id") val id : Int,
    @SerializedName("is_active") val is_active : Boolean,
    @SerializedName("is_available") val is_available : Boolean,
    @SerializedName("lat") val lat : Double,
    @SerializedName("lng") val lng : Double,
    @SerializedName("license_plate_number") val license_plate_number : String,
    @SerializedName("remaining_mileage") val remaining_mileage : Double,
    @SerializedName("remaining_range_in_meters") val remaining_range_in_meters : Double,
    @SerializedName("transmission_mode") val transmission_mode : String,
    @SerializedName("vehicle_make") val vehicle_make : String,
    @SerializedName("vehicle_pic_absolute_url") val vehicle_pic_absolute_url : String,
    @SerializedName("vehicle_type") val vehicle_type : String,
    @SerializedName("vehicle_type_id") val vehicle_type_id : Double
)
