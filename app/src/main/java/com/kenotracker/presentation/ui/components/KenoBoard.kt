package com.kenotracker.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kenotracker.presentation.ui.theme.KenoPrimary
import com.kenotracker.presentation.ui.theme.KenoSelected
import com.kenotracker.presentation.ui.theme.KenoText
import com.kenotracker.presentation.ui.theme.KenoTextSecondary

@Composable
fun KenoBoard(
    selectedNumbers: Set<Int>,
    onNumberClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    // Group numbers into 4 quadrants
    // Q1: 1-20, Q2: 21-40, Q3: 41-60, Q4: 61-80
    val quadrants = listOf(
        (1..20).toList(),
        (21..40).toList(),
        (41..60).toList(),
        (61..80).toList()
    )
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Top row - quadrants 1 and 2
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NumberQuadrant(
                numbers = quadrants[0],
                selectedNumbers = selectedNumbers,
                onNumberClick = onNumberClick,
                modifier = Modifier.weight(1f)
            )
            NumberQuadrant(
                numbers = quadrants[1],
                selectedNumbers = selectedNumbers,
                onNumberClick = onNumberClick,
                modifier = Modifier.weight(1f)
            )
        }
        
        // Bottom row - quadrants 3 and 4
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NumberQuadrant(
                numbers = quadrants[2],
                selectedNumbers = selectedNumbers,
                onNumberClick = onNumberClick,
                modifier = Modifier.weight(1f)
            )
            NumberQuadrant(
                numbers = quadrants[3],
                selectedNumbers = selectedNumbers,
                onNumberClick = onNumberClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun NumberQuadrant(
    numbers: List<Int>,
    selectedNumbers: Set<Int>,
    onNumberClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Split into 2 columns within quadrant
        val col1 = numbers.take(10)
        val col2 = numbers.drop(10)
        
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                col1.forEach { number ->
                    NumberCell(
                        number = number,
                        isSelected = number in selectedNumbers,
                        onClick = { onNumberClick(number) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                col2.forEach { number ->
                    NumberCell(
                        number = number,
                        isSelected = number in selectedNumbers,
                        onClick = { onNumberClick(number) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun NumberCell(
    number: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) KenoSelected else Color.Transparent
    val textColor = if (isSelected) Color.Black else KenoText
    val borderColor = if (isSelected) KenoSelected else KenoTextSecondary
    
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(backgroundColor)
            .border(1.dp, borderColor, RoundedCornerShape(4.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString().padStart(2, '0'),
            color = textColor,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            textAlign = TextAlign.Center
        )
    }
}
