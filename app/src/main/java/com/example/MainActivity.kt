package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.WbTwilight
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.NightsStay
import androidx.compose.material.icons.outlined.Style
import androidx.compose.material.icons.outlined.VolumeOff
import androidx.compose.material.icons.outlined.VolumeUp
import androidx.compose.material.icons.outlined.WbTwilight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.audio.SpiritualAudioManager
import com.example.data.NetworkStatusObserver
import com.example.data.UserPreferencesRepository
import com.example.domain.ThemePreference
import com.example.ui.AppStage
import com.example.ui.KozmikUiState
import com.example.ui.KozmikViewModel
import com.example.ui.components.ArchetypeDetailBottomSheet
import com.example.ui.components.PythagoreanMatrixSheet
import com.example.ui.screens.ChartScreen
import com.example.ui.screens.CompatibilityScreen
import com.example.ui.screens.DailyEnergyScreen
import com.example.ui.screens.HoroscopeDetailBottomSheet
import com.example.ui.screens.HoroscopeScreen
import com.example.ui.screens.LibraryScreen
import com.example.ui.screens.OracleCardDetailBottomSheet
import com.example.ui.screens.OracleCardsScreen
import com.example.ui.screens.ProfileEntryScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.theme.KozmikSayiTheme

private const val ROUTE_CHART = "chart_screen"
private const val ROUTE_DAILY = "daily_energy_screen"
private const val ROUTE_COMPAT = "compatibility_screen"
private const val ROUTE_LIBRARY = "library_screen"
private const val ROUTE_HOROSCOPE = "horoscope_screen"
private const val ROUTE_CARDS = "oracle_cards_screen"

private data class NavTab(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
)

private val appTabs = listOf(
    NavTab(
        route = ROUTE_CHART,
        label = "Haritam",
        selectedIcon = Icons.Filled.AutoAwesome,
        unselectedIcon = Icons.Outlined.AutoAwesome,
        testTag = "nav_tab_chart"
    ),
    NavTab(
        route = ROUTE_DAILY,
        label = "Günlük",
        selectedIcon = Icons.Filled.WbTwilight,
        unselectedIcon = Icons.Outlined.WbTwilight,
        testTag = "nav_tab_daily"
    ),
    NavTab(
        route = ROUTE_COMPAT,
        label = "Uyum",
        selectedIcon = Icons.Filled.Favorite,
        unselectedIcon = Icons.Outlined.FavoriteBorder,
        testTag = "nav_tab_compat"
    ),
    NavTab(
        route = ROUTE_LIBRARY,
        label = "Sayılar",
        selectedIcon = Icons.Filled.MenuBook,
        unselectedIcon = Icons.Outlined.MenuBook,
        testTag = "nav_tab_library"
    ),
    NavTab(
        route = ROUTE_HOROSCOPE,
        label = "Burçlar",
        selectedIcon = Icons.Filled.NightsStay,
        unselectedIcon = Icons.Outlined.NightsStay,
        testTag = "nav_tab_horoscope"
    ),
    NavTab(
        route = ROUTE_CARDS,
        label = "Kartlar",
        selectedIcon = Icons.Filled.Style,
        unselectedIcon = Icons.Outlined.Style,
        testTag = "nav_tab_cards"
    )
)

class MainActivity : ComponentActivity() {

    private lateinit var audioManager: SpiritualAudioManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        audioManager = SpiritualAudioManager(applicationContext)
        val prefsRepository = UserPreferencesRepository(applicationContext)
        val networkObserver = NetworkStatusObserver(applicationContext)

        setContent {
            val viewModel: KozmikViewModel = viewModel(
                factory = KozmikViewModel.Factory(prefsRepository, networkObserver)
            )
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val lifecycleOwner = LocalLifecycleOwner.current

            LaunchedEffect(uiState.storedProfile.soundEnabled) {
                audioManager.setSoundEnabled(uiState.storedProfile.soundEnabled)
            }

            DisposableEffect(lifecycleOwner) {
                val observer = LifecycleEventObserver { _, event ->
                    when (event) {
                        Lifecycle.Event.ON_START -> audioManager.onForegroundChanged(true)
                        Lifecycle.Event.ON_STOP -> audioManager.onForegroundChanged(false)
                        else -> Unit
                    }
                }
                lifecycleOwner.lifecycle.addObserver(observer)
                onDispose {
                    lifecycleOwner.lifecycle.removeObserver(observer)
                }
            }

            val isDarkTheme = when (uiState.storedProfile.themePreference) {
                ThemePreference.DARK -> true
                ThemePreference.LIGHT -> false
                ThemePreference.SYSTEM -> isSystemInDarkTheme()
            }

            KozmikSayiTheme(darkTheme = isDarkTheme) {
                KozmikAppRoot(
                    uiState = uiState,
                    viewModel = viewModel,
                    isDarkTheme = isDarkTheme
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::audioManager.isInitialized) {
            audioManager.release()
        }
    }
}

@Composable
private fun KozmikAppRoot(
    uiState: KozmikUiState,
    viewModel: KozmikViewModel,
    isDarkTheme: Boolean
) {
    if (uiState.selectedDetail != null) {
        ArchetypeDetailBottomSheet(
            detail = uiState.selectedDetail,
            onDismiss = viewModel::closeDetailSheet
        )
    }

    if (uiState.selectedHoroscope != null) {
        HoroscopeDetailBottomSheet(
            horoscope = uiState.selectedHoroscope,
            onDismiss = viewModel::closeHoroscopeDetail
        )
    }

    if (uiState.selectedOracleCard != null) {
        OracleCardDetailBottomSheet(
            card = uiState.selectedOracleCard,
            onDismiss = viewModel::closeOracleCardDetail
        )
    }

    if (uiState.showMatrixSheet) {
        PythagoreanMatrixSheet(
            onDismiss = { viewModel.setMatrixSheetVisible(false) }
        )
    }

    Crossfade(
        targetState = uiState.appStage,
        animationSpec = tween(durationMillis = 400),
        label = "app_stage_transition"
    ) { stage ->
        when (stage) {
            AppStage.SPLASH -> {
                SplashScreen(
                    isOnline = uiState.isOnline,
                    soundEnabled = uiState.storedProfile.soundEnabled,
                    onToggleSound = viewModel::toggleSoundEnabled,
                    onFinished = viewModel::finishSplash
                )
            }

            AppStage.ENTRY -> {
                ProfileEntryScreen(
                    uiState = uiState,
                    onNameChange = viewModel::onInputNameChange,
                    onBirthDateChange = viewModel::onInputBirthDateChange,
                    onCalculateAndContinue = viewModel::saveAndCalculateProfile,
                    onOpenMatrix = { viewModel.setMatrixSheetVisible(true) },
                    onToggleSound = viewModel::toggleSoundEnabled,
                    onBackToResults = viewModel::returnToResultsFromEntry
                )
            }

            AppStage.RESULTS -> {
                KozmikResultsScaffold(
                    uiState = uiState,
                    viewModel = viewModel,
                    isDarkTheme = isDarkTheme
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun KozmikResultsScaffold(
    uiState: KozmikUiState,
    viewModel: KozmikViewModel,
    isDarkTheme: Boolean
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val navigateToTab: (String) -> Unit = { route ->
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val useNavigationRail = maxWidth >= 600.dp

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            contentWindowInsets = WindowInsets.safeDrawing,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Kozmik Sayı",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = viewModel::toggleSoundEnabled,
                            modifier = Modifier.testTag("toggle_sound_button")
                        ) {
                            Icon(
                                imageVector = if (uiState.storedProfile.soundEnabled) {
                                    Icons.Outlined.VolumeUp
                                } else {
                                    Icons.Outlined.VolumeOff
                                },
                                contentDescription = if (uiState.storedProfile.soundEnabled) {
                                    "Arka plan sesini kapat"
                                } else {
                                    "Arka plan sesini aç"
                                }
                            )
                        }
                        IconButton(
                            onClick = { viewModel.setMatrixSheetVisible(true) },
                            modifier = Modifier.testTag("open_matrix_button")
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.GridView,
                                contentDescription = "Pisagor Harf Tablosu"
                            )
                        }
                        IconButton(
                            onClick = viewModel::cycleThemePreference,
                            modifier = Modifier.testTag("toggle_theme_button")
                        ) {
                            Icon(
                                imageVector = if (isDarkTheme) Icons.Outlined.LightMode else Icons.Outlined.DarkMode,
                                contentDescription = "Tema Modunu Değiştir"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.onBackground
                    )
                )
            },
            bottomBar = {
                if (!useNavigationRail) {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surface
                    ) {
                        appTabs.forEach { tab ->
                            val selected = currentDestination?.hierarchy?.any { it.route == tab.route } == true
                            NavigationBarItem(
                                selected = selected,
                                onClick = { navigateToTab(tab.route) },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                                        contentDescription = tab.label
                                    )
                                },
                                label = {
                                    Text(
                                        text = tab.label,
                                        fontSize = 10.sp,
                                        maxLines = 1,
                                        softWrap = false,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                },
                                modifier = Modifier.testTag(tab.testTag)
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (useNavigationRail) {
                    NavigationRail(
                        containerColor = MaterialTheme.colorScheme.surface,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        appTabs.forEach { tab ->
                            val selected = currentDestination?.hierarchy?.any { it.route == tab.route } == true
                            NavigationRailItem(
                                selected = selected,
                                onClick = { navigateToTab(tab.route) },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                                        contentDescription = tab.label
                                    )
                                },
                                label = { Text(tab.label) },
                                modifier = Modifier.testTag(tab.testTag)
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = ROUTE_CHART,
                    modifier = Modifier.weight(1f)
                ) {
                    composable(ROUTE_CHART) {
                        ChartScreen(
                            uiState = uiState,
                            onEditEntryClick = viewModel::openEntryFromResults,
                            onOpenNumberDetail = viewModel::openNumberDetail,
                            onOpenDailyTab = { navigateToTab(ROUTE_DAILY) },
                            onOpenLibraryTab = { navigateToTab(ROUTE_LIBRARY) }
                        )
                    }
                    composable(ROUTE_DAILY) {
                        DailyEnergyScreen(
                            uiState = uiState,
                            onOpenNumberDetail = viewModel::openNumberDetail,
                            onToggleBreathingRitual = viewModel::toggleBreathingRitual
                        )
                    }
                    composable(ROUTE_COMPAT) {
                        CompatibilityScreen(
                            uiState = uiState,
                            onPartnerNameChange = viewModel::onPartnerNameChange,
                            onPartnerBirthDateChange = viewModel::onPartnerBirthDateChange,
                            onCalculateCompatibility = viewModel::calculateCompatibility
                        )
                    }
                    composable(ROUTE_LIBRARY) {
                        LibraryScreen(
                            uiState = uiState,
                            onFilterChange = viewModel::setLibraryFilter,
                            onArchetypeClick = viewModel::openArchetypeDetail
                        )
                    }
                    composable(ROUTE_HOROSCOPE) {
                        HoroscopeScreen(
                            uiState = uiState,
                            onElementFilterChange = viewModel::setHoroscopeElementFilter,
                            onSelectSign = viewModel::openHoroscopeDetail
                        )
                    }
                    composable(ROUTE_CARDS) {
                        OracleCardsScreen(
                            uiState = uiState,
                            onSelectDeck = viewModel::selectCardDeck,
                            onRevealMysteryCard = viewModel::revealMysteryCard,
                            onShuffleCards = viewModel::shuffleMysteryCards,
                            onOpenCardDetail = viewModel::openOracleCardDetail
                        )
                    }
                }
            }
        }
    }
}
