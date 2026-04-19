package com.pharma.link.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pharma.link.app.data.entities.PharmacyEntity
import com.pharma.link.app.data.repositories.PharmacyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PharmacyViewModel @Inject constructor(
    private val pharmacyRepository: PharmacyRepository
) : ViewModel() {

    // ── الداتا الخام من الـ DB ──────────────────
    private val _pharmacies = MutableStateFlow<List<PharmacyEntity>>(emptyList())

    // ── Search ──────────────────────────────────
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // ── Filter ──────────────────────────────────
    private val _selectedFilter = MutableStateFlow("ALL")
    val selectedFilter: StateFlow<String> = _selectedFilter.asStateFlow()

    // ── الداتا النهائية بعد Search + Filter ─────
    // combine بتجمع 3 flows مع بعض
    // أي تغيير في أي منهم بيحسب النتيجة من أول
    val filteredPharmacies: StateFlow<List<PharmacyEntity>> =
        combine(
            _pharmacies,
            _searchQuery,
            _selectedFilter
        ) { pharmacies, query, filter ->

            pharmacies
                .filter { pharmacy ->
                    // فلترة بالـ status
                    filter == "ALL" || pharmacy.status == filter
                }
                .filter { pharmacy ->
                    // فلترة بالـ search (الاسم أو العنوان أو التليفون)
                    query.isBlank() ||
                            pharmacy.name.contains(query, ignoreCase = true) ||
                            pharmacy.address.contains(query, ignoreCase = true) ||
                            pharmacy.phone.contains(query, ignoreCase = true)
                }

        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        getAllPharmacies()
    }

    private fun getAllPharmacies() {
        viewModelScope.launch {
            pharmacyRepository.getAllPharmacies().collect {
                _pharmacies.value = it
            }
        }
    }

    // ── Actions ─────────────────────────────────
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onFilterSelected(filter: String) {
        _selectedFilter.value = filter
    }

    // الخطوة 7 — addPharmacy محدث بكل الـ fields
    fun addPharmacy(
        name: String,
        address: String,
        phone: String,
        email: String,
        licenceNumber: String,
        status: String
    ) {
        viewModelScope.launch {
            val newPharmacy = PharmacyEntity(
                pharmacyId    = UUID.randomUUID().toString(),
                name          = name,
                address       = address,
                phone         = phone,
                email         = email,
                licenceNumber = licenceNumber,
                status        = status
            )
            pharmacyRepository.insertPharmacy(newPharmacy)
        }
    }

    fun deletePharmacy(pharmacy: PharmacyEntity) {
        viewModelScope.launch {
            pharmacyRepository.deletePharmacy(pharmacy)
        }
    }

    suspend fun getPharmacyById(pharmacyId: String): PharmacyEntity? {
        return pharmacyRepository.getPharmacyById(pharmacyId)
    }
}