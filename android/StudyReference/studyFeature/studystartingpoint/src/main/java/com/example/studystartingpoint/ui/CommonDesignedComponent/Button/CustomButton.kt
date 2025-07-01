package com.example.studystartingpoint.ui.CommonDesignedComponent.Button

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun VerticalSpaceButton(
    verticalSpace: Dp,
    button: @Composable () -> Unit
) {
    Spacer(Modifier.height(verticalSpace))
    button()
    Spacer(Modifier.height(verticalSpace))
}