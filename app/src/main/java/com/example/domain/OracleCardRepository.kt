package com.example.domain

enum class CardDeckType(
    val title: String,
    val subtitle: String,
    val badgeLabel: String
) {
    TAROT(
        title = "Tarot Kartları",
        subtitle = "Büyük Arkana arketipleri ile günün derin ruhsal mesajı",
        badgeLabel = "TAROT"
    ),
    ANGEL(
        title = "Melek Kartları",
        subtitle = "Başmeleklerden şifa, koruma ve kalp rehberliği",
        badgeLabel = "MELEK"
    ),
    ORACLE(
        title = "Kehanet Kartları",
        subtitle = "Kadim Mısır ve kozmik sembollerle sezgisel kehanet",
        badgeLabel = "KEHANET"
    )
}

data class SpiritualCard(
    val id: String,
    val deck: CardDeckType,
    val title: String,
    val codeLabel: String,
    val symbol: String,
    val keywords: List<String>,
    val coreMessage: String,
    val loveInsight: String,
    val guidance: String,
    val affirmation: String,
    val accentHex: Long,
    val artType: SpiritualArtType
)

object OracleCardRepository {

    val tarotCards: List<SpiritualCard> = listOf(
        SpiritualCard(
            id = "tarot_0",
            deck = CardDeckType.TAROT,
            title = "Mecnun (Yeni Başlangıç)",
            codeLabel = "0 • Büyük Arkana",
            symbol = "☉",
            keywords = listOf("Saf İnanç", "Özgür Adım", "Keşif"),
            coreMessage = "Evren senden eski korkuları geride bırakıp taze bir sayfaya güvenle adım atmanı istiyor. Bilmediğin bir yol aslında ruhunun uzun zamandır beklediği özgürlüktür.",
            loveInsight = "İlişkilerde spontanlık ve ön yargısız yaklaşım kalpleri yeniden yakınlaştırır.",
            guidance = "Mükemmel planı beklemek yerine kalbinin çağırdığı ilk küçük adımı bugün at.",
            affirmation = "Evrenin kanatlarına güveniyorum; yeni başlangıçlara cesaretle açılıyorum.",
            accentHex = 0xFFFFD166,
            artType = SpiritualArtType.COSMOS
        ),
        SpiritualCard(
            id = "tarot_1",
            deck = CardDeckType.TAROT,
            title = "Büyücü (Tezahür Gücü)",
            codeLabel = "I • Büyük Arkana",
            symbol = "∞",
            keywords = listOf("Yaratım", "Niyet", "Ustalık"),
            coreMessage = "Hayal ettiğin şeyi gerçeğe dönüştürmek için gereken tüm araçlar zaten elinde. Düşüncen, sözün ve eylemin bugün tek bir çizgide birleştiğinde mucizevi sonuçlar doğar.",
            loveInsight = "Çekim gücü yüksek; samimi bir ifade aradaki bağı hızla canlandırabilir.",
            guidance = "Dağınık enerjini tek bir net niyette topla ve hemen somut bir hamle yap.",
            affirmation = "Zihnimdeki ışığı ve ellerimdeki gücü bilgelikle gerçeğe dönüştürüyorum.",
            accentHex = 0xFFE8BA5A,
            artType = SpiritualArtType.EARTH
        ),
        SpiritualCard(
            id = "tarot_2",
            deck = CardDeckType.TAROT,
            title = "Başrahibe (İçsel Bilgelik)",
            codeLabel = "II • Büyük Arkana",
            symbol = "☽",
            keywords = listOf("Sezgi", "Sessiz Bilgi", "Sır"),
            coreMessage = "Cevapları dışarıdaki gürültüde değil, kendi iç tapınağındaki sessizlikte bulacaksın. Rüyaların ve ilk hislerin bugün mantığından çok daha doğru konuşuyor.",
            loveInsight = "Söylenmeyen duyguları hissetme zamanı; sabırlı ve sezgisel kal.",
            guidance = "Acele karar verme, bir gece bekle; perdenin ardındaki gerçek kendiliğinden görünecek.",
            affirmation = "İçimdeki kadim sezgiye güveniyorum; tüm cevaplar sessizliğimde berraklaşıyor.",
            accentHex = 0xFFCBB8FF,
            artType = SpiritualArtType.SOUL
        ),
        SpiritualCard(
            id = "tarot_3",
            deck = CardDeckType.TAROT,
            title = "İmparatoriçe (Bolluk & Zarafet)",
            codeLabel = "III • Büyük Arkana",
            symbol = "♀",
            keywords = listOf("Bereket", "Şefkat", "Yaratıcılık"),
            coreMessage = "Toprak gibi verimli, kalp gibi besleyici bir döngüdesin. Ektiğin sevgi ve emek tohumları filizlenmeye hazırlanıyor; kendine özen göstermek bereketini katlayacak.",
            loveInsight = "Sıcaklık, romantizm ve derin şefkat ilişkini sarmalıyor.",
            guidance = "Doğaya, sanata ve bedenine şefkat ayır; güzellik ürettikçe bolluk artar.",
            affirmation = "Yaşamın bereketini sevgiyle kabul ediyorum; yaratıcılığım huzurla çiçek açıyor.",
            accentHex = 0xFFE89AB8,
            artType = SpiritualArtType.HARMONY
        ),
        SpiritualCard(
            id = "tarot_6",
            deck = CardDeckType.TAROT,
            title = "Âşıklar (Kalbin Seçimi)",
            codeLabel = "VI • Büyük Arkana",
            symbol = "♡",
            keywords = listOf("Uyum", "Birlik", "Bilinçli Seçim"),
            coreMessage = "İki yol ya da iki kalp arasında gerçek değerlerinle uyumlu bir seçim zamanı. Zihninin korkularını değil, ruhunun 'evet' dediği hakikati seçtiğinde denge kurulur.",
            loveInsight = "Ruhsal rezonans ve kalp birliği güçleniyor; dürüstlük bağı derinleştirir.",
            guidance = "Kendi özünle çelişmeyen, kalbini ferahlatan karara sadık kal.",
            affirmation = "Kalbimle uyum içinde olan sevgiyi ve hakikati seçiyorum.",
            accentHex = 0xFFFF8FA3,
            artType = SpiritualArtType.HARMONY
        ),
        SpiritualCard(
            id = "tarot_9",
            deck = CardDeckType.TAROT,
            title = "Ermiş (Işık Feneri)",
            codeLabel = "IX • Büyük Arkana",
            symbol = "✴",
            keywords = listOf("Tefekkür", "Bilgelik", "Rehberlik"),
            coreMessage = "Kısa bir içe çekilme sana uzun zamandır aradığın netliği verecek. Kendi iç fenerini yaktığında yalnızca kendi önünü değil, sana danışanların yolunu da aydınlatırsın.",
            loveInsight = "Derin ve anlamlı sohbetler yüzeysel temaslardan çok daha şifalı olacak.",
            guidance = "Bugün kalabalıklardan 15 dakika uzaklaş ve kendi sesini dinle.",
            affirmation = "İçimdeki bilge ışık her adımda önümü berrakça aydınlatıyor.",
            accentHex = 0xFFB896FF,
            artType = SpiritualArtType.EARTH
        ),
        SpiritualCard(
            id = "tarot_10",
            deck = CardDeckType.TAROT,
            title = "Kader Çarkı (Kutsal Dönüşüm)",
            codeLabel = "X • Büyük Arkana",
            symbol = "☸",
            keywords = listOf("Eşzamanlılık", "Talih", "Döngü"),
            coreMessage = "Kozmik çark lehine dönmeye başlıyor. Karşına çıkan tesadüfler ve beklenmedik karşılaşmalar aslında seni daha yüksek bir kadersel hizaya taşıyor.",
            loveInsight = "Kadersel bir karşılaşma veya ilişkide yenileyici bir dönüm noktası.",
            guidance = "Değişime direnmek yerine akışın sunduğu yeni kapıyı açık kalple karşıla.",
            affirmation = "Evrenin kusursuz zamanlamasına güveniyorum; talih ve bereket benimle.",
            accentHex = 0xFF72D6C8,
            artType = SpiritualArtType.COSMOS
        ),
        SpiritualCard(
            id = "tarot_17",
            deck = CardDeckType.TAROT,
            title = "Yıldız (Umut & Şifa)",
            codeLabel = "XVII • Büyük Arkana",
            symbol = "✦",
            keywords = listOf("İlham", "Arınma", "Umut"),
            coreMessage = "Fırtınaların ardından gökyüzü açılıyor. Ruhun berraklaşıyor, duaların ve saf niyetlerin göksel ışıkla buluşuyor. İnancını canlı tut.",
            loveInsight = "Yaraların sarıldığı, saf ve şifalı bir sevgi akışı başlıyor.",
            guidance = "En yüce hayalini küçümseme; yıldızın bugün seni koruyor.",
            affirmation = "Kalbim umutla parlıyor; ilahi şifa her hücreme huzur taşıyor.",
            accentHex = 0xFF7CB8FF,
            artType = SpiritualArtType.SOUL
        ),
        SpiritualCard(
            id = "tarot_18",
            deck = CardDeckType.TAROT,
            title = "Ay (Sezgisel Ayna)",
            codeLabel = "XVIII • Büyük Arkana",
            symbol = "☾",
            keywords = listOf("Bilinçaltı", "Rüyalar", "Dönüşüm"),
            coreMessage = "Gölgelerin içinde saklanan bir korku yüzeye çıkıp şifalanmak istiyor. Yanılsamalarla gerçeği ayırmak için sezgine ve sükûnete yaslan.",
            loveInsight = "Varsayımlar yerine açık ve yumuşak sorularla gerçeği netleştir.",
            guidance = "Sisli yolda yalnızca önündeki bir adımı görsen bile yürü; ay ışığı yeterlidir.",
            affirmation = "Korkularımı sevgiyle dönüştürüyor, bilinmezliğin içinde güvenle yürüyorum.",
            accentHex = 0xFFA698FF,
            artType = SpiritualArtType.EARTH
        ),
        SpiritualCard(
            id = "tarot_19",
            deck = CardDeckType.TAROT,
            title = "Güneş (Zafer & Neşe)",
            codeLabel = "XIX • Büyük Arkana",
            symbol = "☀",
            keywords = listOf("Aydınlık", "Mutluluk", "Başarı"),
            coreMessage = "Tüm bulutlar dağılıyor; netlik, canlılık ve hak edilmiş bir sevinç seninle. Gerçek kimliğinle parladığında hayat da sana cömertçe gülümser.",
            loveInsight = "Neşeli, sıcak ve ışıl ışıl paylaşımlar kalbini ısıtacak.",
            guidance = "Sevincini erteleme ve bugün başardığın en küçük şeyi bile kutla.",
            affirmation = "Yaşamın ışığını içimde taşıyorum; her günüm neşe ve berraklıkla parlıyor.",
            accentHex = 0xFFFFBC75,
            artType = SpiritualArtType.COSMOS
        )
    )

    val angelCards: List<SpiritualCard> = listOf(
        SpiritualCard(
            id = "angel_michael",
            deck = CardDeckType.ANGEL,
            title = "Başmelek Mikail • İlahi Koruma",
            codeLabel = "Koruma & Cesaret",
            symbol = "⛨",
            keywords = listOf("Güven", "Kalkan", "Cesaret"),
            coreMessage = "Başmelek Mikail etrafını kobalt mavisi bir ışık kalkanıyla sarıyor. Seni yoran tüm endişeleri ve düşük enerjileri şimdi kesip serbest bırakabilirsin; güvendesin.",
            loveInsight = "Sağlıklı sınırlar koymak sevgini zayıflatmaz, aksine ilişkine saygı ve güven getirir.",
            guidance = "Korktuğun o adımı atarken yalnız olmadığını bil; cesaretin ışıkla destekleniyor.",
            affirmation = "İlahi ışıkla korunuyorum; korkuyu bırakıp cesaretle ilerliyorum.",
            accentHex = 0xFF7CB8FF,
            artType = SpiritualArtType.COSMOS
        ),
        SpiritualCard(
            id = "angel_raphael",
            deck = CardDeckType.ANGEL,
            title = "Başmelek Rafael • Zümrüt Şifa",
            codeLabel = "Beden & Ruh Şifası",
            symbol = "✚",
            keywords = listOf("Şifa", "Tazelenme", "Huzur"),
            coreMessage = "Başmelek Rafael kalbine ve bedenine zümrüt yeşili şifa ışığı akıtıyor. Hem fiziksel yorgunluğun hem de eski gönül kırgınlıkların yumuşayarak iyileşiyor.",
            loveInsight = "Şefkatli bir dokunuş veya anlayışlı bir söz aradaki tüm gerginliği eritecek.",
            guidance = "Bugün bol su iç, derin nefes al ve bedenine dinlenme izni ver.",
            affirmation = "Zümrüt şifa ışığı tüm varlığımı tazeliyor; sağlıklıyım ve huzurluyum.",
            accentHex = 0xFF72D6C8,
            artType = SpiritualArtType.SOUL
        ),
        SpiritualCard(
            id = "angel_gabriel",
            deck = CardDeckType.ANGEL,
            title = "Başmelek Cebrail • İlhamlı Söz",
            codeLabel = "Haber & İfade",
            symbol = "🕊",
            keywords = listOf("Müjde", "İfade", "Yaratıcılık"),
            coreMessage = "Beklediğin güzel bir haber veya zihnini açacak bir ilham kapıda. İçindeki gerçeği sevgi dolu ve net kelimelerle ifade ettiğinde yollar açılıyor.",
            loveInsight = "Kalbinden geçen güzel sözleri saklama; paylaşmak mucizeleri tetikler.",
            guidance = "Yaz, konuş, üret; bugün sözlerin taşıdığı ışık başkalarına da umut olacak.",
            affirmation = "İlahi ilham bana kolaylıkla akıyor; gerçeğimi sevgi ve zarafetle ifade ediyorum.",
            accentHex = 0xFFFFDF9B,
            artType = SpiritualArtType.HARMONY
        ),
        SpiritualCard(
            id = "angel_uriel",
            deck = CardDeckType.ANGEL,
            title = "Başmelek Uriel • Berrak Fikir",
            codeLabel = "Bilgelik & Çözüm",
            symbol = "✧",
            keywords = listOf("Aydınlanma", "Netlik", "Çözüm"),
            coreMessage = "Çözümsüz sandığın bir düğüm, ansızın gelen berrak bir fikirle açılıyor. Başmelek Uriel zihnine altın bir kıvılcım bırakarak doğru yolu aydınlatıyor.",
            loveInsight = "Karmaşık duygular sadeleşiyor; karşılıklı anlayış yeniden kuruluyor.",
            guidance = "Aklına gelen ilk parlak fikri hemen not et; büyük bir çözümün anahtarı olabilir.",
            affirmation = "Zihnim berrak, görüşüm net; ilahi bilgelik bana en doğru çözümü gösteriyor.",
            accentHex = 0xFFE8BA5A,
            artType = SpiritualArtType.EARTH
        ),
        SpiritualCard(
            id = "angel_chamuel",
            deck = CardDeckType.ANGEL,
            title = "Başmelek Chamuel • Kalp Huzuru",
            codeLabel = "Sevgi & Ahenk",
            symbol = "❀",
            keywords = listOf("Koşulsuz Sevgi", "Barış", "Bağ"),
            coreMessage = "Kalbinin etrafındaki gül pembesi ışık seni önce kendinle, sonra sevdiklerinle barıştırıyor. Aradığın huzur ya da kayıp bir parça sevgiyle sana geri dönüyor.",
            loveInsight = "Kalp çakran açılıyor; ruhsal yakınlık ve romantik uyum yükseliyor.",
            guidance = "Kendini eleştirdiğin konuları bugün şefkatle kucakla ve affet.",
            affirmation = "Sevgiyi özgürce alıyor ve veriyorum; kalbim derin bir barış içinde.",
            accentHex = 0xFFE89AB8,
            artType = SpiritualArtType.HARMONY
        ),
        SpiritualCard(
            id = "angel_haniel",
            deck = CardDeckType.ANGEL,
            title = "Başmelek Haniel • Ay Zarafeti",
            codeLabel = "Sezgi & Zarafet",
            symbol = "☽",
            keywords = listOf("İncelik", "Ay Işığı", "Duyarlılık"),
            coreMessage = "Başmelek Haniel duygusal hassasiyetini bir yük değil, yüksek bir sezgisel güç olarak kullanmanı hatırlatıyor. Nazik ve sakin kaldığında her kapı aralanır.",
            loveInsight = "Yumuşak bir ses tonu ve ince bir anlayış kalpleri birbirine mühürler.",
            guidance = "Akşam gökyüzüne bakıp ay ışığı altında sessizce dileğini kalbinden geçir.",
            affirmation = "Hassasiyetim benim kutsal gücümdür; zarafetle parlıyorum.",
            accentHex = 0xFFCBB8FF,
            artType = SpiritualArtType.SOUL
        ),
        SpiritualCard(
            id = "angel_jophiel",
            deck = CardDeckType.ANGEL,
            title = "Başmelek Jophiel • Neşe Işığı",
            codeLabel = "Güzellik & İyimserlik",
            symbol = "❁",
            keywords = listOf("Neşe", "Estetik", "Hafiflik"),
            coreMessage = "Düşüncelerini ağırlaştıran kaygıları temizleme vakti. Başmelek Jophiel yaşam alanına ve zihnine ferahlık getirerek küçük şeylerdeki güzelliği görmeni sağlıyor.",
            loveInsight = "Gülümsemek ve birlikte eğlenmek aşk enerjisini tazeliyor.",
            guidance = "Masanı, odanı veya zihnini sadeleştir; ferahlık yeni şansı çağırır.",
            affirmation = "Zihnimi güzellikle dolduruyorum; hayatımda neşe ve hafiflik çoğalıyor.",
            accentHex = 0xFFFFBC75,
            artType = SpiritualArtType.COSMOS
        ),
        SpiritualCard(
            id = "angel_metatron",
            deck = CardDeckType.ANGEL,
            title = "Başmelek Metatron • Kutsal Düzen",
            codeLabel = "Zamanlama & Denge",
            symbol = "✡",
            keywords = listOf("Hizalanma", "Odak", "Yükseliş"),
            coreMessage = "Hayatındaki dağınık parçalar kutsal bir geometri gibi kusursuz bir düzene oturuyor. Çakraların dengeleniyor ve önceliklerin netleşiyor.",
            loveInsight = "Ortak gelecek planları için sağlam ve güven verici bir zemin oluşuyor.",
            guidance = "Bugün tek bir önemli hedefine odaklan; evren zamanı senin lehine büküyor.",
            affirmation = "Kutsal düzenle hizadayım; enerjim yüksek, odaklı ve dengeli.",
            accentHex = 0xFFB896FF,
            artType = SpiritualArtType.EARTH
        ),
        SpiritualCard(
            id = "angel_guardian",
            deck = CardDeckType.ANGEL,
            title = "Koruyucu Melek • Sessiz Mucize",
            codeLabel = "Daima Seninle",
            symbol = "🕊",
            keywords = listOf("Şefkat", "İşaret", "Mucize"),
            coreMessage = "Koruyucu meleğin tam yanında ve dualarını duydu. Göreceğin bir tüy, tekrarlayan bir sayı ya da içindeki ani huzur onun sana gönderdiği işarettir.",
            loveInsight = "Yalnız değilsin; sevgiyle görülüyor ve destekleniyorsun.",
            guidance = "Günün içinde karşına çıkan küçük işaretlere gülümseyerek teşekkür et.",
            affirmation = "Asla yalnız değilim; koruyucu meleğim her an beni sevgiyle sarıyor.",
            accentHex = 0xFF8BE0D8,
            artType = SpiritualArtType.SOUL
        )
    )

    val oracleCards: List<SpiritualCard> = listOf(
        SpiritualCard(
            id = "oracle_isis",
            deck = CardDeckType.ORACLE,
            title = "İsis’in Örtüsü",
            codeLabel = "Mısır Kehaneti • I",
            symbol = "☥",
            keywords = listOf("Gizem", "Şifa Büyüsü", "Koruma"),
            coreMessage = "Kadim Mısır bilgelik tanrıçası İsis’in kanatları seni sarmalıyor. Gizli kalan bir niyet ya da gerçek yakında şefkatle açığa çıkacak; kalbini açık tut.",
            loveInsight = "Derin ruhsal çekim ve geçmişten gelen bir bağın şifalanması.",
            guidance = "Sırrını ve planlarını yalnızca güvendiğin kutsal alanda sakla, olgunlaşmasına izin ver.",
            affirmation = "Kadim bilgelik beni koruyor; her gizem hayrıma aydınlanıyor.",
            accentHex = 0xFFE8BA5A,
            artType = SpiritualArtType.EARTH
        ),
        SpiritualCard(
            id = "oracle_lotus",
            deck = CardDeckType.ORACLE,
            title = "Kutsal Altın Lotus",
            codeLabel = "Mısır Kehaneti • II",
            symbol = "🪷",
            keywords = listOf("Yenilenme", "Saflık", "Uyanış"),
            coreMessage = "Nil’in derin sularından güneşe açılan lotus gibi, zorlu bir sürecin içinden çok daha zarif ve güçlü bir bilinçle doğuyorsun.",
            loveInsight = "Geçmişin izleri silinirken kalbinde taze bir bahar başlıyor.",
            guidance = "Zorlandığın durumların seni olgunlaştıran birer basamak olduğunu hatırla.",
            affirmation = "Her deneyimden güçlenerek ve ışığımı büyüterek yükseliyorum.",
            accentHex = 0xFFFFBC75,
            artType = SpiritualArtType.SOUL
        ),
        SpiritualCard(
            id = "oracle_nile",
            deck = CardDeckType.ORACLE,
            title = "Nil’in Bereket Akışı",
            codeLabel = "Mısır Kehaneti • III",
            symbol = "〰",
            keywords = listOf("Bolluk", "Akış", "Teslimiyet"),
            coreMessage = "Tıpkı Nil Nehri’nin toprağı beslemesi gibi hayatına maddi ve manevi bereket akışı geliyor. Kontrolü gevşetip akışa güvendiğinde bolluk hızlanır.",
            loveInsight = "İlişkide verme ve alma dengesi doğal bir cömertlikle kuruluyor.",
            guidance = "Bir şeyi zorla oldurmaya çalışmayı bırak; doğru olan zaten sana akıyor.",
            affirmation = "Yaşamın bereketli nehrine güveniyorum; bolluk bana kolaylıkla akıyor.",
            accentHex = 0xFF72D6C8,
            artType = SpiritualArtType.HARMONY
        ),
        SpiritualCard(
            id = "oracle_bennu",
            deck = CardDeckType.ORACLE,
            title = "Anka (Bennu) Dönüşümü",
            codeLabel = "Mısır Kehaneti • IV",
            symbol = "🔥",
            keywords = listOf("Yeniden Doğuş", "Küllerden Çıkış", "Güç"),
            coreMessage = "Eski bir dönem tamamen kapanıyor ve sen küllerinden daha parlak kanatlarla yükseliyorsun. Geçmişe veda etmekten korkma; yeni gökyüzün çok daha geniş.",
            loveInsight = "İlişkide eski alışkanlıkları bırakıp tamamen yeni bir sayfa açma zamanı.",
            guidance = "Artık sana hizmet etmeyen bir düşünceyi bugün sembolik olarak serbest bırak.",
            affirmation = "Eskiyi sevgiyle uğurluyor, yeni ve parlak doğuşumu kutluyorum.",
            accentHex = 0xFFFF8866,
            artType = SpiritualArtType.COSMOS
        ),
        SpiritualCard(
            id = "oracle_sirius",
            deck = CardDeckType.ORACLE,
            title = "Sirius Yıldız Kapısı",
            codeLabel = "Kozmik Kehanet • V",
            symbol = "✶",
            keywords = listOf("Yüksek Rehberlik", "Uyanış", "Şans"),
            coreMessage = "Kadim Mısır’ın kutsal yıldızı Sirius bugün senin için parlıyor. Beklenmedik bir fırsat kapısı ya da ani bir içgörü yaşam yönünü olumlu yönde değiştirebilir.",
            loveInsight = "Ruhsal olarak seni anlayan ve frekansını yükselten insanlarla buluşma.",
            guidance = "Büyük düşün; evren bugün küçük değil, kalbine layık vizyonları destekliyor.",
            affirmation = "Yıldızların rehberliğiyle hizadayım; mucizevi kapılar önümde açılıyor.",
            accentHex = 0xFF9FB4FF,
            artType = SpiritualArtType.COSMOS
        ),
        SpiritualCard(
            id = "oracle_ra",
            deck = CardDeckType.ORACLE,
            title = "Ra’nın Güneş Tacı",
            codeLabel = "Mısır Kehaneti • VI",
            symbol = "𓂀",
            keywords = listOf("Özgüven", "Görünürlük", "Liderlik"),
            coreMessage = "Kendi ışığını saklama zamanı bitti. Başkalarının onayını beklemeden otoriteni, yeteneklerini ve hak ettiğin değeri gururla ortaya koy.",
            loveInsight = "Kendine duyduğun saygı ilişkideki çekimi ve saygıyı anında artırır.",
            guidance = "Bugün dik dur, sesini net çıkar ve ışığını cömertçe paylaş.",
            affirmation = "İçimdeki güneş sönmez; kendi değerimi biliyor ve güvenle parlıyorum.",
            accentHex = 0xFFE8BA5A,
            artType = SpiritualArtType.EARTH
        ),
        SpiritualCard(
            id = "oracle_horus",
            deck = CardDeckType.ORACLE,
            title = "Horus’un Gözü",
            codeLabel = "Mısır Kehaneti • VII",
            symbol = "👁",
            keywords = listOf("Berrak Görüş", "Koruma", "Hakikat"),
            coreMessage = "Görünenin ardındaki gerçeği fark ediyorsun. Horus’un Gözü seni yanıltıcı durumlardan korur ve kimin niyetinin saf olduğunu sana sessizce gösterir.",
            loveInsight = "Maskelerin düştüğü, samimi kalplerin netleştiği bir farkındalık anı.",
            guidance = "Sözlerden çok enerjilere ve davranışların tutarlılığına kulak ver.",
            affirmation = "Hakikati berrakça görüyorum; bilgeliğim ve sezgim beni daima koruyor.",
            accentHex = 0xFFCBB8FF,
            artType = SpiritualArtType.SOUL
        ),
        SpiritualCard(
            id = "oracle_temple",
            deck = CardDeckType.ORACLE,
            title = "Sonsuzluk Tapınağı",
            codeLabel = "Kozmik Kehanet • VIII",
            symbol = "⛩",
            keywords = listOf("Sabır", "Temel", "Kalıcı Eser"),
            coreMessage = "Attığın temeller düşündüğünden çok daha sağlam. Sonuçları hemen görmesen bile inşa ettiğin şey zamanla kalıcı ve sarsılmaz bir değere dönüşecek.",
            loveInsight = "Güven, sadakat ve uzun ömürlü bir bağ güçleniyor.",
            guidance = "Sabırsızlığı bırak; büyük tapınaklar tek günde değil, sevgi dolu adımlarla kurulur.",
            affirmation = "Sabırla ve güvenle ilerliyorum; emeğim kalıcı berekete dönüşüyor.",
            accentHex = 0xFF7FD19E,
            artType = SpiritualArtType.EARTH
        ),
        SpiritualCard(
            id = "oracle_mirror",
            deck = CardDeckType.ORACLE,
            title = "Kozmik Ayna",
            codeLabel = "Kozmik Kehanet • IX",
            symbol = "◈",
            keywords = listOf("Yansıma", "Öz Sevgi", "Farkındalık"),
            coreMessage = "Dış dünyada karşılaştığın insanlar ve olaylar şu an iç dünyandaki bir duyguyu yansıtıyor. İçindeki sesi sevgiye çevirdiğinde dış dünya da yumuşar.",
            loveInsight = "Partnerinde hayran olduğun ışık aslında senin kendi özünün yansımasıdır.",
            guidance = "Kendine başkalarına davrandığın kadar nazik ve cömert davran.",
            affirmation = "İç dünyamda huzuru seçiyorum; dış dünyam bana sevgiyle gülümsüyor.",
            accentHex = 0xFFE89AB8,
            artType = SpiritualArtType.HARMONY
        )
    )

    fun cardsForDeck(deck: CardDeckType): List<SpiritualCard> = when (deck) {
        CardDeckType.TAROT -> tarotCards
        CardDeckType.ANGEL -> angelCards
        CardDeckType.ORACLE -> oracleCards
    }

    fun dailyCardFor(deck: CardDeckType, date: BirthDate): SpiritualCard {
        val list = cardsForDeck(deck)
        val seed = date.year * 372 + date.month * 31 + date.day + deck.ordinal * 11
        return list[seed % list.size]
    }

    fun threeMysteryCards(deck: CardDeckType, date: BirthDate, drawSalt: Int): List<SpiritualCard> {
        val list = cardsForDeck(deck)
        val base = date.year * 372 + date.month * 31 + date.day + deck.ordinal * 13 + drawSalt * 5
        val i1 = base % list.size
        val i2 = (base + 3) % list.size
        val i3 = (base + 7) % list.size
        return listOf(list[i1], list[i2], list[i3])
    }
}
