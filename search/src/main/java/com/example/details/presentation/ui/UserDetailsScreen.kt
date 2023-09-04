package com.example.details.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.ui.CircleImage
import com.example.core.ui.EmptyView
import com.example.details.presentation.ui.utils.CIRCULAR_PROGRESS_TEST_TAG
import com.example.details.presentation.ui.utils.EMPTY_VIEW_TEST_TAG
import com.example.details.presentation.ui.utils.USER_DETAILS_TEST_TAG
import com.example.details.presentation.viewmodel.UserDetailsViewModel
import com.example.search.R

@Composable
fun UserDetailsScreen(
    viewModel: UserDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = com.example.core.R.dimen.padding))
            .wrapContentHeight(align = Alignment.Top)
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .testTag(CIRCULAR_PROGRESS_TEST_TAG)
            )
        }

        if (state.error != null) {
            Box(modifier = Modifier.fillMaxHeight(0.5f).testTag(EMPTY_VIEW_TEST_TAG)) {
                EmptyView(text = state.error.asString())
            }
        }

        state.userDetails?.let { userDetails ->
            Column(
                modifier = Modifier.fillMaxWidth().testTag(USER_DETAILS_TEST_TAG),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = com.example.core.R.dimen.margin))
            ) {
                CircleImage(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    url = userDetails.avatarUrl,
                    size = dimensionResource(id = R.dimen.list_item_image_size_large),
                    imageDescription = userDetails.userName
                )

                Text(text = "@${userDetails.userName}", fontStyle = FontStyle.Italic)

                if (userDetails.email.isNotEmpty()) {
                    DetailsItem(
                        Icons.Outlined.Face,
                        stringResource(id = R.string.details_name, userDetails.name)
                    )
                }

                if (userDetails.email.isNotEmpty()) {
                    DetailsItem(
                        Icons.Outlined.Email,
                        stringResource(id = R.string.details_email, userDetails.email)
                    )
                }

                if (userDetails.location.isNotEmpty()) {
                    DetailsItem(
                        Icons.Outlined.LocationOn,
                        stringResource(id = R.string.details_location, userDetails.location)
                    )
                }

                DetailsItem(
                    Icons.Outlined.AccountCircle,
                    stringResource(id = R.string.details_following, userDetails.following)
                )

                DetailsItem(
                    Icons.Outlined.AccountCircle,
                    stringResource(id = R.string.details_followers, userDetails.followers)
                )
            }
        }
    }
}

@Composable
fun DetailsItem(imageVector: ImageVector, itemText: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = com.example.core.R.dimen.margin))
    ) {
        Icon(imageVector = imageVector, contentDescription = "")
        Text(text = itemText)
    }
}

@Preview
@Composable
fun DetailsItemPreview() {
    DetailsItem(Icons.Outlined.Face, "Alex")
}
