package com.example.urlscanner.domain.analyzer

/**
 * Interface for analyzing URL structure for phishing indicators.
 *
 * Follows ISP (Interface Segregation Principle) — focused solely on URL analysis,
 * decoupled from web content analysis.
 */
fun interface UrlAnalyzer {
    /** Returns a phishing score based on URL structure. Higher = more suspicious. */
    fun analyze(url: String): Int
}
