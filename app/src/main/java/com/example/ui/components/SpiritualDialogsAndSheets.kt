package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Air
import androidx.compose.material.icons.outlined.Diamond
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.SelfImprovement
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.domain.BirthDate
import com.example.domain.NumerologyCalculator
import com.example.ui.DetailSheetContent
import com.example.ui.theme.LocalKozmikAccents
import java.util.Calendar
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KozmikDatePickerDialog(
    initialDate: BirthDate,
    onDismiss: () -> Unit,
    onConfirm: (BirthDate) -> Unit
) {
    val utcCal = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
        clear()
        set(initialDate.year, initialDate.month - 1, initialDate.day, 12, 0, 0)
    }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = utcCal.timeInMillis
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    val millis = datePickerState.selectedDateMillis
                    if (millis != null) {
                        val picked = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
                            timeInMillis = millis
                        }
                        onConfirm(
                            BirthDate(
                                day = picked.get(Calendar.DAY_OF_MONTH),
                                month = picked.get(Calendar.MONTH) + 1,
                                year = picked.get(Calendar.YEAR)
                            )
                        )
                    } else {
                        onDismiss()
                    }
                },
                modifier = Modifier.testTag("date_picker_confirm_button")
            ) {
                Text("Seç")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.testTag("date_picker_cancel_button")
            ) {
                Text("Vazgeç")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ArchetypeDetailBottomSheet(
    detail: DetailSheetContent,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val accents = LocalKozmikAccents.current
    val archetype = detail.archetype
    val calc = detail.calculatedNumber

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
            // Spiritual artwork header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {
                Image(
                    painter = painterResource(id = archetype.artType.drawableRes),
                    contentDescription = "${archetype.title} spiritüel görseli",
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
                        .testTag("close_detail_sheet_button")
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Detayı kapat",
                        tint = Color.White
                    )
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(Color(archetype.accentHex).copy(alpha = 0.24f))
                            .border(2.dp, Color(archetype.accentHex), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = archetype.number.toString(),
                            style = MaterialTheme.typography.headlineLarge.copy(
                                color = Color(archetype.accentHex),
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        if (calc != null) {
                            Text(
                                text = calc.type.title.uppercase(),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = accents.goldAccent,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        } else {
                            Text(
                                text = archetype.mysticalSymbol,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = accents.goldAccent,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        Text(
                            text = archetype.title,
                            style = MaterialTheme.typography.headlineSmall.copy(
                                color = Color.White
                            )
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Calculation steps card when opened from user's chart
                if (calc != null) {
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = RoundedCornerShape(18.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "İndirgeme Adımları",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                            calc.steps.forEach { step ->
                                Text(
                                    text = "• $step",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            if (calc.karmicNote != null) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = calc.karmicNote,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        color = accents.roseAccent
                                    )
                                )
                            }
                        }
                    }
                }

                // Spiritual Metadata Grid
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SpiritMetaBadge(Icons.Outlined.Air, "Element", archetype.element)
                    SpiritMetaBadge(Icons.Outlined.Public, "Gezegen", archetype.planet)
                    SpiritMetaBadge(Icons.Outlined.SelfImprovement, "Çakra", archetype.chakra)
                    SpiritMetaBadge(Icons.Outlined.Diamond, "Kristal", archetype.crystal)
                    SpiritMetaBadge(Icons.Outlined.Palette, "Renk", archetype.colorName)
                }

                Text(
                    text = archetype.deepMeaning,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Ruhsal Armağanların",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        archetype.strengths.forEach { strength ->
                            AssistChip(
                                onClick = {},
                                label = { Text(strength) },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                                )
                            )
                        }
                    }
                }

                Surface(
                    color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Lightbulb,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "Gölge Dersi",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Text(
                                text = archetype.shadowSide,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }

                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f),
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AutoAwesome,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = "Ruhsal Rehberlik",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = archetype.spiritualGuidance,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.outlineVariant,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.FavoriteBorder,
                                    contentDescription = null,
                                    tint = accents.roseAccent,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "“${archetype.dailyAffirmation}”",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontStyle = FontStyle.Italic
                                    ),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SpiritMetaBadge(
    icon: ImageVector,
    label: String,
    value: String
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainer,
        shape = RoundedCornerShape(14.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Column {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PythagoreanMatrixSheet(
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val accents = LocalKozmikAccents.current

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Pisagor Harf Tablosu",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Türkçe alfabe uyumlu sayısal titreşim karşılıkları",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.testTag("close_matrix_sheet_button")
                ) {
                    Icon(Icons.Filled.Close, contentDescription = "Kapat")
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                NumerologyCalculator.pythagoreanColumns.forEach { (digit, letters) ->
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(CircleShape)
                                    .background(accents.goldAccent.copy(alpha = 0.2f))
                                    .border(1.dp, accents.goldAccent, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = digit.toString(),
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = accents.goldAccent,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Text(
                                text = letters.joinToString("  •  "),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            Surface(
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Hesaplama İlkeleri",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "• Yaşam Yolu: Doğum tarihindeki gün, ay ve yılın ayrı ayrı sadeleştirilip toplanmasıyla bulunur.",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "• Kader & İfade: Tam isminizdeki tüm harflerin toplamından doğar.",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "• Ruh Dürtüsü: İsimdeki sesli harflerden (A, E, I, İ, O, Ö, U, Ü) hesaplanır.",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "• 11, 22 ve 33 Üstat Sayılar indirgenmeden yüksek frekanslarıyla yorumlanır.",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}
