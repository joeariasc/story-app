package co.wawand.data.repository.auth

import co.wawand.data.service.auth.AuthService
import co.wawand.utils.BaseResponse
import co.wawand.config.*
import co.wawand.routes.auth.CreateUserParams
import co.wawand.routes.auth.UserLoginParams
import co.wawand.security.JwtConfig

class AuthRepositoryImpl(
    private val authService: AuthService
) : AuthRepository {
    override suspend fun registerUser(params: CreateUserParams): BaseResponse<Any> {
        return if (isEmailExist(params.email)) {
            BaseResponse.ErrorResponse(message = MESSAGE_EMAIL_ALREADY_REGISTERED)
        } else {
            val user = authService.registerUser(params)
            if (user != null) {
                val token = JwtConfig.instance.createAccessToken(user.id)
                user.authToken = token
                BaseResponse.SuccessResponse(data = user, message = USER_REGISTRATION_SUCCESS)
            } else {
                BaseResponse.ErrorResponse(GENERIC_ERROR)
            }
        }
    }

    override suspend fun loginUser(params: UserLoginParams): BaseResponse<Any> {
        val user = authService.loginUser(params.email, params.password)
        return if (user != null) {
            val token = JwtConfig.instance.createAccessToken(user.id)
            user.authToken = token
            BaseResponse.SuccessResponse(data = user, message = USER_LOGIN_SUCCESS)
        } else {
            BaseResponse.ErrorResponse(USER_LOGIN_FAILURE)
        }
    }

    private suspend fun isEmailExist(email: String): Boolean {
        return authService.findUserByEmail(email) != null
    }
}