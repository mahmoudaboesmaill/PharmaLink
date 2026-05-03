package com.pharma.link.app.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {

    @Serializable
    data object PharmacyList : Screen()

    @Serializable
    data object AddPharmacy : Screen()

    @Serializable
    data class PharmacyDetail(val pharmacyId: String) : Screen()

    @Serializable
    data class EditPharmacy(val pharmacyId: String) : Screen()

    @Serializable
    data object InvoiceList : Screen()

    @Serializable
    data class AddInvoice(val preSelectedPharmacyId: String = "") : Screen()

    @Serializable
    data class InvoiceDetail(val invoiceId: Int) : Screen()
}