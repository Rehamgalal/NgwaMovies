package com.scan.ngwamovies.ui

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.scan.ngwamovies.adapters.MediaAdapter
import com.scan.ngwamovies.databinding.ActivityMainBinding
import com.scan.ngwamovies.databinding.LayoutLoadingDialogBinding
import com.scan.ngwamovies.model.MediaItem
import com.scan.ngwamovies.ui.viewmodel.MainViewModel
import com.scan.ngwamovies.utils.OnItemClicked
import java.util.*

class MainActivity : AppCompatActivity(), OnItemClicked {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private lateinit var progressBinding: LayoutLoadingDialogBinding

    private lateinit var dialog: Dialog

    private lateinit var recyclerAdapter: MediaAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initRecyclerView()
        setDialogLayout()
        getData()
    }

    private fun initRecyclerView() {
        recyclerAdapter = MediaAdapter()
        recyclerAdapter.setListener(this)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = recyclerAdapter
        }
    }

    private fun setDialogLayout() {
        progressBinding = LayoutLoadingDialogBinding.inflate(layoutInflater)
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(progressBinding.root)
    }
    private fun getData() {
        viewModel.getRecyclerListObserver().observe(this, {
            recyclerAdapter.setDataList(it)
            recyclerAdapter.notifyDataSetChanged()
        })
        viewModel.getNetworkState().observe(this, {
            recyclerAdapter.setNetworkState(it)
        })
    }

    override fun onClick(item: MediaItem) {
        dialog.show()
        val window = dialog.window
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        viewModel.downloadVideo()
        progressBinding.name.text = item.name
        viewModel.getDownLoadProcess().observe(this, {
            progressBinding.progressBar.progress = it.toInt()
            if (it== 99L) dialog.dismiss()
        })
    }
}