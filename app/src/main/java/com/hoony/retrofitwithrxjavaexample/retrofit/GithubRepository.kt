package com.hoony.retrofitwithrxjavaexample.retrofit

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class GithubRepository {
    private val githubApi: GithubApi =
        createRetrofit("https://api.github.com").create(GithubApi::class.java)

    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                .connectionPool(ConnectionPool(5, 20, TimeUnit.SECONDS))
                .build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    val getFollowers: Single<List<GithubUser>> = githubApi
        .getFollowers("octocat")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}