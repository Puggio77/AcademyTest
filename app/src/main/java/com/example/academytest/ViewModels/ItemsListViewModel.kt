package com.example.academytest.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academytest.model.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.UUID

class ItemsListViewModel : ViewModel() {

    private val _items = MutableStateFlow(defaultItems)
    val items: StateFlow<List<Item>> = _items.asStateFlow()

    private val _selectedItemId = MutableStateFlow<UUID?>(null)
    val selectedItemId: StateFlow<UUID?> = _selectedItemId.asStateFlow()

    private var nextCreationIndex = defaultItems.maxOf { it.creationIndex } + 1

    val sortedItems: StateFlow<List<Item>> = items
        .map { list -> list.sortedWith(compareBy({ it.name.lowercase() }, { it.creationIndex })) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val selectedItem: StateFlow<Item?> = combine(items, selectedItemId) { list, id ->
        list.firstOrNull { it.id == id }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun selectItem(id: UUID?) {
        _selectedItemId.value = id
    }

    fun addItem(name: String) {
        val trimmed = name.trim()
        if (trimmed.isEmpty()) return
        _items.value = _items.value + Item(creationIndex = nextCreationIndex, name = trimmed)
        nextCreationIndex++
    }

    fun toggleFavorite(id: UUID) {
        _items.value = _items.value.map {
            if (it.id == id) it.copy(isFavorite = !it.isFavorite) else it
        }
    }

    fun deleteItem(id: UUID) {
        _items.value = _items.value.filterNot { it.id == id }
        if (_selectedItemId.value == id) {
            _selectedItemId.value = null
        }
    }

    companion object {
        private val defaultItems = listOf(
            Item(creationIndex = 0, name = "Lupo 🐺", isFavorite = true),
            Item(creationIndex = 1, name = "Giraffa 🦒", isFavorite = false),
            Item(creationIndex = 2, name = "Leone 🦁", isFavorite = false)
        )
    }
}