package com.example.domain

import androidx.annotation.DrawableRes
import com.example.R

data class BirthDate(
    val day: Int,
    val month: Int,
    val year: Int
) {
    fun formatted(): String = "%02d.%02d.%04d".format(day, month, year)
}

enum class NumberType(
    val title: String,
    val subtitle: String
) {
    LIFE_PATH("Yaşam Yolu", "Doğum tarihinin çizdiği ana ruhsal yolculuk"),
    EXPRESSION("Kader & İfade", "Tam isminin taşıdığı potansiyel ve yetenekler"),
    SOUL_URGE("Ruh Dürtüsü", "Sesli harflerden doğan içsel kalp arzusu"),
    PERSONALITY("Dış Kişilik", "Sessiz harflerin dünyaya yansıttığı aura"),
    BIRTH_DAY("Doğum Günü", "Doğuştan getirdiğin özel armağan titreşimi"),
    PERSONAL_YEAR("Kişisel Yıl", "İçinde bulunduğun yılın ruhsal teması"),
    PERSONAL_DAY("Kişisel Gün", "Bugünün sana özel frekansı"),
    UNIVERSAL_DAY("Evrensel Gün", "Tüm evreni saran ortak günlük titreşim")
}

data class CalculatedNumber(
    val type: NumberType,
    val value: Int,
    val rawTotal: Int,
    val steps: List<String>,
    val isMaster: Boolean,
    val karmicNote: String? = null
)

data class LetterMapping(
    val char: Char,
    val digit: Int,
    val isVowel: Boolean
)

data class NumerologyChart(
    val fullName: String,
    val birthDate: BirthDate,
    val lifePath: CalculatedNumber,
    val expression: CalculatedNumber,
    val soulUrge: CalculatedNumber,
    val personality: CalculatedNumber,
    val birthDay: CalculatedNumber,
    val personalYear: CalculatedNumber,
    val personalDay: CalculatedNumber,
    val letterBreakdown: List<LetterMapping>
)

enum class SpiritualArtType(@DrawableRes val drawableRes: Int) {
    COSMOS(R.drawable.img_hero_cosmos),
    SOUL(R.drawable.img_spiritual_soul),
    EARTH(R.drawable.img_sacred_earth),
    HARMONY(R.drawable.img_spiritual_harmony)
}

data class NumberArchetype(
    val number: Int,
    val title: String,
    val mysticalSymbol: String,
    val element: String,
    val planet: String,
    val chakra: String,
    val crystal: String,
    val colorName: String,
    val accentHex: Long,
    val summary: String,
    val deepMeaning: String,
    val strengths: List<String>,
    val shadowSide: String,
    val spiritualGuidance: String,
    val dailyAffirmation: String,
    val artType: SpiritualArtType
)

data class CompatibilityReport(
    val personOneName: String,
    val personTwoName: String,
    val personOneLifePath: Int,
    val personTwoLifePath: Int,
    val personOneSoul: Int,
    val personTwoSoul: Int,
    val harmonyScore: Int,
    val bondTitle: String,
    val bondSummary: String,
    val strengthsText: String,
    val growthLessonText: String,
    val sharedEnergyNumber: Int
)

enum class ThemePreference(val label: String) {
    DARK("Gece Tapınağı"),
    LIGHT("Işık Parşömeni"),
    SYSTEM("Sistem")
}
