package com.example.musicplayercompose.components.musiclistview

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.musicplayercompose.R
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    onSearchSound: (String) -> Unit
) {
    val searchQuery = remember { mutableStateOf("") }
    val keyBoardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()

    OutlinedTextField(
        value = searchQuery.value,
        onValueChange = {
            searchQuery.value = it
            scope.launch {
                onSearchSound(it)
            }
                        },
        label = { Text(text = "Search Sounds") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            scope.launch {
                onSearchSound(searchQuery.value)
                keyBoardController?.hide()
            }
        }),
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_all)),
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.onSurface
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
        ),
        trailingIcon = {
            IconButton(onClick = {
                    scope.launch {
                        onSearchSound(searchQuery.value)
                        keyBoardController?.hide()
                    }
            }) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }
    )

}