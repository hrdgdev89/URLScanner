package com.example.urlscanner.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.urlscanner.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(onNavigateBack: () -> Unit) {
    Scaffold(
        containerColor = DeepNavy,
        topBar = {
            TopAppBar(
                title          = { Text("Contact Us", color = TextPrimary) },
                navigationIcon = {
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
            Spacer(Modifier.height(32.dp))

            Icon(
                Icons.Filled.AccountCircle,
                contentDescription = null,
                tint     = CyanPrimary,
                modifier = Modifier.size(80.dp)
            )

            Spacer(Modifier.height(12.dp))

            Text("Eko Murdiansyah", style = MaterialTheme.typography.headlineMedium, color = TextPrimary)
            Text("Android Developer", style = MaterialTheme.typography.bodyLarge, color = TextSecondary)

            Spacer(Modifier.height(32.dp))

            ContactCard(icon = Icons.Filled.Email,  label = "Email",   value = "eko8757@example.com")
            Spacer(Modifier.height(12.dp))
            ContactCard(icon = Icons.Filled.School, label = "Institution", value = "Universitas — Skripsi 2018")
            Spacer(Modifier.height(12.dp))
            ContactCard(icon = Icons.Filled.Code,   label = "GitHub",  value = "github.com/eko8757")

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ContactCard(icon: ImageVector, label: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape    = RoundedCornerShape(12.dp),
        colors   = CardDefaults.cardColors(containerColor = CardDark)
    ) {
        Row(
            modifier           = Modifier.padding(16.dp),
            verticalAlignment  = Alignment.CenterVertically
        ) {
            Icon(icon, null, tint = CyanPrimary, modifier = Modifier.size(24.dp))
            Spacer(Modifier.width(16.dp))
            Column {
                Text(label, style = MaterialTheme.typography.labelMedium, color = TextSecondary)
                Text(value, style = MaterialTheme.typography.bodyLarge,  color = TextPrimary)
            }
        }
    }
}
