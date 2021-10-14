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
import java.lang.StringBuilder
import java.util.*

@HiltViewModel
class CharacterViewModel @Inject constructor(private val repository: TheOneRepository) :
    ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val characters = currentQuery.switchMap { queryString ->
        repository.getCharacters(queryString).cachedIn(viewModelScope)
    }

    fun searchCharacter(query: String) {
        currentQuery.value = createUpperCase(query)
        Log.d("CharacterViewModel", createUpperCase(query))

    }

    companion object {
        private const val DEFAULT_QUERY = ""
    }

    /**
     * Makes the first char of every string a uppercase
     *
     * @author Jamal Aartsen
     */
    private fun createUpperCase(query: String): String {
        val strArray = query.split(" ").toTypedArray()
        val builder = StringBuilder()
        for (s in strArray) {
            val cap = s.substring(0, 1).uppercase(Locale.getDefault()) + s.substring(1).trim()
            builder.append("$cap ")
        }

        return builder.toString().trim()
    }
}