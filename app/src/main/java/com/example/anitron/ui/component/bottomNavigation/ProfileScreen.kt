package com.example.anitron.ui.component.bottomNavigation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.anitron.R
import com.example.anitron.data.datasource.ProfileState
import com.example.anitron.ui.theme.fonts
import com.example.anitron.ui.viewmodel.HomeViewModel

@Composable
fun ProfileScreen(
    viewModel: HomeViewModel
) {
    val backgroundColor = Color.Transparent
    val profileState by viewModel.profileState
    Scaffold(
        backgroundColor = backgroundColor,
        topBar = {
            when (profileState) {
                ProfileState.Edit -> {
                    EditProfileTopBar(backgroundColor, viewModel)
                }
                else -> {}
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .wrapContentHeight()
                    .padding(padding)
            ) {
                Spacer(modifier = Modifier.height(130.dp))
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorResource(R.color.blue_g)),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .padding(top = 10.dp, start = 10.dp)
                                .background(Color.Transparent, CircleShape),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .width(100.dp)
                                    .clip(CircleShape),
                                model = "https://files.worldwildlife.org/wwfcmsprod/images/Sea_Turtle_Hol_Chan_Marine_Reserve_WW1105958/hero_full/p35wuxwr3_Sea_Turtle_Hol_Chan_Marine_Reserve_WW1105958.jpg",
                                contentDescription = "turtle",
                                contentScale = ContentScale.Crop
                            )
                        }
                        Text(
                            modifier = Modifier
                                .padding(start = 60.dp, top = 15.dp)
                                .weight(1f),
                            text = "Placeholder Username",
                            fontFamily = fonts,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                            color = Color.White,
                        )
                    }

                    Row(
                        modifier = Modifier.padding(start = 10.dp, bottom = 35.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            modifier = Modifier
                                .weight(0.7f),
                            text = "Member since DD/MM/YY",
                            fontFamily = fonts,
                            fontWeight = FontWeight.Normal,
                            fontSize = 13.sp,
                            color = Color.White,
                        )
                        when (profileState){
                            ProfileState.Default -> {
                                TextButton(
                                onClick = {
                                      viewModel.profileStateChange(ProfileState.Edit)
                                },
                                modifier = Modifier
                                    .weight(0.5f)
                                    .padding(horizontal = 10.dp)
                                    .background(
                                        shape = RoundedCornerShape(10.dp),
                                        color = colorResource(R.color.dark_blue_grey)
                                    ),
                            ){
                                Text(
                                    text = "Edit Profile",
                                    fontFamily = fonts,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 13.sp,
                                    color = Color.White
                                )
                            }
                            }
                            else -> {}
                        }
                    }

                    Text(
                        modifier = Modifier
                            .padding(start = 10.dp, bottom = 25.dp),
                        text = "Placeholder Description",
                        fontFamily = fonts,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        color = Color.White,
                    )

                    Row(
                        modifier = Modifier
                            .padding(start = 15.dp, bottom = 10.dp,),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        modifier = Modifier
                            .weight(0.7f),
                        text = "Top Shows",
                        fontFamily = fonts,
                        fontWeight = FontWeight.Normal,
                        fontSize = 25.sp,
                        color = Color.White,)
                        when (profileState){
                            ProfileState.Edit -> {
                                TextButton(
                                    modifier = Modifier
                                        .weight(0.3f),
                                    onClick = { },
                                ) {
                                    Text(
                                        text = "Edit shows",
                                        fontFamily = fonts,
                                        fontWeight = FontWeight.SemiBold,
                                        textDecoration = TextDecoration.Underline,
                                        fontSize = 12.sp,
                                        color = Color.White,
                                    )
                                }
                            }
                            else -> {}
                        }
                    }
                    LazyRow(
                        content = {
                            items(listOf("a", "b", "c", "d", "e")) {
                                MediaProfileItem(profileState = profileState)
                            }
                        })

                    Row(
                        modifier = Modifier
                            .padding(start = 15.dp, top = 10.dp, bottom = 10.dp,),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        modifier = Modifier
                            .weight(0.7f),
                        text = "Top Movies",
                        fontFamily = fonts,
                        fontWeight = FontWeight.Normal,
                        fontSize = 25.sp,
                        color = Color.White,)
                        when (profileState) {
                            ProfileState.Edit -> {
                                TextButton(
                                    modifier = Modifier
                                        .weight(0.3f),
                                    onClick = { },
                                ) {
                                    Text(
                                        text = "Edit movies",
                                        fontFamily = fonts,
                                        fontWeight = FontWeight.SemiBold,
                                        textDecoration = TextDecoration.Underline,
                                        fontSize = 12.sp,
                                        color = Color.White,
                                    )
                                }
                            }
                            else -> {}
                        }
                    }
                    LazyRow(
                        content = {
                            items(listOf("a", "b", "c", "d", "e")) {
                                MediaProfileItem(profileState = profileState)
                            }
                        })
                    Spacer(Modifier.padding(40.dp))
                }
            }
        }
    )
    }

@Composable
fun MediaProfileItem(image: String = "", profileState: ProfileState) {
    when (profileState) {
        ProfileState.Edit -> {
            Card(
                Modifier
                    .width(110.dp)
                    .height(160.dp)
                    .clickable {},
                elevation = 1.dp,
                backgroundColor = Color.Transparent,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {

                    if (image == "") {
                        Icon(Icons.Default.Add, contentDescription = "", tint = Color.LightGray)
                        Text(
                            "Tap to add",
                            fontFamily = fonts,
                            fontWeight = FontWeight.Normal,
                            fontSize = 10.sp,
                            color = Color.LightGray,
                        )
                    }
                }
            }
        }
        else -> {}
    }
}

@Composable
fun EditProfileTopBar(
    color: Color,
    viewModel: HomeViewModel
){
    Column {
        TopAppBar(
            elevation = 0.dp,
            backgroundColor = color,

            title = {
                IconButton(
                    modifier = Modifier,
                    onClick = {
                        viewModel.profileStateChange(ProfileState.Default)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.White
                    )
                }
            },
            actions = {
                IconButton(
                    modifier = Modifier,
                    onClick = {
                        viewModel.profileStateChange(ProfileState.Default)
                    }
                ) {
                    Text(
                        "Save",
                        fontFamily = fonts,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        color = Color.White,
                    )
                }
                Spacer(Modifier.padding(8.dp))
            }
        )
    }

}