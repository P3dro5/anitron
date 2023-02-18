package com.example.anitron.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.anitron.R
import com.example.anitron.data.datasource.State
import com.example.anitron.data.datasource.info.personInfo.MediaAppearances
import com.example.anitron.data.datasource.info.personInfo.PersonInfo
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
                        personInfo.value.let { PersonInfoScreen(personInfo = it.personInfo!!, movieCredits = it.movieCredits!!, tvShowCredits = it.tvShowCredits!!)
                        }
                    }
                    else -> {}
                }
            }

    }

    @Composable
    private fun PersonInfoScreen(personInfo : PersonInfo, movieCredits : MediaAppearances, tvShowCredits : MediaAppearances){
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
                                modifier = Modifier
                                    .padding(5.dp)
                                    .weight(1f),
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
                        Spacer( modifier = Modifier.padding(25.dp) )
                        MediaCredits(movieCredits, "Movies")
                        Spacer( modifier = Modifier.padding(10.dp) )
                        MediaCredits(tvShowCredits,"Tv Shows")
                    }

                }
            }

        }
    }

    @Composable
    private fun MediaCredits(mediaAppearances: MediaAppearances, title: String){
        if(mediaAppearances.cast.isNotEmpty()) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(
                    modifier = Modifier.padding(10.dp)
                )
                Text(
                    title,
                    fontFamily = fonts,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(
                modifier = Modifier.padding(10.dp)
            )
            LazyRow {
                itemsIndexed(mediaAppearances.cast) { _, cast ->
                    Card(
                        modifier = Modifier.width(110.dp),
                        backgroundColor = Color.Transparent,
                        elevation = 1.dp,
                        content = {
                            val context = LocalContext.current
                            Column(
                                modifier = Modifier.height(200.dp).clickable {
                                    val intent =
                                        Intent(context, InfoActivity::class.java)
                                    intent.putExtra("id", cast.id)
                                    if(title == "Movies") intent.putExtra("isMovie", true)
                                    else intent.putExtra("isMovie", false)
                                    context.startActivity(intent)
                                },
                                horizontalAlignment = Alignment.CenterHorizontally,
                                content = {
                                    if(cast.posterPath != null) {
                                        AsyncImage(
                                            modifier = Modifier.height(160.dp).fillMaxWidth(),
                                            contentScale = ContentScale.FillBounds,
                                            alignment = Alignment.TopCenter,
                                            model = "https://image.tmdb.org/t/p/w300" + cast.posterPath,
                                            contentDescription = ""
                                        )
                                    } else Image(modifier = Modifier.height(160.dp), alignment = Alignment.Center, painter = painterResource(R.drawable.ic_baseline_question_mark_24),contentDescription = "")

                                    Text(
                                        modifier = Modifier.width(100.dp),
                                        text = cast.character!!,
                                        textAlign = TextAlign.Center,
                                        fontFamily = fonts,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 15.sp,
                                        color = Color.White,
                                    )

                                }
                            )
                        }
                    )
                }
            }
        }
    }


}