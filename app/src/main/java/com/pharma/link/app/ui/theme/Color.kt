package com.pharma.link.app.ui.theme

import androidx.compose.ui.graphics.Color

// 1. الألوان الأساسية للخلفية والنصوص (Main Colors)
val BackgroundColor = Color(0xFFF3F7FA) // الخلفية الفاتحة جداً (مائلة للأزرق)
val CardBackground = Color(0xFFFFFFFF) // خلفية الكروت بيضاء تماماً
val PrimaryDarkText = Color(0xFF001F3F) // النص الغامق جداً (العناوين)
val SecondaryGrayText = Color(0xFF757575) // النص الرمادي (العناوين الفرعية والهاتف)
val IconsGray = Color(0xFF9E9E9E) // الأيقونات الرمادية الصغيرة

// 2. ألوان الهوية البصرية والتابات النظيفة (Brand & Tabs)
val PharmaLinkBlue = Color(0xFF1976D2) // الأزرق الأساسي للتابات والزر العائم
val TabBackgroundOn = Color(0xFF0D47A1) // الأزرق الغامق جداً للتاب المختار
val TabTextOn = Color(0xFFFFFFFF) // نص أبيض للتاب المختار
val TabBackgroundOff = Color(0xFFEDF2F7) // رمادي فاتح للتاب غير المختار
val TabTextOff = Color(0xFF718096) // رمادي غامق لنص التاب غير المختار
val SearchBarBackground = Color(0xFFEDF2F7) // خلفية بار البحث

// 3. ألوان الحالة (Status Colors) - مهمة جداً للكروت
// حالة: نشط (الأخضر)
val StatusActiveText = Color(0xFF2E7D32) // نص حالة "نشط"
val StatusActiveBg = Color(0xFFC8E6C9) // خلفية حالة "نشط"

// حالة: مديونية عالية (البرتقالي/الذهبي)
val StatusDebtText = Color(0xFFBF8F00) // نص حالة "مديونية" ورقم الرصيد
val StatusDebtBg = Color(0xFFFFF8E1) // خلفية حالة "مديونية"
val DebtAccentLine = Color(0xFFFFC107) // الخط الجانبي الذهبي للكارت

// حالة: موقوف (الرمادي)
val StatusPausedText = Color(0xFF616161) // نص حالة "موقوف"
val StatusPausedBg = Color(0xFFE0E0E0) // خلفية حالة "موقوف"

// 4. ألوان الأزرار والإضافات (Buttons & Others)
val FabButtonBlue = Color(0xFF4285F4) // الأزرق الفاتح لزر "+" العائم
val CardActionButtonsBg = Color(0xFFEDF2F7) // خلفية أزرار التعديل والحذف جوه الكارت
val CardActionButtonsIcon = Color(0xFF90A4AE) // أيقونات أزرار التعديل والحذف
val BottomNavIconOff = Color(0xFF90A4AE) // أيقونات القائمة السفلية غير النشطة