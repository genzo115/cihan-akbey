package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
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
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.outlined.CloudDone
import androidx.compose.material.icons.outlined.VolumeOff
import androidx.compose.material.icons.outlined.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.CelestialGold
import com.example.ui.theme.MoonLavender
import com.example.ui.theme.RoseQuartz
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

private val SplashLaNightPlum = Color(0xFF23112D)
private val SplashLaDuskMagenta = Color(0xFF6B2D5C)
private val SplashLaSunsetCoral = Color(0xFFE97E6B)
private val SplashLaGoldenAmber = Color(0xFFFFBC75)
private val SplashLaPeachCream = Color(0xFFFFEAD6)

fun openInstagramProfile(context: Context) {
    val webUri = Uri.parse("https://www.instagram.com/konusankalpler_ea/")
    val appIntent = Intent(Intent.ACTION_VIEW, webUri).apply {
        setPackage("com.instagram.android")
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    val fallbackIntent = Intent(Intent.ACTION_VIEW, webUri).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    runCatching { context.startActivity(appIntent) }
        .onFailure { runCatching { context.startActivity(fallbackIntent) } }
}

@Composable
fun SplashScreen(
    isOnline: Boolean,
    soundEnabled: Boolean,
    onToggleSound: () -> Unit,
    onFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val progress = remember { Animatable(0f) }

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

    LaunchedEffect(Unit) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 6000, easing = LinearEasing)
        )
        onFinished()
    }

    val phaseMessage = when {
        progress.value < 0.34f -> stringResource(id = R.string.splash_loading_phase_1)
        progress.value < 0.68f -> stringResource(id = R.string.splash_loading_phase_2)
        else -> stringResource(id = R.string.splash_loading_phase_3)
    }

    val infiniteTransition = rememberInfiniteTransition(label = "splash_halo")
    val haloRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 18000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "splash_angle"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("splash_screen")
    ) {
        // Los Angeles filter background artwork
        Image(
            painter = painterResource(id = R.drawable.img_hero_cosmos),
            contentDescription = "Los Angeles filtreli kozmik açılış arka planı",
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
                            SplashLaNightPlum.copy(alpha = 0.80f),
                            SplashLaDuskMagenta.copy(alpha = 0.58f),
                            SplashLaSunsetCoral.copy(alpha = 0.42f),
                            SplashLaNightPlum.copy(alpha = 0.92f)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .padding(horizontal = 24.dp, vertical = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Branding: Es&Ci Akbey + directly below clickable Instagram icon & konusankalpler_ea
            Box(modifier = Modifier.fillMaxWidth()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 6.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.brand_creators),
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp
                        ),
                        modifier = Modifier.testTag("splash_brand_creators")
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .clickable { openInstagramProfile(context) }
                            .padding(horizontal = 14.dp, vertical = 10.dp)
                            .testTag("splash_instagram_row")
                    ) {
                        InstagramBrandIcon(modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(7.dp))
                        Text(
                            text = stringResource(id = R.string.brand_instagram),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = SplashLaGoldenAmber,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 0.3.sp,
                                textDecoration = TextDecoration.Underline
                            )
                        )
                    }
                }

                IconButton(
                    onClick = onToggleSound,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.14f))
                        .testTag("splash_sound_toggle")
                ) {
                    Icon(
                        imageVector = if (soundEnabled) Icons.Outlined.VolumeUp else Icons.Outlined.VolumeOff,
                        contentDescription = if (soundEnabled) "Arka plan sesini kapat" else "Arka plan sesini aç",
                        tint = SplashLaGoldenAmber
                    )
                }
            }

            // Center App Name & Sacred Geometry 6-Second Loader
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Box(
                    modifier = Modifier.size(160.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val center = Offset(size.width / 2f, size.height / 2f)
                        val radius = size.minDimension * 0.44f

                        drawCircle(
                            color = SplashLaGoldenAmber.copy(alpha = 0.40f),
                            radius = radius,
                            center = center,
                            style = Stroke(width = 1.5.dp.toPx())
                        )

                        rotate(degrees = haloRotation, pivot = center) {
                            val pathA = Path()
                            val pathB = Path()
                            for (i in 0 until 3) {
                                val a1 = (i * 120.0 - 90.0) * PI / 180.0
                                val a2 = (i * 120.0 + 90.0) * PI / 180.0
                                val p1 = Offset(
                                    center.x + (radius * 0.88f * cos(a1)).toFloat(),
                                    center.y + (radius * 0.88f * sin(a1)).toFloat()
                                )
                                val p2 = Offset(
                                    center.x + (radius * 0.88f * cos(a2)).toFloat(),
                                    center.y + (radius * 0.88f * sin(a2)).toFloat()
                                )
                                if (i == 0) {
                                    pathA.moveTo(p1.x, p1.y)
                                    pathB.moveTo(p2.x, p2.y)
                                } else {
                                    pathA.lineTo(p1.x, p1.y)
                                    pathB.lineTo(p2.x, p2.y)
                                }
                            }
                            pathA.close()
                            pathB.close()
                            drawPath(
                                path = pathA,
                                color = SplashLaGoldenAmber.copy(alpha = 0.68f),
                                style = Stroke(width = 1.6.dp.toPx())
                            )
                            drawPath(
                                path = pathB,
                                color = SplashLaSunsetCoral.copy(alpha = 0.60f),
                                style = Stroke(width = 1.6.dp.toPx())
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .size(66.dp)
                            .clip(CircleShape)
                            .background(SplashLaNightPlum.copy(alpha = 0.88f))
                            .border(1.5.dp, SplashLaGoldenAmber, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AutoAwesome,
                            contentDescription = null,
                            tint = SplashLaGoldenAmber,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.displayLarge.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.testTag("splash_app_name")
                    )
                    Text(
                        text = stringResource(id = R.string.app_subtitle),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = SplashLaPeachCream
                        ),
                        textAlign = TextAlign.Center
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                ) {
                    LinearProgressIndicator(
                        progress = { progress.value },
                        color = SplashLaGoldenAmber,
                        trackColor = Color.White.copy(alpha = 0.18f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(7.dp)
                            .clip(RoundedCornerShape(50))
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = phaseMessage,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = SplashLaPeachCream.copy(alpha = 0.86f)
                            )
                        )
                        Text(
                            text = "%${(progress.value * 100).roundToInt()}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = SplashLaGoldenAmber,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }

            // Online & Unlimited Free Badge at bottom
            Surface(
                color = Color.White.copy(alpha = 0.14f),
                shape = RoundedCornerShape(50),
                modifier = Modifier.padding(bottom = 6.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CloudDone,
                        contentDescription = null,
                        tint = SplashLaGoldenAmber,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = stringResource(
                            id = if (isOnline) R.string.online_unlimited_badge else R.string.offline_ready_badge
                        ),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = SplashLaPeachCream
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun InstagramBrandIcon(
    modifier: Modifier = Modifier
) {
    val gradient = Brush.linearGradient(
        colors = listOf(CelestialGold, RoseQuartz, MoonLavender)
    )
    Canvas(
        modifier = modifier.semantics {
            contentDescription = "Instagram"
        }
    ) {
        val strokeWidth = size.minDimension * 0.11f
        val corner = size.minDimension * 0.28f
        val inset = strokeWidth / 2f

        drawRoundRect(
            brush = gradient,
            topLeft = Offset(inset, inset),
            size = Size(size.width - strokeWidth, size.height - strokeWidth),
            cornerRadius = CornerRadius(corner, corner),
            style = Stroke(width = strokeWidth)
        )

        drawCircle(
            brush = gradient,
            radius = size.minDimension * 0.22f,
            center = Offset(size.width / 2f, size.height / 2f),
            style = Stroke(width = strokeWidth)
        )

        drawCircle(
            color = CelestialGold,
            radius = size.minDimension * 0.065f,
            center = Offset(size.width * 0.74f, size.height * 0.26f)
        )
    }
}
