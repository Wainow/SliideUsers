package co.wainow.sliideusers.presentation.entity

enum class Gender {
    Male, Female;

    companion object {

        fun fromString(value: String): Gender {
            return Gender.entries.firstOrNull { it.name.equals(value, ignoreCase = true) }
                ?: throw IllegalArgumentException("Unknown gender: $value")
        }
    }
}