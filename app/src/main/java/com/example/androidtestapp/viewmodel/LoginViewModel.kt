package com.example.androidtestapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.androidtestapp.repo.LoginRepository

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val loginRepo = LoginRepository()
    val loginStatus = MutableLiveData<Boolean>()
    var key : String? = null
    var token : String? = null

    init {
        loginRepo.getLoginData().observeForever {
            if (it?.authentication_token != null && it.person != null) {
                loginStatus.postValue(true)
                key = it.person.key
                token = it.authentication_token
            } else {
                loginStatus.postValue(false)
            }
        }
        }

    fun nativeLogin(email: String, password: String) {
        loginRepo.login(email, password)
    }
}
