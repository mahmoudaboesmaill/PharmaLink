package com.pharma.link.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pharma.link.app.data.entities.InvoiceEntity
import com.pharma.link.app.data.repositories.InvoiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvoiceViewModel @Inject constructor(
    private val invoiceRepository: InvoiceRepository
) : ViewModel() {

    private val _invoices = MutableStateFlow<List<InvoiceEntity>>(emptyList())
    val invoices: StateFlow<List<InvoiceEntity>> = _invoices.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedFilter = MutableStateFlow("ALL")
    val selectedFilter: StateFlow<String> = _selectedFilter.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val filteredInvoices: StateFlow<List<InvoiceEntity>> =
        combine(
            _invoices,
            _searchQuery,
            _selectedFilter
        ) { invoices, query, filter ->
            invoices
                .filter { filter == "ALL" || it.status == filter }
                .filter {
                    query.isBlank() ||
                            it.invoiceId.toString().contains(query) ||
                            it.pharmacyId.contains(query, ignoreCase = true)
                }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init { getAllInvoices() }

    private fun getAllInvoices() {
        viewModelScope.launch {
            invoiceRepository.getAllInvoices().collect {
                _invoices.value = it
                _isLoading.value = false
            }
        }
    }

    fun onSearchQueryChange(query: String) { _searchQuery.value = query }
    fun onFilterSelected(filter: String)   { _selectedFilter.value = filter }

    fun addInvoice(
        pharmacyId: String,
        salesRepId: String,
        totalAmount: Long,
        dueDate: Long,
        notes: String?
    ) {
        viewModelScope.launch {
            val newInvoice = InvoiceEntity(
                pharmacyId  = pharmacyId,
                salesRepId  = salesRepId,
                date        = System.currentTimeMillis(),
                dueDate     = dueDate,
                totalAmount = totalAmount,
                status      = "PENDING",
                notes       = notes
            )
            invoiceRepository.insertInvoice(newInvoice)
        }
    }

    fun updateInvoiceStatus(invoice: InvoiceEntity, newStatus: String) {
        viewModelScope.launch {
            invoiceRepository.updateInvoice(invoice.copy(status = newStatus))
        }
    }

    fun deleteInvoice(invoice: InvoiceEntity) {
        viewModelScope.launch {
            invoiceRepository.deleteInvoice(invoice)
        }
    }

    suspend fun getInvoiceById(invoiceId: Int): InvoiceEntity? =
        invoiceRepository.getInvoiceById(invoiceId)

    fun getInvoicesByPharmacyId(pharmacyId: String): Flow<List<InvoiceEntity>> =
        invoiceRepository.getInvoicesByPharmacyId(pharmacyId)
}