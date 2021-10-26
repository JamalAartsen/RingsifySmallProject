package com.jamal.ringsifysmallproject.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jamal.ringsifysmallproject.api.RingsifyAPI
import retrofit2.HttpException
import java.io.IOException

private const val CHARACTER_STARTING_PAGE_INDEX = 1

class TheOnePagingSource(
    private val ringsifyApi: RingsifyAPI,
    private val query: String?
) : PagingSource<Int, RingsifyCharacter>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RingsifyCharacter> {
        val position = params.key ?: CHARACTER_STARTING_PAGE_INDEX

        return try {
            val response = ringsifyApi.getCharacters(
                searchTerm = query,
                limit = params.loadSize,
                page = position
            )
            val characters = response.results

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

    override fun getRefreshKey(state: PagingState<Int, RingsifyCharacter>): Int? {
        return null
    }
}