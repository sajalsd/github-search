package com.example.details.domain.model

import com.example.helper.ResponseUtil
import com.example.network.data.dto.UserDetailsDto
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UserDetailsMapperKtTest() {
    private val userDetailsDto: UserDetailsDto = ResponseUtil.getMockUserDetailsResponse("user_details_response.json")

    @Test
    fun `toUser converts a UserDto to ui model successfully`() {
        val userDetails = userDetailsDto.toUserDetails()

        assertThat(userDetailsDto.login).isEqualTo(userDetails.userName)
        assertThat(userDetailsDto.name).isAnyOf(userDetails.name, "")
        assertThat(userDetailsDto.avatarUrl).isAnyOf(userDetails.avatarUrl, "")
        assertThat(userDetailsDto.bio).isAnyOf(userDetails.bio, "")
        assertThat(userDetailsDto.email).isAnyOf(userDetails.email, "")
        assertThat(userDetailsDto.followers).isAnyOf(userDetails.followers, "")
        assertThat(userDetailsDto.following).isAnyOf(userDetails.following, "", "")
        assertThat(userDetailsDto.location).isAnyOf(userDetails.location, "")
    }
}
