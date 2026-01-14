package com.mtcnextlabs.imnuricrestine.utils

import com.mtcnextlabs.imnuricrestine.data.db.models.HymnWithFavoriteStatus
import com.mtcnextlabs.imnuricrestine.data.db.models.HymnWithLyrics
import com.mtcnextlabs.imnuricrestine.models.Hymn
import com.mtcnextlabs.imnuricrestine.models.Verse

fun HymnWithFavoriteStatus.asHymn(): Hymn {
    val verses = ArrayList<Verse>()
    var verseTagCount = 0

    for (verse in this.hymnWithLyrics.lyrics) {
        val tag: String = if (verse.is_chorus) CHORUS_TAG else (++verseTagCount).toString()
        verses.add(Verse(verse.verse_text, tag, verse.is_chorus))
    }

    return Hymn(
        this.hymnWithLyrics.hymn.id,
        this.hymnWithLyrics.hymn.hymn_number,
        this.hymnWithLyrics.hymn.title, verses,
        this.isFavorite
    )
}

fun HymnWithLyrics.asHymn(): Hymn {
    val verses = ArrayList<Verse>()
    var verseTagCount = 0

    for (verse in this.lyrics) {
        val tag: String = if (verse.is_chorus) CHORUS_TAG else (++verseTagCount).toString()
        verses.add(Verse(verse.verse_text, tag, verse.is_chorus))
    }

    return Hymn(
        this.hymn.id,
        this.hymn.hymn_number,
        this.hymn.title, verses,
        true
    )
}
