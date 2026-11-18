package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.domain.CalculatedNumber
import com.example.domain.NumerologyChart
import com.example.ui.BreathPhase
import com.example.ui.BreathingRitualState
import com.example.ui.theme.LocalKozmikAccents
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun SacredNumerologyMandala(
    chart: NumerologyChart,
    onNodeClick: (CalculatedNumber) -> Unit,
    modifier: Modifier = Modifier
) {
    val accents = LocalKozmikAccents.current
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val tertiaryColor = MaterialTheme.colorScheme.tertiary

    val infiniteTransition = rememberInfiniteTransition(label = "sacred_mandala_rot")
    val slowRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 48000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "mandala_angle"
    )

    val orbitalNumbers = listOf(
        chart.expression,
        chart.soulUrge,
        chart.personality,
        chart.birthDay,
        chart.personalYear
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp)),
        shape = RoundedCornerShape(28.dp),
        tonalElevation = 4.dp
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .height(330.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_hero_cosmos),
                contentDescription = "Kozmik kutsal geometri arka planı",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF140F2D).copy(alpha = 0.45f),
                                Color(0xFF0B0917).copy(alpha = 0.88f)
                            )
                        )
                    )
            )

            Canvas(modifier = Modifier.fillMaxSize()) {
                val center = Offset(size.width / 2f, size.height / 2f)
                val maxRadius = min(size.width, size.height) * 0.41f

                drawCircle(
                    color = primaryColor.copy(alpha = 0.28f),
                    radius = maxRadius,
                    center = center,
                    style = Stroke(
                        width = 1.5.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    )
                )

                drawCircle(
                    color = secondaryColor.copy(alpha = 0.25f),
                    radius = maxRadius * 0.62f,
                    center = center,
                    style = Stroke(width = 1.dp.toPx())
                )

                rotate(degrees = slowRotation, pivot = center) {
                    val starRadius = maxRadius * 0.72f
                    val path1 = Path()
                    val path2 = Path()
                    for (i in 0 until 3) {
                        val a1 = (i * 120.0 - 90.0) * PI / 180.0
                        val a2 = (i * 120.0 + 90.0) * PI / 180.0
                        val p1 = Offset(
                            center.x + (starRadius * cos(a1)).toFloat(),
                            center.y + (starRadius * sin(a1)).toFloat()
                        )
                        val p2 = Offset(
                            center.x + (starRadius * cos(a2)).toFloat(),
                            center.y + (starRadius * sin(a2)).toFloat()
                        )
                        if (i == 0) {
                            path1.moveTo(p1.x, p1.y)
                            path2.moveTo(p2.x, p2.y)
                        } else {
                            path1.lineTo(p1.x, p1.y)
                            path2.lineTo(p2.x, p2.y)
                        }
                    }
                    path1.close()
                    path2.close()
                    drawPath(
                        path = path1,
                        color = accents.goldAccent.copy(alpha = 0.24f),
                        style = Stroke(width = 1.2.dp.toPx())
                    )
                    drawPath(
                        path = path2,
                        color = tertiaryColor.copy(alpha = 0.22f),
                        style = Stroke(width = 1.2.dp.toPx())
                    )
                }
            }

            // Center Life Path Orb
            val centerOrbSize = 96.dp
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(centerOrbSize)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                accents.goldAccent.copy(alpha = 0.95f),
                                Color(0xFF7D5313).copy(alpha = 0.95f)
                            )
                        )
                    )
                    .border(2.dp, Color(0xFFFFEAB5), CircleShape)
                    .clickable { onNodeClick(chart.lifePath) }
                    .testTag("mandala_center_life_path")
                    .semantics {
                        contentDescription = "Yaşam Yolu Sayısı ${chart.lifePath.value}, detay için dokunun"
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = chart.lifePath.value.toString(),
                        style = MaterialTheme.typography.displaySmall.copy(
                            color = Color(0xFF1F1300),
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "YAŞAM YOLU",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 9.sp,
                            color = Color(0xFF2E1E00),
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            // 5 Orbital Nodes positioned cleanly in dp
            val orbitRadiusPx = with(androidx.compose.ui.platform.LocalDensity.current) {
                (min(maxWidth.toPx(), maxHeight.toPx()) * 0.38f)
            }
            val nodeWidthPx = with(androidx.compose.ui.platform.LocalDensity.current) { 78.dp.toPx() }
            val nodeHeightPx = with(androidx.compose.ui.platform.LocalDensity.current) { 62.dp.toPx() }
            val centerX = with(androidx.compose.ui.platform.LocalDensity.current) { maxWidth.toPx() / 2f }
            val centerY = with(androidx.compose.ui.platform.LocalDensity.current) { maxHeight.toPx() / 2f }

            orbitalNumbers.forEachIndexed { index, calcNumber ->
                val angleRad = (-90.0 + index * 72.0) * PI / 180.0
                val x = centerX + (orbitRadiusPx * cos(angleRad)).toFloat() - nodeWidthPx / 2f
                val y = centerY + (orbitRadiusPx * sin(angleRad)).toFloat() - nodeHeightPx / 2f

                Box(
                    modifier = Modifier
                        .offset { IntOffset(x.roundToInt(), y.roundToInt()) }
                        .size(width = 78.dp, height = 62.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF1B1636).copy(alpha = 0.88f))
                        .border(
                            width = 1.dp,
                            color = accents.lavenderAccent.copy(alpha = 0.55f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable { onNodeClick(calcNumber) }
                        .testTag("mandala_node_${calcNumber.type.name.lowercase()}")
                        .padding(horizontal = 4.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = calcNumber.value.toString(),
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = accents.goldAccent,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = calcNumber.type.title,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 9.5.sp,
                                color = Color(0xFFECE6FF)
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BreathingLotusOrb(
    ritualState: BreathingRitualState,
    modifier: Modifier = Modifier
) {
    val accents = LocalKozmikAccents.current
    val targetFraction = when {
        !ritualState.isActive -> 0.72f
        ritualState.phase == BreathPhase.INHALE -> 1.0f
        ritualState.phase == BreathPhase.HOLD -> 1.0f
        else -> 0.56f
    }

    val animatedScale by animateFloatAsState(
        targetValue = targetFraction,
        animationSpec = tween(durationMillis = if (ritualState.phase == BreathPhase.HOLD) 400 else 3600),
        label = "breath_orb_scale"
    )

    Box(
        modifier = modifier.size(196.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val outerRadius = (min(size.width, size.height) / 2f) * 0.92f
            val innerRadius = outerRadius * animatedScale

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        accents.goldAccent.copy(alpha = 0.35f),
                        accents.lavenderAccent.copy(alpha = 0.14f),
                        Color.Transparent
                    ),
                    center = center,
                    radius = outerRadius
                ),
                radius = outerRadius,
                center = center
            )

            drawCircle(
                color = accents.lavenderAccent.copy(alpha = 0.35f),
                radius = outerRadius * 0.9f,
                center = center,
                style = Stroke(width = 1.5.dp.toPx())
            )

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        accents.goldAccent.copy(alpha = 0.85f),
                        accents.tealAccent.copy(alpha = 0.45f)
                    ),
                    center = center,
                    radius = innerRadius
                ),
                radius = innerRadius,
                center = center
            )

            drawCircle(
                color = Color.White.copy(alpha = 0.6f),
                radius = innerRadius,
                center = center,
                style = Stroke(width = 2.dp.toPx())
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = if (ritualState.isActive) "${ritualState.secondsRemainingInPhase}" else "4·4·4",
                style = MaterialTheme.typography.displaySmall.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = if (ritualState.isActive) ritualState.phase.label else "Hazır",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = Color.White.copy(alpha = 0.95f)
                )
            )
        }
    }
}

@Composable
fun HarmonyGaugeCanvas(
    score: Int,
    modifier: Modifier = Modifier
) {
    val accents = LocalKozmikAccents.current
    val animatedSweep by animateFloatAsState(
        targetValue = (score / 100f) * 260f,
        animationSpec = tween(durationMillis = 800),
        label = "harmony_gauge"
    )

    Box(
        modifier = modifier.size(164.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokePx = 12.dp.toPx()
            val arcSize = Size(size.width - strokePx, size.height - strokePx)
            val topLeft = Offset(strokePx / 2f, strokePx / 2f)

            drawArc(
                color = accents.lavenderAccent.copy(alpha = 0.22f),
                startAngle = 140f,
                sweepAngle = 260f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokePx, cap = StrokeCap.Round)
            )

            drawArc(
                brush = Brush.sweepGradient(
                    colors = listOf(
                        accents.roseAccent,
                        accents.goldAccent,
                        accents.tealAccent,
                        accents.roseAccent
                    )
                ),
                startAngle = 140f,
                sweepAngle = animatedSweep,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokePx, cap = StrokeCap.Round)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "%$score",
                style = MaterialTheme.typography.displayMedium.copy(
                    color = accents.goldAccent,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "Rezonans",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
