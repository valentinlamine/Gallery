package com.dot.gallery.feature_node.presentation.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dot.gallery.feature_node.presentation.util.Screen


@Composable
fun TopBar(
    title: String,
    navigate: (route: String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(color = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
            .padding(start = 8.dp, end = 8.dp, top = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,


    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.primary,
        )
        Row(

        ) {
            Icon(
                imageVector = Icons.Filled.SwapVert,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier
                    .padding(start = 188.dp)
                    .clickable {

                    }
            )
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable {
                        navigate(Screen.SettingsScreen.route)
                    }
             )
        }
    }
}





