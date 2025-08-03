package com.example.quiz.presentation.ui.general

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.example.quiz.presentation.ui.theme.Shapes

@Composable
fun TemplateTextField(
    modifier : Modifier = Modifier,
    title: String,
    textValue:String,
    onValueChange: (String)->Unit,
    imeAction:ImeAction
){
    OutlinedTextField(
        value = textValue,
        onValueChange = {
            newValue -> onValueChange(newValue)
        },
        placeholder = {
            Text(text = title)
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction
        ),
        singleLine = true,
        shape = RoundedCornerShape(Shapes.textFieldShape),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.onSurface,
            cursorColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun TemplateTextFieldPreview(){
    TemplateTextField(
        modifier = Modifier.fillMaxWidth(),
        title = "",
        textValue = "",
        imeAction = ImeAction.Default,
        onValueChange = {}
    )
}
