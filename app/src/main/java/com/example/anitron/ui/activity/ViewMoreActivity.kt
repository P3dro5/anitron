package com.example.anitron.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.anitron.data.repository.ViewMoreRepository
import com.example.anitron.databinding.ViewMoreBinding
import com.example.anitron.domain.service.RetrofitService
import com.example.anitron.ui.modelfactory.ViewMoreViewModelFactory
import com.example.anitron.ui.viewmodel.ViewMoreViewModel

class ViewMoreActivity : AppCompatActivity() {

    private lateinit var binding: ViewMoreBinding

    lateinit var viewModel: ViewMoreViewModel

    private val retrofitService = RetrofitService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ViewMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            ViewMoreViewModelFactory(ViewMoreRepository(retrofitService))
        )[ViewMoreViewModel::class.java]
    }
}