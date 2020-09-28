package com.hoony.retrofitwithrxjavaexample.retrofit

import android.util.Log
import com.hoony.retrofitwithrxjavaexample.BuildConfig
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Response
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
                    .addInterceptor(
                        HttpLoggingInterceptor(
                            HttpLoggingInterceptor.Logger {
                                Log.i("TEST", it)
                            }
                        ).apply {
                            level = if (BuildConfig.DEBUG) {
                                HttpLoggingInterceptor.Level.BODY
                            } else {
                                HttpLoggingInterceptor.Level.NONE
                            }
                        }
                    )
                    .build()
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    val getFollowers: Single<Response<List<GithubUser>>> = githubApi
        .getFollowers("octocat")
        .subscribeOn(Schedulers.io())
        .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
//        .retry() // 성공할때까지 재시도
        .retry(2) // 실패시 2회 재시도
//        .retry { t -> t is HttpException } // 실패시 HttpException 일경우 성공할때까지 재시도
//        .retry(2) { t -> t is HttpException } // 실패시 HttpException 일경우 2회 재시도
        .observeOn(AndroidSchedulers.mainThread())


    val getFollowersFail: Single<Response<List<GithubUser>>> = githubApi
        .getFollowers("qqweaasda")
        .subscribeOn(Schedulers.io())
        .map { t -> if (t.isSuccessful) t else throw HttpException(t) }
        .observeOn(AndroidSchedulers.mainThread())
}