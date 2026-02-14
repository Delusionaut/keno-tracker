package com.kenotracker.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kenotracker.presentation.ui.theme.KenoPrimary
import com.kenotracker.presentation.ui.theme.KenoSecondary
import com.kenotracker.presentation.ui.theme.KenoSelected
import com.kenotracker.presentation.ui.theme.KenoSurface
import com.kenotracker.presentation.ui.theme.KenoText
import com.kenotracker.presentation.ui.theme.KenoTextSecondary

@Composable
fun StatCard(
    value: String,
    label: String,
    color: androidx.compose.ui.graphics.Color = KenoPrimary,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(KenoSurface)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                color = color,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = KenoTextSecondary
            )
        }
    }
}
