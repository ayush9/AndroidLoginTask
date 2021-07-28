package com.example.androidtestapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.androidtestapp.objects.LocationDataResponse
import com.example.androidtestapp.repo.MainRepository
import com.example.androidtestapp.objects.UserDataResponse

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    val toShowUserUI = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()
    val userResponse = MutableLiveData<UserDataResponse?>()
    val locationResponse = MutableLiveData<ArrayList<LocationDataResponse?>>()
    private val loginRepository = MainRepository()

    init {
        userResponse.observeForever {
            isLoading.postValue(false)
            if (it == null) {
                toShowUserUI.postValue(false)
            } else {
                toShowUserUI.postValue(true)
            }
        }
    }

    fun fetchUserDetails(key: String?, token: String?) {
        isLoading.postValue(true)
        loginRepository.fetchCurrentUser(userResponse, key, token)
    }

    fun fetchUserLocationDetails(token: String?) {
        isLoading.postValue(true)
        loginRepository.fetchUserLocationDetails(locationResponse, token)
    }
}
