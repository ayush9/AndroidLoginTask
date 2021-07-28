package com.example.androidtestapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val loginRepo = LoginRepository()
    val loginStatus = MutableLiveData<Boolean>()

    init {
        loginRepo.getLoginData().observeForever {
            if (it?.authentication_token != null && it.person != null) {
                loginStatus.postValue(true)
            } else {
                loginStatus.postValue(false)
            }
        }
        }

    fun nativeLogin(email: String, password: String) {
        loginRepo.login(email, password)
    }
}
