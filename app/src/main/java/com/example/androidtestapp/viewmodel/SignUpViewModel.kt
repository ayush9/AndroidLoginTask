package com.example.androidtestapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.androidtestapp.repo.LoginRepository

class SignUpViewModel(application: Application) : AndroidViewModel(application) {

    private val loginRepo = LoginRepository()
    val signUpStatus = MutableLiveData<Boolean>()
    var key : String? = null
    var token : String? = null

    init {
        loginRepo.getSignUpData().observeForever {
            if (it?.authentication_token != null && it.person != null) {
                signUpStatus.postValue(true)
                key = it.person.key
                token = it.authentication_token
            } else {
                signUpStatus.postValue(false)
            }
        }
    }

    fun registerUser(email: String, password: String, name: String) {
        loginRepo.signUp(email, password, name)
    }
}
