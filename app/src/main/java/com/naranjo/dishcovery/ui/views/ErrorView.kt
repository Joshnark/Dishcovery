package com.naranjo.dishcovery.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun ErrorView(
    emoji: String = "(´⊙ω⊙`)！",
    title: String = "Looks like something broke! :(",
    subTitle: String = "Please try again later!"
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            text = emoji,
            style = TextStyle(
                fontSize = 35.sp,
                color = MaterialTheme.colorScheme.primary
            )
        )

        SmallSpacer()

        Text(
            text = title,
            style = TextStyle(
                fontSize = 25.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        )

        SmallSpacer()

        Text(
            text = subTitle,
            style = TextStyle(
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}