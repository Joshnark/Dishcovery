package com.naranjo.dishcovery.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.naranjo.dishcovery.R
import com.naranjo.dishcovery.ui.views.LargeSpacer
import com.naranjo.dishcovery.ui.views.GradientBox

private const val BOTTOM_SEPARATION = 50

@Composable
fun SplashScreen() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.img_splash),
                modifier = Modifier.fillMaxSize(),
                contentDescription = "Splash image",
                contentScale = ContentScale.Crop
            )

            GradientBox()

            Header()

            Footer()
        }
    }
}

@Composable
private fun Header() {
    val offset = Offset(.0f, 5.0f)

    val labelStyle = TextStyle(
        color = Color.White,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        shadow = Shadow(
            color = Color.Black, offset = offset, blurRadius = 2f
        )
    )

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(20.dp))

        Text(
            text = buildAnnotatedString {
                append("Now with ")
                withStyle(
                    SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        shadow = Shadow(
                            color = Color.Black, offset = offset, blurRadius = 2f
                        )
                    )
                ) {
                    append("+100")
                }
                append(" Recipes!")
            },
            style = labelStyle
        )
    }
}

@Composable
private fun Footer() {
    val titleStyle = TextStyle(
        color = Color.White,
        fontSize = 45.sp
    )

    val captionsStyle = TextStyle(
        color = Color.White,
        fontSize = 25.sp
    )

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Dishcovery",
            style = titleStyle
        )

        LargeSpacer()

        Text(
            text = "Make your own food",
            style = captionsStyle
        )

        Text(
            text = "stay at home",
            style = captionsStyle
        )

        LargeSpacer()

        StartButton()

        Spacer(modifier = Modifier.size(BOTTOM_SEPARATION.dp))

    }
}

@Composable
private fun StartButton(viewModel: SplashViewModel = viewModel()) {
    Button(
        onClick = {
            viewModel.navigate(SplashFragmentDirections.actionSplashFragmentToMainFragment())
        }
    ) {
        Text(text = "Start cooking!")
    }
}

@Preview
@Composable
private fun PreviewSplashScreen() {
    SplashScreen()
}