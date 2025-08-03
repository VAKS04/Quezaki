package com.example.quiz.presentation.ui.homeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.quiz.R
import com.example.quiz.presentation.ui.general.TemplateButton
import com.example.quiz.presentation.ui.general.TemplateTextField
import com.example.quiz.presentation.ui.authScreen.general.ResultSpacerOrError
import com.example.quiz.presentation.ui.theme.Dimens
import com.example.quiz.presentation.viewModels.ChangeUsernameViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChangeUsernamePage(
    modifier: Modifier = Modifier,
    viewModel: ChangeUsernameViewModel = koinViewModel(),
    onSettings:()->Unit
) {
    val username by viewModel.username.collectAsState()
    val isButtonEnabled by viewModel.isButtonEnabled.collectAsState()
    val validationException by viewModel.validateExceptionMessage.collectAsState()
    val result by viewModel.changeUsernameResult.collectAsState()

    val showAlert by viewModel.showAlert.collectAsState()
    Column(
        modifier = modifier.fillMaxSize().padding(horizontal = Dimens.middlePadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TemplateTextField(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.change_username),
            textValue = username,
            onValueChange = {newValue->
                viewModel.updateUsername(newValue)
            },
            imeAction = ImeAction.Default
        )
        ResultSpacerOrError(validationException)
        TemplateButton(
            content = {
                Text("Change")
            },
            onClick = {
                viewModel.saveChanges()
            },
            enabled = isButtonEnabled
        )
    }
    if (showAlert){
        when{
            result.isSuccess->{
                AlertDialog(
                    onDismissRequest = {
                        viewModel.updateShowAlertState()
                    },
                    title = {
                        Text(stringResource(R.string.app_name))
                    },
                    text = {
                        Text(stringResource(R.string.username_changed))
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                viewModel.updateShowAlertState()
                                onSettings()
                            }
                        ) { Text(stringResource(R.string.ok)) }
                    }
                )
            }
            result.isFailure->{
                AlertDialog(
                    onDismissRequest = {
                        viewModel.updateShowAlertState()
                    },
                    title = {
                        Text(stringResource(R.string.exception))
                    },
                    text = {
                        Text(result.exceptionOrNull()?.message?: stringResource(R.string.error))
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                viewModel.updateShowAlertState()
                                onSettings()
                            }
                        ) { Text(stringResource(R.string.ok)) }
                    }
                )
            }
        }
    }
}