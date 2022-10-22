package com.example.anitron.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.TextStyle
import com.example.anitron.R
import androidx.compose.material.Typography
import androidx.compose.ui.unit.sp

val fonts = FontFamily(
    Font(R.font.makio),
    Font(R.font.makiobold, weight = FontWeight.Bold),
    Font(R.font.makiobolditalic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.makioitalic, style = FontStyle.Italic),
    Font(R.font.makiolight),
    Font(R.font.makiolightitalic, style = FontStyle.Italic)
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp
    ),
    h2 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Thin,
        fontSize = 16.sp
    ),
    h3 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Thin,
        fontSize = 16.sp
    )




)