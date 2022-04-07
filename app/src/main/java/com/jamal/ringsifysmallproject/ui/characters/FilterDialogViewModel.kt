package com.jamal.ringsifysmallproject.ui.characters

import androidx.lifecycle.ViewModel
import com.jamal.ringsifysmallproject.R
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class FilterDialogViewModel : ViewModel() {

    var raceList = MutableStateFlow(R.array.races)

    fun filterRaceList() {

    }
}