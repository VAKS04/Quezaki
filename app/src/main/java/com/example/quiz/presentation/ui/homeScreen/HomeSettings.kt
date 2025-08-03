package com.example.quiz.presentation.ui.homeScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.quiz.R
import com.example.quiz.presentation.models.Settings
import com.example.quiz.presentation.ui.homeScreen.general.TemplateCard
import com.example.quiz.presentation.ui.theme.Dimens

@Composable
fun HomeSettings(
    modifier: Modifier= Modifier,
    navigateTo:(String)->Unit={},
    onLogOut:()->Unit
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.middlePadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.padding(Dimens.middlePadding))
        Settings.items.forEach{
            TemplateCard(
                modifier = modifier
                    .padding(vertical = Dimens.smallPadding)
                    .clickable {
                        navigateTo(it.route)
                    },
                title = stringResource(it.title),
                painter = painterResource(it.icon))
        }
        TemplateCard(
            modifier = modifier
                .padding(vertical = Dimens.smallPadding)
                .clickable {
                    onLogOut()
                },
            title = stringResource(R.string.log_out),
            painter = painterResource(R.drawable.log_out)
        )
    }
}