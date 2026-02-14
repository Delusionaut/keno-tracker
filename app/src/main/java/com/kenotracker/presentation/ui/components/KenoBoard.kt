package com.kenotracker.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
    // Group numbers into 4 quadrants (2x2 grid)
    // Q1: 1-20 (top-left), Q2: 21-40 (top-right)
    // Q3: 41-60 (bottom-left), Q4: 61-80 (bottom-right)
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Top row - Quadrants 1 (1-20) and 2 (21-40)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Quadrant 1: 1-20
            QuadrantBox(
                title = "1-20",
                numbers = (1..20).toList(),
                selectedNumbers = selectedNumbers,
                onNumberClick = onNumberClick,
                modifier = Modifier.weight(1f)
            )
            
            // Quadrant 2: 21-40
            QuadrantBox(
                title = "21-40",
                numbers = (21..40).toList(),
                selectedNumbers = selectedNumbers,
                onNumberClick = onNumberClick,
                modifier = Modifier.weight(1f)
            )
        }
        
        // Bottom row - Quadrants 3 (41-60) and 4 (61-80)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Quadrant 3: 41-60
            QuadrantBox(
                title = "41-60",
                numbers = (41..60).toList(),
                selectedNumbers = selectedNumbers,
                onNumberClick = onNumberClick,
                modifier = Modifier.weight(1f)
            )
            
            // Quadrant 4: 61-80
            QuadrantBox(
                title = "61-80",
                numbers = (61..80).toList(),
                selectedNumbers = selectedNumbers,
                onNumberClick = onNumberClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun QuadrantBox(
    title: String,
    numbers: List<Int>,
    selectedNumbers: Set<Int>,
    onNumberClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(8.dp))
            .background(KenoTextSecondary.copy(alpha = 0.1f))
            .padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        // Quadrant title
        Text(
            text = title,
            color = KenoPrimary,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        
        // Numbers in 2 columns
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            // Column 1 - first half
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                numbers.take(10).forEach { num ->
                    NumberCell(
                        number = num,
                        isSelected = num in selectedNumbers,
                        onClick = { onNumberClick(num) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            
            // Column 2 - second half
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                numbers.drop(10).forEach { num ->
                    NumberCell(
                        number = num,
                        isSelected = num in selectedNumbers,
                        onClick = { onNumberClick(num) },
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
    val borderColor = if (isSelected) KenoSelected else KenoTextSecondary.copy(alpha = 0.5f)
    
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(backgroundColor)
            .border(1.dp, borderColor, RoundedCornerShape(4.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = String.format("00", number),
            color = textColor,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}
