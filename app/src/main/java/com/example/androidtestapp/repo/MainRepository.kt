package com.example.androidtestapp.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidtestapp.objects.LocationDataResponse
import com.example.androidtestapp.objects.LoginDataResponse
import com.example.androidtestapp.objects.UserDataResponse
import com.example.androidtestapp.repo.RetrofitClientInstance.retrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainRepository {

    private var loginData: MutableLiveData<LoginDataResponse?>? = null
    private var signUpData: MutableLiveData<LoginDataResponse?>? = null

    fun getLoginData(): LiveData<LoginDataResponse?> {
        if (loginData == null) {
            loginData = MutableLiveData()
        }
        return loginData as MutableLiveData<LoginDataResponse?>
    }

    fun getSignUpData(): LiveData<LoginDataResponse?> {
        if (signUpData == null) {
            signUpData = MutableLiveData()
        }
        return signUpData as MutableLiveData<LoginDataResponse?>
    }

    fun login(email: String, password: String) {
        val service =
            retrofitInstance!!.create(
                GetDataService::class.java
            )
        val map = HashMap<String, String>()
        map["email"] = email
        map["password"] = password
        val call: Call<LoginDataResponse?> = service.getCurrentUser(map)
        call.enqueue(object : Callback<LoginDataResponse?> {
            override fun onResponse(
                call: Call<LoginDataResponse?>,
                response: Response<LoginDataResponse?>
            ) {
                loginData?.postValue(response.body())
            }

            override fun onFailure(
                call: Call<LoginDataResponse?>?,
                t: Throwable?
            ) {
                loginData?.postValue(null)
            }
        })
    }

    fun signUp(email: String, password: String, name: String) {
        val service =
            retrofitInstance!!.create(
                GetDataService::class.java
            )
        val map = HashMap<String, String>()
        map["display_name"] = name
        map["email"] = email
        map["password"] = password
        val call: Call<LoginDataResponse?> = service.registerUser(map)
        call.enqueue(object : Callback<LoginDataResponse?> {
            override fun onResponse(
                call: Call<LoginDataResponse?>,
                response: Response<LoginDataResponse?>
            ) {
                signUpData?.postValue(response.body())
            }

            override fun onFailure(
                call: Call<LoginDataResponse?>?,
                t: Throwable?
            ) {
                signUpData?.postValue(null)
            }
        })
    }

    fun fetchCurrentUser(
        userResponse: MutableLiveData<UserDataResponse?>,
        key: String?,
        token: String?
    ) {
        val service =
            retrofitInstance!!.create(
                GetDataService::class.java
            )
        val headerMap = HashMap<String, String>()
        headerMap["Content-Type"] = "application/json"
        headerMap["Authorization"] = token.toString()
        val call: Call<UserDataResponse?> = service.fetchCurrentUser(headerMap, key)
        call.enqueue(object : Callback<UserDataResponse?> {
            override fun onResponse(
                call: Call<UserDataResponse?>,
                response: Response<UserDataResponse?>
            ) {
                userResponse.postValue(response.body())
            }

            override fun onFailure(
                call: Call<UserDataResponse?>?,
                t: Throwable?
            ) {
                userResponse.postValue(null)
            }
        })
    }

    fun fetchUserLocationDetails(
        locationDataResponse: MutableLiveData<ArrayList<LocationDataResponse?>>,
        token: String?
    ) {
        val service =
            retrofitInstance!!.create(
                GetDataService::class.java
            )
        val headerMap = HashMap<String, String>()
        headerMap["Content-Type"] = "application/json"
        headerMap["Authorization"] = token.toString()
        val call: Call<ArrayList<LocationDataResponse?>> = service.fetchCurrentLocation(headerMap)
        call.enqueue(object : Callback<ArrayList<LocationDataResponse?>> {
            override fun onResponse(
                call: Call<ArrayList<LocationDataResponse?>>,
                response: Response<ArrayList<LocationDataResponse?>>
            ) {
                locationDataResponse.postValue(response.body())
            }

            override fun onFailure(
                call: Call<ArrayList<LocationDataResponse?>>?,
                t: Throwable?
            ) {
                locationDataResponse.postValue(null)
            }
        })
    }
}
