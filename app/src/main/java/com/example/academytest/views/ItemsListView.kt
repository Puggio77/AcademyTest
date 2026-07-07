package com.example.academytest.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.academytest.ui.theme.AcademyTestTheme
import com.example.academytest.ViewModels.ItemsListViewModel
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemsListView(
    viewModel: ItemsListViewModel = viewModel(),
    onItemClick: (UUID) -> Unit
) {
    val items by viewModel.sortedItems.collectAsStateWithLifecycle()
    var showAddItemDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Oggetti") },
                actions = {
                    IconButton(onClick = { showAddItemDialog = true }) {
                        Icon(Icons.Filled.Add, contentDescription = "Aggiungi oggetto")
                    }
                }
            )
        }
    ) { padding ->
        if (items.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Filled.Inbox, contentDescription = null, modifier = Modifier.size(48.dp))
                    Spacer(Modifier.height(8.dp))
                    Text("Nessun oggetto")
                    Text("Aggiungi un oggetto dalla barra in alto.", style = MaterialTheme.typography.bodySmall)
                }
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(items, key = { it.id }) { item ->
                    ItemRowView(
                        item = item,
                        onToggleFavorite = { viewModel.toggleFavorite(item.id) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.selectItem(item.id)
                                onItemClick(item.id)
                            }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                    HorizontalDivider()
                }
            }
        }
    }

    if (showAddItemDialog) {
        AddItemView(
            onDismiss = { showAddItemDialog = false },
            onSave = { name -> viewModel.addItem(name) }
        )
    }
}

@Suppress("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
private fun ItemsListViewPreview() {
    AcademyTestTheme {
        ItemsListView(viewModel = ItemsListViewModel(), onItemClick = {})
    }
}