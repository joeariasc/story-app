package co.wawand.routes.story

data class StoryParams(
    val userId: Int,
    val title: String,
    val content: String,
    val isDraft: Boolean
)

data class CommentParams(
    val userId: Int,
    val storyId: Int,
    val comment: String
)

data class LikeParams(
    val userId: Int,
    val storyId: Int,
)