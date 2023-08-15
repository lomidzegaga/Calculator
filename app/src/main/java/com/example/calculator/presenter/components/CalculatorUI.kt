package com.example.calculator.presenter.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.CalculatorAction
import com.example.calculator.CalculatorOperation
import com.example.calculator.R
import com.example.calculator.presenter.common.CustomButtonForKeyboard
import com.example.calculator.presenter.common.CustomIconButton
import com.example.calculator.theme.buttonHighEmphasisLight

@Composable
fun CalculatorUI(
    onAction: (CalculatorAction) -> Unit,
    isDarkTheme: Boolean,
    resultText: String,
    click: (Unit) -> Unit,
    style: TextStyle = MaterialTheme.typography.bodyLarge
) {

    val numberButtonColor by animateColorAsState(
        targetValue = MaterialTheme.colorScheme.secondary,
        animationSpec = tween(500, easing = LinearOutSlowInEasing),
        label = ""
    )
    val symbolButtonColor by animateColorAsState(
        targetValue = MaterialTheme.colorScheme.primary,
        animationSpec = tween(500, easing = LinearOutSlowInEasing),
        label = ""
    )
    val textColor by animateColorAsState(
        targetValue = MaterialTheme.colorScheme.surface,
        animationSpec = tween(500, easing = LinearOutSlowInEasing),
        label = ""
    )
    var resizedText by remember {
        mutableStateOf(style)
    }

    var textSize by remember {
        mutableStateOf(80.sp)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomIconButton(
                onClick = { click(Unit) },
                backgroundColor = MaterialTheme.colorScheme.surface,
                shape = 12.dp,
                width = 135.dp,
                height = 45.dp,
                icon = if (isDarkTheme) R.drawable.moon else R.drawable.sun,
                tintColor = MaterialTheme.colorScheme.onSurface
            )
        }
        Spacer(modifier = Modifier.padding(vertical = 20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.18f)
                .padding(end = 20.dp, start = 3.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(
                visible = resultText.isNotEmpty(),
                enter = slideInVertically() + fadeIn(
                    initialAlpha = 0.7f
                ),
                exit = slideOutVertically()
            ) {
                Text(
                    text = resultText,
                    color = textColor,
                    fontSize = textSize,
                    softWrap = false,
                    style = resizedText,
                    onTextLayout = { result ->
                        if (result.hasVisualOverflow) {
                            textSize *= 0.80
                            resizedText = resizedText.copy(
                                fontSize = textSize
                            )
                        }
                    }
                )
            }

        }
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(
                0.dp,
                Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomButtonForKeyboard(
                    onClick = { onAction(CalculatorAction.Clear) },
                    text = "C",
                    btnColor = symbolButtonColor,
                    textColor = MaterialTheme.colorScheme.surface
                )
                CustomButtonForKeyboard(
                    onClick = { onAction(CalculatorAction.NegativeNumber) },
                    text = "+/-",
                    btnColor = symbolButtonColor,
                    textColor = MaterialTheme.colorScheme.surface,
                    textSize = 25.sp
                )
                CustomButtonForKeyboard(
                    onClick = { onAction(CalculatorAction.Percent) },
                    text = "%",
                    btnColor = symbolButtonColor,
                    textColor = MaterialTheme.colorScheme.surface
                )
                CustomButtonForKeyboard(
                    onClick = { onAction(CalculatorAction.Operation(CalculatorOperation.Divide)) },
                    text = "÷",
                    btnColor = MaterialTheme.colorScheme.tertiary,
                    textColor = MaterialTheme.colorScheme.onBackground,
                    textSize = 35.sp
                )
            }
            Spacer(modifier = Modifier.padding(vertical = 7.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomButtonForKeyboard(
                    onClick = { onAction(CalculatorAction.Number(7)) },
                    text = "7",
                    btnColor = numberButtonColor,
                    textColor = MaterialTheme.colorScheme.surface
                )
                CustomButtonForKeyboard(
                    onClick = { onAction(CalculatorAction.Number(8)) },
                    text = "8",
                    btnColor = numberButtonColor,
                    textColor = MaterialTheme.colorScheme.surface
                )
                CustomButtonForKeyboard(
                    onClick = { onAction(CalculatorAction.Number(9)) },
                    text = "9",
                    btnColor = numberButtonColor,
                    textColor = MaterialTheme.colorScheme.surface
                )
                CustomButtonForKeyboard(
                    onClick = { onAction(CalculatorAction.Operation(CalculatorOperation.Multiply)) },
                    text = "×",
                    btnColor = MaterialTheme.colorScheme.tertiary,
                    textColor = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.padding(vertical = 7.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomButtonForKeyboard(
                    onClick = { onAction(CalculatorAction.Number(4)) },
                    text = "4",
                    btnColor = numberButtonColor,
                    textColor = MaterialTheme.colorScheme.surface
                )
                CustomButtonForKeyboard(
                    onClick = { onAction(CalculatorAction.Number(5)) },
                    text = "5",
                    btnColor = numberButtonColor,
                    textColor = MaterialTheme.colorScheme.surface
                )
                CustomButtonForKeyboard(
                    onClick = { onAction(CalculatorAction.Number(6)) },
                    text = "6",
                    btnColor = numberButtonColor,
                    textColor = MaterialTheme.colorScheme.surface
                )
                CustomButtonForKeyboard(
                    onClick = { onAction(CalculatorAction.Operation(CalculatorOperation.Subtract)) },
                    text = "-",
                    btnColor = MaterialTheme.colorScheme.tertiary,
                    textColor = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.padding(vertical = 7.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomButtonForKeyboard(
                    onClick = { onAction(CalculatorAction.Number(1)) },
                    text = "1",
                    btnColor = numberButtonColor,
                    textColor = MaterialTheme.colorScheme.surface
                )
                CustomButtonForKeyboard(
                    onClick = { onAction(CalculatorAction.Number(2)) },
                    text = "2",
                    btnColor = numberButtonColor,
                    textColor = MaterialTheme.colorScheme.surface
                )
                CustomButtonForKeyboard(
                    onClick = { onAction(CalculatorAction.Number(3)) },
                    text = "3",
                    btnColor = numberButtonColor,
                    textColor = MaterialTheme.colorScheme.surface
                )
                CustomButtonForKeyboard(
                    onClick = { onAction(CalculatorAction.Operation(CalculatorOperation.Add)) },
                    text = "+",
                    btnColor = MaterialTheme.colorScheme.tertiary,
                    textColor = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.padding(vertical = 7.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomButtonForKeyboard(
                    onClick = { onAction(CalculatorAction.Decimal) },
                    text = ".",
                    btnColor = numberButtonColor,
                    textColor = MaterialTheme.colorScheme.surface
                )
                CustomButtonForKeyboard(
                    onClick = { onAction(CalculatorAction.Number(0)) },
                    text = "0",
                    btnColor = numberButtonColor,
                    textColor = MaterialTheme.colorScheme.surface
                )
                CustomIconButton(
                    onClick = { onAction(CalculatorAction.Delete) },
                    backgroundColor = numberButtonColor,
                    icon = R.drawable.delete_icon,
                    tintColor = MaterialTheme.colorScheme.surface
                )
                CustomButtonForKeyboard(
                    onClick = { onAction(CalculatorAction.Calculate) },
                    text = "=",
                    btnColor = buttonHighEmphasisLight,
                    textColor = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
    }
}