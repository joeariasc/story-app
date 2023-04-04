package co.wawand.data.repository.auth

import co.wawand.routes.auth.CreateUserParams
import co.wawand.routes.auth.UserLoginParams
import co.wawand.utils.BaseResponse

interface AuthRepository {
    suspend fun registerUser(params: CreateUserParams): BaseResponse<Any>
    suspend fun loginUser(params: UserLoginParams): BaseResponse<Any>
}