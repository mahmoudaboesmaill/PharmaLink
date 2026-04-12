package com.pharma.link.app.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object PharmacyList :Screen()

    @Serializable
    data object AddPharmacy :Screen()


}