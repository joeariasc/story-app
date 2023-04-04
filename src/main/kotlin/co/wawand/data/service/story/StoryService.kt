package co.wawand.data.service.story

import co.wawand.data.models.Comment
import co.wawand.data.models.Like
import co.wawand.data.models.Story
import co.wawand.data.models.common.PaginatedResult
import co.wawand.routes.story.CommentParams
import co.wawand.routes.story.LikeParams
import co.wawand.routes.story.StoryParams

interface StoryService {
    suspend fun get(id: Int): Story?
    suspend fun getMyStories(userId: Int, page: Int, limit: Int): PaginatedResult<Story>
    suspend fun getAllStories(page: Int, limit: Int): PaginatedResult<Story>
    suspend fun getLikes(storyId: Int): List<Like>
    suspend fun add(storyParams: StoryParams): Story?
    suspend fun update(id: Int, storyParams: StoryParams): Boolean
    suspend fun delete(storyId: Int): Boolean
    suspend fun like(likeParams: LikeParams): Boolean
    suspend fun comment(commentParams: CommentParams): Boolean
    suspend fun getComments(storyId: Int): List<Comment>
}