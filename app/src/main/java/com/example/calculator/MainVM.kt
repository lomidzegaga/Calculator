package com.example.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainVM : ViewModel() {

    var state by mutableStateOf(CalculatorState())
        private set

    fun onAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Clear -> state = CalculatorState()
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Operation -> enterOperation(action.operation)
            is CalculatorAction.Calculate -> performCalculation()
            is CalculatorAction.Delete -> performDeletion()
            is CalculatorAction.Percent -> calculatePercent()
            is CalculatorAction.NegativeNumber -> negativeNumber()
        }
    }

    private fun negativeNumber() {
        if (state.firstNumber.isNotEmpty()) {
            val firstNumber = state.firstNumber.toDouble()
            val negativeNumber = -firstNumber

            if (state.secondNumber.isBlank() && state.operation == null) {
                state = if (negativeNumber.toString().endsWith(".0")) {
                    state.copy(
                        firstNumber = negativeNumber.toString().dropLast(2)
                    )
                } else {
                    state.copy(
                        firstNumber = negativeNumber.toString()
                    )
                }
            }
        }
    }

    private fun calculatePercent() {

        if (state.firstNumber.isNotEmpty()) {

            val firstNumber = state.firstNumber.toDouble()
            val percent = firstNumber / 100

            state = if (percent.toString().endsWith(".0")) {
                state.copy(
                    firstNumber = percent.toString().dropLast(2)
                )
            } else {
                state.copy(
                    firstNumber = percent.toString()
                )
            }
        }
    }

    private fun performDeletion() {
        when {
            state.secondNumber.isNotBlank() -> state = state.copy(
                secondNumber = state.secondNumber.dropLast(1)
            )

            state.operation != null -> state = state.copy(
                operation = null
            )

            state.firstNumber.isNotBlank() -> state = state.copy(
                firstNumber = if (state.firstNumber.startsWith("-") && state.firstNumber.length == 2)
                    state.firstNumber.dropLast(2)
                else state.firstNumber.dropLast(1)
            )
        }
    }

    private fun performCalculation() {
        val firstNumber = state.firstNumber.toDoubleOrNull()
        val secondNumber = state.secondNumber.toDoubleOrNull()

        if (firstNumber != null && secondNumber != null) {
            val result = when (state.operation) {
                is CalculatorOperation.Add -> firstNumber + secondNumber
                is CalculatorOperation.Subtract -> firstNumber - secondNumber
                is CalculatorOperation.Divide -> firstNumber / secondNumber
                is CalculatorOperation.Multiply -> firstNumber * secondNumber
                null -> return
            }

            state = if (result.toString().endsWith(".0")) {
                state.copy(
                    firstNumber = result.toString().dropLast(2),
                    secondNumber = "",
                    operation = null
                )
            } else {
                state.copy(
                    firstNumber = result.toString().take(6),
                    secondNumber = "",
                    operation = null
                )
            }
        }
    }

    private fun enterOperation(operation: CalculatorOperation) {
        if (state.firstNumber.isNotBlank()) {
            state = state.copy(operation = operation)
        }
    }

    private fun enterDecimal() {
        if (state.operation == null && state.firstNumber.isNotBlank() && !state.firstNumber.contains(
                "."
            )
        ) {
            state = state.copy(
                firstNumber = state.firstNumber + "."
            )
            return
        }

        if (state.secondNumber.isNotBlank() && !state.secondNumber.contains(
                "."
            )
        ) {
            state = state.copy(
                secondNumber = state.secondNumber + "."
            )
            return
        }

        if (state.firstNumber.isBlank()) {
            state = state.copy(
                firstNumber = "0."
            )
        }
    }

    private fun enterNumber(number: Int) {
        if (state.operation == null) {
            if (state.firstNumber.length >= MAX_NUMBER_LENGTH) {
                return
            }
            state = state.copy(
                firstNumber = state.firstNumber + number
            )
            return
        }
        if (state.secondNumber.length >= MAX_NUMBER_LENGTH) {
            return
        }
        state = state.copy(
            secondNumber = state.secondNumber + number
        )
    }

    companion object {
        private const val MAX_NUMBER_LENGTH = 9
    }

}