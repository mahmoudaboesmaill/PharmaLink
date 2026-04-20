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

// ── نتيجة عملية الإضافة ─────────────────────
sealed class AddPharmacyResult {
    object Success          : AddPharmacyResult()
    object DuplicatePhone   : AddPharmacyResult()
    object DuplicateEmail   : AddPharmacyResult()
    object DuplicateLicence : AddPharmacyResult()
}

@HiltViewModel
class PharmacyViewModel @Inject constructor(
    private val pharmacyRepository: PharmacyRepository
) : ViewModel() {

    // ── الداتا الخام من الـ DB ───────────────────
    private val _pharmacies = MutableStateFlow<List<PharmacyEntity>>(emptyList())

    // ── Search ───────────────────────────────────
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // ── Filter ───────────────────────────────────
    private val _selectedFilter = MutableStateFlow("ALL")
    val selectedFilter: StateFlow<String> = _selectedFilter.asStateFlow()

    // ── Loading ──────────────────────────────────
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // ── نتيجة الإضافة ────────────────────────────
    private val _addResult = MutableStateFlow<AddPharmacyResult?>(null)
    val addResult: StateFlow<AddPharmacyResult?> = _addResult.asStateFlow()

    // ── الداتا النهائية بعد Search + Filter ──────
    val filteredPharmacies: StateFlow<List<PharmacyEntity>> =
        combine(
            _pharmacies,
            _searchQuery,
            _selectedFilter
        ) { pharmacies, query, filter ->
            pharmacies
                .filter { pharmacy ->
                    filter == "ALL" || pharmacy.status == filter
                }
                .filter { pharmacy ->
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
                _isLoading.value  = false
            }
        }
    }

    // ── Actions ──────────────────────────────────
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onFilterSelected(filter: String) {
        _selectedFilter.value = filter
    }

    // reset نتيجة الإضافة بعد ما الـ Screen يستقبلها
    fun resetAddResult() {
        _addResult.value = null
    }

    fun addPharmacy(
        name: String,
        address: String,
        phone: String,
        email: String,
        licenceNumber: String,
        status: String
    ) {
        viewModelScope.launch {

            // ── تحقق من التكرار ───────────────────
            if (phone.isNotBlank() &&
                pharmacyRepository.getPharmacyByPhone(phone) != null) {
                _addResult.value = AddPharmacyResult.DuplicatePhone
                return@launch
            }
            if (email.isNotBlank() &&
                pharmacyRepository.getPharmacyByEmail(email) != null) {
                _addResult.value = AddPharmacyResult.DuplicateEmail
                return@launch
            }
            if (licenceNumber.isNotBlank() &&
                pharmacyRepository.getPharmacyByLicenceNumber(licenceNumber) != null) {
                _addResult.value = AddPharmacyResult.DuplicateLicence
                return@launch
            }

            // ── احفظ لو مفيش تكرار ───────────────
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
            _addResult.value = AddPharmacyResult.Success
        }
    }

    fun updatePharmacy(pharmacy: PharmacyEntity) {
        viewModelScope.launch {
            pharmacyRepository.updatePharmacy(pharmacy)
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