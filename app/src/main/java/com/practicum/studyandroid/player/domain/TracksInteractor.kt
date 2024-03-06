package com.practicum.studyandroid.player.domain

interface TracksInteractor {
    fun loadTrackData(trackId : String, onComplete : (TrackModel) -> Unit)
    fun loadSomeData(onComplete : () -> Unit)
}