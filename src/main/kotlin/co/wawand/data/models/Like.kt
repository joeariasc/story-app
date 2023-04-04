package co.wawand.data.models

data class Like(
    val id: Int,
    val user: User? = null,
    val story: Story? = null,
    val createdAt: String
)