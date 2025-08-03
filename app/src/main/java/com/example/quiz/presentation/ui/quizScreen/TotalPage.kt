package com.example.quiz.presentation.ui.quizScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.BackspaceCommand
import androidx.compose.ui.tooling.preview.Preview
import com.example.quiz.R
import com.example.quiz.presentation.ui.theme.Dimens

@Preview(showBackground = true)
@Composable
fun TotalPage(
    result:String = "0",
    onHomePage:()->Unit = {}
){
    BackHandler(enabled = true) {
        onHomePage()
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.total_message1),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = Dimens.smallPadding)
        )
        Text(
            text = stringResource(R.string.total_message2,result),
            style = MaterialTheme.typography.titleMedium
        )
    }
}