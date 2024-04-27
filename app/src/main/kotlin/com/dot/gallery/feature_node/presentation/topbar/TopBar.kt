package com.dot.gallery.feature_node.presentation.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dot.gallery.core.Settings
import com.dot.gallery.core.presentation.components.FilterButton
import com.dot.gallery.feature_node.presentation.common.MediaViewModel
import com.dot.gallery.feature_node.presentation.util.Screen

@Composable
fun TopBar(
    title: String,
    navigate: (route: String) -> Unit,
    viewModel: MediaViewModel,
    isScrolling: MutableState<Boolean>
) {
    var expandedDropdown by remember { mutableStateOf(false) }
    val filterOptions = viewModel.rememberFiltersTimeline()
    var lastSort by Settings.Album.rememberLastSort()
    var selectedFilter by remember(lastSort) { mutableStateOf(filterOptions.first { it.selected }.titleRes) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = if (isScrolling.value) 0.dp else 48.dp) // Adjust padding based on scrolling
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.weight(1f)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { expandedDropdown = !expandedDropdown }) {
                Icon(
                    imageVector = Icons.Filled.SwapVert,
                    contentDescription = "Sort",
                    modifier = Modifier.size(28.dp)
                )
            }
            DropdownMenu(
                expanded = expandedDropdown,
                onDismissRequest = { expandedDropdown = false }
            ) {
                for (filter in filterOptions) {
                    DropdownMenuItem(
                        text = { Text(text = stringResource(filter.titleRes)) },
                        onClick = {
                            filterOptions.forEach {
                                it.selected = it.titleRes == filter.titleRes
                                if (it.selected) selectedFilter = it.titleRes
                            }
                            filter.onClick(filter.mediaOrder)
                            lastSort = filterOptions.indexOf(filter)
                        },
                        trailingIcon = {
                            if (filter.selected)
                                Icon(
                                    imageVector = Icons.Outlined.Check,
                                    contentDescription = null
                                )
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.size(8.dp))
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "Settings",
                modifier = Modifier
                    .size(28.dp)
                    .clickable {
                        navigate(Screen.SettingsScreen.route)
                    }
            )
        }
    }
}