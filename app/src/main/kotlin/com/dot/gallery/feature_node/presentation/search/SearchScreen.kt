package com.dot.gallery.feature_node.presentation.search

import android.app.AlertDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HdrOn
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.PanoramaPhotosphere
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dot.gallery.R
import com.dot.gallery.feature_node.presentation.common.MediaViewModel
import com.dot.gallery.feature_node.presentation.util.Screen

@Composable
fun SearchScreen(
    navigate: (route: String) -> Unit,
    mediaViewModel: MediaViewModel,
    toggleNavbar: (Boolean) -> Unit,
    paddingValues: PaddingValues,
    isScrolling: MutableState<Boolean>,
    searchBarActive: MutableState<Boolean>
) {
    var showDialog by remember { mutableStateOf(false) }
    val isLightTheme = !isSystemInDarkTheme() // Invert the result of isSystemInDarkTheme()
    Scaffold(
        topBar = {
            MainSearchBar(
                mediaViewModel = mediaViewModel,
                bottomPadding = paddingValues.calculateBottomPadding(),
                navigate = navigate,
                toggleNavbar = toggleNavbar,
                isScrolling = isScrolling,
                activeState = searchBarActive
            ) {
                var expandedDropdown by remember { mutableStateOf(false) }
                IconButton(onClick = { expandedDropdown = !expandedDropdown }) {
                    Icon(
                        imageVector = Icons.Outlined.MoreVert,
                        contentDescription = stringResource(R.string.drop_down_cd)
                    )
                }
                DropdownMenu(
                    expanded = expandedDropdown,
                    onDismissRequest = { expandedDropdown = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = stringResource(id = R.string.settings_title)) },
                        onClick = {
                            expandedDropdown = false
                            navigate(Screen.SettingsScreen.route)
                        }
                    )
                }
            }
        }
    ) {
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxSize(),
            contentPadding = PaddingValues(
                top = it.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding() + 16.dp + 64.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item(
                key = "SearchMapItem"
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .padding(horizontal = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(id = R.string.places),
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = stringResource(id = R.string.all),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.clickable { showDialog = true }
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth()
                            .aspectRatio(1f)
                    ) {
                        Image(
                            painter = painterResource(id = if (isLightTheme) R.drawable.map_light else R.drawable.map_dark),
                            contentDescription = stringResource(id = R.string.map_image_description),
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(16.dp))
                                .clickable { showDialog = true }
                        )

                        Text(
                            text = stringResource(id = R.string.your_map),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 16.dp)
                        )
                    }
                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = { showDialog = false },
                            title = { Text(text = "This functionality is not implemented yet") },
                            text = { Text(text = "Please wait for future updates") },
                            confirmButton = {
                                Button(
                                    onClick = { showDialog = false }
                                ) {
                                    Text(text = "OK")
                                }
                            }
                        )
                    }
                }
            }
            item(
                key = "CategoriesItem"
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = stringResource(id = (R.string.categories)),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Column(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                    ) {
                        ListItem(
                            headlineContent = { Text("Videos", style = MaterialTheme.typography.bodyLarge)},
                            leadingContent = {
                                Icon(
                                    Icons.Outlined.PlayCircle,
                                    contentDescription = "Videos category icon",
                                    tint = LocalContentColor.current
                                )
                            },
                            modifier = Modifier
                                .clickable { showDialog = true }
                                .background(MaterialTheme.colorScheme.primary)
                        )
                        HorizontalDivider()
                        ListItem(
                            headlineContent = { Text("Videos HDR", style = MaterialTheme.typography.bodyLarge) },
                            leadingContent = {
                                Icon(
                                    Icons.Outlined.HdrOn,
                                    contentDescription = "HDR Videos category icon",
                                    tint = LocalContentColor.current
                                )
                            },
                            modifier = Modifier.clickable { showDialog = true }
                        )
                        HorizontalDivider()
                        //next item is for photosphere
                        ListItem(
                            headlineContent = { Text("Photosphere", style = MaterialTheme.typography.bodyLarge) },
                            leadingContent = {
                                Icon(
                                    Icons.Outlined.PanoramaPhotosphere,
                                    contentDescription = "Photosphere category icon",
                                    tint = LocalContentColor.current
                                )
                            },
                            modifier = Modifier.clickable { showDialog = true }
                        )
                    }
                }
            }
        }
    }
}