package com.example.quiz.presentation.ui.authScreen.general

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quiz.R
import com.example.quiz.presentation.ui.theme.Dimens

@Composable
fun BottomInformationPanel(
    modifier: Modifier
){
    Column(modifier = modifier) {
        Box(
            modifier = modifier.height(1.dp).background(MaterialTheme.colorScheme.onSurface)
        )
        Spacer(modifier = Modifier.padding(Dimens.smallSpacer/2))
        Row(
            modifier = modifier.padding(bottom = Dimens.smallPadding),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.google_icon),
                contentDescription = stringResource(R.string.google),
                tint =Color.Unspecified
            )
            Text(
                modifier = Modifier.padding(start = Dimens.smallPadding),
                text = stringResource(R.string.google)
            )
        }
        Text(
            text = stringResource(R.string.account_accept),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = stringResource(R.string.privacy_policy),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomInformationPanelPreview(){
    BottomInformationPanel(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.largePadding))
}