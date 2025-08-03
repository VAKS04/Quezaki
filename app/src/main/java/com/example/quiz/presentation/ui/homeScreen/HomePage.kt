package com.example.quiz.presentation.ui.homeScreen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.example.quiz.R
import com.example.quiz.data.models.QuizCategoryDTO
import com.example.quiz.presentation.models.homeScreenModels.homePageModels.QuizCategoryUiState
import com.example.quiz.presentation.ui.general.ErrorPage
import com.example.quiz.presentation.ui.general.LoadingPage
import com.example.quiz.presentation.ui.general.NetworkErrorPage
import com.example.quiz.presentation.ui.general.ServerErrorPage
import com.example.quiz.presentation.ui.general.TemplateBody
import com.example.quiz.presentation.ui.general.TemplateButton
import com.example.quiz.presentation.ui.general.TemplateTextField
import com.example.quiz.presentation.ui.homeScreen.general.TemplateCard
import com.example.quiz.presentation.ui.theme.Dimens
import com.example.quiz.presentation.ui.theme.Shapes
import com.example.quiz.presentation.viewModels.HomeViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(),
    viewModel: HomeViewModel = koinViewModel(),
    onQuizSelectLevel: (Int?,String?) -> Unit,
){
    val categories by viewModel.quizCategoryUiState.collectAsState()
    val panelField by viewModel.quizPanelField.collectAsState()
    val username by viewModel.username.collectAsState()

    when(val state = categories){
        is QuizCategoryUiState.Loading->{
            LoadingPage()
        }
        is QuizCategoryUiState.Error -> {
            ErrorPage(text = stringResource(R.string.unknown_error))
        }
        is QuizCategoryUiState.NetworkError -> {
            NetworkErrorPage {
                viewModel.refresh()
            }
        }
        is QuizCategoryUiState.ServerError ->{
            ServerErrorPage()
        }
        is QuizCategoryUiState.Success ->{
            val items = state.items
            Column(
                modifier = modifier
                    .verticalScroll(scrollState)
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                HomeHead(
                    modifier = Modifier.padding(Dimens.middlePadding),
                    textValue = panelField,
                    username = username,
                    onValueChange = {viewModel.changeValue(it)},
                    onChangeList = {viewModel.updateQuizCategoryUiState()}
                )
                HomeBody(
                    modifier = Modifier.padding(Dimens.middlePadding),
                    items = items,
                    onQuizSelectLevel = onQuizSelectLevel
                )
            }
        }
    }
}

@Composable
fun UsernamePanel(
    modifier: Modifier = Modifier,
    username: String
){
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column{
            Text(
                text = stringResource(R.string.welcome, stringResource(R.string.app_name)),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = username,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary)
        }
        Spacer(modifier=Modifier.weight(1f))
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.avatar_male),
            contentDescription = stringResource(R.string.avatar),
            tint = Color.Unspecified,
            modifier = Modifier
                .size(Dimens.avatarSize)
                .clip(RoundedCornerShape(Shapes.avatarShape)))
    }
}

@Preview(showBackground = true)
@Composable
fun UsernamePanelPreview(){
    UsernamePanel(
        modifier = Modifier.padding(Dimens.middlePadding),
        username = ""
    )
}

@Composable
fun SearchQuizPanel(
    modifier: Modifier = Modifier,
    textValue:String,
    onValueChange:(String)->Unit,
    onChangeList: () -> Unit
){
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = RoundedCornerShape(Shapes.panelShape)
    ) {
        Column(modifier = Modifier.padding(Dimens.searchFieldPadding)) {
            Text(
                text = stringResource(R.string.find_favorite_quiz),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(bottom = Dimens.smallPadding)
            )
            Row(
                modifier= Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TemplateTextField(
                    modifier = Modifier.weight(10f),
                    title = stringResource(R.string.field_quiz_title),
                    textValue = textValue,
                    imeAction = ImeAction.Default,
                    onValueChange = onValueChange
                )
                Spacer(modifier = Modifier.weight(1f))
                TemplateButton(
                    modifier = Modifier,
                    enabled = true,
                    onClick = {
                        onChangeList()
                    },
                    content = {
                        Text(
                            text = stringResource(R.string.search),
                            style = MaterialTheme.typography.labelSmall)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchQuizPanelPreview(){
    SearchQuizPanel(
        modifier = Modifier,
        textValue = "",
        onValueChange = {},
        onChangeList = {}
    )
}

@Composable
fun HomeHead(
    modifier: Modifier = Modifier,
    textValue: String,
    username: String,
    onValueChange: (String) -> Unit,
    onChangeList:()->Unit
){
    Column(
        modifier = modifier,
    ) {
        UsernamePanel(username = username)
        Spacer(modifier = Modifier.padding(Dimens.largeSpacer))
        SearchQuizPanel(
            textValue = textValue,
            onValueChange = onValueChange,
            onChangeList = onChangeList
        )
    }
}

@Composable
fun HomeBody(
    modifier: Modifier = Modifier,
    onQuizSelectLevel:(Int?,String?)->Unit,
    items:List<QuizCategoryDTO>
){
    TemplateBody {
        Column(modifier = modifier) {
            Text(
                text = stringResource(R.string.all_quizzes),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            Spacer(modifier = Modifier.padding(Dimens.smallSpacer))

            TemplateCard(
                modifier = Modifier
                    .padding(vertical = Dimens.smallPadding)
                    .clickable {
                        onQuizSelectLevel(0,null)
                    },
                painter = painterResource(R.drawable.ic_action_name),
                title = stringResource(R.string.custom_quiz),
            )
            items.forEach{item->
                TemplateCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Dimens.smallPadding)
                        .clickable {
                            onQuizSelectLevel(item.id,item.name)
                        },
                    painter = painterResource(R.drawable.ic_action_name),
                    title = item.name
                )
            }
        }
    }
}
