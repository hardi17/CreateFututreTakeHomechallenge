package com.createfuture.takehome.data.models

data class ApiCharacter(
    val name: String,
    val gender: String,
    val culture: String? = null,
    val born: String,
    val died: String? = null,
    val aliases: List<String>? = null,
    val tvSeries: List<String>,
    val playedBy: List<String>,
)