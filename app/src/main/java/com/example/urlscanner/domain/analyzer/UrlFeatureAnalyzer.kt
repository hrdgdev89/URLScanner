package com.example.urlscanner.domain.analyzer

/**
 * Analyzes URL structure to detect phishing indicators.
 * Implements [UrlAnalyzer] — SRP: only responsible for URL feature checks.
 *
 * Scoring rules:
 *  +1  URL uses http:// (not https) — insecure
 *  +1  URL contains '@' symbol — suspicious redirect trick
 *  +1  Domain is an IP address instead of a hostname
 */
class UrlFeatureAnalyzer : UrlAnalyzer {

    override fun analyze(url: String): Int {
        val normalizedUrl = url.replaceFirst("://www.", "://")
        var score = 0
        if (hasHttp(normalizedUrl)) score++
        score += countAtSymbols(normalizedUrl)
        if (hasIpAddress(normalizedUrl)) score++
        return score
    }

    private fun hasHttp(url: String): Boolean =
        HTTP_PATTERN.containsMatchIn(url)

    private fun countAtSymbols(url: String): Int =
        url.count { it == '@' }

    private fun hasIpAddress(url: String): Boolean =
        IP_PATTERN.containsMatchIn(url)

    companion object {
        private val HTTP_PATTERN = Regex(""".*http://.*""")
        private val IP_PATTERN = Regex(
            """(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)"""
        )
    }
}
