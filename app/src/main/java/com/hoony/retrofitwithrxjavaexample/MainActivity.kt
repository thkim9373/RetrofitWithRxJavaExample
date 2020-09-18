package com.hoony.retrofitwithrxjavaexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.hoony.retrofitwithrxjavaexample.databinding.ActivityMainBinding
import com.hoony.retrofitwithrxjavaexample.retrofit.GithubRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val repository = GithubRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setListener()
    }

    private fun setListener() {
        binding.followersRequestButton.setOnClickListener {
            repository.getFollowers.subscribe(
                    { githubUsers ->
                        var text = ""
                        githubUsers.forEach {
                            text += it.toString()
                        }

                    }, { throwable ->

            })
        }
    }
}