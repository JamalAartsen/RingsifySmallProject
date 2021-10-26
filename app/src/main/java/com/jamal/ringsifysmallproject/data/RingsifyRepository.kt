package com.jamal.ringsifysmallproject.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.jamal.ringsifysmallproject.api.RingsifyAPI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RingsifyRepository @Inject constructor(private val ringsifyApi: RingsifyAPI) {

    fun getCharacters(query: String?): LiveData<PagingData<RingsifyCharacter>> {
        Log.d("Repository: ", query.toString())
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory =
            {
                TheOnePagingSource(
                    ringsifyApi,
                    if (query == "") {
                        null
                    } else {
                        query
                    }
                )
            }
        ).liveData
    }
}