package co.wawand.routes.auth

data class CreateUserParams(
    val fullName: String,
    val email: String,
    val password: String,
    val nickName: String
)