package com.example.quiz.data.mappers

import com.example.quiz.data.database.UserEntity
import com.example.quiz.domain.models.User

fun UserEntity.toUser (): User {
    return User(
        id = id,
        email = email,
        username = username,
        password = password,
        avatarPath = avatarPath
    )
}

fun User.toUserEntity (): UserEntity {
    return UserEntity(
        id = id,
        email = email,
        username = username,
        password = password,
        avatarPath = avatarPath
    )
}