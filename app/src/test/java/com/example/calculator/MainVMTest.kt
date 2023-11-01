package com.example.calculator

import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

class MainVMTest {

    private lateinit var mainVM: MainVM
    private lateinit var useCase: UseCaseForTesting

    @Before
    fun setUp() {
        useCase = mockk<UseCaseForTesting>()
        mainVM = MainVM(useCase)
    }

    @Test
    fun `perform deletion`() {
        // delete from firstNumber When it's not empty
        mainVM.onAction(CalculatorAction.Number(78))
        assertTrue(mainVM.state.firstNumber.isNotBlank())
        mainVM.onAction(CalculatorAction.Delete)
        assertEquals(mainVM.state.firstNumber, "7")

        // delete operation when it's not null
        mainVM.onAction(CalculatorAction.Operation(CalculatorOperation.Add))
        assertTrue(mainVM.state.operation != null)
        mainVM.onAction(CalculatorAction.Delete)
        assertEquals(mainVM.state.operation, null)

        // delete from secondNumber
        mainVM.onAction(CalculatorAction.Number(334))
        assertEquals(mainVM.state.firstNumber, "7334")
        mainVM.onAction(CalculatorAction.Operation(CalculatorOperation.Add))
        assertEquals(mainVM.state.operation, CalculatorOperation.Add)
        mainVM.onAction(CalculatorAction.Number(34))
        mainVM.onAction(CalculatorAction.Delete)
        assertEquals(mainVM.state.secondNumber, "3")
        mainVM.onAction(CalculatorAction.Clear)

        // delete from first number with negative
        mainVM.onAction(CalculatorAction.Number(-12))
        mainVM.onAction(CalculatorAction.Delete)
        assertEquals(mainVM.state.firstNumber, "-1")
        mainVM.onAction(CalculatorAction.Clear)

        // no change when all fields are empty
        mainVM.onAction(CalculatorAction.Delete)
        assertEquals(mainVM.state.firstNumber, "")
        assertEquals(mainVM.state.secondNumber, "")
        assertEquals(mainVM.state.operation, null)
    }

    @Test
    fun `enter number`() {
        // added firstNumber
        mainVM.onAction(CalculatorAction.Number(98))
        assertEquals(mainVM.state.firstNumber, "98")
        assertEquals(mainVM.state.operation, null)
        assertEquals(mainVM.state.secondNumber, "")

        // added operation
        mainVM.onAction(CalculatorAction.Operation(CalculatorOperation.Add))
        assertEquals(mainVM.state.firstNumber, "98")
        assertEquals(mainVM.state.operation, CalculatorOperation.Add)
        assertEquals(mainVM.state.secondNumber, "")

        // added secondNumber
        mainVM.onAction(CalculatorAction.Number(34))
        assertEquals(mainVM.state.firstNumber, "98")
        assertEquals(mainVM.state.operation, CalculatorOperation.Add)
        assertEquals(mainVM.state.secondNumber, "34")

        // press divide when operation is already selected
        mainVM.onAction(CalculatorAction.Operation(CalculatorOperation.Divide))
        assertEquals(mainVM.state.firstNumber, "98")
        assertEquals(mainVM.state.operation, CalculatorOperation.Divide)
        assertEquals(mainVM.state.secondNumber, "34")
    }

    @Test
    fun `calculate percent`() {
        // calculate percent of firstNumber
        mainVM.onAction(CalculatorAction.Number(100))
        assertTrue(mainVM.state.firstNumber.isNotBlank())
        mainVM.onAction(CalculatorAction.Percent)
        assertEquals(mainVM.state.firstNumber, "1")
        mainVM.onAction(CalculatorAction.Delete)

        // try to calculate percent when both numbers are entered
        mainVM.onAction(CalculatorAction.Number(100))
        mainVM.onAction(CalculatorAction.Operation(CalculatorOperation.Add))
        assertTrue(mainVM.state.operation != null)
        mainVM.onAction(CalculatorAction.Number(50))
        mainVM.onAction(CalculatorAction.Percent)
        assertEquals(mainVM.state.firstNumber, "100")
        assertEquals(mainVM.state.operation, CalculatorOperation.Add)
        assertEquals(mainVM.state.secondNumber, "50")
        mainVM.onAction(CalculatorAction.Clear)

        // try to calculate percent when only first number and operation is entered
        mainVM.onAction(CalculatorAction.Number(100))
        mainVM.onAction(CalculatorAction.Operation(CalculatorOperation.Multiply))
        mainVM.onAction(CalculatorAction.Percent)
        assertEquals(mainVM.state.firstNumber, "100")
        assertEquals(mainVM.state.operation, CalculatorOperation.Multiply)

    }

}