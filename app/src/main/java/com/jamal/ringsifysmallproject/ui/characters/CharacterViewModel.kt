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

    private val String.capitalizeWord
        get() = this.lowercase(Locale.getDefault()).split(" ").joinToString(" ") { it.replaceFirstChar { it ->
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        } }

    fun searchCharacter(query: String?) {
        currentQuery.value = query!!.capitalizeWord
    }

    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = ""
    }
}