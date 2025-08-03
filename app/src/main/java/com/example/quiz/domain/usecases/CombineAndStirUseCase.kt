package com.example.quiz.domain.usecases

class CombineAndStirUseCase{
    operator fun invoke(
        correctAnswer:String,
        incorrectList:List<String>
    ):List<String>{
        val mutList = incorrectList.toMutableList()
        mutList.add(correctAnswer)
        return mutList.shuffled()
    }
}