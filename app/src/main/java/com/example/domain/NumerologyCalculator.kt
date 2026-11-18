package com.example.domain

import java.util.Calendar
import java.util.Locale
import kotlin.math.abs

object NumerologyCalculator {

    private val turkishLocale = Locale("tr", "TR")

    private val letterValues: Map<Char, Int> = mapOf(
        'A' to 1, 'J' to 1, 'S' to 1, 'Ş' to 1,
        'B' to 2, 'K' to 2, 'T' to 2,
        'C' to 3, 'Ç' to 3, 'L' to 3, 'U' to 3, 'Ü' to 3,
        'D' to 4, 'M' to 4, 'V' to 4,
        'E' to 5, 'N' to 5, 'W' to 5,
        'F' to 6, 'O' to 6, 'Ö' to 6, 'X' to 6,
        'G' to 7, 'Ğ' to 7, 'P' to 7, 'Y' to 7,
        'H' to 8, 'Q' to 8, 'Z' to 8,
        'I' to 9, 'İ' to 9, 'R' to 9
    )

    private val vowels = setOf('A', 'E', 'I', 'İ', 'O', 'Ö', 'U', 'Ü')
    private val masterNumbers = setOf(11, 22, 33)

    val pythagoreanColumns: List<Pair<Int, List<String>>> = listOf(
        1 to listOf("A", "J", "S", "Ş"),
        2 to listOf("B", "K", "T"),
        3 to listOf("C", "Ç", "L", "U", "Ü"),
        4 to listOf("D", "M", "V"),
        5 to listOf("E", "N", "W"),
        6 to listOf("F", "O", "Ö", "X"),
        7 to listOf("G", "Ğ", "P", "Y"),
        8 to listOf("H", "Q", "Z"),
        9 to listOf("I", "İ", "R")
    )

    fun todayDate(): BirthDate {
        val cal = Calendar.getInstance()
        return BirthDate(
            day = cal.get(Calendar.DAY_OF_MONTH),
            month = cal.get(Calendar.MONTH) + 1,
            year = cal.get(Calendar.YEAR)
        )
    }

    fun formatTurkishDate(date: BirthDate): String {
        val months = listOf(
            "Ocak", "Şubat", "Mart", "Nisan", "Mayıs", "Haziran",
            "Temmuz", "Ağustos", "Eylül", "Ekim", "Kasım", "Aralık"
        )
        val monthName = months.getOrElse(date.month - 1) { "" }
        return "${date.day} $monthName ${date.year}"
    }

    fun calculateChart(
        fullName: String,
        birthDate: BirthDate,
        referenceDate: BirthDate = todayDate()
    ): NumerologyChart {
        val mappings = mapNameLetters(fullName)
        val lifePath = calculateLifePath(birthDate)
        val expression = calculateFromMappings(NumberType.EXPRESSION, mappings)
        val soulUrge = calculateFromMappings(
            NumberType.SOUL_URGE,
            mappings.filter { it.isVowel }
        )
        val personality = calculateFromMappings(
            NumberType.PERSONALITY,
            mappings.filter { !it.isVowel }
        )
        val birthDay = calculateBirthDay(birthDate.day)
        val personalYear = calculatePersonalYear(birthDate, referenceDate.year)
        val personalDay = calculatePersonalDay(personalYear.value, referenceDate)

        return NumerologyChart(
            fullName = fullName.trim(),
            birthDate = birthDate,
            lifePath = lifePath,
            expression = expression,
            soulUrge = soulUrge,
            personality = personality,
            birthDay = birthDay,
            personalYear = personalYear,
            personalDay = personalDay,
            letterBreakdown = mappings
        )
    }

    fun mapNameLetters(name: String): List<LetterMapping> {
        val normalized = name.uppercase(turkishLocale)
        return normalized.mapNotNull { ch ->
            val digit = letterValues[ch] ?: return@mapNotNull null
            LetterMapping(
                char = ch,
                digit = digit,
                isVowel = ch in vowels
            )
        }
    }

    fun calculateLifePath(birthDate: BirthDate): CalculatedNumber {
        val dayReduced = reduceKeepingMaster(birthDate.day)
        val monthReduced = reduceKeepingMaster(birthDate.month)
        val yearDigitsSum = sumDigits(birthDate.year)
        val yearReduced = reduceKeepingMaster(yearDigitsSum)

        val combined = dayReduced + monthReduced + yearReduced
        val steps = mutableListOf(
            "Gün: ${birthDate.day} → $dayReduced | Ay: ${birthDate.month} → $monthReduced | Yıl: ${birthDate.year} → $yearReduced",
            "$dayReduced + $monthReduced + $yearReduced = $combined"
        )
        val (finalValue, reductionSteps, karmic) = reduceWithTrace(combined)
        steps.addAll(reductionSteps)

        return CalculatedNumber(
            type = NumberType.LIFE_PATH,
            value = finalValue,
            rawTotal = combined,
            steps = steps,
            isMaster = finalValue in masterNumbers,
            karmicNote = karmic
        )
    }

    private fun calculateFromMappings(
        type: NumberType,
        mappings: List<LetterMapping>
    ): CalculatedNumber {
        if (mappings.isEmpty()) {
            return CalculatedNumber(
                type = type,
                value = 1,
                rawTotal = 0,
                steps = listOf("Harf bulunamadı, varsayılan titreşim 1."),
                isMaster = false
            )
        }
        val rawSum = mappings.sumOf { it.digit }
        val expressionPreview = mappings.joinToString("+") { "${it.char}(${it.digit})" }
        val steps = mutableListOf(
            "$expressionPreview = $rawSum"
        )
        val (finalValue, reductionSteps, karmic) = reduceWithTrace(rawSum)
        steps.addAll(reductionSteps)

        return CalculatedNumber(
            type = type,
            value = finalValue,
            rawTotal = rawSum,
            steps = steps,
            isMaster = finalValue in masterNumbers,
            karmicNote = karmic
        )
    }

    private fun calculateBirthDay(day: Int): CalculatedNumber {
        val (finalValue, reductionSteps, karmic) = reduceWithTrace(day)
        val steps = if (reductionSteps.isEmpty()) {
            listOf("Ayın $day. günü doğrudan $finalValue titreşimi taşır.")
        } else {
            listOf("Doğum günü: $day") + reductionSteps
        }
        return CalculatedNumber(
            type = NumberType.BIRTH_DAY,
            value = finalValue,
            rawTotal = day,
            steps = steps,
            isMaster = finalValue in masterNumbers,
            karmicNote = karmic
        )
    }

    fun calculatePersonalYear(birthDate: BirthDate, currentYear: Int): CalculatedNumber {
        val dayR = reduceSingleDigit(birthDate.day)
        val monthR = reduceSingleDigit(birthDate.month)
        val yearR = reduceSingleDigit(currentYear)
        val total = dayR + monthR + yearR
        val (finalValue, reductionSteps, karmic) = reduceWithTrace(total)
        val steps = mutableListOf(
            "Doğum Günü ($dayR) + Doğum Ayı ($monthR) + $currentYear ($yearR) = $total"
        )
        steps.addAll(reductionSteps)
        return CalculatedNumber(
            type = NumberType.PERSONAL_YEAR,
            value = finalValue,
            rawTotal = total,
            steps = steps,
            isMaster = finalValue in masterNumbers,
            karmicNote = karmic
        )
    }

    fun calculatePersonalDay(personalYearValue: Int, date: BirthDate): CalculatedNumber {
        val pyBase = reduceSingleDigit(personalYearValue)
        val monthR = reduceSingleDigit(date.month)
        val dayR = reduceSingleDigit(date.day)
        val total = pyBase + monthR + dayR
        val (finalValue, reductionSteps, karmic) = reduceWithTrace(total)
        val steps = mutableListOf(
            "Kişisel Yıl ($pyBase) + Bugünün Ayı ($monthR) + Günü ($dayR) = $total"
        )
        steps.addAll(reductionSteps)
        return CalculatedNumber(
            type = NumberType.PERSONAL_DAY,
            value = finalValue,
            rawTotal = total,
            steps = steps,
            isMaster = finalValue in masterNumbers,
            karmicNote = karmic
        )
    }

    fun calculateUniversalDay(date: BirthDate = todayDate()): CalculatedNumber {
        val d = reduceSingleDigit(date.day)
        val m = reduceSingleDigit(date.month)
        val y = reduceSingleDigit(date.year)
        val total = d + m + y
        val (finalValue, reductionSteps, karmic) = reduceWithTrace(total)
        val steps = mutableListOf(
            "${date.day} ($d) + ${date.month} ($m) + ${date.year} ($y) = $total"
        )
        steps.addAll(reductionSteps)
        return CalculatedNumber(
            type = NumberType.UNIVERSAL_DAY,
            value = finalValue,
            rawTotal = total,
            steps = steps,
            isMaster = finalValue in masterNumbers,
            karmicNote = karmic
        )
    }

    fun calculateCompatibility(
        personOneName: String,
        personOneBirth: BirthDate,
        personTwoName: String,
        personTwoBirth: BirthDate
    ): CompatibilityReport {
        val chart1 = calculateChart(personOneName, personOneBirth)
        val chart2 = calculateChart(personTwoName, personTwoBirth)

        val lp1 = chart1.lifePath.value
        val lp2 = chart2.lifePath.value
        val soul1 = chart1.soulUrge.value
        val soul2 = chart2.soulUrge.value

        val sharedEnergy = reduceKeepingMaster(reduceSingleDigit(lp1) + reduceSingleDigit(lp2))

        val lpScore = pairHarmonyScore(reduceSingleDigit(lp1), reduceSingleDigit(lp2))
        val soulScore = pairHarmonyScore(reduceSingleDigit(soul1), reduceSingleDigit(soul2))
        val masterBonus = if ((lp1 in masterNumbers && lp2 in masterNumbers) || lp1 == lp2) 6 else 0
        val finalScore = ((lpScore * 0.65) + (soulScore * 0.35) + masterBonus).toInt().coerceIn(58, 99)

        val (bondTitle, bondSummary, strengths, lesson) = interpretBond(
            lp1 = lp1,
            lp2 = lp2,
            shared = sharedEnergy,
            score = finalScore
        )

        return CompatibilityReport(
            personOneName = personOneName.trim(),
            personTwoName = personTwoName.trim(),
            personOneLifePath = lp1,
            personTwoLifePath = lp2,
            personOneSoul = soul1,
            personTwoSoul = soul2,
            harmonyScore = finalScore,
            bondTitle = bondTitle,
            bondSummary = bondSummary,
            strengthsText = strengths,
            growthLessonText = lesson,
            sharedEnergyNumber = sharedEnergy
        )
    }

    private fun pairHarmonyScore(a: Int, b: Int): Int {
        if (a == b) return 90
        val naturalTriads = listOf(
            setOf(1, 5, 7),
            setOf(2, 4, 8),
            setOf(3, 6, 9)
        )
        if (naturalTriads.any { a in it && b in it }) return 92
        val complementary = setOf(
            setOf(1, 2), setOf(2, 6), setOf(3, 5), setOf(4, 6), setOf(7, 9), setOf(8, 1)
        )
        if (setOf(a, b) in complementary) return 84
        val diff = abs(a - b)
        return when (diff) {
            1, 8 -> 74
            2, 7 -> 78
            else -> 72
        }
    }

    private fun interpretBond(
        lp1: Int,
        lp2: Int,
        shared: Int,
        score: Int
    ): List<String> {
        val sharedArchetype = ArchetypeRepository.getArchetype(shared)
        val bondTitle = when {
            score >= 90 -> "Işık İkizleri & Doğal Rezonans"
            score >= 82 -> "Tamamlayıcı Ruhsal Yoldaşlık"
            score >= 75 -> "Dönüştürücü Simya Bağı"
            else -> "Karmik Öğretmen & Keşif Bağı"
        }
        val bondSummary = "$lp1 ve $lp2 Yaşam Yolu enerjilerinin birleşimi, aranızda ${sharedArchetype.number} (${sharedArchetype.title}) ortak titreşimini uyandırıyor. Bu birliktelik ${sharedArchetype.element.lowercase(turkishLocale)} elementinin rehberliğinde derinleşir."
        val strengths = "Bir araya geldiğinizde ${sharedArchetype.strengths.take(2).joinToString(" ve ").lowercase(turkishLocale)} özellikleri güçlenir. Ruhsal olarak birbirinizin görmediği potansiyeli açığa çıkarırsınız."
        val lesson = "Ortak gelişim alanınız: ${sharedArchetype.shadowSide} Sabır ve açık kalple iletişim kurduğunuzda bu bağ en yüksek frekansına ulaşır."
        return listOf(bondTitle, bondSummary, strengths, lesson)
    }

    private fun reduceKeepingMaster(n: Int): Int {
        var current = n
        while (current > 9 && current !in masterNumbers) {
            current = sumDigits(current)
        }
        return current
    }

    private fun reduceSingleDigit(n: Int): Int {
        var current = n
        while (current > 9) {
            current = sumDigits(current)
        }
        return current
    }

    private fun reduceWithTrace(start: Int): Triple<Int, List<String>, String?> {
        val steps = mutableListOf<String>()
        var current = start
        var karmicNote: String? = karmicNoteFor(current)

        while (current > 9 && current !in masterNumbers) {
            val digits = current.toString().map { it.digitToInt() }
            val next = digits.sum()
            steps.add("${digits.joinToString(" + ")} = $next")
            current = next
            if (karmicNote == null) {
                karmicNote = karmicNoteFor(current)
            }
        }
        if (current in masterNumbers) {
            steps.add("$current bir Üstat Sayı olduğu için tek haneye indirilmez.")
        }
        return Triple(current, steps, karmicNote)
    }

    private fun karmicNoteFor(number: Int): String? = when (number) {
        13 -> "13/4 Karmik Borç: Odaklanma, sabır ve düzenli emekle dönüşüm potansiyeli."
        14 -> "14/5 Karmik Borç: Özgürlüğü ölçülülük ve öz disiplinle dengeleme dersi."
        16 -> "16/7 Karmik Borç: Ego katmanlarını bırakıp içsel bilgeliğe uyanış."
        19 -> "19/1 Karmik Borç: Bağımsızlığı alçakgönüllülük ve yardımlaşmayla taçlandırma."
        else -> null
    }

    private fun sumDigits(number: Int): Int =
        abs(number).toString().sumOf { it.digitToInt() }
}
