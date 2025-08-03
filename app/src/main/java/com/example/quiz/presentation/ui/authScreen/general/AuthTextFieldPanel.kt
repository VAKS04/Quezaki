package com.example.quiz.presentation.ui.authScreen.general

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import com.example.quiz.presentation.ui.general.TemplateTextField
import com.example.quiz.presentation.ui.theme.Dimens

@Composable
fun AuthTextFieldPanel(
    modifier: Modifier = Modifier,
    title:String,
    textValue: String,
    onValueChange: (String)-> Unit,
    imeAction: ImeAction
){
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title,
            modifier = Modifier.padding(bottom = Dimens.smallPadding))

        TemplateTextField(
            modifier = Modifier.fillMaxWidth(),
            title = title,
            textValue = textValue,
            imeAction = imeAction,
            onValueChange = {onValueChange(it)}
        )
    }
}