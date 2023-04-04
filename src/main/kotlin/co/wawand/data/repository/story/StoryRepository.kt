package co.wawand.data.repository.story

import co.wawand.routes.story.CommentParams
import co.wawand.routes.story.LikeParams
import co.wawand.routes.story.StoryParams
import co.wawand.utils.BaseResponse

interface StoryRepository {

    suspend fun getMyStories(userId: Int, page: Int, limit: Int): BaseResponse<Any>
    suspend fun getAllStories(page: Int, limit: Int): BaseResponse<Any>
    suspend fun add(storyParams: StoryParams): BaseResponse<Any>
    suspend fun update(id: Int, storyParams: StoryParams): BaseResponse<Any>
    suspend fun delete(storyId: Int): BaseResponse<Any>
    suspend fun like(likeParams: LikeParams): BaseResponse<Any>
    suspend fun getLikedStories(userId: Int): BaseResponse<Any>
    suspend fun comment(commentParams: CommentParams): BaseResponse<Any>
    suspend fun getComments(storyId: Int): BaseResponse<Any>
}