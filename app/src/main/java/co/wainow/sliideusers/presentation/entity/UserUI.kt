package co.wainow.sliideusers.presentation.entity

import androidx.compose.ui.graphics.Color

data class UserUI(
    val id: Long,
    val userColor: Color,
    val name: String,
    val email: String,
    val gender: Gender,
    val isActive: Boolean,
)