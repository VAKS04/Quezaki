package com.example.quiz.presentation.ui.general

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun TemplatePage(
    headContent: @Composable () -> Unit,
    bodyContent: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TemplateHead(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                headContent()
            }

            TemplateBody(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                bodyContent()
            }
        }
    }
}


@Preview
@Composable
fun TemplatePagePreview(){
    TemplatePage(
//        headHeight = 300.dp,
        headContent = {},
        bodyContent = {}
    )
}