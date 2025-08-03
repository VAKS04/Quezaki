package com.example.quiz.presentation.ui.authScreen.general

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.quiz.presentation.ui.general.TemplateBody
import com.example.quiz.presentation.ui.theme.Dimens

@Composable
fun AuthBody(
    modifier: Modifier,
    content: @Composable () -> Unit
){
    TemplateBody(
        modifier
            .padding(Dimens.middlePadding)
    ) {
        Column(){
            content()
            Spacer(modifier = Modifier.weight(1f))
            BottomInformationPanel(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Preview
@Composable
fun AuthBodyPreview(){
    AuthBody(
        modifier = Modifier,
    ) {

    }
}