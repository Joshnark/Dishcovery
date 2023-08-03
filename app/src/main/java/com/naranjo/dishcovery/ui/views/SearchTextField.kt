package com.naranjo.dishcovery.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.Placeholder
import com.naranjo.dishcovery.ui.theme.SMALL

@Composable
fun SearchTextField(
    text: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    onValueChanged: (String) -> Unit = {}
) {
    CustomTextField(
        text = text,
        paddingLeadingIconEnd = 10.dp,
        paddingTrailingIconStart = 10.dp,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(percent = SMALL),
            )
            .padding(
                horizontal = SMALL.dp,
                vertical = SMALL.dp
            )
            .then(modifier),
        placeholder = placeholder,
        readOnly = readOnly,
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null
            )
        },
        onValueChange = onValueChanged
    )
}