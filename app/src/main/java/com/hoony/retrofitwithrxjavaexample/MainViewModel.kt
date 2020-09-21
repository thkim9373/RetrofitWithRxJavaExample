package com.hoony.retrofitwithrxjavaexample

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hoony.retrofitwithrxjavaexample.retrofit.GithubRepository
import com.hoony.retrofitwithrxjavaexample.retrofit.GithubUser

class MainViewModel : ViewModel() {
    private val githubRepository = GithubRepository()

    private val _followerList = MutableLiveData<List<GithubUser>>()
    val followerList: LiveData<List<GithubUser>>
        get() = _followerList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun requestFollowers() {
        _isLoading.value = true

        githubRepository.getFollowers.subscribe(
            { githubUsers ->
                _isLoading.value = false

                if (githubUsers.isSuccessful) {
                    Log.d("hoony", "Request success : ${githubUsers.code()}")
                    _followerList.value = githubUsers.body()
                } else {
                    Log.d("hoony", "Request fail : ${githubUsers.code()}")
                }
            }, { throwable ->
                _isLoading.value = false

                Log.d("hoony", "Request error : $throwable")
            })
    }

    fun requestFollowersFail() {
        _isLoading.value = true

        githubRepository.getFollowersFail.subscribe(
            { githubUsers ->
                _isLoading.value = false

                if (githubUsers.isSuccessful) {
                    Log.d("hoony", "Request success : ${githubUsers.code()}")
                    _followerList.value = githubUsers.body()
                } else {
                    Log.d("hoony", "Request fail : ${githubUsers.code()}")
                }
            }, { throwable ->
                _isLoading.value = false

                Log.d("hoony", "Request error : $throwable")
            })
    }
}