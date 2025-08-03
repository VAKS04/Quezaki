package com.example.quiz.domain.models

data class User(
    val id: Int = 0,
    val email:String,
    val username : String,
    val password:String,
    val avatarPath:String? = null
)