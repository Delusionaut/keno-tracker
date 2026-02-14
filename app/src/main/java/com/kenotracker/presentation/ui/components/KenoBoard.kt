package com.kenotracker.presentation.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kenotracker.presentation.ui.theme.KenoSelected
import com.kenotracker.presentation.ui.theme.KenoText
import com.kenotracker.presentation.ui.theme.KenoUnselected

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun KenoBoard(
    selectedNumbers: Set<Int>,
    onNumberToggle: (Int) -> Unit,
    maxSelection: Int = 20,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            maxItemsInEachRow = 10
        ) {
            (1..80).forEach { number ->
                KenoNumberCell(
                    number = number,
                    isSelected = selectedNumbers.contains(number),
                    enabled = selectedNumbers.size < maxSelection || selectedNumbers.contains(number),
                    onClick = { onNumberToggle(number) }
                )
            }
        }
    }
}

@Composable
fun KenoNumberCell(
    number: Int,
    isSelected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) KenoSelected else Color.Transparent,
        animationSpec = tween(durationMillis = 150),
        label = "background"
    )

    val textColor by animateColorAsState(
        targetValue = if (isSelected) Color.Black else KenoText,
        animationSpec = tween(durationMillis = 150),
        label = "text"
    )

    val borderColor by animateColorAsState(
        targetValue = if (isSelected) KenoSelected else KenoUnselected,
        animationSpec = tween(durationMillis = 150),
        label = "border"
    )

    Box(
        modifier = modifier
            .padding(2.dp)
            .size(32.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString().padStart(2, '0'),
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            textAlign = TextAlign.Center
        )
    }
}
