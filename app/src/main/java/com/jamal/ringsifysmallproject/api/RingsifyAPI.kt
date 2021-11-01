package com.jamal.ringsifysmallproject.api

import retrofit2.http.GET
import retrofit2.http.Query

interface RingsifyAPI {

    companion object {
        const val CHARACTER_ENDPOINT = "characters"
        const val BASE_URL = "https://ringsify-api-v1.herokuapp.com/"
    }

    @GET(CHARACTER_ENDPOINT)
    suspend fun getCharacters(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("name") searchTerm: String?,
        @Query("race") filterRace: String?
    ): RingsifyCharacterResponse
}