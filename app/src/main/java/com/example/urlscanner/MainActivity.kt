package com.example.urlscanner

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.urlscanner.ui.navigation.AppNavigation
import com.example.urlscanner.ui.theme.URLScannerTheme

/**
 * Single-activity entry point. Extracts intent URL and hands everything
 * to Compose. No business logic lives here — SRP in action.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val initialUrl = resolveInitialUrl(intent)

        setContent {
            URLScannerTheme {
                AppNavigation(initialUrl = initialUrl)
            }
        }
    }

    /** Extracts a URL from the launch Intent if the app was opened via Share or browser link. */
    private fun resolveInitialUrl(intent: Intent?): String {
        if (intent == null) return ""
        return when (intent.action) {
            Intent.ACTION_VIEW -> intent.data?.toString().orEmpty()
            Intent.ACTION_SEND -> intent.getStringExtra(Intent.EXTRA_TEXT).orEmpty()
            else               -> ""
        }
    }
}
