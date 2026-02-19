package com.example.urlscanner.domain.analyzer

/**
 * Analyzes HTML content to detect phishing indicators.
 * Implements [WebContentAnalyzer] — SRP: only responsible for HTML pattern checks.
 *
 * Scoring rules (each match adds +1):
 *  External <script src> tags
 *  <iframe> elements
 *  <img src> tags
 *  Email addresses in the page
 *  Popup window.open calls
 */
class WebHtmlAnalyzer : WebContentAnalyzer {

    override fun analyze(html: String): Int {
        var score = 0
        if (SCRIPT_PATTERN.containsMatchIn(html)) score++
        if (IFRAME_PATTERN.containsMatchIn(html)) score++
        if (IMAGE_PATTERN.containsMatchIn(html)) score++
        if (EMAIL_PATTERN.containsMatchIn(html)) score++
        if (POPUP_PATTERN.containsMatchIn(html)) score++
        return score
    }

    companion object {
        private val SCRIPT_PATTERN = Regex("""<script[^>]+src(.*?)[^>]*>""")
        private val IFRAME_PATTERN = Regex("""<iframe[^>]""")
        private val IMAGE_PATTERN  = Regex("""<img[^>]+src(.*?)[^>]*>""")
        private val EMAIL_PATTERN  = Regex("""[a-z0-9_.+-]+@[\da-z.-]+\.[a-z.]{2,6}""")
        private val POPUP_PATTERN  = Regex("""<a[^>]+onclick=["javascript:]+\[window\.open](.*?)\[style]["'][^>]*>""")
    }
}
