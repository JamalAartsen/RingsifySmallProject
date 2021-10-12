package com.jamal.ringsifysmallproject.ui.characters

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.jamal.ringsifysmallproject.data.TheOneRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(private val repository: TheOneRepository) :
    ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val characters = currentQuery.switchMap { queryString ->
        repository.getCharacters(queryString).cachedIn(viewModelScope)
    }

    fun searchCharacter(query: String) {
        currentQuery.value = query.lowercase().replaceFirstChar { it.uppercase() }
        Log.d("CharacterViewModel", query.lowercase().replaceFirstChar { it.uppercase() })
    }

    companion object {
        private const val DEFAULT_QUERY = ""
    }
}