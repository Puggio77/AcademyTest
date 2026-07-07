package com.example.academytest.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class AddItemViewModel {
    var name by mutableStateOf("")

    val trimmedName: String
        get() = name.trim()

    val canSave: Boolean
        get() = trimmedName.isNotEmpty()
}
