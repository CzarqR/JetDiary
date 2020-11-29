package com.myniprojects.jetdiary.ui.theme

import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val AppShapess = Shapes(
    small = CutCornerShape(
        topLeft = 4.dp,
        bottomRight = 4.dp
    ),
    medium = CutCornerShape(
        topLeft = 8.dp,
        bottomRight = 8.dp
    ),
    large = CutCornerShape(
        topLeft = 12.dp,
        bottomRight = 12.dp
    )
)