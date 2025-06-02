package com.createfuture.takehome.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ApiCharacter(
    val name: String,
    val gender: String,
    val culture: String,
    val born: String,
    val died: String,
    val aliases: List<String>,
    val tvSeries: List<String>,
    val playedBy: List<String>,
) : Parcelable