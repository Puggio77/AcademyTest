package com.example.academytest.views

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.example.academytest.ViewModels.AddItemViewModel
import com.example.academytest.ui.theme.AcademyTestTheme

@Composable
fun AddItemView(
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    val viewModel = remember { AddItemViewModel() }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Aggiungi") },
        text = {
            OutlinedTextField(
                value = viewModel.name,
                onValueChange = { viewModel.name = it },
                label = { Text("Nome") },
                singleLine = true
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onSave(viewModel.trimmedName)
                    onDismiss()
                },
                enabled = viewModel.canSave
            ) {
                Text("Salva")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annulla")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun AddItemViewPreview() {
    AcademyTestTheme {
        AddItemView(onDismiss = {}, onSave = {})
    }
}