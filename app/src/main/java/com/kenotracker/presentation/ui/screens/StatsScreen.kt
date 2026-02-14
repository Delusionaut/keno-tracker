package com.kenotracker.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kenotracker.presentation.ui.components.StatCard
import com.kenotracker.presentation.ui.theme.KenoPrimary
import com.kenotracker.presentation.ui.theme.KenoSecondary
import com.kenotracker.presentation.ui.theme.KenoSelected
import com.kenotracker.presentation.ui.theme.KenoSurface
import com.kenotracker.presentation.ui.theme.KenoText
import com.kenotracker.presentation.ui.theme.KenoTextSecondary
import com.kenotracker.presentation.viewmodel.KenoViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun StatsScreen(
    viewModel: KenoViewModel = hiltViewModel()
) {
    val uiState by viewModel.statsState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadStatistics()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "STATISTICS",
            style = MaterialTheme.typography.titleLarge,
            color = KenoText,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else {
            // Stats cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    value = uiState.statistics.totalDraws.toString(),
                    label = "Total\nDraws",
                    color = KenoPrimary,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    value = uiState.statistics.totalNumbers.toString(),
                    label = "Total\nNumbers",
                    color = KenoSecondary,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    value = String.format("%.1f", uiState.statistics.averageHitsPerDraw),
                    label = "Avg Hits\nper Draw",
                    color = KenoSelected,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "RECENT DRAWS",
                style = MaterialTheme.typography.titleMedium,
                color = KenoText,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (uiState.recentDraws.isEmpty()) {
                Text(
                    text = "No draws logged yet. Start logging to see statistics!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = KenoTextSecondary
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.recentDraws) { draw ->
                        DrawHistoryItem(draw = draw)
                    }
                }
            }
        }
    }
}

@Composable
fun DrawHistoryItem(draw: com.kenotracker.domain.model.Draw) {
    val dateFormat = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
    val formattedDate = dateFormat.format(Date(draw.timestamp))

    Card(
        colors = CardDefaults.cardColors(
            containerColor = KenoSurface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.bodySmall,
                    color = KenoTextSecondary
                )
                Text(
                    text = "${draw.numbers.size} hits",
                    style = MaterialTheme.typography.bodySmall,
                    color = KenoSelected
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = draw.numbers.joinToString(", ") { it.toString().padStart(2, '0') },
                style = MaterialTheme.typography.bodyMedium,
                color = KenoText
            )
        }
    }
}
