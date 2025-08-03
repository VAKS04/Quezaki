package com.example.quiz.presentation.ui.general

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.quiz.R
import com.example.quiz.presentation.ui.theme.Dimens

@Preview
@Composable
fun NetworkErrorPage(
    modifier:Modifier = Modifier,
    onRefresh:()->Unit={}
){
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier.size(Dimens.errorIconSize),
            painter = painterResource(R.drawable.network_error),
            contentDescription = stringResource(R.string.network_error)
        )
        Text(text = stringResource(R.string.network_error))
        Spacer(Modifier.padding(Dimens.smallPadding))
        TemplateButton(
            onClick = {
                onRefresh()
            },
            enabled = true,
            content = {
                Text(text = stringResource(R.string.refresh))
            }
        )
    }
}