package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Diamond
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.SelfImprovement
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.domain.ArchetypeRepository
import com.example.domain.CalculatedNumber
import com.example.domain.NumerologyCalculator
import com.example.ui.KozmikUiState
import com.example.ui.components.BreathingLotusOrb
import com.example.ui.theme.LocalKozmikAccents

@Composable
fun DailyEnergyScreen(
    uiState: KozmikUiState,
    onOpenNumberDetail: (CalculatedNumber) -> Unit,
    onToggleBreathingRitual: () -> Unit,
    modifier: Modifier = Modifier
) {
    val accents = LocalKozmikAccents.current
    val personalDay = uiState.chart.personalDay
    val universalDay = uiState.universalDay
    val personalYear = uiState.chart.personalYear
    val personalDayArchetype = ArchetypeRepository.getArchetype(personalDay.value)
    val universalDayArchetype = ArchetypeRepository.getArchetype(universalDay.value)

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
            // Today's Spiritual Flow Banner
            item {
                Surface(
                    shape = RoundedCornerShape(26.dp),
                    modifier = Modifier.fillMaxWidth(),
                    tonalElevation = 4.dp
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(210.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_sacred_earth),
                            contentDescription = "Günün spiritüel gökyüzü görseli",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color(0xFF0E0A1C).copy(alpha = 0.2f),
                                            Color(0xFF0E0A1C).copy(alpha = 0.92f)
                                        )
                                    )
                                )
                        )

                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = NumerologyCalculator.formatTurkishDate(uiState.todayDate).uppercase(),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = accents.goldAccent,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text = "Bugünün Kozmik Akışı",
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    color = Color.White
                                )
                            )
                            Text(
                                text = "${uiState.chart.fullName} için kişisel gün enerjisi: ${personalDayArchetype.title}",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color.White.copy(alpha = 0.88f)
                                )
                            )
                        }
                    }
                }
            }

            // Personal Day vs Universal Day Split Cards
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    DailyVibrationTile(
                        title = "Kişisel Gün",
                        number = personalDay.value,
                        archetypeTitle = personalDayArchetype.title,
                        accent = Color(personalDayArchetype.accentHex),
                        onClick = { onOpenNumberDetail(personalDay) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("daily_personal_day_card")
                    )
                    DailyVibrationTile(
                        title = "Evrensel Gün",
                        number = universalDay.value,
                        archetypeTitle = universalDayArchetype.title,
                        accent = Color(universalDayArchetype.accentHex),
                        onClick = { onOpenNumberDetail(universalDay) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("daily_universal_day_card")
                    )
                }
            }

            // Daily Affirmation Card with Soul Lotus Art
            item {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(130.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_spiritual_soul),
                                contentDescription = "Altın lotus olumlama görseli",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                MaterialTheme.colorScheme.surfaceContainer
                                            )
                                        )
                                    )
                            )
                            Row(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(horizontal = 18.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.FavoriteBorder,
                                    contentDescription = null,
                                    tint = accents.roseAccent
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Günün Olumlaması",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        Column(
                            modifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "“${personalDayArchetype.dailyAffirmation}”",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontStyle = FontStyle.Italic
                                ),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = personalDayArchetype.spiritualGuidance,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Surface(
                                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                                    shape = RoundedCornerShape(14.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Diamond,
                                            contentDescription = null,
                                            tint = accents.tealAccent,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column {
                                            Text(
                                                text = "Kristal",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                            Text(
                                                text = personalDayArchetype.crystal,
                                                style = MaterialTheme.typography.labelLarge
                                            )
                                        }
                                    }
                                }
                                Surface(
                                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                                    shape = RoundedCornerShape(14.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.SelfImprovement,
                                            contentDescription = null,
                                            tint = accents.lavenderAccent,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column {
                                            Text(
                                                text = "Kişisel Yıl",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                            Text(
                                                text = "${personalYear.value}. Döngü",
                                                style = MaterialTheme.typography.labelLarge
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Mindful 4-4-4 Breathing & Intention Ritual
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.AutoAwesome,
                                contentDescription = null,
                                tint = accents.goldAccent,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Nefes ve Niyet Ritüeli",
                                style = MaterialTheme.typography.headlineSmall
                            )
                        }

                        Text(
                            text = if (uiState.breathingRitual.isActive) {
                                uiState.breathingRitual.phase.guidance
                            } else {
                                "Günün ${personalDay.value} titreşimini kalbinde dengelemek için 4-4-4 nefes ritüelini başlat."
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )

                        BreathingLotusOrb(ritualState = uiState.breathingRitual)

                        if (uiState.breathingRitual.completedCycles > 0) {
                            Text(
                                text = "Tamamlanan döngü: ${uiState.breathingRitual.completedCycles}",
                                style = MaterialTheme.typography.labelMedium,
                                color = accents.tealAccent
                            )
                        }

                        Button(
                            onClick = onToggleBreathingRitual,
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (uiState.breathingRitual.isActive) {
                                    MaterialTheme.colorScheme.secondaryContainer
                                } else {
                                    MaterialTheme.colorScheme.primary
                                },
                                contentColor = if (uiState.breathingRitual.isActive) {
                                    MaterialTheme.colorScheme.onSecondaryContainer
                                } else {
                                    MaterialTheme.colorScheme.onPrimary
                                }
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("breathing_ritual_toggle_button")
                        ) {
                            Icon(
                                imageVector = if (uiState.breathingRitual.isActive) {
                                    Icons.Filled.Pause
                                } else {
                                    Icons.Filled.PlayArrow
                                },
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (uiState.breathingRitual.isActive) "Ritüeli Durdur" else "Ritüeli Başlat",
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun DailyVibrationTile(
    title: String,
    number: Int,
    archetypeTitle: String,
    accent: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        modifier = modifier
            .clip(RoundedCornerShape(22.dp))
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Box(
                modifier = Modifier
                    .size(58.dp)
                    .clip(CircleShape)
                    .background(accent.copy(alpha = 0.16f))
                    .border(2.dp, accent, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = number.toString(),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = accent,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Text(
                text = archetypeTitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}
