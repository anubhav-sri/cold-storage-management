package com.saikrishna.wms.models


data class UserDto(
		val username: String,
		val role: Role) {

	companion object {
		fun from(user: User): UserDto {
			return UserDto(user.username, user.role)
		}
	}
}
