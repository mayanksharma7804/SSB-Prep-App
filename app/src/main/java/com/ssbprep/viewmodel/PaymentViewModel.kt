package com.ssbprep.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssbprep.repository.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class PaymentViewModel : ViewModel() {
    private val repository = UserRepository()

    val isPaid: StateFlow<Boolean> = repository.getUserPaymentStatus()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = false
        )

    fun getUserId(): String? = repository.getUserId()
}
