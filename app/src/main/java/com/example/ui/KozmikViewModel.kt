package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.NetworkStatusObserver
import com.example.data.StoredUserProfile
import com.example.data.UserPreferencesRepository
import com.example.domain.ArchetypeRepository
import com.example.domain.BirthDate
import com.example.domain.CalculatedNumber
import com.example.domain.CardDeckType
import com.example.domain.CompatibilityReport
import com.example.domain.DailyHoroscope
import com.example.domain.HoroscopeRepository
import com.example.domain.NumberArchetype
import com.example.domain.NumerologyCalculator
import com.example.domain.NumerologyChart
import com.example.domain.SpiritualCard
import com.example.domain.ThemePreference
import com.example.domain.ZodiacElement
import com.example.domain.ZodiacSign
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class AppStage {
    SPLASH,
    ENTRY,
    RESULTS
}

data class DetailSheetContent(
    val archetype: NumberArchetype,
    val calculatedNumber: CalculatedNumber? = null
)

enum class BreathPhase(val label: String, val guidance: String) {
    INHALE("Nefes Al", "Kozmik altın ışığı kalbine çek"),
    HOLD("Tut", "Titreşimi ruhunda hisset"),
    EXHALE("Nefes Ver", "Tüm gerilimi evrene bırak")
}

data class BreathingRitualState(
    val isActive: Boolean = false,
    val phase: BreathPhase = BreathPhase.INHALE,
    val secondsRemainingInPhase: Int = 4,
    val completedCycles: Int = 0
)

enum class LibraryFilter(val label: String) {
    ALL("Tümü"),
    CORE("Temel (1–9)"),
    MASTER("Üstat (11, 22, 33)")
}

data class KozmikUiState(
    val appStage: AppStage = AppStage.SPLASH,
    val canReturnToResults: Boolean = false,
    val isOnline: Boolean = true,
    val storedProfile: StoredUserProfile = StoredUserProfile(),
    val inputName: String = "",
    val inputBirthDate: BirthDate = BirthDate(18, 11, 1995),
    val nameValidationError: String? = null,
    val chart: NumerologyChart = NumerologyCalculator.calculateChart(
        "Defne Yıldız",
        BirthDate(18, 11, 1995)
    ),
    val todayDate: BirthDate = NumerologyCalculator.todayDate(),
    val universalDay: CalculatedNumber = NumerologyCalculator.calculateUniversalDay(),
    val partnerInputName: String = "Kaan Deniz",
    val partnerInputBirthDate: BirthDate = BirthDate(7, 5, 1994),
    val compatibilityReport: CompatibilityReport = NumerologyCalculator.calculateCompatibility(
        "Defne Yıldız",
        BirthDate(18, 11, 1995),
        "Kaan Deniz",
        BirthDate(7, 5, 1994)
    ),
    val libraryFilter: LibraryFilter = LibraryFilter.ALL,
    val horoscopeElementFilter: ZodiacElement? = null,
    val selectedHoroscope: DailyHoroscope? = null,
    val selectedCardDeck: CardDeckType = CardDeckType.TAROT,
    val revealedMysteryIndex: Int? = null,
    val cardDrawSalt: Int = 0,
    val selectedOracleCard: SpiritualCard? = null,
    val selectedDetail: DetailSheetContent? = null,
    val showMatrixSheet: Boolean = false,
    val breathingRitual: BreathingRitualState = BreathingRitualState()
)

class KozmikViewModel(
    private val preferencesRepository: UserPreferencesRepository,
    private val networkStatusObserver: NetworkStatusObserver
) : ViewModel() {

    private val _uiState = MutableStateFlow(KozmikUiState())
    val uiState: StateFlow<KozmikUiState> = _uiState.asStateFlow()

    private var breathingJob: Job? = null
    private var syncedInitialProfile = false

    init {
        viewModelScope.launch {
            networkStatusObserver.isOnlineFlow.collect { online ->
                _uiState.update { it.copy(isOnline = online) }
            }
        }

        viewModelScope.launch {
            preferencesRepository.userProfileFlow.collect { stored ->
                val today = NumerologyCalculator.todayDate()
                val chart = NumerologyCalculator.calculateChart(
                    fullName = stored.fullName,
                    birthDate = stored.birthDate,
                    referenceDate = today
                )
                val compat = NumerologyCalculator.calculateCompatibility(
                    personOneName = stored.fullName,
                    personOneBirth = stored.birthDate,
                    personTwoName = stored.partnerName,
                    personTwoBirth = stored.partnerBirthDate
                )
                _uiState.update { current ->
                    current.copy(
                        storedProfile = stored,
                        inputName = if (!syncedInitialProfile) {
                            if (stored.hasCustomizedProfile) stored.fullName else ""
                        } else current.inputName,
                        inputBirthDate = if (!syncedInitialProfile) stored.birthDate else current.inputBirthDate,
                        partnerInputName = if (!syncedInitialProfile) stored.partnerName else current.partnerInputName,
                        partnerInputBirthDate = if (!syncedInitialProfile) stored.partnerBirthDate else current.partnerInputBirthDate,
                        todayDate = today,
                        universalDay = NumerologyCalculator.calculateUniversalDay(today),
                        chart = chart,
                        compatibilityReport = compat
                    )
                }
                syncedInitialProfile = true
            }
        }
    }

    fun finishSplash() {
        _uiState.update {
            if (it.appStage == AppStage.SPLASH) it.copy(appStage = AppStage.ENTRY) else it
        }
    }

    fun openEntryFromResults() {
        _uiState.update {
            it.copy(
                appStage = AppStage.ENTRY,
                canReturnToResults = true,
                inputName = it.storedProfile.fullName,
                inputBirthDate = it.storedProfile.birthDate,
                nameValidationError = null
            )
        }
    }

    fun returnToResultsFromEntry() {
        if (_uiState.value.canReturnToResults) {
            _uiState.update { it.copy(appStage = AppStage.RESULTS, nameValidationError = null) }
        }
    }

    fun onInputNameChange(newName: String) {
        _uiState.update { it.copy(inputName = newName, nameValidationError = null) }
    }

    fun onInputBirthDateChange(newDate: BirthDate) {
        _uiState.update { it.copy(inputBirthDate = newDate) }
    }

    fun saveAndCalculateProfile() {
        val trimmed = _uiState.value.inputName.trim()
        val letters = NumerologyCalculator.mapNameLetters(trimmed)
        if (letters.isEmpty()) {
            _uiState.update {
                it.copy(nameValidationError = "Lütfen en az bir harf içeren ad ve soyad girin.")
            }
            return
        }
        val birthDate = _uiState.value.inputBirthDate
        val today = NumerologyCalculator.todayDate()
        val newChart = NumerologyCalculator.calculateChart(trimmed, birthDate, today)
        val stored = _uiState.value.storedProfile
        val newCompat = NumerologyCalculator.calculateCompatibility(
            personOneName = trimmed,
            personOneBirth = birthDate,
            personTwoName = stored.partnerName,
            personTwoBirth = stored.partnerBirthDate
        )
        _uiState.update {
            it.copy(
                appStage = AppStage.RESULTS,
                canReturnToResults = true,
                nameValidationError = null,
                chart = newChart,
                compatibilityReport = newCompat,
                todayDate = today,
                universalDay = NumerologyCalculator.calculateUniversalDay(today)
            )
        }
        viewModelScope.launch {
            preferencesRepository.saveUserProfile(trimmed, birthDate)
        }
    }

    fun onPartnerNameChange(newName: String) {
        _uiState.update { it.copy(partnerInputName = newName) }
    }

    fun onPartnerBirthDateChange(newDate: BirthDate) {
        _uiState.update { it.copy(partnerInputBirthDate = newDate) }
    }

    fun calculateCompatibility() {
        val state = _uiState.value
        val p2Name = state.partnerInputName.trim().ifEmpty { "Ruh Eşi" }
        if (NumerologyCalculator.mapNameLetters(p2Name).isEmpty()) return
        val p2Birth = state.partnerInputBirthDate
        viewModelScope.launch {
            preferencesRepository.savePartnerProfile(p2Name, p2Birth)
            val report = NumerologyCalculator.calculateCompatibility(
                personOneName = state.storedProfile.fullName,
                personOneBirth = state.storedProfile.birthDate,
                personTwoName = p2Name,
                personTwoBirth = p2Birth
            )
            _uiState.update { it.copy(compatibilityReport = report) }
        }
    }

    fun openNumberDetail(calculatedNumber: CalculatedNumber) {
        val archetype = ArchetypeRepository.getArchetype(calculatedNumber.value)
        _uiState.update {
            it.copy(selectedDetail = DetailSheetContent(archetype = archetype, calculatedNumber = calculatedNumber))
        }
    }

    fun openArchetypeDetail(archetype: NumberArchetype) {
        _uiState.update {
            it.copy(selectedDetail = DetailSheetContent(archetype = archetype, calculatedNumber = null))
        }
    }

    fun closeDetailSheet() {
        _uiState.update { it.copy(selectedDetail = null) }
    }

    fun setMatrixSheetVisible(visible: Boolean) {
        _uiState.update { it.copy(showMatrixSheet = visible) }
    }

    fun setLibraryFilter(filter: LibraryFilter) {
        _uiState.update { it.copy(libraryFilter = filter) }
    }

    fun setHoroscopeElementFilter(element: ZodiacElement?) {
        _uiState.update { it.copy(horoscopeElementFilter = element) }
    }

    fun openHoroscopeDetail(sign: ZodiacSign) {
        val forecast = HoroscopeRepository.getDailyHoroscope(sign, _uiState.value.todayDate)
        _uiState.update { it.copy(selectedHoroscope = forecast) }
    }

    fun closeHoroscopeDetail() {
        _uiState.update { it.copy(selectedHoroscope = null) }
    }

    fun selectCardDeck(deck: CardDeckType) {
        _uiState.update {
            it.copy(
                selectedCardDeck = deck,
                revealedMysteryIndex = null
            )
        }
    }

    fun revealMysteryCard(index: Int) {
        _uiState.update { it.copy(revealedMysteryIndex = index) }
    }

    fun shuffleMysteryCards() {
        _uiState.update {
            it.copy(
                revealedMysteryIndex = null,
                cardDrawSalt = it.cardDrawSalt + 1
            )
        }
    }

    fun openOracleCardDetail(card: SpiritualCard) {
        _uiState.update { it.copy(selectedOracleCard = card) }
    }

    fun closeOracleCardDetail() {
        _uiState.update { it.copy(selectedOracleCard = null) }
    }

    fun cycleThemePreference() {
        val current = _uiState.value.storedProfile.themePreference
        val next = when (current) {
            ThemePreference.DARK -> ThemePreference.LIGHT
            ThemePreference.LIGHT -> ThemePreference.DARK
            ThemePreference.SYSTEM -> ThemePreference.DARK
        }
        viewModelScope.launch {
            preferencesRepository.saveThemePreference(next)
        }
    }

    fun toggleSoundEnabled() {
        val next = !_uiState.value.storedProfile.soundEnabled
        viewModelScope.launch {
            preferencesRepository.saveSoundEnabled(next)
        }
    }

    fun toggleBreathingRitual() {
        if (_uiState.value.breathingRitual.isActive) {
            stopBreathingRitual()
        } else {
            startBreathingRitual()
        }
    }

    private fun startBreathingRitual() {
        breathingJob?.cancel()
        _uiState.update {
            it.copy(
                breathingRitual = BreathingRitualState(
                    isActive = true,
                    phase = BreathPhase.INHALE,
                    secondsRemainingInPhase = 4,
                    completedCycles = it.breathingRitual.completedCycles
                )
            )
        }
        breathingJob = viewModelScope.launch {
            var phase = BreathPhase.INHALE
            var secondsLeft = 4
            var cycles = _uiState.value.breathingRitual.completedCycles
            while (true) {
                delay(1000L)
                secondsLeft--
                if (secondsLeft <= 0) {
                    phase = when (phase) {
                        BreathPhase.INHALE -> BreathPhase.HOLD
                        BreathPhase.HOLD -> BreathPhase.EXHALE
                        BreathPhase.EXHALE -> {
                            cycles++
                            BreathPhase.INHALE
                        }
                    }
                    secondsLeft = 4
                }
                _uiState.update {
                    it.copy(
                        breathingRitual = BreathingRitualState(
                            isActive = true,
                            phase = phase,
                            secondsRemainingInPhase = secondsLeft,
                            completedCycles = cycles
                        )
                    )
                }
            }
        }
    }

    private fun stopBreathingRitual() {
        breathingJob?.cancel()
        breathingJob = null
        _uiState.update {
            it.copy(
                breathingRitual = it.breathingRitual.copy(
                    isActive = false,
                    phase = BreathPhase.INHALE,
                    secondsRemainingInPhase = 4
                )
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        breathingJob?.cancel()
    }

    class Factory(
        private val preferencesRepository: UserPreferencesRepository,
        private val networkStatusObserver: NetworkStatusObserver
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return KozmikViewModel(preferencesRepository, networkStatusObserver) as T
        }
    }
}
