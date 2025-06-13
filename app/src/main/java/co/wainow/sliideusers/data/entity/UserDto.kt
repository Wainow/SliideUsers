package co.wainow.sliideusers.data.entity

data class UserDto(
    val id: Long,
    val name: String,
    val email: String,
    val gender: String,
    val status: String,
)