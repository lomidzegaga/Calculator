package com.example.calculator

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class UseCaseForTesting {

    // this use case do nothing, its only for testing purpose
    fun execute() = runBlocking {
        delay(4)
    }
}