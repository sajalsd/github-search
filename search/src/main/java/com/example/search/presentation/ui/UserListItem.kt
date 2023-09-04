package com.example.search.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.ui.CircleImage
import com.example.search.R
import com.example.search.domain.model.User
import com.example.core.R as CoreR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListItem(
    user: User,
    onUserClick: () -> Unit
) {
    return Card(
        modifier = Modifier.fillMaxWidth().testTag(user.id.toString()),
        elevation = CardDefaults.cardElevation(dimensionResource(CoreR.dimen.elevation)),
        shape = RoundedCornerShape(dimensionResource(CoreR.dimen.border_radius)),
        onClick = onUserClick
    ) {
        Row(
            modifier = Modifier.height(intrinsicSize = IntrinsicSize.Max)
        ) {
            CircleImage(
                modifier = Modifier.align(Alignment.CenterVertically),
                url = user.avatarUrl,
                size = dimensionResource(id = R.dimen.list_item_image_size),
                imageDescription = user.login
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = user.login)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchItemPreview() {
    UserListItem(
        user = User(id = 1, login = "user1", avatarUrl = "placeholder", followersUrl = "", followingUrl = ""),
        onUserClick = {}
    )
}
