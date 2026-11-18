package com.example.domain

enum class ZodiacElement(val label: String) {
    ATES("Ateş"),
    TOPRAK("Toprak"),
    HAVA("Hava"),
    SU("Su")
}

enum class ZodiacSign(
    val turkishName: String,
    val symbol: String,
    val dateRange: String,
    val element: ZodiacElement,
    val rulingPlanet: String,
    val crystal: String,
    val colorName: String,
    val accentHex: Long,
    val artType: SpiritualArtType,
    val coreEssence: String
) {
    KOC(
        turkishName = "Koç",
        symbol = "♈",
        dateRange = "21 Mart – 19 Nisan",
        element = ZodiacElement.ATES,
        rulingPlanet = "Mars",
        crystal = "Kırmızı Jasper",
        colorName = "Mercan Ateşi",
        accentHex = 0xFFFF8866,
        artType = SpiritualArtType.COSMOS,
        coreEssence = "öncü cesaretin ve saf başlangıç ateşin"
    ),
    BOGA(
        turkishName = "Boğa",
        symbol = "♉",
        dateRange = "20 Nisan – 20 Mayıs",
        element = ZodiacElement.TOPRAK,
        rulingPlanet = "Venüs",
        crystal = "Zümrüt & Yeşim",
        colorName = "Yosun Yeşili",
        accentHex = 0xFF7FD19E,
        artType = SpiritualArtType.EARTH,
        coreEssence = "köklü huzurun ve bereketi somutlaştırma gücün"
    ),
    IKIZLER(
        turkishName = "İkizler",
        symbol = "♊",
        dateRange = "21 Mayıs – 20 Haziran",
        element = ZodiacElement.HAVA,
        rulingPlanet = "Merkür",
        crystal = "Sitrin & Akik",
        colorName = "Altın Kehribar",
        accentHex = 0xFFFFD166,
        artType = SpiritualArtType.SOUL,
        coreEssence = "kıvrak zihnin ve ilham taşıyan sözlerin"
    ),
    YENGEC(
        turkishName = "Yengeç",
        symbol = "♋",
        dateRange = "21 Haziran – 22 Temmuz",
        element = ZodiacElement.SU,
        rulingPlanet = "Ay",
        crystal = "Ay Taşı",
        colorName = "İnci Gümüşü",
        accentHex = 0xFFCBB8FF,
        artType = SpiritualArtType.HARMONY,
        coreEssence = "derin sezgilerin ve şifalı kalp sıcaklığın"
    ),
    ASLAN(
        turkishName = "Aslan",
        symbol = "♌",
        dateRange = "23 Temmuz – 22 Ağustos",
        element = ZodiacElement.ATES,
        rulingPlanet = "Güneş",
        crystal = "Güneş Taşı",
        colorName = "Kraliyet Altını",
        accentHex = 0xFFE8BA5A,
        artType = SpiritualArtType.COSMOS,
        coreEssence = "parlayan yüreğin ve cömert yaratıcı ışığın"
    ),
    BASAK(
        turkishName = "Başak",
        symbol = "♍",
        dateRange = "23 Ağustos – 22 Eylül",
        element = ZodiacElement.TOPRAK,
        rulingPlanet = "Merkür",
        crystal = "Peridot & Amazonit",
        colorName = "Adaçayı Yeşili",
        accentHex = 0xFF72D6C8,
        artType = SpiritualArtType.EARTH,
        coreEssence = "incelikli bilgeliğin ve şifalandırıcı düzenin"
    ),
    TERAZI(
        turkishName = "Terazi",
        symbol = "♎",
        dateRange = "23 Eylül – 22 Ekim",
        element = ZodiacElement.HAVA,
        rulingPlanet = "Venüs",
        crystal = "Pembe Kuvars",
        colorName = "Gül Kuvarsı",
        accentHex = 0xFFE89AB8,
        artType = SpiritualArtType.HARMONY,
        coreEssence = "zarif adaletin ve kalpler arası uyum arayışın"
    ),
    AKREP(
        turkishName = "Akrep",
        symbol = "♏",
        dateRange = "23 Ekim – 21 Kasım",
        element = ZodiacElement.SU,
        rulingPlanet = "Plüton & Mars",
        crystal = "Obsidyen & Labradorit",
        colorName = "Mistik Mürdüm",
        accentHex = 0xFFB896FF,
        artType = SpiritualArtType.EARTH,
        coreEssence = "küllerinden doğan dönüşüm ve derin hakikat sezgin"
    ),
    YAY(
        turkishName = "Yay",
        symbol = "♐",
        dateRange = "22 Kasım – 21 Aralık",
        element = ZodiacElement.ATES,
        rulingPlanet = "Jüpiter",
        crystal = "Turkuaz & Lapis",
        colorName = "Safir Turkuaz",
        accentHex = 0xFF6EC8F5,
        artType = SpiritualArtType.COSMOS,
        coreEssence = "ufukları aşan bilgeliğin ve iyimser ruhun"
    ),
    OGLAK(
        turkishName = "Oğlak",
        symbol = "♑",
        dateRange = "22 Aralık – 19 Ocak",
        element = ZodiacElement.TOPRAK,
        rulingPlanet = "Satürn",
        crystal = "Dumanlı Kuvars",
        colorName = "Kehribar Bronz",
        accentHex = 0xFFD4A373,
        artType = SpiritualArtType.EARTH,
        coreEssence = "sarsılmaz kararlılığın ve zamanı aşan ustalığın"
    ),
    KOVA(
        turkishName = "Kova",
        symbol = "♒",
        dateRange = "20 Ocak – 18 Şubat",
        element = ZodiacElement.HAVA,
        rulingPlanet = "Uranüs",
        crystal = "Ametist & Akuamarin",
        colorName = "Elektrik Lavanta",
        accentHex = 0xFF9FB4FF,
        artType = SpiritualArtType.SOUL,
        coreEssence = "özgür vizyonun ve kolektif aydınlanma enerjin"
    ),
    BALIK(
        turkishName = "Balık",
        symbol = "♓",
        dateRange = "19 Şubat – 20 Mart",
        element = ZodiacElement.SU,
        rulingPlanet = "Neptün",
        crystal = "Florit & Selenit",
        colorName = "Deniz Köpüğü",
        accentHex = 0xFF8BE0D8,
        artType = SpiritualArtType.SOUL,
        coreEssence = "sınırsız merhametin ve rüyaları okuyan ruhsal kanalın"
    )
}

data class DailyHoroscope(
    val sign: ZodiacSign,
    val universalDayNumber: Int,
    val generalForecast: String,
    val loveForecast: String,
    val careerForecast: String,
    val spiritualAdvice: String,
    val affirmation: String,
    val luckyNumber: Int,
    val energyPercent: Int
)

object HoroscopeRepository {

    fun detectZodiacSign(birthDate: BirthDate): ZodiacSign {
        val m = birthDate.month
        val d = birthDate.day
        return when {
            (m == 3 && d >= 21) || (m == 4 && d <= 19) -> ZodiacSign.KOC
            (m == 4 && d >= 20) || (m == 5 && d <= 20) -> ZodiacSign.BOGA
            (m == 5 && d >= 21) || (m == 6 && d <= 20) -> ZodiacSign.IKIZLER
            (m == 6 && d >= 21) || (m == 7 && d <= 22) -> ZodiacSign.YENGEC
            (m == 7 && d >= 23) || (m == 8 && d <= 22) -> ZodiacSign.ASLAN
            (m == 8 && d >= 23) || (m == 9 && d <= 22) -> ZodiacSign.BASAK
            (m == 9 && d >= 23) || (m == 10 && d <= 22) -> ZodiacSign.TERAZI
            (m == 10 && d >= 23) || (m == 11 && d <= 21) -> ZodiacSign.AKREP
            (m == 11 && d >= 22) || (m == 12 && d <= 21) -> ZodiacSign.YAY
            (m == 12 && d >= 22) || (m == 1 && d <= 19) -> ZodiacSign.OGLAK
            (m == 1 && d >= 20) || (m == 2 && d <= 18) -> ZodiacSign.KOVA
            else -> ZodiacSign.BALIK
        }
    }

    fun getDailyHoroscope(sign: ZodiacSign, date: BirthDate): DailyHoroscope {
        val universalDay = NumerologyCalculator.calculateUniversalDay(date).value
        val daySeed = (date.year * 372 + date.month * 31 + date.day + sign.ordinal * 19)
        val generalOptions = listOf(
            "Bugün gökyüzü ve $universalDay evrensel titreşimi birleşerek ${sign.coreEssence} için berrak bir kapı aralıyor. İç sesinin fısıltılarını ciddiye aldığında beklemediğin bir kolaylık akışı başlayacak.",
            "${sign.rulingPlanet} yönetimindeki burcun için bugün farkındalık ve sadeleşme günü. Ertelediğin bir konuşmayı veya niyeti kalbinden geldiği gibi netleştirirsen günün ikinci yarısı büyük bir ferahlık getirecek.",
            "Evren bugün küçük eşzamanlılıklarla sana işaretler gönderiyor. ${sign.element.label} elementinin doğal dengesini koruyarak acele kararlar yerine sezgisel zamanlamaya güvenmen seni öne çıkaracak.",
            "Bugünün $universalDay enerjisi yaratıcı çözümleri ve içsel yenilenmeyi destekliyor. Zihnini yoran detayları bir kenara bırakıp önceliğini ruhuna iyi gelen adımlara verdiğinde bereket kapıları açılıyor."
        )
        val loveOptions = listOf(
            "Kalp alanında şeffaflık ve yumuşaklık ön planda; yargılamadan dinlediğinde sevdiğin kişiyle ya da yeni bir tanışmada derin bir ruhsal bağ kurulabilir.",
            "Duygularını kelimelere dökmekten çekinme; samimi bir mesaj ya da sıcak bir jest aradaki görünmez mesafeleri hızla eritecek.",
            "İlişkilerde denge ve karşılıklı anlayış günü. Kendi sınırlarına saygı gösterirken karşındakinin niyetini de şefkatle okumak sana huzur verecek.",
            "Kalbinde beklettiğin eski bir kırgınlığı sevgiyle serbest bırakmak için ideal bir gökyüzü var; af dilemek ya da affetmek aşk frekansını yükseltecek."
        )
        val careerOptions = listOf(
            "İş ve üretim alanında odaklanman yüksek; yarım kalan önemli bir detayı tamamlamak yeni bir fırsatın önünü açabilir.",
            "Maddi konularda uzun vadeli ve ölçülü adımlar bugün kazandırıyor. Fikirlerini güvenilir kişilerle paylaşmak somut destek getirebilir.",
            "Yaratıcı bir çözümün takdir görmesi muhtemel. Kendi değerini küçümsemeden emeğinin karşılığını net bir duruşla iste.",
            "Planlı hareket etmek ve öncelik sırası belirlemek gün sonunda verimliliğini ikiye katlayacak; akşam saatleri yeni ilhamlar için açık."
        )
        val spiritualOptions = listOf(
            "Akşam saatlerinde 5 dakika sessizlikte kalıp ${sign.crystal} enerjisini niyetine bağla; zihnindeki sis kendiliğinden dağılacak.",
            "Bugün doğayla veya suyla kısa bir temas kurmak ${sign.element.label} elementini dengeleyecek ve aurandaki yorgunluğu temizleyecek.",
            "Şükrettiğin üç şeyi içinden geçir ve kalbine elini koyarak derin bir nefes al; evren şükür frekansına anında yanıt verir.",
            "Rüyalarına ve gün içinde tekrar eden sayılara dikkat et; sezgisel rehberliğin bugün alışılmışın üzerinde."
        )
        val affirmations = listOf(
            "Gökyüzünün rehberliğiyle uyum içindeyim; bana ait olan tüm güzellikler kolaylıkla bana akıyor.",
            "İçsel bilgeliğime güveniyorum; bugün attığım her adım huzur, sevgi ve berekete açılıyor.",
            "Kalbim açık, zihnim berrak; evrenin şefkatli akışında tam olmam gereken yerdeyim."
        )

        val generalIdx = (daySeed + sign.ordinal) % generalOptions.size
        val loveIdx = (daySeed / 3 + sign.ordinal * 2) % loveOptions.size
        val careerIdx = (daySeed / 5 + sign.ordinal * 3) % careerOptions.size
        val spiritIdx = (daySeed / 7 + sign.ordinal) % spiritualOptions.size
        val affIdx = (daySeed + universalDay) % affirmations.size

        val luckyRaw = ((universalDay + sign.ordinal + date.day) % 9) + 1
        val energy = 74 + ((daySeed + sign.ordinal * 7) % 25)

        return DailyHoroscope(
            sign = sign,
            universalDayNumber = universalDay,
            generalForecast = generalOptions[generalIdx],
            loveForecast = loveOptions[loveIdx],
            careerForecast = careerOptions[careerIdx],
            spiritualAdvice = spiritualOptions[spiritIdx],
            affirmation = affirmations[affIdx],
            luckyNumber = luckyRaw,
            energyPercent = energy
        )
    }
}
