package com.hoony.retrofitwithrxjavaexample.retrofit

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    @GET("users/{user_name}/followers")
    fun getFollowers(@Path("user_name") userName: String) : Single<List<GithubUser>>

    @GET("users/{user_name}/followers")
    fun getFollower(@Path("user_name") userName: String) : Single<Response<List<GithubUser>>>
}

data class GithubUser(
    val login: String,
    val id: Long,
    val node_id: String,
    val avatar_url: String,
    val gravatar_id: String,
    val url: String,
    val html_url: String,
    val followers_url: String,
    val following_url: String,
    val gists_url: String,
    val starred_url: String,
    val subscriptions_url: String,
    val organizations_url: String,
    val repos_url: String,
    val events_url: String,
    val received_events_url: String,
    val type: String,
    val site_admin: Boolean
)