package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.domain.ArchetypeRepository
import com.example.domain.BirthDate
import com.example.domain.NumerologyCalculator
import com.example.ui.KozmikUiState
import com.example.ui.components.HarmonyGaugeCanvas
import com.example.ui.components.KozmikDatePickerDialog
import com.example.ui.theme.LocalKozmikAccents

@Composable
fun CompatibilityScreen(
    uiState: KozmikUiState,
    onPartnerNameChange: (String) -> Unit,
    onPartnerBirthDateChange: (BirthDate) -> Unit,
    onCalculateCompatibility: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showPartnerDatePicker by remember { mutableStateOf(false) }
    val accents = LocalKozmikAccents.current
    val report = uiState.compatibilityReport
    val sharedArchetype = ArchetypeRepository.getArchetype(report.sharedEnergyNumber)

    if (showPartnerDatePicker) {
        KozmikDatePickerDialog(
            initialDate = uiState.partnerInputBirthDate,
            onDismiss = { showPartnerDatePicker = false },
            onConfirm = { picked ->
                onPartnerBirthDateChange(picked)
                showPartnerDatePicker = false
            }
        )
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 720.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Spiritual Harmony Banner
            item {
                Surface(
                    shape = RoundedCornerShape(26.dp),
                    modifier = Modifier.fillMaxWidth(),
                    tonalElevation = 4.dp
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_spiritual_harmony),
                            contentDescription = "İkili ruhsal uyum mandala görseli",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color(0xFF0E0A1C).copy(alpha = 0.25f),
                                            Color(0xFF0E0A1C).copy(alpha = 0.90f)
                                        )
                                    )
                                )
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "RUHSAL REZONANS",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = accents.roseAccent,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text = "İki Ruhun Sayı Uyumu",
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    color = Color.White
                                )
                            )
                            Text(
                                text = "Yaşam Yolu ve Ruh Dürtüsü frekanslarınızın ortak simyası",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color.White.copy(alpha = 0.88f)
                                )
                            )
                        }
                    }
                }
            }

            // Inputs Card
            item {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceContainerHigh,
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = "Birinci Ruh",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = uiState.storedProfile.fullName,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                                Text(
                                    text = NumerologyCalculator.formatTurkishDate(uiState.storedProfile.birthDate),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        OutlinedTextField(
                            value = uiState.partnerInputName,
                            onValueChange = onPartnerNameChange,
                            label = { Text("İkinci Kişi Ad ve Soyad") },
                            placeholder = { Text("Örn. Kaan Deniz") },
                            leadingIcon = { Icon(Icons.Outlined.Person, contentDescription = null) },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("partner_name_input")
                        )

                        OutlinedButton(
                            onClick = { showPartnerDatePicker = true },
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("partner_birth_date_button")
                        ) {
                            Icon(Icons.Filled.CalendarMonth, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Doğum Tarihi: ${NumerologyCalculator.formatTurkishDate(uiState.partnerInputBirthDate)}"
                            )
                        }

                        Button(
                            onClick = onCalculateCompatibility,
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("calculate_compatibility_button")
                        ) {
                            Icon(Icons.Filled.Favorite, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Ruhsal Uyumu Hesapla")
                        }
                    }
                }
            }

            // Result Card
            item {
                Card(
                    shape = RoundedCornerShape(26.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        HarmonyGaugeCanvas(score = report.harmonyScore)

                        Text(
                            text = report.bondTitle,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            PersonNumberBadge(
                                name = report.personOneName,
                                lifePath = report.personOneLifePath,
                                soulUrge = report.personOneSoul,
                                accent = accents.goldAccent
                            )
                            PersonNumberBadge(
                                name = "Ortak Bağ",
                                lifePath = report.sharedEnergyNumber,
                                soulUrge = null,
                                accent = accents.roseAccent
                            )
                            PersonNumberBadge(
                                name = report.personTwoName,
                                lifePath = report.personTwoLifePath,
                                soulUrge = report.personTwoSoul,
                                accent = accents.lavenderAccent
                            )
                        }

                        Text(
                            text = report.bondSummary,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.AutoAwesome,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = "Güçlü Sinerji (${sharedArchetype.title})",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = report.strengthsText,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }

                        Surface(
                            color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.45f),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Lightbulb,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.secondary
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = "Gelişim & Şifa Alanı",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                    Text(
                                        text = report.growthLessonText,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun PersonNumberBadge(
    name: String,
    lifePath: Int,
    soulUrge: Int?,
    accent: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.widthIn(max = 98.dp)
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(accent.copy(alpha = 0.16f))
                .border(1.5.dp, accent, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = lifePath.toString(),
                style = MaterialTheme.typography.titleLarge.copy(
                    color = accent,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Text(
            text = name,
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
        if (soulUrge != null) {
            Text(
                text = "Ruh: $soulUrge",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            Text(
                text = "Sentez",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
