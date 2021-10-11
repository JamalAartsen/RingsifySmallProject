package com.jamal.ringsifysmallproject.api

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TheOneAPI {

    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
        const val BEARER_TOKEN_TEXT = "Bearer"
        const val BEARER_TOKEN = "sGzk5GdxwqL6slA-K4MS"
        const val CHARACTER_ENDPOINT = "character"
        const val BASE_URL = "https://the-one-api.dev/v2/"
    }

    @GET(CHARACTER_ENDPOINT)
    suspend fun getCharacters(
        @Header(HEADER_AUTHORIZATION) auth: String,
        @Query("name") query: String,
        @Query("limit") limit_per_page: Int,
        @Query("page") page: Int
    ): CharacterResponse
}