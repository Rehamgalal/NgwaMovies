package com.scan.ngwamovies.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.scan.ngwamovies.adapters.MediaAdapter
import com.scan.ngwamovies.databinding.ActivityMainBinding
import com.scan.ngwamovies.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerAdapter: MediaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initRecyclerView()
        getData()
    }

    private fun initRecyclerView() {
        recyclerAdapter = MediaAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = recyclerAdapter
        }
    }

    private fun getData() {
        viewModel.getRecyclerListObserver().observe(this, {
            recyclerAdapter.setDataList(it)
        })
        viewModel.getNetworkState().observe(this, {
            recyclerAdapter.setNetworkState(it)

        })
    }
}