package com.example.academytest.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.academytest.model.Item
import com.example.academytest.ui.theme.AcademyTestTheme

@Composable
fun ItemRowView(
    item: Item,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = item.name, style = MaterialTheme.typography.titleMedium)
            Text(
                text = if (item.isFavorite) "Preferito" else "Non preferito",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        IconButton(onClick = onToggleFavorite) {
            Icon(
                imageVector = if (item.isFavorite) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = if (item.isFavorite) "Rimuovi dai preferiti" else "Aggiungi ai preferiti",
                tint = if (item.isFavorite) Color(0xFFFFC107) else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemRowViewPreview() {
    AcademyTestTheme {
        Column {
            ItemRowView(item = Item(creationIndex = 0, name = "Caffè", isFavorite = true), onToggleFavorite = {})
            ItemRowView(item = Item(creationIndex = 1, name = "Zaino", isFavorite = false), onToggleFavorite = {})
        }
    }
}