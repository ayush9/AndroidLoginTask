package com.example.androidtestapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class SignUpViewModel(application: Application) : AndroidViewModel(application) {

    private val loginRepo = LoginRepository()
    val signUpStatus = MutableLiveData<Boolean>()

    init {
        loginRepo.getSignUpData().observeForever {
            if (it?.authentication_token != null && it.person != null) {
                signUpStatus.postValue(true)
            } else {
                signUpStatus.postValue(false)
            }
        }
    }

    fun registerUser(email: String, password: String, name: String) {
        loginRepo.signUp(email, password, name)
    }
}
