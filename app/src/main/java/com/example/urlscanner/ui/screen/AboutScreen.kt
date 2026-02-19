package com.example.urlscanner.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.urlscanner.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(onNavigateBack: () -> Unit) {
    Scaffold(
        containerColor = DeepNavy,
        topBar = {
            TopAppBar(
                title              = { Text("About", color = TextPrimary) },
                navigationIcon     = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = TextSecondary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CardDark)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))

            Icon(
                Icons.Filled.Shield,
                contentDescription = null,
                tint     = CyanPrimary,
                modifier = Modifier.size(72.dp)
            )

            Spacer(Modifier.height(12.dp))

            Text("URL Scanner", style = MaterialTheme.typography.headlineMedium, color = TextPrimary)
            Text("Phishing Detection App", style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
            Text("v1.0", style = MaterialTheme.typography.labelMedium, color = TextHint)

            Spacer(Modifier.height(28.dp))

            AboutCard(
                title   = "What is Phishing?",
                content = "Phishing is a cyber attack where malicious actors create fake websites " +
                        "that mimic legitimate ones to steal personal data like passwords or credit card numbers."
            )

            Spacer(Modifier.height(16.dp))

            AboutCard(
                title   = "How Detection Works",
                content = "URL Scanner checks two dimensions:\n\n" +
                        "• URL Structure — detects HTTP (not HTTPS), IP-based domains, and suspicious '@' characters\n\n" +
                        "• Web Content — scans HTML for iframes, external scripts, email addresses, and popups\n\n" +
                        "A total score above 2 triggers a phishing warning."
            )

            Spacer(Modifier.height(16.dp))

            AboutCard(
                title   = "Developer",
                content = "Eko Murdiansyah\nSkripsi Project — 2018\nRefactored with Clean Architecture + Jetpack Compose"
            )

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun AboutCard(title: String, content: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape    = RoundedCornerShape(12.dp),
        colors   = CardDefaults.cardColors(containerColor = CardDark)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium, color = CyanPrimary)
            Spacer(Modifier.height(8.dp))
            Text(content, style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
        }
    }
}
