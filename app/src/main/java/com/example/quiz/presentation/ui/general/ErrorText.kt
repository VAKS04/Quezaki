package com.example.quiz.presentation.ui.general

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.quiz.presentation.ui.theme.Dimens

@Composable
fun ErrorText(message:String){
    Text(
        modifier = Modifier.padding(Dimens.smallPadding),
        style = MaterialTheme.typography.labelSmall,
        text = message,
        color = MaterialTheme.colorScheme.error
    )
}