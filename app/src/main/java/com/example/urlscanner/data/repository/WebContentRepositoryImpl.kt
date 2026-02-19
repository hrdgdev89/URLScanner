package com.example.urlscanner.data.repository

import com.example.urlscanner.domain.repository.WebContentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

/**
 * Concrete implementation of [WebContentRepository] using OkHttp.
 *
 * Fetches HTML content on the IO dispatcher — no network-on-main-thread risk.
 * Follows SRP — only responsible for fetching raw HTML over HTTP/HTTPS.
 */
class WebContentRepositoryImpl(
    private val httpClient: OkHttpClient = buildHttpClient()
) : WebContentRepository {

    override suspend fun fetchHtml(url: String): Result<String> =
        withContext(Dispatchers.IO) {
            runCatching {
                val request = Request.Builder()
                    .url(url)
                    .header("User-Agent", "Mozilla/5.0 (Android; URLScanner/1.0)")
                    .build()

                httpClient.newCall(request).execute().use { response ->
                    response.body?.string().orEmpty()
                }
            }
        }

    companion object {
        private fun buildHttpClient(): OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .followRedirects(true)
            .build()
    }
}
