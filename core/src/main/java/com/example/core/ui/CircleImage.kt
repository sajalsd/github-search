package com.example.core.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.core.R

@Composable
fun CircleImage(
    modifier: Modifier,
    url: String,
    imageDescription: String = "",
    size: Dp
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = imageDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .padding(dimensionResource(R.dimen.margin))
            .size(size)
            .clip(CircleShape)
            .border(2.dp, MaterialTheme.colorScheme.inversePrimary, CircleShape)
    )
}
