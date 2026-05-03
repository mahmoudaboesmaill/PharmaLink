package com.pharma.link.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pharma.link.app.data.entities.SalesRepEntity
import com.pharma.link.app.data.repositories.SalesRepRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SalesRepViewModel @Inject constructor(
    private val salesRepRepository: SalesRepRepository
) : ViewModel() {

    private val _salesReps = MutableStateFlow<List<SalesRepEntity>>(emptyList())
    val salesReps: StateFlow<List<SalesRepEntity>> = _salesReps.asStateFlow()

    init {
        getAllSalesReps()
    }

    private fun getAllSalesReps() {
        viewModelScope.launch {
            salesRepRepository.getAllSalesReps().collect {
                _salesReps.value = it
            }
        }
    }

    fun addSalesRep(name: String, phone: String, email: String) {
        viewModelScope.launch {
            val newRep = SalesRepEntity(
                repId  = UUID.randomUUID().toString(),
                name   = name,
                phone  = phone,
                email  = email,
            )
            salesRepRepository.insertSalesRep(newRep)
        }
    }
}