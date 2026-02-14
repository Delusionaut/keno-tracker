package com.kenotracker.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kenotracker.presentation.ui.components.RecommendationCard
import com.kenotracker.presentation.ui.theme.ColdColor
import com.kenotracker.presentation.ui.theme.HotColor
import com.kenotracker.presentation.ui.theme.KenoText
import com.kenotracker.presentation.ui.theme.KenoTextSecondary
import com.kenotracker.presentation.ui.theme.TrendColor
import com.kenotracker.presentation.viewmodel.KenoViewModel

@Composable
fun PredictScreen(
    viewModel: KenoViewModel = hiltViewModel(),
    onPlayNumbers: (Set<Int>) -> Unit
) {
    val uiState by viewModel.predictState.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "RECOMMENDATIONS",
            style = MaterialTheme.typography.titleLarge,
            color = KenoText,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Based on your logged draws",
            style = MaterialTheme.typography.bodyMedium,
            color = KenoTextSecondary
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else {
            RecommendationCard(
                title = "HOT (Most Frequent)",
                numbers = uiState.hotNumbers,
                color = HotColor,
                onNumbersSelected = onPlayNumbers
            )

            Spacer(modifier = Modifier.height(16.dp))

            RecommendationCard(
                title = "COLD (Least Frequent)",
                numbers = uiState.coldNumbers,
                color = ColdColor,
                onNumbersSelected = onPlayNumbers
            )

            Spacer(modifier = Modifier.height(16.dp))

            RecommendationCard(
                title = "TREND (Pattern-Based)",
                numbers = uiState.trendNumbers,
                color = TrendColor,
                onNumbersSelected = onPlayNumbers
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}
