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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kenotracker.presentation.ui.components.KenoBoard
import com.kenotracker.presentation.ui.theme.KenoPrimary
import com.kenotracker.presentation.ui.theme.KenoSecondary
import com.kenotracker.presentation.ui.theme.KenoSelected
import com.kenotracker.presentation.ui.theme.KenoText
import com.kenotracker.presentation.ui.theme.KenoTextSecondary
import com.kenotracker.presentation.ui.theme.KenoTrackerTheme
import com.kenotracker.presentation.viewmodel.KenoViewModel

@Composable
fun InputScreen(
    viewModel: KenoViewModel = hiltViewModel()
) {
    KenoTrackerTheme {
        val uiState by viewModel.inputState.collectAsState()
        val scrollState = rememberScrollState()

        Scaffold { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "LOG A DRAW",
                    style = MaterialTheme.typography.titleLarge,
                    color = KenoText,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Tap the 20 numbers the machine drew",
                    style = MaterialTheme.typography.bodyMedium,
                    color = KenoTextSecondary
                )

                Spacer(modifier = Modifier.height(16.dp))

                KenoBoard(
                    selectedNumbers = uiState.selectedNumbers,
                    onNumberClick = { viewModel.toggleNumber(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Selection status
                val selectedCount = uiState.selectedNumbers.size
                val isComplete = selectedCount == 20

                Text(
                    text = when {
                        isComplete -> "✓ 20/20 selected - Ready to save"
                        else -> "$selectedCount/20 selected (need ${20 - selectedCount} more)"
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (isComplete) KenoSelected else KenoTextSecondary,
                    fontWeight = if (isComplete) FontWeight.Bold else FontWeight.Normal
                )

                if (uiState.saveSuccess) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "✓ Draw saved successfully!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = KenoSelected
                    )
                }

                if (uiState.errorMessage != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = uiState.errorMessage!!,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { viewModel.clearSelection() },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = KenoText
                        )
                    ) {
                        Text("Clear")
                    }

                    Button(
                        onClick = { viewModel.saveDraw() },
                        modifier = Modifier.weight(1f),
                        enabled = isComplete && !uiState.isSaving,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = KenoPrimary,
                            disabledContainerColor = KenoPrimary.copy(alpha = 0.5f)
                        )
                    ) {
                        Text(if (uiState.isSaving) "Saving..." else "Save Draw")
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
