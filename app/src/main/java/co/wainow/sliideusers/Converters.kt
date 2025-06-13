package co.wainow.sliideusers

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import co.wainow.sliideusers.data.entity.UserDto
import co.wainow.sliideusers.domain.entity.User
import co.wainow.sliideusers.presentation.entity.Gender
import co.wainow.sliideusers.presentation.entity.UserUI

fun UserDto.toModel() = User(
    id = id,
    name = name,
    email = email,
    gender = gender,
    isActive = status.equals("active", ignoreCase = true)
)

fun User.toUi() = UserUI(
    id = id,
    name = name,
    email = email,
    gender = Gender.fromString(gender),
    isActive = isActive,
    userColor = Color(getColorHEXFromId(id).toColorInt())
)

fun User.toRequest() = UserDto(
    id = id,
    name = name,
    email = email,
    gender = gender,
    status = if (isActive) "active" else "inactive"
)

fun List<User>.toUi() = map { it.toUi() }
fun List<UserDto>.toModel() = map { it.toModel() }

fun getColorHEXFromId(id: Long): String {
    val hash = fnv1aHash(id.toString())
    val color = hash and 0xFFFFFF
    return String.format("#%06X", color)
}

fun fnv1aHash(input: String): Long {
    val fnvPrime = 0x01000193
    var hash = 0x811c9dc5
    for (char in input) {
        hash = hash xor char.code.toLong()
        hash *= fnvPrime
    }
    return hash
}