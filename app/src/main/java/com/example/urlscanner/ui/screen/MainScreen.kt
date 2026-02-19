package com.example.urlscanner.ui.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.urlscanner.domain.model.PhishingResult
import com.example.urlscanner.presentation.ScanUiState
import com.example.urlscanner.presentation.ScanViewModel
import com.example.urlscanner.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: ScanViewModel,
    initialUrl: String = "",
    onNavigateToAbout: () -> Unit,
    onNavigateToContact: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var urlInput by remember { mutableStateOf(initialUrl) }
    var showMenu by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    // Dialogs
    when (val state = uiState) {
        is ScanUiState.Success -> ScanResultDialog(
            result    = state.result,
            onDismiss = viewModel::reset
        )
        is ScanUiState.Error -> ErrorDialog(
            message   = state.message,
            onDismiss = viewModel::reset
        )
        else -> {}
    }

    Scaffold(
        containerColor = DeepNavy,
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Shield, null, tint = CyanPrimary, modifier = Modifier.size(22.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("URL Scanner", color = TextPrimary, style = MaterialTheme.typography.titleLarge)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CardDark),
                actions = {
                    Box {
                        IconButton(onClick = { showMenu = true }) {
                            Icon(Icons.Filled.MoreVert, "Menu", tint = TextSecondary)
                        }
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false },
                            modifier = Modifier.background(SurfaceDark)
                        ) {
                            DropdownMenuItem(
                                text = { Text("About", color = TextPrimary) },
                                leadingIcon = { Icon(Icons.Filled.Info, null, tint = CyanPrimary) },
                                onClick = { showMenu = false; onNavigateToAbout() }
                            )
                            DropdownMenuItem(
                                text = { Text("Contact Us", color = TextPrimary) },
                                leadingIcon = { Icon(Icons.Filled.Person, null, tint = CyanPrimary) },
                                onClick = { showMenu = false; onNavigateToContact() }
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(40.dp))

            // ── Shield Hero ────────────────────────────────────────────────
            ShieldHero(isLoading = uiState is ScanUiState.Loading)

            Spacer(Modifier.height(20.dp))

            Text(
                "Phishing Detector",
                style     = MaterialTheme.typography.headlineMedium,
                color     = TextPrimary
            )
            Text(
                "Enter a URL to check if it's safe",
                style     = MaterialTheme.typography.bodyMedium,
                color     = TextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(36.dp))

            // ── URL Input ──────────────────────────────────────────────────
            OutlinedTextField(
                value         = urlInput,
                onValueChange = { urlInput = it },
                modifier      = Modifier.fillMaxWidth(),
                label         = { Text("Enter URL", color = TextHint) },
                placeholder   = { Text("https://example.com", color = TextHint) },
                leadingIcon   = { Icon(Icons.Filled.Link, null, tint = CyanPrimary) },
                trailingIcon  = {
                    if (urlInput.isNotEmpty()) {
                        IconButton(onClick = { urlInput = "" }) {
                            Icon(Icons.Filled.Clear, "Clear", tint = TextSecondary)
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Uri,
                    imeAction    = ImeAction.Go
                ),
                keyboardActions = KeyboardActions(onGo = {
                    keyboardController?.hide()
                    viewModel.analyze(urlInput)
                }),
                singleLine = true,
                colors     = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor    = CyanPrimary,
                    unfocusedBorderColor  = BorderColor,
                    focusedTextColor      = TextPrimary,
                    unfocusedTextColor    = TextPrimary,
                    cursorColor           = CyanPrimary,
                    focusedLabelColor     = CyanPrimary,
                    unfocusedLabelColor   = TextSecondary,
                    focusedContainerColor   = SurfaceDark,
                    unfocusedContainerColor = SurfaceDark
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.height(16.dp))

            // ── Scan Button ────────────────────────────────────────────────
            Button(
                onClick  = {
                    keyboardController?.hide()
                    viewModel.analyze(urlInput)
                },
                enabled  = uiState !is ScanUiState.Loading,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape    = RoundedCornerShape(12.dp),
                colors   = ButtonDefaults.buttonColors(
                    containerColor         = CyanPrimary,
                    contentColor           = DeepNavy,
                    disabledContainerColor = BorderColor,
                    disabledContentColor   = TextSecondary
                )
            ) {
                AnimatedContent(
                    targetState    = uiState is ScanUiState.Loading,
                    transitionSpec = { fadeIn(tween(200)) togetherWith fadeOut(tween(200)) },
                    label          = "scan_button_content"
                ) { isLoading ->
                    if (isLoading) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CircularProgressIndicator(
                                modifier    = Modifier.size(18.dp),
                                color       = TextSecondary,
                                strokeWidth = 2.dp
                            )
                            Spacer(Modifier.width(8.dp))
                            Text("Scanning...", style = MaterialTheme.typography.labelLarge)
                        }
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.Search, null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("Scan URL", style = MaterialTheme.typography.labelLarge)
                        }
                    }
                }
            }

            Spacer(Modifier.height(28.dp))

            // ── Info Card ──────────────────────────────────────────────────
            HowItWorksCard()

            Spacer(Modifier.height(24.dp))
        }
    }
}

// ── Sub-composables ─────────────────────────────────────────────────────────

@Composable
private fun ShieldHero(isLoading: Boolean) {
    val borderAlpha = if (isLoading) 0.8f else 0.4f
    Box(
        modifier = Modifier
            .size(96.dp)
            .clip(CircleShape)
            .background(
                Brush.radialGradient(
                    colors = listOf(CyanPrimary.copy(alpha = if (isLoading) 0.25f else 0.12f), Color.Transparent)
                )
            )
            .border(2.dp, CyanPrimary.copy(alpha = borderAlpha), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Shield,
            contentDescription = "Shield",
            tint     = CyanPrimary,
            modifier = Modifier.size(48.dp)
        )
    }
}

@Composable
private fun HowItWorksCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape    = RoundedCornerShape(12.dp),
        colors   = CardDefaults.cardColors(containerColor = CardDark)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("How it works", style = MaterialTheme.typography.labelLarge, color = CyanPrimary)
            Spacer(Modifier.height(10.dp))
            InfoRow(Icons.Filled.Link,     "Checks URL structure — http, IP address, @ symbol")
            InfoRow(Icons.Filled.Code,     "Scans page HTML for iframe, script, popup patterns")
            InfoRow(Icons.Filled.Warning,  "Score > 2 triggers a phishing warning")
        }
    }
}

@Composable
private fun InfoRow(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier          = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(icon, null, tint = TextSecondary, modifier = Modifier.size(15.dp))
        Spacer(Modifier.width(8.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
    }
}

@Composable
private fun ErrorDialog(message: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon             = { Icon(Icons.Filled.Error, null, tint = DangerRed, modifier = Modifier.size(28.dp)) },
        title            = { Text("Error", color = DangerRed) },
        text             = { Text(message, color = TextPrimary, style = MaterialTheme.typography.bodyMedium) },
        confirmButton    = {
            TextButton(onClick = onDismiss) { Text("OK", color = CyanPrimary) }
        },
        containerColor   = CardDark
    )
}

@Composable
private fun ScanResultDialog(result: PhishingResult, onDismiss: () -> Unit) {
    val isPhishing  = result.isPhishing
    val accentColor = if (isPhishing) DangerRed else SafeGreen
    val icon        = if (isPhishing) Icons.Filled.Warning else Icons.Filled.CheckCircle
    val title       = if (isPhishing) "⚠️ Phishing Detected!" else "✅ Site Looks Safe"
    val message     = if (isPhishing)
        "This site shows ${result.totalScore} phishing indicators. Proceed with extreme caution!"
    else
        "No significant phishing pattern detected (score: ${result.totalScore})."

    AlertDialog(
        onDismissRequest = onDismiss,
        icon             = { Icon(icon, null, tint = accentColor, modifier = Modifier.size(32.dp)) },
        title            = { Text(title, color = accentColor, textAlign = TextAlign.Center) },
        text             = {
            Column {
                Text(message, color = TextSecondary, style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(16.dp))
                HorizontalDivider(color = BorderColor)
                Spacer(Modifier.height(12.dp))
                ScoreRow("URL Score", result.urlScore)
                ScoreRow("Web Score", result.webScore)
                ScoreRow("Total Score", result.totalScore, bold = true)
            }
        },
        confirmButton    = {
            Button(
                onClick = onDismiss,
                colors  = ButtonDefaults.buttonColors(containerColor = CyanPrimary)
            ) { Text("Done", color = DeepNavy) }
        },
        containerColor = CardDark
    )
}

@Composable
private fun ScoreRow(label: String, score: Int, bold: Boolean = false) {
    val style = if (bold) MaterialTheme.typography.labelLarge else MaterialTheme.typography.bodyMedium
    Row(
        modifier              = Modifier.fillMaxWidth().padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = TextSecondary, style = style)
        Text(
            "$score",
            color = if (score > 0) WarnOrange else SafeGreen,
            style = style
        )
    }
}
