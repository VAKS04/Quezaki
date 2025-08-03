package com.example.quiz.presentation.ui.general

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TemplateHead(
    modifier: Modifier,
    content : @Composable () -> Unit
){
    Row(modifier = modifier) {
        content()
    }
}