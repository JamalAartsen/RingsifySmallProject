package com.jamal.ringsifysmallproject.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jamal.ringsifysmallproject.api.TheOneAPI
import retrofit2.HttpException
import java.io.IOException

private const val CHARACTER_STARTING_PAGE_INDEX = 1

class TheOnePagingSource(
    private val theOneApi: TheOneAPI,
    private val query: String
) : PagingSource<Int, Character>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val position = params.key ?: CHARACTER_STARTING_PAGE_INDEX

        return try {
            val response = theOneApi.getCharacters(
                auth = "${TheOneAPI.BEARER_TOKEN_TEXT} ${TheOneAPI.BEARER_TOKEN}",
                query = query,
                limit_per_page = params.loadSize,
                page = position
            )
            val characters = response.docs
            Log.d("TheOnePagingSource", "$characters")

            LoadResult.Page(
                data = characters,
                prevKey = if (position == CHARACTER_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (characters.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return null
    }
}