package co.wawand.data.models


data class User(
    val id: Int,
    val fullName: String,
    val nickName: String,
    val email: String,
    var authToken: String? = null,
    val createdAt: String
)