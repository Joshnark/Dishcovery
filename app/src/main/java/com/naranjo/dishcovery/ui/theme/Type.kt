package com.naranjo.dishcovery.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    headlineLarge = TextStyle(
        color = Color.White,
        fontSize = 45.sp
    ),
    headlineMedium = TextStyle(
        color = Color.White,
        fontSize = 25.sp
    ),
    headlineSmall = TextStyle(
        color = Color.White,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
    ),
    titleLarge =  TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold
    ),
    titleMedium = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold
    ),
    titleSmall = TextStyle(
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
)