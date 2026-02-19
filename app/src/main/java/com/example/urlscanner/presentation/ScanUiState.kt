package com.example.urlscanner.presentation

import com.example.urlscanner.domain.model.PhishingResult

/**
 * Represents all possible UI states for the scan screen.
 * Follows SRP — each state carries only the data it needs.
 */
sealed interface ScanUiState {
    /** Initial state — no scan has been triggered yet. */
    data object Idle : ScanUiState

    /** Scan is in progress. */
    data object Loading : ScanUiState

    /** Scan completed successfully. */
    data class Success(val result: PhishingResult) : ScanUiState

    /** Scan failed with an error message. */
    data class Error(val message: String) : ScanUiState
}
