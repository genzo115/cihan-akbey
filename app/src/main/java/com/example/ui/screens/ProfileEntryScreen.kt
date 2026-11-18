package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.VolumeOff
import androidx.compose.material.icons.outlined.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.domain.BirthDate
import com.example.domain.NumerologyCalculator
import com.example.ui.KozmikUiState
import com.example.ui.components.KozmikDatePickerDialog

// Los Angeles Sunset / Golden Hour Filter Palette
private val LaNightPlum = Color(0xFF23112D)
private val LaDuskMagenta = Color(0xFF6B2D5C)
private val LaSunsetCoral = Color(0xFFE97E6B)
private val LaGoldenAmber = Color(0xFFFFBC75)
private val LaPeachCream = Color(0xFFFFEAD6)
private val LaCardGlass = Color(0xFF2A1434).copy(alpha = 0.80f)

@Composable
fun ProfileEntryScreen(
    uiState: KozmikUiState,
    onNameChange: (String) -> Unit,
    onBirthDateChange: (BirthDate) -> Unit,
    onCalculateAndContinue: () -> Unit,
    onOpenMatrix: () -> Unit,
    onToggleSound: () -> Unit,
    onBackToResults: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showDatePicker by remember { mutableStateOf(false) }

    // Warm Los Angeles Golden Hour ColorMatrix LUT (warm amber highlights, rosy midtones, plum-violet shadows)
    val losAngelesFilterMatrix = remember {
        ColorMatrix(
            floatArrayOf(
                1.18f, 0.08f, 0.00f, 0f, 18f,
                0.04f, 0.96f, 0.04f, 0f, 8f,
                0.02f, 0.04f, 0.86f, 0f, 16f,
                0f,    0f,    0f,    1f, 0f
            )
        )
    }

    BackHandler(enabled = uiState.canReturnToResults) {
        onBackToResults()
    }

    if (showDatePicker) {
        KozmikDatePickerDialog(
            initialDate = uiState.inputBirthDate,
            onDismiss = { showDatePicker = false },
            onConfirm = { picked ->
                onBirthDateChange(picked)
                showDatePicker = false
            }
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("los_angeles_entry_screen"),
        contentAlignment = Alignment.TopCenter
    ) {
        // Full-bleed Los Angeles Filter Background Layer
        Image(
            painter = painterResource(id = R.drawable.img_hero_cosmos),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.colorMatrix(losAngelesFilterMatrix),
            modifier = Modifier.fillMaxSize()
        )

        // Los Angeles Golden Hour / Sunset Palm-Rose Atmospheric Gradient Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            LaNightPlum.copy(alpha = 0.78f),
                            LaDuskMagenta.copy(alpha = 0.62f),
                            LaSunsetCoral.copy(alpha = 0.44f),
                            LaNightPlum.copy(alpha = 0.92f)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .widthIn(max = 600.dp)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top bar with Es&Ci Akbey • konusankalpler_ea + sound & matrix buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (uiState.canReturnToResults) {
                    IconButton(
                        onClick = onBackToResults,
                        modifier = Modifier.testTag("entry_back_to_results_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Sonuçlara dön",
                            tint = LaPeachCream
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { openInstagramProfile(context) }
                            .padding(vertical = 4.dp, horizontal = 4.dp)
                            .testTag("entry_instagram_link")
                    ) {
                        Text(
                            text = stringResource(id = R.string.brand_creators),
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = LaPeachCream
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            InstagramBrandIcon(modifier = Modifier.size(13.dp))
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = stringResource(id = R.string.brand_instagram),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    textDecoration = TextDecoration.Underline
                                ),
                                color = LaGoldenAmber
                            )
                        }
                    }
                }

                Row {
                    IconButton(
                        onClick = onToggleSound,
                        modifier = Modifier.testTag("entry_sound_toggle")
                    ) {
                        Icon(
                            imageVector = if (uiState.storedProfile.soundEnabled) Icons.Outlined.VolumeUp else Icons.Outlined.VolumeOff,
                            contentDescription = if (uiState.storedProfile.soundEnabled) "Sesi kapat" else "Sesi aç",
                            tint = LaPeachCream
                        )
                    }
                    IconButton(
                        onClick = onOpenMatrix,
                        modifier = Modifier.testTag("entry_matrix_button")
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.GridView,
                            contentDescription = "Pisagor Harf Tablosu",
                            tint = LaPeachCream
                        )
                    }
                }
            }

            // Los Angeles Filtered Spiritual Lotus Banner
            Surface(
                shape = RoundedCornerShape(28.dp),
                color = Color.Transparent,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        brush = Brush.linearGradient(listOf(LaGoldenAmber.copy(alpha = 0.65f), LaSunsetCoral.copy(alpha = 0.55f))),
                        shape = RoundedCornerShape(28.dp)
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(206.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_spiritual_soul),
                        contentDescription = "Los Angeles gün batımı filtreli spiritüel lotus",
                        contentScale = ContentScale.Crop,
                        colorFilter = ColorFilter.colorMatrix(losAngelesFilterMatrix),
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        LaSunsetCoral.copy(alpha = 0.18f),
                                        LaNightPlum.copy(alpha = 0.90f)
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
                        Surface(
                            color = LaGoldenAmber.copy(alpha = 0.22f),
                            shape = RoundedCornerShape(50)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.AllInclusive,
                                    contentDescription = null,
                                    tint = LaGoldenAmber,
                                    modifier = Modifier.size(13.dp)
                                )
                                Text(
                                    text = "ÜCRETSİZ • SINIRSIZ ERİŞİM",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = LaGoldenAmber,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                        Text(
                            text = stringResource(id = R.string.entry_title),
                            style = MaterialTheme.typography.headlineLarge.copy(
                                color = Color.White
                            )
                        )
                        Text(
                            text = stringResource(id = R.string.entry_subtitle),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = LaPeachCream.copy(alpha = 0.90f)
                            )
                        )
                    }
                }
            }

            // Frosted Warm Sunset Input Card
            Surface(
                shape = RoundedCornerShape(26.dp),
                color = LaCardGlass,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = LaGoldenAmber.copy(alpha = 0.35f),
                        shape = RoundedCornerShape(26.dp)
                    )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Kişisel Bilgilerin",
                        style = MaterialTheme.typography.titleLarge,
                        color = LaGoldenAmber
                    )

                    OutlinedTextField(
                        value = uiState.inputName,
                        onValueChange = onNameChange,
                        label = { Text("Ad ve Soyad") },
                        placeholder = { Text("Örn. Defne Yılmaz") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = null,
                                tint = LaGoldenAmber
                            )
                        },
                        singleLine = true,
                        isError = uiState.nameValidationError != null,
                        supportingText = uiState.nameValidationError?.let { err ->
                            { Text(err, color = Color(0xFFFFB4AB)) }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = LaPeachCream,
                            focusedBorderColor = LaGoldenAmber,
                            unfocusedBorderColor = LaSunsetCoral.copy(alpha = 0.6f),
                            focusedLabelColor = LaGoldenAmber,
                            unfocusedLabelColor = LaPeachCream.copy(alpha = 0.78f),
                            cursorColor = LaGoldenAmber
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("entry_name_input")
                    )

                    OutlinedButton(
                        onClick = { showDatePicker = true },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = LaPeachCream
                        ),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            LaGoldenAmber.copy(alpha = 0.55f)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .testTag("entry_birth_date_button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CalendarMonth,
                            contentDescription = null,
                            tint = LaGoldenAmber
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Doğum Tarihi: ${NumerologyCalculator.formatTurkishDate(uiState.inputBirthDate)}",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    Button(
                        onClick = onCalculateAndContinue,
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color(0xFF28101C)
                        ),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(LaGoldenAmber, LaSunsetCoral)
                                )
                            )
                            .testTag("entry_calculate_button")
                    ) {
                        Icon(Icons.Filled.AutoAwesome, contentDescription = null)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Hesapla ve Sayılarını Gör",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }

            // Preview of free unlimited content
            Surface(
                color = LaCardGlass.copy(alpha = 0.68f),
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = if (uiState.isOnline) {
                            stringResource(id = R.string.online_unlimited_badge)
                        } else {
                            stringResource(id = R.string.offline_ready_badge)
                        },
                        style = MaterialTheme.typography.labelLarge,
                        color = LaGoldenAmber
                    )
                    Text(
                        text = "Günün Sayısı • Kişisel Sayılarının Anlamları • Uyum Analizi • 1–9 & Üstat Sayılar Arketipleri",
                        style = MaterialTheme.typography.bodySmall,
                        color = LaPeachCream.copy(alpha = 0.85f),
                        textAlign = TextAlign.Center
                    )
                    TextButton(
                        onClick = onOpenMatrix,
                        modifier = Modifier.testTag("entry_view_matrix_link")
                    ) {
                        Text(
                            text = "Pisagor Harf Tablosunu İncele",
                            color = LaGoldenAmber
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
