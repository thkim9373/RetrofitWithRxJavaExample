package com.hoony.retrofitwithrxjavaexample

import androidx.lifecycle.ViewModel
import com.hoony.retrofitwithrxjavaexample.retrofit.GithubRepository

class MainViewModel : ViewModel() {
    val githubRepository = GithubRepository()


}