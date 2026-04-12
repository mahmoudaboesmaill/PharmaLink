package com.pharma.link.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pharma.link.app.data.entities.PharmacyEntity
import com.pharma.link.app.data.repositories.PharmacyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PharmacyViewModel @Inject constructor(
    private val pharmacyRepository: PharmacyRepository
) : ViewModel() {
    private val _pharmacies = MutableStateFlow<List<PharmacyEntity>>(emptyList())
    val pharmacies: StateFlow<List<PharmacyEntity>> = _pharmacies.asStateFlow()

    init {
        getAllPharmacies()
    }

    private fun getAllPharmacies() {
        viewModelScope.launch {
            pharmacyRepository.getAllPharmacies().collect { pharmacies ->
                _pharmacies.value = pharmacies
            }
        }
    }

    fun addPharmacy(name: String, address: String, phone: String) {
        viewModelScope.launch {
            val newPharmacy = PharmacyEntity(
                 pharmacyId = UUID.randomUUID().toString(), // ID فريد مبني على الوقت
                name = name,
                address = address,
                phone = phone,
                email = "",
                licenceNumber = "",

            )
            pharmacyRepository.insertPharmacy(newPharmacy)
        }
    }

}