package com.example.ui.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.WbTwilight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.domain.ArchetypeRepository
import com.example.domain.CalculatedNumber
import com.example.domain.NumerologyCalculator
import com.example.ui.KozmikUiState
import com.example.ui.components.SacredNumerologyMandala
import com.example.ui.theme.LocalKozmikAccents

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChartScreen(
    uiState: KozmikUiState,
    onEditEntryClick: () -> Unit,
    onOpenNumberDetail: (CalculatedNumber) -> Unit,
    onOpenDailyTab: () -> Unit,
    onOpenLibraryTab: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val accents = LocalKozmikAccents.current
    val chart = uiState.chart
    val lifeArchetype = ArchetypeRepository.getArchetype(chart.lifePath.value)
    val universalDayArchetype = ArchetypeRepository.getArchetype(uiState.universalDay.value)
    val personalDayArchetype = ArchetypeRepository.getArchetype(chart.personalDay.value)

    val shareSummaryText = buildString {
        appendLine("✨ Kozmik Sayı Haritam — ${chart.fullName}")
        appendLine("📅 Doğum Tarihi: ${NumerologyCalculator.formatTurkishDate(chart.birthDate)}")
        appendLine("🌟 Yaşam Yolu: ${chart.lifePath.value} (${lifeArchetype.title})")
        appendLine("🔮 Kader & İfade: ${chart.expression.value} | Ruh Dürtüsü: ${chart.soulUrge.value}")
        appendLine("🌙 Bugünün Kişisel Günü: ${chart.personalDay.value} (${personalDayArchetype.title})")
        appendLine("💫 Es&Ci Akbey • @konusankalpler_ea")
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
            // Spiritual Hero Header + Current Profile Summary
            item {
                Surface(
                    shape = RoundedCornerShape(26.dp),
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    tonalElevation = 4.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(166.dp)
                        ) {
                            Image(
                                painter = painterResource(id = lifeArchetype.artType.drawableRes),
                                contentDescription = "Ruhsal numeroloji kapak görseli",
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
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Filled.AutoAwesome,
                                        contentDescription = null,
                                        tint = accents.goldAccent,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "KİŞİSEL KOZMİK HARİTA",
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            color = accents.goldAccent,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                                Text(
                                    text = chart.fullName,
                                    style = MaterialTheme.typography.headlineLarge.copy(
                                        color = Color.White
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "${NumerologyCalculator.formatTurkishDate(chart.birthDate)} • ${lifeArchetype.title}",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color.White.copy(alpha = 0.88f)
                                    ),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.AllInclusive,
                                    contentDescription = null,
                                    tint = accents.tealAccent,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = if (uiState.isOnline) "Çevrimiçi • Sınırsız & Ücretsiz" else "Sınırsız & Ücretsiz Erişim",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            TextButton(
                                onClick = onEditEntryClick,
                                modifier = Modifier.testTag("change_profile_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Edit,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("İsim / Tarih Değiştir")
                            }
                        }
                    }
                }
            }

            // Today's Number Summary Strip (Günün Sayısı ve Anlamları)
            item {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.WbTwilight,
                                    contentDescription = null,
                                    tint = accents.goldAccent,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Günün Sayısı • ${NumerologyCalculator.formatTurkishDate(uiState.todayDate)}",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            TextButton(
                                onClick = onOpenDailyTab,
                                modifier = Modifier.testTag("chart_open_daily_button")
                            ) {
                                Text("Günlük Akış")
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            QuickDayHighlightPill(
                                label = "Evrensel Gün",
                                number = uiState.universalDay.value,
                                meaning = universalDayArchetype.summary,
                                accent = Color(universalDayArchetype.accentHex),
                                onClick = { onOpenNumberDetail(uiState.universalDay) },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("chart_universal_day_pill")
                            )
                            QuickDayHighlightPill(
                                label = "Kişisel Günün",
                                number = chart.personalDay.value,
                                meaning = personalDayArchetype.summary,
                                accent = Color(personalDayArchetype.accentHex),
                                onClick = { onOpenNumberDetail(chart.personalDay) },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("chart_personal_day_pill")
                            )
                        }
                    }
                }
            }

            // Interactive Sacred Geometry Mandala
            item {
                SacredNumerologyMandala(
                    chart = chart,
                    onNodeClick = onOpenNumberDetail
                )
            }

            // Core Numbers Section
            item {
                Text(
                    text = "Kişisel Sayılarının Anlamları",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            val coreCards = listOf(
                chart.lifePath,
                chart.expression,
                chart.soulUrge,
                chart.personality,
                chart.birthDay,
                chart.personalYear
            )

            items(coreCards.size) { index ->
                val calcNumber = coreCards[index]
                SpiritualNumberRowCard(
                    calculatedNumber = calcNumber,
                    onClick = { onOpenNumberDetail(calcNumber) }
                )
            }

            // Letter Breakdown Matrix for User's Name
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
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "İsminin Harf Titreşimleri",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Altın halkalar Ruh Dürtünü (sesli harfler), mor halkalar Dış Kişiliğini (sessiz harfler) oluşturur.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            chart.letterBreakdown.forEach { item ->
                                val ringColor = if (item.isVowel) accents.goldAccent else accents.lavenderAccent
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(ringColor.copy(alpha = 0.14f))
                                        .border(1.dp, ringColor.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                                        .padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = item.char.toString(),
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    )
                                    Text(
                                        text = item.digit.toString(),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = ringColor,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Share & Numerology Library Shortcuts
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = {
                            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                                putExtra(Intent.EXTRA_TEXT, shareSummaryText)
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, "Kozmik Haritamı Paylaş")
                            runCatching { context.startActivity(shareIntent) }
                        },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("chart_share_button")
                    ) {
                        Icon(Icons.Filled.Share, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Kozmik Haritamı Paylaş")
                    }

                    OutlinedButton(
                        onClick = onOpenLibraryTab,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("chart_open_library_button")
                    ) {
                        Icon(Icons.Outlined.MenuBook, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Tüm Numeroloji Sayılarının Anlamları (1–9 & Üstat Sayılar)")
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun QuickDayHighlightPill(
    label: String,
    number: Int,
    meaning: String,
    accent: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        shape = RoundedCornerShape(18.dp),
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(accent.copy(alpha = 0.18f))
                        .border(1.2.dp, accent, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = number.toString(),
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = accent,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
            Text(
                text = meaning,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun SpiritualNumberRowCard(
    calculatedNumber: CalculatedNumber,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val archetype = ArchetypeRepository.getArchetype(calculatedNumber.value)
    val accent = Color(archetype.accentHex)

    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .clickable(onClick = onClick)
            .testTag("number_card_${calculatedNumber.type.name.lowercase()}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(18.dp))
            ) {
                Image(
                    painter = painterResource(id = archetype.artType.drawableRes),
                    contentDescription = "${archetype.title} görseli",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF0E0A1C).copy(alpha = 0.45f)),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF140F28).copy(alpha = 0.8f))
                            .border(1.5.dp, accent, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = calculatedNumber.value.toString(),
                            style = MaterialTheme.typography.headlineSmall.copy(
                                color = accent,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = calculatedNumber.type.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (calculatedNumber.isMaster) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            color = accent.copy(alpha = 0.18f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "ÜSTAT",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = accent,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
                Text(
                    text = archetype.title,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = archetype.summary,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = "Detayı görüntüle",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
