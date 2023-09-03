package com.example.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.R

/**
 * Shows a empty view with a Icon and Text under it
 * @param imageVector - imageVector to show eg [Icons.Filled.Search]
 * @param imageContentDescription - content description of icon
 * @param text - actual text to show
 */
@Composable
fun EmptyView(
    imageVector: ImageVector = Icons.Outlined.Warning,
    imageContentDescription: String = stringResource(id = R.string.search_warning_image_description),
    text: String
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = imageContentDescription,
            modifier = Modifier.size(dimensionResource(id = R.dimen.hint_image_size))
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin)))
        Text(text, textAlign = TextAlign.Center)
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyViewPreview() {
    EmptyView(Icons.Filled.Search, "", "No search result found")
}
