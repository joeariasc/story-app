package co.wawand.routes.auth

data class UserLoginParams(
    val email: String,
    val password: String
)