package com.hoony.retrofitwithrxjavaexample

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

    fun requestFollowers() {
        githubRepository.getFollowers.subscribe(
            { githubUsers ->
                _followerList.value = githubUsers
            }, { throwable ->

            })
    }
}