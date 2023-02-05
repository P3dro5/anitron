package com.example.anitron.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.anitron.data.repository.PersonInfoRepository
import com.example.anitron.databinding.PersonInfoBinding
import com.example.anitron.domain.service.RetrofitService
import com.example.anitron.ui.modelfactory.PersonInfoViewModelFactory
import com.example.anitron.ui.viewmodel.PersonInfoViewModel

class PersonInfoActivity: AppCompatActivity(){

    private lateinit var binding: PersonInfoBinding

    lateinit var viewModel: PersonInfoViewModel

    private val retrofitService = RetrofitService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = PersonInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            PersonInfoViewModelFactory(PersonInfoRepository(retrofitService))
        )[PersonInfoViewModel::class.java]

    }
}