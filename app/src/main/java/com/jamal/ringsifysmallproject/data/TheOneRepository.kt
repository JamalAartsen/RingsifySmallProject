package com.jamal.ringsifysmallproject.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.jamal.ringsifysmallproject.api.TheOneAPI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TheOneRepository @Inject constructor(private val theOneAPI: TheOneAPI) {

    fun getCharacters(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { TheOnePagingSource(theOneAPI, query) }
        ).liveData
}