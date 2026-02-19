package com.example.urlscanner.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.urlscanner.domain.usecase.AnalyzeUrlUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the URL scan screen.
 * Follows SRP — only manages the UI state for the phishing scan feature.
 */
class ScanViewModel(
    private val analyzeUrlUseCase: AnalyzeUrlUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ScanUiState>(ScanUiState.Idle)
    val uiState: StateFlow<ScanUiState> = _uiState.asStateFlow()

    /**
     * Triggers a phishing analysis for the given [url].
     * Updates [uiState] to Loading → Success or Error.
     */
    fun analyze(url: String) {
        if (url.isBlank()) {
            _uiState.value = ScanUiState.Error("Please enter a URL to scan.")
            return
        }

        viewModelScope.launch {
            _uiState.value = ScanUiState.Loading
            analyzeUrlUseCase(url)
                .onSuccess { result -> _uiState.value = ScanUiState.Success(result) }
                .onFailure { error ->
                    _uiState.value = ScanUiState.Error(
                        error.message ?: "Failed to reach the URL. Check your connection."
                    )
                }
        }
    }

    /** Resets back to the [ScanUiState.Idle] state. */
    fun reset() {
        _uiState.value = ScanUiState.Idle
    }
}
