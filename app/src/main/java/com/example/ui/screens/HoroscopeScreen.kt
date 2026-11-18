package com.example.ui.screens

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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Diamond
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.SelfImprovement
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.domain.DailyHoroscope
import com.example.domain.HoroscopeRepository
import com.example.domain.NumerologyCalculator
import com.example.domain.ZodiacElement
import com.example.domain.ZodiacSign
import com.example.ui.KozmikUiState
import com.example.ui.theme.LocalKozmikAccents

@Composable
fun HoroscopeScreen(
    uiState: KozmikUiState,
    onElementFilterChange: (ZodiacElement?) -> Unit,
    onSelectSign: (ZodiacSign) -> Unit,
    modifier: Modifier = Modifier
) {
    val accents = LocalKozmikAccents.current
    val userSign = HoroscopeRepository.detectZodiacSign(uiState.storedProfile.birthDate)
    val userHoroscope = HoroscopeRepository.getDailyHoroscope(userSign, uiState.todayDate)

    val filteredSigns = ZodiacSign.entries.filter { sign ->
        uiState.horoscopeElementFilter == null || sign.element == uiState.horoscopeElementFilter
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
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Spiritual Zodiac Banner
            item {
                Surface(
                    shape = RoundedCornerShape(26.dp),
                    tonalElevation = 4.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(184.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_hero_cosmos),
                            contentDescription = "Günlük burç yorumları kozmik görsel",
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
                                            Color(0xFF0E0A1C).copy(alpha = 0.92f)
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
                                text = "GÜNLÜK BURÇ YORUMLARI • ${NumerologyCalculator.formatTurkishDate(uiState.todayDate).uppercase()}",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = accents.goldAccent,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text = "12 Burcun Günlük Kozmik Rehberi",
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    color = Color.White
                                )
                            )
                            Text(
                                text = "Doğum tarihine göre burcun: ${userSign.symbol} ${userSign.turkishName} (${userSign.dateRange})",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color.White.copy(alpha = 0.90f)
                                )
                            )
                        }
                    }
                }
            }

            // Highlight Card for User's Own Zodiac Sign
            item {
                ZodiacDailyCard(
                    horoscope = userHoroscope,
                    isUserSign = true,
                    onClick = { onSelectSign(userSign) }
                )
            }

            // Element Filter Chips
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        FilterChip(
                            selected = uiState.horoscopeElementFilter == null,
                            onClick = { onElementFilterChange(null) },
                            label = { Text("Tümü (12)") },
                            modifier = Modifier.testTag("horoscope_filter_all")
                        )
                    }
                    items(ZodiacElement.entries) { element ->
                        FilterChip(
                            selected = uiState.horoscopeElementFilter == element,
                            onClick = { onElementFilterChange(element) },
                            label = { Text(element.label) },
                            modifier = Modifier.testTag("horoscope_filter_${element.name.lowercase()}")
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Tüm Burçlar",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            items(filteredSigns, key = { it.name }) { sign ->
                val horoscope = HoroscopeRepository.getDailyHoroscope(sign, uiState.todayDate)
                ZodiacDailyCard(
                    horoscope = horoscope,
                    isUserSign = sign == userSign,
                    onClick = { onSelectSign(sign) }
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun ZodiacDailyCard(
    horoscope: DailyHoroscope,
    isUserSign: Boolean,
    onClick: () -> Unit
) {
    val sign = horoscope.sign
    val accent = Color(sign.accentHex)

    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .then(
                if (isUserSign) {
                    Modifier.border(1.5.dp, accent.copy(alpha = 0.7f), RoundedCornerShape(22.dp))
                } else Modifier
            )
            .clickable(onClick = onClick)
            .testTag("zodiac_card_${sign.name.lowercase()}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(accent.copy(alpha = 0.16f))
                    .border(1.5.dp, accent, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = sign.symbol,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = accent,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = sign.turkishName,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (isUserSign) {
                        Surface(
                            color = accent.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "SENİN BURCUN",
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
                    text = "${sign.dateRange} • ${sign.element.label} • Şanslı Sayı: ${horoscope.luckyNumber}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = horoscope.generalForecast,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = "Günlük yorumu oku",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun HoroscopeDetailBottomSheet(
    horoscope: DailyHoroscope,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val accents = LocalKozmikAccents.current
    val sign = horoscope.sign
    val accent = Color(sign.accentHex)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
                .padding(bottom = 28.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(206.dp)
            ) {
                Image(
                    painter = painterResource(id = sign.artType.drawableRes),
                    contentDescription = "${sign.turkishName} spiritüel görseli",
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
                                    Color(0xFF0E0A1C).copy(alpha = 0.92f)
                                )
                            )
                        )
                )

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.45f))
                        .testTag("close_horoscope_sheet_button")
                ) {
                    Icon(Icons.Filled.Close, contentDescription = "Kapat", tint = Color.White)
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(62.dp)
                            .clip(CircleShape)
                            .background(accent.copy(alpha = 0.22f))
                            .border(2.dp, accent, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = sign.symbol,
                            style = MaterialTheme.typography.headlineLarge.copy(
                                color = accent,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = "${sign.dateRange} • Enerji %${horoscope.energyPercent}",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = accents.goldAccent,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "${sign.turkishName} Günlük Yorumu",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                color = Color.White
                            )
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    HoroscopeChip(Icons.Outlined.Public, "Yönetici: ${sign.rulingPlanet}")
                    HoroscopeChip(Icons.Outlined.Diamond, "Kristal: ${sign.crystal}")
                    HoroscopeChip(Icons.Outlined.Palette, "Renk: ${sign.colorName}")
                    HoroscopeChip(Icons.Filled.AutoAwesome, "Şanslı Sayı: ${horoscope.luckyNumber}")
                }

                HoroscopeSectionBlock(
                    icon = Icons.Filled.AutoAwesome,
                    title = "Genel Kozmik Etki",
                    body = horoscope.generalForecast,
                    tint = MaterialTheme.colorScheme.primary
                )

                HoroscopeSectionBlock(
                    icon = Icons.Outlined.FavoriteBorder,
                    title = "Aşk & İlişkiler",
                    body = horoscope.loveForecast,
                    tint = accents.roseAccent
                )

                HoroscopeSectionBlock(
                    icon = Icons.Outlined.WorkOutline,
                    title = "Kariyer & Bolluk",
                    body = horoscope.careerForecast,
                    tint = accents.tealAccent
                )

                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f),
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.SelfImprovement,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Ruhsal Tavsiye & Olumlama",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Text(
                            text = horoscope.spiritualAdvice,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                        Text(
                            text = "“${horoscope.affirmation}”",
                            style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HoroscopeChip(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainer,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = text, style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
private fun HoroscopeSectionBlock(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    body: String,
    tint: Color
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainer,
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = icon, contentDescription = null, tint = tint, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = title, style = MaterialTheme.typography.titleSmall, color = tint)
            }
            Text(
                text = body,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
