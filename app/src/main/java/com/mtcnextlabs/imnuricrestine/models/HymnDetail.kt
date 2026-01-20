package com.mtcnextlabs.imnuricrestine.models

data class HymnDetail(
    val id: Int,
    val number: String,
    val title: String,
    val lyrics: List<Verse>,
    val isFavorite: Boolean
)
