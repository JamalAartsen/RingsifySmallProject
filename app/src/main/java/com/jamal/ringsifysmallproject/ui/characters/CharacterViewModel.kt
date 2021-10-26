package com.jamal.ringsifysmallproject.ui.characters

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.jamal.ringsifysmallproject.data.RingsifyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import java.lang.StringBuilder
import java.util.*

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: RingsifyRepository,
    state: SavedStateHandle
) :
    ViewModel() {

    val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    val characters = currentQuery.switchMap { queryString ->
        repository.getCharacters(queryString).cachedIn(viewModelScope)
    }

    fun searchCharacter(query: String?) {
        currentQuery.value = createUpperCase(query)
        Log.d("CharacterViewModel", createUpperCase(query).toString())

    }

    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = ""
    }

    /**
     * Makes the first char of every string a uppercase
     *
     * @author Jamal Aartsen
     */
    private fun createUpperCase(query: String?): String? {
        if (query != null) {
            if (query.isNotBlank() || query.isNotEmpty()) {
                val strArray = query.split(" ").toTypedArray()
                val builder = StringBuilder()
                for (s in strArray) {
                    val cap = s.substring(0, 1).uppercase(Locale.getDefault()) + s.substring(1)
                    builder.append("$cap ")
                }

                return builder.toString().trim()
            } else {
                return null
            }
        } else {
            return null
        }
    }
}