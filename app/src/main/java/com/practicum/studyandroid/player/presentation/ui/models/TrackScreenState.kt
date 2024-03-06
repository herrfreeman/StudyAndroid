package com.practicum.studyandroid.player.presentation.ui.models

import com.practicum.studyandroid.player.domain.TrackModel

sealed class TrackScreenState {
    object Loading: TrackScreenState()
    data class Content(
        val trackModel: TrackModel,
    ): TrackScreenState()
}