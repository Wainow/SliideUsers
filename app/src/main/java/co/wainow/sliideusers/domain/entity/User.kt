package co.wainow.sliideusers.domain.entity

data class User(
    val id: Long,
    val name: String,
    val email: String,
    val gender: String,
    val isActive: Boolean,
)