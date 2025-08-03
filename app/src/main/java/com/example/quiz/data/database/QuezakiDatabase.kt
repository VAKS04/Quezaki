package com.example.quiz.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

const val DB_NAME = "quizaki"

@Database(entities = [UserEntity::class], version = 1)
abstract class QuezakiDatabase:RoomDatabase() {
    abstract fun userDao(): UserDao
}