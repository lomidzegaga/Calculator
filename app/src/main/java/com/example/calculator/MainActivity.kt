package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.calculator.presenter.components.CalculatorUI
import com.example.calculator.theme.CalculatorTheme
import com.example.calculator.theme.lightBackground

class MainActivity : ComponentActivity() {

    private val viewModel: MainVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val checked = remember {
                mutableStateOf(false)
            }

            val state = viewModel.state

            CalculatorTheme(checked.value) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(lightBackground)
                )
                Box(
                    contentAlignment = Alignment.TopCenter
                ) {
                    Pulsating(
                        isRounded = checked.value,
                        content = {
                            Surface(
                                color = MaterialTheme.colorScheme.surface,
                                shape = CircleShape,
                                modifier = Modifier.size(100.dp),
                                content = {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(500.dp))
                                            .fillMaxSize()
                                            .background(
                                                Color.Black
                                            )
                                    )
                                }
                            )
                        }
                    )
                    CalculatorUI(
                        isDarkTheme = checked.value,
                        resultText = state.firstNumber + (state.operation?.symbol
                            ?: "") + state.secondNumber,
                        click = {
                            checked.value = !checked.value
                        },
                        onAction = viewModel::onAction
                    )
                }
            }
        }
    }
}

@Composable
fun Pulsating(
    content: @Composable () -> Unit,
    isRounded: Boolean
) {

    val alpha: Float by animateFloatAsState(
        targetValue = if (isRounded) 18f else 0.001f,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing), label = ""
    )

    Box(
        modifier = Modifier
            .scale(alpha)
            .alpha(0.9f)
    ) {
        content()
    }
}