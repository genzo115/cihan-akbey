package com.example

import com.example.domain.BirthDate
import com.example.domain.CardDeckType
import com.example.domain.HoroscopeRepository
import com.example.domain.NumerologyCalculator
import com.example.domain.OracleCardRepository
import com.example.domain.ZodiacSign
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NumerologyCalculatorTest {

    @Test
    fun turkishLettersMapCorrectlyInPythagoreanSystem() {
        val mappings = NumerologyCalculator.mapNameLetters("Çiğdem Şahin Ünal")
        // Ç=3, İ=9(vowel), Ğ=7, D=4, E=5(vowel), M=4
        // Ş=1, A=1(vowel), H=8, İ=9(vowel), N=5
        // Ü=3(vowel), N=5, A=1(vowel), L=3
        assertEquals(15, mappings.size)
        assertEquals(3, mappings[0].digit) // Ç
        assertTrue(mappings[1].isVowel)    // İ
        assertEquals(9, mappings[1].digit) // İ
        assertEquals(7, mappings[2].digit) // Ğ
        assertEquals(1, mappings[6].digit) // Ş
        assertTrue(mappings[11].isVowel)   // Ü
        assertEquals(3, mappings[11].digit)// Ü
    }

    @Test
    fun lifePathPreservesMasterNumbersAndReducesProperly() {
        // 18 -> 9, 11 -> 11, 1995 -> 24 -> 6 => 9 + 11 + 6 = 26 -> 8
        val lifePath = NumerologyCalculator.calculateLifePath(BirthDate(18, 11, 1995))
        assertEquals(8, lifePath.value)

        // 29 -> 11, 02 -> 2, 1990 -> 19 -> 10 -> 1 => 11 + 2 + 1 = 14 -> 5
        val masterBirthDay = NumerologyCalculator.calculateChart("Ali Can", BirthDate(29, 2, 1990))
        assertEquals(11, masterBirthDay.birthDay.value)
        assertTrue(masterBirthDay.birthDay.isMaster)
    }

    @Test
    fun compatibilityProducesValidScoreAndSharedEnergy() {
        val report = NumerologyCalculator.calculateCompatibility(
            personOneName = "Defne Yıldız",
            personOneBirth = BirthDate(18, 11, 1995),
            personTwoName = "Kaan Deniz",
            personTwoBirth = BirthDate(7, 5, 1994)
        )
        assertTrue(report.harmonyScore in 58..99)
        assertTrue(report.sharedEnergyNumber in 1..33)
        assertTrue(report.bondTitle.isNotBlank())
    }

    @Test
    fun allTwelveZodiacSignsGenerateDailyHoroscopes() {
        assertEquals(12, ZodiacSign.entries.size)
        assertEquals(ZodiacSign.AKREP, HoroscopeRepository.detectZodiacSign(BirthDate(18, 11, 1995)))
        ZodiacSign.entries.forEach { sign ->
            val forecast = HoroscopeRepository.getDailyHoroscope(sign, BirthDate(18, 11, 2026))
            assertTrue(forecast.generalForecast.isNotBlank())
            assertTrue(forecast.loveForecast.isNotBlank())
            assertTrue(forecast.careerForecast.isNotBlank())
            assertTrue(forecast.luckyNumber in 1..9)
        }
    }

    @Test
    fun tarotAngelAndOracleDecksProvideMysteryAndDailyCards() {
        CardDeckType.entries.forEach { deck ->
            val cards = OracleCardRepository.cardsForDeck(deck)
            assertTrue(cards.size >= 9)
            val mystery = OracleCardRepository.threeMysteryCards(deck, BirthDate(18, 11, 2026), drawSalt = 0)
            assertEquals(3, mystery.size)
            val daily = OracleCardRepository.dailyCardFor(deck, BirthDate(18, 11, 2026))
            assertTrue(daily.coreMessage.isNotBlank())
            assertTrue(daily.affirmation.isNotBlank())
        }
    }
}
