package com.example.quiz.presentation.ui.general

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quiz.presentation.ui.theme.Shapes

@Composable
fun TemplateBody(
    modifier: Modifier = Modifier,
    content: @Composable ()-> Unit
){
    Surface(
        modifier = modifier
            .clip(
                RoundedCornerShape(
                    topStart = Shapes.templateBodyShape,
                    topEnd = Shapes.templateBodyShape
                )
            ),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        content()
    }
}

@Preview
@Composable
fun TemplateBodyPreview(){
    TemplateBody(
        modifier = Modifier.size(300.dp),
    ) {

    }
}