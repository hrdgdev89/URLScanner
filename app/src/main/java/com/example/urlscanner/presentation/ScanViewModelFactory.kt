package com.example.urlscanner.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.urlscanner.data.repository.WebContentRepositoryImpl
import com.example.urlscanner.domain.analyzer.UrlFeatureAnalyzer
import com.example.urlscanner.domain.analyzer.WebHtmlAnalyzer
import com.example.urlscanner.domain.usecase.AnalyzeUrlUseCase

/**
 * Factory for creating [ScanViewModel] with its wired dependencies.
 *
 * Acts as the manual DI composition root — all dependencies are assembled here,
 * following DIP by providing concrete instances through their interfaces.
 */
class ScanViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        check(modelClass.isAssignableFrom(ScanViewModel::class.java)) {
            "Unknown ViewModel class: ${modelClass.name}"
        }
        val useCase = AnalyzeUrlUseCase(
            repository = WebContentRepositoryImpl(),
            urlAnalyzer = UrlFeatureAnalyzer(),
            webContentAnalyzer = WebHtmlAnalyzer()
        )
        return ScanViewModel(useCase) as T
    }
}
