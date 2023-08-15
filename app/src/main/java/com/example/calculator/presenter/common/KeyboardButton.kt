package com.example.calculator.presenter.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.R

@Composable
fun CustomButtonForKeyboard(
    onClick: (Unit) -> Unit,
    text: String,
    btnColor: Color,
    textColor: Color,
    shape: Dp = 22.dp,
    width: Dp = 76.75.dp,
    height: Dp = 75.dp,
    textSize: TextUnit = 30.sp
) {

    val haptic = LocalHapticFeedback.current

    IconButton(
        onClick = { onClick(haptic.performHapticFeedback(HapticFeedbackType.LongPress)) },
        modifier = Modifier
            .width(width)
            .height(height)
            .background(color = btnColor, shape = RoundedCornerShape(size = shape))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {}
    ) {
        Text(
            text = text,
            fontSize = textSize,
            color = textColor,
            fontFamily = FontFamily(Font(R.font.work_sans))
        )
    }
}

@Composable
fun CustomIconButton(
    onClick: (Unit) -> Unit,
    backgroundColor: Color,
    tintColor: Color,
    icon: Int,
    shape: Dp = 22.dp,
    width: Dp = 71.75.dp,
    height: Dp = 72.dp,
    paddingStart: Dp = 14.dp,
    paddingEnd: Dp = 14.dp,
    paddingTop: Dp = 12.dp,
    paddingBottom: Dp = 12.dp
) {

    val haptic = LocalHapticFeedback.current

    IconButton(
        onClick = { onClick(haptic.performHapticFeedback(HapticFeedbackType.LongPress)) },
        modifier = Modifier
            .width(width)
            .height(height)
            .background(color = backgroundColor, shape = RoundedCornerShape(size = shape))
            .padding(
                start = paddingStart,
                top = paddingTop,
                end = paddingEnd,
                bottom = paddingBottom
            )
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = tintColor

        )
    }
}