package com.example.saborlocalspa.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector


@Composable
fun StandardScaffold(
    title: String,
    onNavigateBack: () -> Unit,
    actionIcon: ImageVector? = null,
    onActionClick: (() -> Unit)? = null,
    actionContent: (@Composable () -> Unit)? = null,
    containerColor: Color = Color.White,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            SimpleTopBar(
                title = title,
                onNavigateBack = onNavigateBack,
                actionIcon = actionIcon,
                onActionClick = onActionClick,
                actionContent = actionContent
            )
        },
        containerColor = containerColor
    ) { paddingValues ->
        content(paddingValues)
    }
}
