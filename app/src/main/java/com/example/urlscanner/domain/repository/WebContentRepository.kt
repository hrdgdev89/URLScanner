package com.example.urlscanner.domain.repository

/**
 * Repository interface for fetching web page content.
 *
 * Follows DIP (Dependency Inversion Principle) — the domain depends on this
 * abstraction, not on a concrete HTTP implementation.
 */
interface WebContentRepository {
    /**
     * Fetches raw HTML content from the given [url].
     * @return [Result] wrapping the HTML string on success, or an exception on failure.
     */
    suspend fun fetchHtml(url: String): Result<String>
}
