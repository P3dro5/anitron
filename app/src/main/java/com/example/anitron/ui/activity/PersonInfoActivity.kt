package com.example.anitron.ui.activity

import android.app.Person
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.anitron.R
import com.example.anitron.data.datasource.State
import com.example.anitron.data.datasource.tvshowInfo.PersonInfo
import com.example.anitron.data.repository.PersonInfoRepository
import com.example.anitron.databinding.PersonInfoBinding
import com.example.anitron.domain.service.RetrofitService
import com.example.anitron.ui.modelfactory.PersonInfoViewModelFactory
import com.example.anitron.ui.theme.fonts
import com.example.anitron.ui.viewmodel.PersonInfoViewModel

class PersonInfoActivity: AppCompatActivity(){

    private lateinit var binding: PersonInfoBinding

    lateinit var viewModel: PersonInfoViewModel

    private val retrofitService = RetrofitService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = PersonInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val personId = intent.getStringExtra("id")

        viewModel = ViewModelProvider(
            this,
            PersonInfoViewModelFactory(PersonInfoRepository(retrofitService))
        )[PersonInfoViewModel::class.java]

        findViewById<ComposeView>(binding.personInfoView.id)
            .setContent {
                viewModel.getPersonInformation(personId!!)

                val personInfo = viewModel.personInfo.collectAsState()

                when(personInfo.value.state){
                    State.Success -> {
                        personInfo.value.personInfo.let { PersonInfoScreen(personInfo = it!!) }
                    }
                    else -> {}
                }
            }

    }

    @Composable
    private fun PersonInfoScreen(personInfo : PersonInfo){
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                backgroundColor = Color.Transparent,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .fillMaxSize(), elevation = 0.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500" + personInfo.profilePersonImage),
                        contentDescription = null,
                        modifier = Modifier.size(328.dp)
                    )
                    Row(
                        modifier = Modifier.padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            personInfo.name,
                            fontFamily = fonts,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.White,
                            modifier = Modifier.weight(1f)
                        )
                        if(personInfo.birthday != null) {
                            Text(
                                "Born in " +
                                        personInfo.birthday,
                                fontFamily = fonts,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.weight(0.5f),
                                fontSize = 15.sp,
                                color = Color.White
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .padding(10.dp),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxHeight(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Role: " + personInfo.role,
                                modifier = Modifier.padding(5.dp).weight(1f),
                                fontFamily = fonts,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color.White,
                            )
                            if(personInfo.deathDay != null) {
                                Text(
                                    "Died: " + personInfo.deathDay,
                                    fontFamily = fonts,
                                    fontWeight = FontWeight.Normal,
                                    modifier = Modifier.weight(0.5f),
                                    fontSize = 15.sp,
                                    color = Color.White
                                )
                            }
                        }
                        Spacer(modifier = Modifier.padding(15.dp))
                        if(personInfo.biography != null) {
                            Text(
                                personInfo.biography,
                                fontFamily = fonts,
                                fontWeight = FontWeight.Normal,
                                fontSize = 15.sp,
                                color = Color.White,
                            )
                        }



                    }

                }
            }

        }
    }
}