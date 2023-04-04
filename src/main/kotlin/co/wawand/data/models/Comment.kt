package co.wawand.data.models

data class Comment(
    val id: Int,
    val user: User? = null,
    val story: Story? = null,
    val comment: String,
    val createdAt: String
)