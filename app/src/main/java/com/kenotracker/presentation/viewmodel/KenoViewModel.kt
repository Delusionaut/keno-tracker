package com.kenotracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenotracker.domain.model.Draw
import com.kenotracker.domain.model.NumberRecommendation
import com.kenotracker.domain.model.Statistics
import com.kenotracker.domain.usecase.GetAllDrawsUseCase
import com.kenotracker.domain.usecase.GetStatisticsUseCase
import com.kenotracker.domain.usecase.SaveDrawUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class InputUiState(
    val selectedNumbers: Set<Int> = emptySet(),
    val isSaving: Boolean = false,
    val saveSuccess: Boolean = false,
    val errorMessage: String? = null
)

data class PredictUiState(
    val hotNumbers: List<NumberRecommendation> = emptyList(),
    val coldNumbers: List<NumberRecommendation> = emptyList(),
    val trendNumbers: List<NumberRecommendation> = emptyList(),
    val isLoading: Boolean = true
)

data class StatsUiState(
    val statistics: Statistics = Statistics(),
    val recentDraws: List<Draw> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class KenoViewModel @Inject constructor(
    private val saveDrawUseCase: SaveDrawUseCase,
    private val getAllDrawsUseCase: GetAllDrawsUseCase,
    private val getStatisticsUseCase: GetStatisticsUseCase
) : ViewModel() {

    private val _inputState = MutableStateFlow(InputUiState())
    val inputState: StateFlow<InputUiState> = _inputState.asStateFlow()

    private val _predictState = MutableStateFlow(PredictUiState())
    val predictState: StateFlow<PredictUiState> = _predictState.asStateFlow()

    private val _statsState = MutableStateFlow(StatsUiState())
    val statsState: StateFlow<StatsUiState> = _statsState.asStateFlow()

    val drawCount = getAllDrawsUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
        .let { flow ->
            val stateFlow = MutableStateFlow(0)
            viewModelScope.launch {
                getAllDrawsUseCase().collect { draws ->
                    stateFlow.value = draws.size
                }
            }
            stateFlow
        }

    init {
        loadRecommendations()
        loadStatistics()
    }

    fun toggleNumber(number: Int) {
        val current = _inputState.value.selectedNumbers.toMutableSet()
        if (current.contains(number)) {
            current.remove(number)
        } else if (current.size < 20) {
            current.add(number)
        }
        _inputState.value = _inputState.value.copy(
            selectedNumbers = current,
            saveSuccess = false,
            errorMessage = null
        )
    }

    fun clearSelection() {
        _inputState.value = _inputState.value.copy(
            selectedNumbers = emptySet(),
            saveSuccess = false,
            errorMessage = null
        )
    }

    fun saveDraw() {
        val numbers = _inputState.value.selectedNumbers
        if (numbers.size != 20) {
            _inputState.value = _inputState.value.copy(
                errorMessage = "Please select exactly 20 numbers"
            )
            return
        }

        viewModelScope.launch {
            _inputState.value = _inputState.value.copy(isSaving = true)
            try {
                saveDrawUseCase(numbers.toList())
                _inputState.value = InputUiState(
                    saveSuccess = true
                )
                loadRecommendations()
                loadStatistics()
            } catch (e: Exception) {
                _inputState.value = _inputState.value.copy(
                    isSaving = false,
                    errorMessage = "Failed to save: ${e.message}"
                )
            }
        }
    }

    fun loadRecommendations() {
        viewModelScope.launch {
            _predictState.value = _predictState.value.copy(isLoading = true)
            try {
                val stats = getStatisticsUseCase()
                _predictState.value = PredictUiState(
                    hotNumbers = stats.hotNumbers,
                    coldNumbers = stats.coldNumbers,
                    trendNumbers = stats.trendNumbers,
                    isLoading = false
                )
            } catch (e: Exception) {
                _predictState.value = _predictState.value.copy(isLoading = false)
            }
        }
    }

    fun loadStatistics() {
        viewModelScope.launch {
            _statsState.value = _statsState.value.copy(isLoading = true)
            try {
                val stats = getStatisticsUseCase()
                val draws = getAllDrawsUseCase.getOnce()
                _statsState.value = StatsUiState(
                    statistics = stats,
                    recentDraws = draws.take(20),
                    isLoading = false
                )
            } catch (e: Exception) {
                _statsState.value = _statsState.value.copy(isLoading = false)
            }
        }
    }

    fun setNumbersForInput(numbers: Set<Int>) {
        _inputState.value = _inputState.value.copy(
            selectedNumbers = numbers,
            saveSuccess = false,
            errorMessage = null
        )
    }

    fun dismissSuccess() {
        _inputState.value = _inputState.value.copy(saveSuccess = false)
    }
}
