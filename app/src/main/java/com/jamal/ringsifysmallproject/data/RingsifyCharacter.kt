package com.jamal.ringsifysmallproject.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RingsifyCharacter(
    val _id: String,
    val name: String,
    val imageUrl: String?,
    val description: String?,
    val race: String?,
    val birth: String?,
    val death: String?,
    val realm: String?,
    val culture: String?,
    val fandomUrl: String?
) : Parcelable
