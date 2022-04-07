package com.jamal.ringsifysmallproject.ui.characters

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.jamal.ringsifysmallproject.data.RingsifyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import java.lang.StringBuilder
import java.util.*

@ExperimentalCoroutinesApi
@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: RingsifyRepository,
    state: SavedStateHandle
) :
    ViewModel() {

    val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)
    private val filterRace: MutableStateFlow<Races?> = MutableStateFlow(null)

    private val charactersFlow = combine(currentQuery.asFlow(), filterRace) { searchQuery, filter ->
        Pair(searchQuery, filter)
    }.flatMapLatest { (s, f) ->
        repository.getCharacters(s, f?.toString()).cachedIn(viewModelScope)
    }

    val characters = charactersFlow.asLiveData()


    private val String.capitalizeWord
        get() = this.lowercase(Locale.getDefault()).split(" ").joinToString(" ") {
            it.replaceFirstChar { it ->
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }
        }

    fun searchCharacter(query: String?) {
        currentQuery.value = query!!.capitalizeWord
    }

    fun filterRaces(race: Races) {
        if (race == Races.Null) {
            filterRace.value = null
        } else {
            filterRace.value = race
        }
    }

    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = ""
    }

}

enum class Races {
    Men, Elves, Hobbits, Null
}

