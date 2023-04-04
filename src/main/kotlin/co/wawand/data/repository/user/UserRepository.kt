package co.wawand.data.repository.user

import co.wawand.utils.BaseResponse

interface UserRepository {
    suspend fun getUser(id: Int): BaseResponse<Any>
}