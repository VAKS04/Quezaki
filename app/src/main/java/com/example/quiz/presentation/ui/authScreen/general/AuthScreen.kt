package com.example.quiz.presentation.ui.authScreen.general

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.quiz.presentation.ui.theme.Dimens


@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
){
    Column(modifier = modifier
        .background(MaterialTheme.colorScheme.primary)
    ) {
        MainTitle(modifier = Modifier.padding(vertical = Dimens.mainTitlePadding))
        content()
    }
}