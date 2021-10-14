package com.jamal.ringsifysmallproject.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    val _id: String,
    val race: String?,
    val gender: String?,
    val birth: String?,
    val spouse: String?,
    val death: String?,
    val realm: String?,
    val name: String,
    val wikiUrl: String?
) : Parcelable
