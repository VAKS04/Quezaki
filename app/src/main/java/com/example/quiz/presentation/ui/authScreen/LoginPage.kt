package com.example.quiz.presentation.ui.authScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.quiz.presentation.models.authScreenModels.loginPageModels.LoginPageUiState
import com.example.quiz.presentation.ui.general.TemplateBody
import com.example.quiz.presentation.ui.general.TemplateButton
import com.example.quiz.presentation.ui.authScreen.general.AuthTextFieldPanel
import com.example.quiz.presentation.ui.authScreen.general.BottomInformationPanel
import com.example.quiz.presentation.ui.authScreen.general.ResultSpacerOrError
import com.example.quiz.presentation.ui.general.ErrorPage
import com.example.quiz.presentation.ui.theme.Dimens
import com.example.quiz.presentation.viewModels.LoginViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun LoginPage(
    modifier: Modifier = Modifier,
    onRegisterClick: ()-> Unit,
    onHomeClick: ()-> Unit,
    viewModel: LoginViewModel = koinViewModel()
){
    LoginBody(
        modifier = modifier
            .padding(Dimens.middlePadding),
        onRegisterClick = onRegisterClick,
        viewModel = viewModel,
        onHomeClick = onHomeClick
    )
}

@Composable
fun LoginBody(
    modifier: Modifier = Modifier,
    onRegisterClick: () -> Unit,
    onHomeClick: () -> Unit,
    viewModel: LoginViewModel
) {
    val loginPageUiState by viewModel.loginPageUiState.collectAsState()
    val uiState by viewModel.loginDataUiState.collectAsState()

    val emailValidation by viewModel.emailValidation.collectAsState()
    val passwordValidation by viewModel.passwordValidation.collectAsState()

    TemplateBody{
        Column(
            modifier = modifier
        ) {
            Text(
                text = stringResource(R.string.sign_in),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(Dimens.middleSpacer))
            AuthTextFieldPanel(
                title = stringResource(R.string.email),
                textValue = uiState.email,
                imeAction = ImeAction.Next,
                onValueChange = {
                    newValue -> viewModel.updateEmail(newValue)
                }
            )
            ResultSpacerOrError(emailValidation)
            AuthTextFieldPanel(
                title = stringResource(R.string.password),
                textValue = uiState.password,
                imeAction = ImeAction.Default,
                onValueChange = {
                    newValue -> viewModel.updatePassword(newValue)
                }
            )
            ResultSpacerOrError(passwordValidation, spacing = Dimens.smallPadding)
            Text(
                text = stringResource(R.string.forgot_password),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Dimens.smallPadding),
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.End
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                TemplateButton(
                    onClick = {
                        viewModel.loginUser()
                    },
                    content = {
                        Text(text = stringResource(R.string.sign_in))
                    },
                    enabled = uiState.isButtonEnabled
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.smallPadding)
            ) {
                Text(
                    text = stringResource(R.string.not_have_account),
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.registration),
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.clickable {
                        onRegisterClick()
                    }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            BottomInformationPanel(modifier = Modifier.fillMaxWidth())

            when (val state = loginPageUiState){
                is LoginPageUiState.Idle->{
                    Unit
                }
                is LoginPageUiState.UserNotFound->{
                    AlertDialog(
                        onDismissRequest = {
                            viewModel.updateToIdle()
                        },
                        title = { Text(stringResource(R.string.app_name)) },
                        text = { Text(text = stringResource(R.string.user_not_found)) },
                        confirmButton = {
                            Button(onClick = {
                                viewModel.updateToIdle()}
                            ) { Text(stringResource(R.string.ok)) }
                        }
                    )
                }
                is LoginPageUiState.Error ->{
                    ErrorPage(text = state.message)
                }
                is LoginPageUiState.Success->{
                    onHomeClick()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginPagePreview(){
    LoginPage(
        onRegisterClick = {},
        onHomeClick = {},
    )
}