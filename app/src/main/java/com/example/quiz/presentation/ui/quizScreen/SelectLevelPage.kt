package com.example.quiz.presentation.ui.quizScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.quiz.R
import com.example.quiz.data.models.Category
import com.example.quiz.domain.models.Difficulty
import com.example.quiz.domain.models.NumberOfQuestions
import com.example.quiz.domain.models.TypeAnswer
import com.example.quiz.presentation.models.quizScreenModels.SelectLevelUiState
import com.example.quiz.presentation.ui.general.TemplateButton
import com.example.quiz.presentation.ui.general.TemplatePage
import com.example.quiz.presentation.ui.theme.Dimens
import com.example.quiz.presentation.ui.theme.Shapes
import com.example.quiz.presentation.viewModels.SelectLevelViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import kotlin.enums.EnumEntries

@Composable
fun SelectLevelPage(
    category: Category?,
    onQuiz: (SelectLevelUiState)-> Unit
){
    val viewModel: SelectLevelViewModel = koinViewModel(
        parameters = {
            parametersOf(category)
        }
    )
    val uiState by viewModel.quizUiState.collectAsState()

    TemplatePage(
        headContent = {
            SelectLevelHead(
                modifier = Modifier.padding(Dimens.middlePadding),
                title = uiState.category?.title?: stringResource(R.string.custom_quiz)
            )
        },
        bodyContent = {
            SelectLevelBody(
                modifier = Modifier.padding(Dimens.middlePadding),
                uiState = uiState,
                updateDifficulty = {viewModel.setDifficulty(it)},
                updateNumber = {viewModel.setNumber(it)},
                updateTypeAnswer = {viewModel.setTypeAnswer(it)},
                onQuiz = {onQuiz(it)}
            )
        }
    )
}

@Composable
fun SelectLevelHead(
    modifier:Modifier = Modifier,
    title: String
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.name_quiz,title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun SelectLevelBody(
    modifier: Modifier = Modifier,
    updateDifficulty: (Difficulty)->Unit,
    updateNumber: (NumberOfQuestions)->Unit,
    updateTypeAnswer: (TypeAnswer)->Unit,
    uiState: SelectLevelUiState,
    onQuiz: (SelectLevelUiState) -> Unit
){
    Column(modifier = modifier) {
        val difficultiesList = Difficulty.entries
        val typesList = TypeAnswer.entries
        val numbersList = NumberOfQuestions.entries

        Text(
            text = stringResource(R.string.title_quiz_field),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.padding(Dimens.smallSpacer))

        NameTitle(
            text = stringResource(R.string.difficulty)
        )
        SelectBar(
            modifier = Modifier.weight(1f),
            options = difficultiesList,
            onOptionSelected = {updateDifficulty(it)},
            selectedOption = uiState.difficulty
        )

        Spacer(modifier = Modifier.padding(Dimens.smallSpacer))

        NameTitle(
            text = stringResource(R.string.number_of_question)
        )
        SelectBar(
            displayText = {it.value.toString()},
            modifier = Modifier.weight(1f),
            options = numbersList,
            onOptionSelected = {
                updateNumber(it)
            },
            selectedOption = uiState.numberOfQuestion
        )
        Spacer(modifier = Modifier.padding(Dimens.smallSpacer))

        NameTitle(
            text = stringResource(R.string.type_of_answer)
        )
        SelectBar(
            modifier = Modifier.weight(1f),
            options = typesList,
            onOptionSelected = {updateTypeAnswer(it)},
            selectedOption = uiState.typeAnswer
        )

        Spacer(modifier = Modifier.padding(Dimens.smallSpacer))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            TemplateButton(
                modifier = Modifier,
                onClick = {
                    onQuiz(uiState)
                },
                content = {
                    Text(text = stringResource(R.string.start))
                },
                enabled = true
            )
        }
    }
}

@Composable
fun NameTitle(
    text:String
){
    Row(modifier = Modifier.padding(vertical = Dimens.smallPadding)) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun <T : Enum<T>> SelectBar(
    modifier: Modifier = Modifier,
    options: EnumEntries<T>,
    selectedOption: T,
    displayText: (T) -> String = { it.name.lowercase() },
    onOptionSelected: (T) -> Unit = {},
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(10.dp)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = horizontalArrangement
    ) {
        options.forEach { option ->
            val isSelected = option == selectedOption

            SelectBarItem(
                modifier = modifier,
                isSelected = isSelected,
                title = displayText(option)
            ) {
                onOptionSelected(option)
            }
        }
    }
}

@Composable
fun SelectBarItem(
    modifier: Modifier = Modifier,
    isSelected:Boolean,
    title:String,
    onClick: () -> Unit
){
    Box(
        modifier = modifier
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(Shapes.itemBarShape)
            )
            .border(
                width = 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(Shapes.itemBarShape)
            )
            .clickable {
                onClick()
            }
            .padding(Dimens.selectBarItemPadding),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimary
            else MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelLarge
        )
    }
}