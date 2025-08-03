package com.example.quiz.presentation.ui.authScreen.general

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.example.quiz.presentation.ui.general.ErrorText
import com.example.quiz.presentation.ui.theme.Dimens

@Composable
fun ResultSpacerOrError(
    result: Result<Unit>,
    spacing: Dp = Dimens.middleSpacer
){
    when{
        result.isSuccess ->{
            Spacer(modifier = Modifier.height(spacing))
        }
        result.isFailure ->{
            ErrorText(message = result.exceptionOrNull()?.message?:"")
        }
    }
}