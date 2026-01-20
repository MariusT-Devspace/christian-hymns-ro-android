package com.mtcnextlabs.imnuricrestine.data

import com.mtcnextlabs.imnuricrestine.data.db.models.HymnDetailWithFavorite
import com.mtcnextlabs.imnuricrestine.data.db.models.HymnWithFavorite
import com.mtcnextlabs.imnuricrestine.models.Hymn
import com.mtcnextlabs.imnuricrestine.models.HymnDetail
import com.mtcnextlabs.imnuricrestine.models.Verse
import com.mtcnextlabs.imnuricrestine.utils.CHORUS_TAG

fun HymnWithFavorite.asHymn(): Hymn {
    return Hymn(
        hymnEntity.id,
        hymnEntity.number,
        hymnEntity.title,
        isFavorite
    )
}

fun HymnDetailWithFavorite.asHymn(): HymnDetail {
    val verses: MutableList<Verse> = mutableListOf()
    var verseTagCount = 0

    for (verse in lyrics) {
        val tag: String = if (verse.is_chorus) CHORUS_TAG else (++verseTagCount).toString()
        verses.add(Verse(verse.verse_text, tag, verse.is_chorus))
    }

    return HymnDetail(
        hymnEntity.id,
        hymnEntity.number,
        hymnEntity.title,
        verses,
        isFavorite
    )
}