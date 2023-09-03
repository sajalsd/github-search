package com.example.search.domain.model

import com.example.helper.ResponseUtil
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UserMapperKtTest {
    private val userDto = ResponseUtil.getSuccessResponse("search_response.json").responseData.items.first()

    @Test
    fun `toUser converts a UserDto to ui model successfully`() {
        val user = userDto.toUser()

        assertThat(userDto.id).isEqualTo(user.id)
        assertThat(userDto.login).isEqualTo(user.login)
        assertThat(userDto.avatarUrl).isEqualTo(user.avatarUrl)
        assertThat(userDto.followingUrl).isEqualTo(user.followingUrl)
        assertThat(userDto.followersUrl).isEqualTo(user.followersUrl)
    }
}
