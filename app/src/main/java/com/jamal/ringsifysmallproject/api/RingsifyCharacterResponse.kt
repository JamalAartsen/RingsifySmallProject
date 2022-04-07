package com.jamal.ringsifysmallproject.api

import com.jamal.ringsifysmallproject.data.RingsifyCharacter

data class RingsifyCharacterResponse(
    val results: List<RingsifyCharacter>
)