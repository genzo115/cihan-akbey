package com.example.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.domain.BirthDate
import com.example.domain.ThemePreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "kozmik_sayilar_prefs")

data class StoredUserProfile(
    val fullName: String = "Defne Yıldız",
    val birthDate: BirthDate = BirthDate(day = 18, month = 11, year = 1995),
    val partnerName: String = "Kaan Deniz",
    val partnerBirthDate: BirthDate = BirthDate(day = 7, month = 5, year = 1994),
    val themePreference: ThemePreference = ThemePreference.DARK,
    val hasCustomizedProfile: Boolean = false,
    val soundEnabled: Boolean = true
)

class UserPreferencesRepository(private val context: Context) {

    private object Keys {
        val FULL_NAME = stringPreferencesKey("full_name")
        val BIRTH_DAY = intPreferencesKey("birth_day")
        val BIRTH_MONTH = intPreferencesKey("birth_month")
        val BIRTH_YEAR = intPreferencesKey("birth_year")
        val PARTNER_NAME = stringPreferencesKey("partner_name")
        val PARTNER_DAY = intPreferencesKey("partner_day")
        val PARTNER_MONTH = intPreferencesKey("partner_month")
        val PARTNER_YEAR = intPreferencesKey("partner_year")
        val THEME_PREF = stringPreferencesKey("theme_pref")
        val HAS_CUSTOMIZED = booleanPreferencesKey("has_customized")
        val SOUND_ENABLED = booleanPreferencesKey("sound_enabled")
    }

    val userProfileFlow: Flow<StoredUserProfile> = context.dataStore.data.map { prefs ->
        val themeName = prefs[Keys.THEME_PREF] ?: ThemePreference.DARK.name
        val themePref = runCatching { ThemePreference.valueOf(themeName) }.getOrDefault(ThemePreference.DARK)
        StoredUserProfile(
            fullName = prefs[Keys.FULL_NAME] ?: "Defne Yıldız",
            birthDate = BirthDate(
                day = prefs[Keys.BIRTH_DAY] ?: 18,
                month = prefs[Keys.BIRTH_MONTH] ?: 11,
                year = prefs[Keys.BIRTH_YEAR] ?: 1995
            ),
            partnerName = prefs[Keys.PARTNER_NAME] ?: "Kaan Deniz",
            partnerBirthDate = BirthDate(
                day = prefs[Keys.PARTNER_DAY] ?: 7,
                month = prefs[Keys.PARTNER_MONTH] ?: 5,
                year = prefs[Keys.PARTNER_YEAR] ?: 1994
            ),
            themePreference = themePref,
            hasCustomizedProfile = prefs[Keys.HAS_CUSTOMIZED] ?: false,
            soundEnabled = prefs[Keys.SOUND_ENABLED] ?: true
        )
    }

    suspend fun saveUserProfile(fullName: String, birthDate: BirthDate) {
        context.dataStore.edit { prefs ->
            prefs[Keys.FULL_NAME] = fullName.trim()
            prefs[Keys.BIRTH_DAY] = birthDate.day
            prefs[Keys.BIRTH_MONTH] = birthDate.month
            prefs[Keys.BIRTH_YEAR] = birthDate.year
            prefs[Keys.HAS_CUSTOMIZED] = true
        }
    }

    suspend fun savePartnerProfile(partnerName: String, partnerBirthDate: BirthDate) {
        context.dataStore.edit { prefs ->
            prefs[Keys.PARTNER_NAME] = partnerName.trim()
            prefs[Keys.PARTNER_DAY] = partnerBirthDate.day
            prefs[Keys.PARTNER_MONTH] = partnerBirthDate.month
            prefs[Keys.PARTNER_YEAR] = partnerBirthDate.year
        }
    }

    suspend fun saveThemePreference(themePreference: ThemePreference) {
        context.dataStore.edit { prefs ->
            prefs[Keys.THEME_PREF] = themePreference.name
        }
    }

    suspend fun saveSoundEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[Keys.SOUND_ENABLED] = enabled
        }
    }
}
