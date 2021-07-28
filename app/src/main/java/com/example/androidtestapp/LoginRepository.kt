package com.example.androidtestapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherdemoapp.LoginDataResponse
import com.example.weatherdemoapp.RetrofitClientInstance.retrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginRepository {

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
}
