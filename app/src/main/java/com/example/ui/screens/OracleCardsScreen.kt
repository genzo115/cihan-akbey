package com.example.ui.screens

import android.content.Intent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.SelfImprovement
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.domain.CardDeckType
import com.example.domain.NumerologyCalculator
import com.example.domain.OracleCardRepository
import com.example.domain.SpiritualCard
import com.example.ui.KozmikUiState
import com.example.ui.theme.LocalKozmikAccents

@Composable
fun OracleCardsScreen(
    uiState: KozmikUiState,
    onSelectDeck: (CardDeckType) -> Unit,
    onRevealMysteryCard: (Int) -> Unit,
    onShuffleCards: () -> Unit,
    onOpenCardDetail: (SpiritualCard) -> Unit,
    modifier: Modifier = Modifier
) {
    val accents = LocalKozmikAccents.current
    val activeDeck = uiState.selectedCardDeck
    val mysteryCards = OracleCardRepository.threeMysteryCards(
        deck = activeDeck,
        date = uiState.todayDate,
        drawSalt = uiState.cardDrawSalt
    )
    val allDeckCards = OracleCardRepository.cardsForDeck(activeDeck)
    val revealedCard = uiState.revealedMysteryIndex?.let { idx -> mysteryCards.getOrNull(idx) }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 720.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Spiritual Cards Hero Banner
            item {
                Surface(
                    shape = RoundedCornerShape(26.dp),
                    tonalElevation = 4.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(176.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_sacred_earth),
                            contentDescription = "Tarot, Melek ve Kehanet kartları görseli",
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
                                text = "GÜNLÜK KART REHBERLİĞİ • ${NumerologyCalculator.formatTurkishDate(uiState.todayDate).uppercase()}",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = accents.goldAccent,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text = "Tarot, Melek & Kehanet",
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    color = Color.White
                                )
                            )
                            Text(
                                text = activeDeck.subtitle,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color.White.copy(alpha = 0.88f)
                                )
                            )
                        }
                    }
                }
            }

            // 3 Deck Selector Chips
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(CardDeckType.entries) { deck ->
                        FilterChip(
                            selected = activeDeck == deck,
                            onClick = { onSelectDeck(deck) },
                            label = { Text(deck.title) },
                            modifier = Modifier.testTag("deck_chip_${deck.name.lowercase()}")
                        )
                    }
                }
            }

            // Interactive 3-Card Mystery Draw with 3D Flip
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
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Günün Kartını Seç",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = if (revealedCard == null) {
                                        "Sezgine güven ve kapalı 3 karttan birine dokun"
                                    } else {
                                        "Seçtiğin kart açıldı: ${revealedCard.title}"
                                    },
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            IconButton(
                                onClick = onShuffleCards,
                                modifier = Modifier.testTag("shuffle_cards_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Refresh,
                                    contentDescription = "Yeni kart çek",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            mysteryCards.forEachIndexed { index, card ->
                                val isRevealed = uiState.revealedMysteryIndex == index
                                FlippableMysteryCard(
                                    card = card,
                                    isRevealed = isRevealed,
                                    onClick = {
                                        if (isRevealed) {
                                            onOpenCardDetail(card)
                                        } else {
                                            onRevealMysteryCard(index)
                                        }
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("mystery_card_$index")
                                )
                            }
                        }

                        if (revealedCard != null) {
                            Surface(
                                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                                shape = RoundedCornerShape(18.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(14.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = revealedCard.coreMessage,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        Button(
                                            onClick = { onOpenCardDetail(revealedCard) },
                                            shape = RoundedCornerShape(12.dp),
                                            modifier = Modifier
                                                .weight(1f)
                                                .testTag("open_revealed_card_detail_button")
                                        ) {
                                            Text("Detaylı Yorum")
                                        }
                                        OutlinedButton(
                                            onClick = onShuffleCards,
                                            shape = RoundedCornerShape(12.dp),
                                            modifier = Modifier
                                                .weight(1f)
                                                .testTag("redraw_mystery_card_button")
                                        ) {
                                            Text("Yeniden Çek")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = "${activeDeck.title} Destesi (${allDeckCards.size} Kart)",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            items(allDeckCards, key = { it.id }) { card ->
                DeckListCardRow(
                    card = card,
                    onClick = { onOpenCardDetail(card) }
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun FlippableMysteryCard(
    card: SpiritualCard,
    isRevealed: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val accents = LocalKozmikAccents.current
    val accent = Color(card.accentHex)
    val density = LocalDensity.current.density
    val rotation by animateFloatAsState(
        targetValue = if (isRevealed) 180f else 0f,
        animationSpec = tween(durationMillis = 520),
        label = "card_flip_rotation"
    )

    Box(
        modifier = modifier
            .height(164.dp)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            }
            .clip(RoundedCornerShape(18.dp))
            .clickable(onClick = onClick)
    ) {
        if (rotation <= 90f) {
            // Card Back (Face-down spiritual artwork)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 1.5.dp,
                        color = accents.goldAccent.copy(alpha = 0.65f),
                        shape = RoundedCornerShape(18.dp)
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_spiritual_soul),
                    contentDescription = "Kapalı spiritüel kart",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF120C24).copy(alpha = 0.62f)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(accents.goldAccent.copy(alpha = 0.18f))
                                .border(1.dp, accents.goldAccent, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.AutoAwesome,
                                contentDescription = null,
                                tint = accents.goldAccent,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Text(
                            text = "Dokun & Aç",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else {
            // Card Front (mirrored horizontally back so text reads cleanly!)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { rotationY = 180f }
                    .border(1.8.dp, accent, RoundedCornerShape(18.dp))
            ) {
                Image(
                    painter = painterResource(id = card.artType.drawableRes),
                    contentDescription = card.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF0E0A1C).copy(alpha = 0.30f),
                                    Color(0xFF0E0A1C).copy(alpha = 0.92f)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(
                            text = card.symbol,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                color = accent
                            )
                        )
                        Text(
                            text = card.title,
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            ),
                            textAlign = TextAlign.Center,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DeckListCardRow(
    card: SpiritualCard,
    onClick: () -> Unit
) {
    val accent = Color(card.accentHex)

    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .clickable(onClick = onClick)
            .testTag("deck_item_${card.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(66.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Image(
                    painter = painterResource(id = card.artType.drawableRes),
                    contentDescription = card.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF0E0A1C).copy(alpha = 0.50f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = card.symbol,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = accent
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = card.codeLabel,
                    style = MaterialTheme.typography.labelSmall,
                    color = accent
                )
                Text(
                    text = card.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = card.coreMessage,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = "Kart mesajını oku",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun OracleCardDetailBottomSheet(
    card: SpiritualCard,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val accents = LocalKozmikAccents.current
    val accent = Color(card.accentHex)

    val shareText = buildString {
        appendLine("🔮 ${card.deck.title} — ${card.title}")
        appendLine("✨ ${card.coreMessage}")
        appendLine("💛 Rehberlik: ${card.guidance}")
        appendLine("🕊 Olumlama: “${card.affirmation}”")
        appendLine("Es&Ci Akbey • @konusankalpler_ea")
    }

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
                    .height(216.dp)
            ) {
                Image(
                    painter = painterResource(id = card.artType.drawableRes),
                    contentDescription = card.title,
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
                        .testTag("close_oracle_card_sheet_button")
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
                            text = card.symbol,
                            style = MaterialTheme.typography.headlineLarge.copy(
                                color = accent
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = "${card.deck.badgeLabel} • ${card.codeLabel}",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = accents.goldAccent,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = card.title,
                            style = MaterialTheme.typography.headlineSmall.copy(
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
                    card.keywords.forEach { kw ->
                        AssistChip(
                            onClick = {},
                            label = { Text(kw) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                            )
                        )
                    }
                }

                Text(
                    text = card.coreMessage,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = null,
                            tint = accents.roseAccent
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "Aşk & Kalp Yorumu",
                                style = MaterialTheme.typography.titleSmall,
                                color = accents.roseAccent
                            )
                            Text(
                                text = card.loveInsight,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

                Surface(
                    color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.45f),
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
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "Günün Ruhsal Tavsiyesi",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Text(
                                text = card.guidance,
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
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.SelfImprovement,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Günün Olumlaması",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                        Text(
                            text = "“${card.affirmation}”",
                            style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic)
                        )
                    }
                }

                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            putExtra(Intent.EXTRA_TEXT, shareText)
                            type = "text/plain"
                        }
                        runCatching {
                            context.startActivity(Intent.createChooser(intent, "Kart Mesajını Paylaş"))
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("share_oracle_card_button")
                ) {
                    Icon(Icons.Filled.Share, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Kart Mesajını Paylaş")
                }
            }
        }
    }
}
