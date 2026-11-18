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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.domain.ArchetypeRepository
import com.example.domain.NumberArchetype
import com.example.ui.KozmikUiState
import com.example.ui.LibraryFilter

@Composable
fun LibraryScreen(
    uiState: KozmikUiState,
    onFilterChange: (LibraryFilter) -> Unit,
    onArchetypeClick: (NumberArchetype) -> Unit,
    modifier: Modifier = Modifier
) {
    val archetypes = when (uiState.libraryFilter) {
        LibraryFilter.ALL -> ArchetypeRepository.allArchetypes
        LibraryFilter.CORE -> ArchetypeRepository.allArchetypes.filter { it.number <= 9 }
        LibraryFilter.MASTER -> ArchetypeRepository.allArchetypes.filter { it.number > 9 }
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
            item {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Kutsal Sayı Arketipleri",
                        style = MaterialTheme.typography.headlineLarge
                    )
                    Text(
                        text = "1’den 9’a temel titreşimler ve 11, 22, 33 Üstat Sayılar",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(LibraryFilter.entries) { filter ->
                        FilterChip(
                            selected = uiState.libraryFilter == filter,
                            onClick = { onFilterChange(filter) },
                            label = { Text(filter.label) },
                            modifier = Modifier.testTag("library_filter_${filter.name.lowercase()}")
                        )
                    }
                }
            }

            items(archetypes, key = { it.number }) { archetype ->
                ArchetypeShowcaseCard(
                    archetype = archetype,
                    onClick = { onArchetypeClick(archetype) }
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun ArchetypeShowcaseCard(
    archetype: NumberArchetype,
    onClick: () -> Unit
) {
    val accent = Color(archetype.accentHex)

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable(onClick = onClick)
            .testTag("archetype_card_${archetype.number}")
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(118.dp)
            ) {
                Image(
                    painter = painterResource(id = archetype.artType.drawableRes),
                    contentDescription = "${archetype.title} arketip görseli",
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
                                    Color(0xFF0E0A1C).copy(alpha = 0.88f)
                                )
                            )
                        )
                )

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF151029).copy(alpha = 0.86f))
                            .border(1.5.dp, accent, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = archetype.number.toString(),
                            style = MaterialTheme.typography.headlineSmall.copy(
                                color = accent,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = archetype.mysticalSymbol,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = accent,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = archetype.title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color.White
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = archetype.summary,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceContainerHigh,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Element: ${archetype.element}",
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceContainerHigh,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = archetype.planet,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceContainerHigh,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = archetype.crystal.substringBefore(" &"),
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}
