package com.example.quiz.data.repositories

import com.example.quiz.data.mappers.toUser
import com.example.quiz.data.mappers.toUserEntity
import com.example.quiz.data.database.UserDao
import com.example.quiz.domain.models.User
import com.example.quiz.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl (
    private val userDao: UserDao
):UserRepository{
    override suspend fun insertUser(user: User): Result<Unit>{
        userDao.insert(user.toUserEntity())
        return Result.success(Unit)
    }

    override suspend fun updateUser(user: User): Result<Unit>{
        userDao.update(user.toUserEntity())
        return Result.success(Unit)
    }

    override suspend fun countUsers(email: String):Int {
        return userDao.countUsersByEmail(email)
    }

    override fun getUser(email: String,password:String): Flow<User?> {
        return userDao
            .getItem(
                email, password)
            .map {
                it?.toUser()
            }
    }
}