package com.example.urlscanner.domain.usecase

import com.example.urlscanner.domain.analyzer.UrlAnalyzer
import com.example.urlscanner.domain.analyzer.WebContentAnalyzer
import com.example.urlscanner.domain.model.PhishingResult
import com.example.urlscanner.domain.repository.WebContentRepository

/**
 * Orchestrates the URL phishing analysis workflow.
 *
 * Follows SRP — single responsibility: coordinate fetch + analyze.
 * Follows OCP — new analyzers can be added without modifying this class.
 * Follows DIP — depends on abstractions (interfaces), not concrete implementations.
 */
class AnalyzeUrlUseCase(
    private val repository: WebContentRepository,
    private val urlAnalyzer: UrlAnalyzer,
    private val webContentAnalyzer: WebContentAnalyzer
) {
    /**
     * Runs the full phishing analysis for the given [url].
     * @return [Result] wrapping a [PhishingResult] on success.
     */
    suspend operator fun invoke(url: String): Result<PhishingResult> =
        repository.fetchHtml(url).map { html ->
            val urlScore = urlAnalyzer.analyze(url)
            val webScore = webContentAnalyzer.analyze(html)
            PhishingResult(url = url, urlScore = urlScore, webScore = webScore)
        }
}
