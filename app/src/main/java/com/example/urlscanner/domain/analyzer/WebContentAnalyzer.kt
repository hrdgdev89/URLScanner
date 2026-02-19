package com.example.urlscanner.domain.analyzer

/**
 * Interface for analyzing web page HTML content for phishing indicators.
 *
 * Follows ISP (Interface Segregation Principle) — focused solely on HTML content analysis,
 * decoupled from URL structure analysis.
 */
fun interface WebContentAnalyzer {
    /** Returns a phishing score based on HTML content. Higher = more suspicious. */
    fun analyze(html: String): Int
}
