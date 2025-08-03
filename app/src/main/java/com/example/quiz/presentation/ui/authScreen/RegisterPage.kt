package com.example.quiz.presentation.ui.authScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.quiz.R
import com.example.quiz.presentation.models.authScreenModels.registerPageModels.RegisterPageUiState
import com.example.quiz.presentation.ui.general.TemplateBody
import com.example.quiz.presentation.ui.general.TemplateButton
import com.example.quiz.presentation.ui.authScreen.general.AuthTextFieldPanel
import com.example.quiz.presentation.ui.authScreen.general.BottomInformationPanel
import com.example.quiz.presentation.ui.authScreen.general.ResultSpacerOrError
import com.example.quiz.presentation.ui.theme.Dimens
import com.example.quiz.presentation.viewModels.RegisterViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun RegisterPage(
    onLoginClick: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel()
){
    RegisterBody(
        modifier=Modifier.padding(Dimens.middlePadding),
        viewModel = viewModel,
        onLoginClick = onLoginClick,
    )
}

@Composable
fun RegisterBody(
    modifier: Modifier,
    viewModel: RegisterViewModel,
    onLoginClick:()->Unit,
){
    val registerPageUiState by viewModel.registerPageUiState.collectAsState()
    val uiState by viewModel.registerDataUiState.collectAsState()

    val usernameValidation by viewModel.usernameValidation.collectAsState()
    val emailValidation by viewModel.emailValidation.collectAsState()
    val passwordValidation by viewModel.passwordValidation.collectAsState()

    TemplateBody(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = modifier.fillMaxSize()) {
            Text(
                text = stringResource(R.string.registration),
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.height(Dimens.middleSpacer))
            AuthTextFieldPanel(
                title = stringResource(R.string.username),
                textValue = uiState.username,
                imeAction = ImeAction.Next,
                onValueChange = {viewModel.updateUsername(it)}
            )
            ResultSpacerOrError(usernameValidation)
            AuthTextFieldPanel(
                title = stringResource(R.string.email),
                textValue = uiState.email,
                imeAction = ImeAction.Next,
                onValueChange = {viewModel.updateEmail(it)}
            )

            ResultSpacerOrError(emailValidation)

            AuthTextFieldPanel(
                title = stringResource(R.string.password),
                textValue = uiState.password,
                imeAction = ImeAction.Default,
                onValueChange = {viewModel.updatePassword(it)}
            )
            ResultSpacerOrError(passwordValidation)
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                TemplateButton(
                    modifier = Modifier,
                    onClick = {
                        viewModel.registerUser()
                              },
                    content = {
                        Text(
                            text = stringResource(R.string.registration)
                        )
                    },
                    enabled = uiState.isButtonEnabled
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth().padding(Dimens.smallPadding)
            ) {
                Text(
                    text = stringResource(R.string.have_account),
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.sign_in),
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.clickable {
                        onLoginClick()
                    }
                )
            }
            Spacer(modifier = Modifier.weight(1f).heightIn(min = Dimens.middleSpacer))
            BottomInformationPanel(modifier = Modifier.fillMaxWidth())
        }
        when(val state = registerPageUiState){
            is RegisterPageUiState.Idle ->{
                Unit
            }
            is RegisterPageUiState.UserExist ->{
                AlertDialog(
                    onDismissRequest = {
                        viewModel.updateToIdle()
                    },
                    title = { Text(text = stringResource(R.string.app_name)) },
                    text = { Text(text = stringResource(R.string.user_exist)) },
                    confirmButton = {
                        Button(onClick = {
                            viewModel.updateToIdle()
                        }) { Text(stringResource(R.string.ok)) }
                    }
                )
            }
            is RegisterPageUiState.Error ->{
                AlertDialog(
                    onDismissRequest = {
                        viewModel.updateToIdle()
                    },
                    title = { Text(text = stringResource(R.string.unknown_error)) },
                    text = { Text(state.message) },
                    confirmButton = {
                        Button(onClick = { viewModel.updateToIdle()}
                        ) { Text(stringResource(R.string.ok)) }
                    }
                )
            }
            is RegisterPageUiState.Success ->{
                onLoginClick()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPagePreview(){
    RegisterPage(
        onLoginClick = {},
    )
}