package com.example.urlscanner.domain.model

/**
 * Represents the complete result of a phishing analysis.
 *
 * @param url         The URL that was analyzed.
 * @param urlScore    Score from URL structure checks (http, IP, @ symbol).
 * @param webScore    Score from HTML content checks (script, iframe, image, etc).
 */
data class PhishingResult(
    val url: String,
    val urlScore: Int,
    val webScore: Int
) {
    val totalScore: Int get() = urlScore + webScore

    /** A score greater than 2 is considered phishing. */
    val isPhishing: Boolean get() = totalScore > 2
}
