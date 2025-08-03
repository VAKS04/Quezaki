package com.example.quiz.presentation.ui.general

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.quiz.R
import com.example.quiz.presentation.ui.theme.Shapes

@Composable
fun TemplateButton(
    modifier: Modifier = Modifier,
    content: @Composable ()->Unit,
    onClick:()->Unit,
    enabled:Boolean = false
){
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        ),
        enabled = enabled,
        shape = RoundedCornerShape(Shapes.buttonShape),
        modifier = modifier
    ) {
        content()
    }
}


@Preview(showBackground = true)
@Composable
fun TemplateButtonPreview(){
    TemplateButton(
        modifier = Modifier,
        onClick = {},
        content = {
            Text(text = stringResource(R.string.template))
        }
    )
}