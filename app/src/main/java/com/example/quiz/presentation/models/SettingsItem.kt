package com.example.quiz.presentation.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class SettingsItem(
    @DrawableRes val icon:Int,
    @StringRes val title:Int,
    val route:String
)