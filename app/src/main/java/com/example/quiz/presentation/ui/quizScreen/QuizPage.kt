package com.example.quiz.presentation.ui.quizScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quiz.R
import com.example.quiz.presentation.models.quizScreenModels.quizPageModels.QuizUiState
import com.example.quiz.presentation.ui.general.ErrorPage
import com.example.quiz.presentation.ui.general.LoadingPage
import com.example.quiz.presentation.ui.general.NetworkErrorPage
import com.example.quiz.presentation.ui.general.ServerErrorPage
import com.example.quiz.presentation.ui.general.TemplateButton
import com.example.quiz.presentation.ui.theme.Dimens
import com.example.quiz.presentation.ui.theme.Shapes
import com.example.quiz.presentation.viewModels.QuizViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun QuizPage(
    viewModel: QuizViewModel = koinViewModel(),
    onTotal:(Int)->Unit
){
    val quizUiState by viewModel.quizUiState.collectAsState()
    val currentQuestion by viewModel.currentQuestion.collectAsState()
    val currentQuestionIndex by viewModel.currentQuestionIndex.collectAsState()

    when (quizUiState){
        is QuizUiState.Loading -> {
            LoadingPage()
        }
        is QuizUiState.Error ->{
            ErrorPage(
                text = stringResource(R.string.unknown_error)
            )
        }
        is QuizUiState.NetworkError->{
            NetworkErrorPage{
                viewModel.refresh()
            }
        }
        is QuizUiState.ErrorResponseCode->{
            ErrorPage(
                text = stringResource(R.string.change_quiz)
            )
        }
        is QuizUiState.ServerError->{
            ServerErrorPage()
        }
        is QuizUiState.Success ->{
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                QuizHead(
                    modifier = Modifier.padding(Dimens.smallPadding),
                    question = currentQuestion?.question?:"")
                QuizBody(
                    modifier = Modifier.weight(1f).padding(horizontal = Dimens.middlePadding),
                    options = currentQuestion?.answers?: emptyList(),
                    level = currentQuestion?.difficulty?:"",
                    updateSelectedItem = {
                        viewModel.updateSelectItem(it)
                        viewModel.updateButtonState()
                                         },
                    showAnswer = currentQuestion?.showAnswer?:false,
                    amount = currentQuestionIndex + 1,
                    selectedItem = currentQuestion?.selectedItem,
                    correctAnswer = currentQuestion?.correctAnswer?:""
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (currentQuestion?.showAnswer == false){
                        TemplateButton(
                            modifier = Modifier.padding(vertical = Dimens.middlePadding),
                            enabled = currentQuestion?.isActiveButton?:false,
                            onClick = {
                                viewModel.updateShowAnswer(true)
                                viewModel.updateCountCorrectAnswer(
                                    isCorrect = (
                                            currentQuestion?.selectedItem == currentQuestion?.correctAnswer
                                            )
                                )

                            },
                            content = {
                                Text(
                                    text = stringResource(R.string.check_answer)
                                )
                            }
                        )
                    }else{
                        TemplateButton(
                            modifier = Modifier.padding(vertical = Dimens.middlePadding),
                            enabled = true,
                            onClick = {
                                if((currentQuestionIndex + 1) == viewModel.amount?.toInt()){
                                    onTotal(viewModel.countCorrectAnswer.value)
                                }
                                else{
                                    viewModel.moveToNextQuestion()
                                }
                            },
                            content = {
                                Text(
                                    text = stringResource(R.string.next)
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuizHead(
    modifier: Modifier = Modifier,
    question: String
){
    QuizBox(
        modifier = modifier,
        isSelectedItem = false,
        contentAlignment = Alignment.Center
    ){
        Text(
            modifier = Modifier
                .padding(Dimens.middlePadding),
            style = MaterialTheme.typography.titleLarge,
            text = question,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TestHeadPreview(){
    QuizHead(
        modifier = Modifier.fillMaxWidth().height(150.dp),
        question = "")
}

@Composable
fun QuizBody(
    modifier: Modifier = Modifier,
    updateSelectedItem:(String)->Unit,
    selectedItem: String?,
    showAnswer:Boolean,
    level: String,
    correctAnswer:String,
    amount: Int,
    options: List<String>
){
    Column(modifier = Modifier.fillMaxWidth()) {
        ShortInfoRow(
            level = level,
            amount = amount
        )
        Text(
            text = stringResource(R.string.select_correct_option),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(
                start = Dimens.middlePadding,
                top = Dimens.smallPadding,
                end = Dimens.middlePadding
            )
        )
    }
    AnswerOptions(
        modifier = modifier,
        options = options,
        selectedItem = selectedItem,
        showAnswer = showAnswer,
        updateSelectedItem = updateSelectedItem,
        correctAnswer = correctAnswer)
}

@Composable
fun ShortInfoRow(
    modifier: Modifier = Modifier,
    level: String,
    amount: Int
){
    Row(modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween) {
        ShowInfoItem(text = stringResource(R.string.question_number,amount))
        ShowInfoItem(text = level)
    }
}

@Composable
fun ShowInfoItem(text:String){
    Text(
        modifier = Modifier
            .padding(
                horizontal = Dimens.middlePadding,
                vertical = Dimens.smallPadding),
        color = MaterialTheme.colorScheme.onPrimary,
        text = text
    )
}

@Composable
fun QuizBox(
    modifier: Modifier,
    contentAlignment: Alignment,
    isSelectedItem:Boolean,
    showCorrectAnswer:Boolean = false,
    isCorrectAnswer: Boolean = false,
    content: @Composable ()-> Unit
){
    val transparency:Float = when(isSelectedItem){
        true -> 0.5f
        false -> 0.05f
    }
    val color = if (showCorrectAnswer && isCorrectAnswer){
        Color.Green
    }else if (showCorrectAnswer && !isCorrectAnswer){
        MaterialTheme.colorScheme.error
    }else{
        Color.White.copy(alpha = transparency)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Shapes.panelShape))
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(Shapes.panelShape))
            .background(
                color),
        contentAlignment = contentAlignment
    ) {
        content()
    }
}

@Composable
fun AnswerOptions(
    modifier: Modifier,
    options:List<String>,
    selectedItem:String?,
    showAnswer: Boolean,
    updateSelectedItem: (String) -> Unit,
    correctAnswer:String
){
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        items(options){item->
            QuizBox(
                modifier = Modifier.clickable {
                    updateSelectedItem(item)
                },
                showCorrectAnswer = showAnswer,
                isCorrectAnswer = correctAnswer == item,
                isSelectedItem = item == selectedItem,
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = item,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.quizItemPadding)
                )
            }
        }
    }
}
