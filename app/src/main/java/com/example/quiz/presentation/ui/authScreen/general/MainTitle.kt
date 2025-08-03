package com.example.quiz.presentation.ui.authScreen.general

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quiz.R

@Composable
fun MainTitle(
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.quizaki_icon),
            contentDescription = ""
        )
    }
}


@Preview(showBackground = true)
@Composable
fun MainTitlePreview(){
    MainTitle(modifier = Modifier.fillMaxWidth().height(300.dp))
}
