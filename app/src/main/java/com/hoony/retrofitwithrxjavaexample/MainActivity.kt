package com.hoony.retrofitwithrxjavaexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hoony.retrofitwithrxjavaexample.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initViews()
        setListener()
        setObserve()
    }

    private fun initViews() {
        binding.apply {
            followerList.apply {
                layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
            }
        }
    }

    private fun setListener() {
        binding.followersRequestButton.setOnClickListener {
            viewModel.requestFollowers()
        }
    }

    private fun setObserve() {
        viewModel.followerList.observe(this, Observer {
            val adapter: FollowerListAdapter? = binding.followerList.adapter as FollowerListAdapter?
            if (adapter != null) {
                adapter.submitList(it)
            } else {
                binding.followerList.adapter = FollowerListAdapter(it)
            }
        })
    }
}