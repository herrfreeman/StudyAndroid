package com.practicum.studyandroid.player.domain

class TracksInteractorImpl(any: Any?) : TracksInteractor {

    override fun loadSomeData(onComplete : () -> Unit) {
        onComplete()
    }

    override fun loadTrackData(trackId : String, onComplete : (TrackModel) -> Unit) {
        onComplete(TrackModel("1", "2", "3", "4"))
    }
}