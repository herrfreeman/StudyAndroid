package com.practicum.studyandroid.player

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.studyandroid.StudyApplication
import com.practicum.studyandroid.player.domain.PlayStatus
import com.practicum.studyandroid.player.domain.TrackModel
import com.practicum.studyandroid.player.domain.TrackPlayer
import com.practicum.studyandroid.player.domain.TracksInteractor
import com.practicum.studyandroid.player.domain.TracksInteractorImpl
import com.practicum.studyandroid.player.presentation.ui.models.TrackScreenState

class TrackViewModel(private val trackId: String,
                     private val tracksInteractor: TracksInteractor,
                     private val trackPlayer: TrackPlayer,
    ) : ViewModel() {

    private var screenStateLiveData = MutableLiveData<TrackScreenState>(TrackScreenState.Loading)
    private val playStatusLiveData = MutableLiveData<PlayStatus>()

    init {
        Log.d("TEST", "init!: $trackId")

        /*tracksInteractor.loadSomeData(
            onComplete = {
                //Set value
                loadingLiveData.postValue(false)
                //Or: loadingLiveData.value = false
            }
        )*/

        tracksInteractor.loadTrackData(trackId = trackId,
            onComplete = { trackModel ->
                screenStateLiveData.postValue(
                    TrackScreenState.Content(trackModel)
                )
            })

    }

    fun getScreenStateLiveData(): LiveData<TrackScreenState> = screenStateLiveData
    fun getPlayStatusLiveData(): LiveData<PlayStatus> = playStatusLiveData

    fun setState(trackModel: TrackModel) {
        screenStateLiveData.postValue(
            TrackScreenState.Content(trackModel)
        )
    }

    /*Without LiveData
    private var loadingObserver: ((Boolean) -> Unit)? = null
    var isLoading: Boolean = true
        private set(value) {
            field = value
            loadingObserver?.invoke(value)
        }

    fun addLoadingObserver(loadingObserver: ((Boolean) -> Unit)) {
        this.loadingObserver = loadingObserver
    }
    fun removeLoadingObserver() {
        this.loadingObserver = null
    }
*/

    private var loadingLiveData = MutableLiveData(true)

    fun getLoadingLiveData(): LiveData<Boolean> = loadingLiveData

    fun play() {
        trackPlayer.play(
            trackId = trackId,
            // 1
            statusObserver = object : TrackPlayer.StatusObserver {
                override fun onProgress(progress: Float) {
                    // 2
                    playStatusLiveData.value = getCurrentPlayStatus().copy(progress = progress)
                }

                override fun onStop() {
                    // 3
                    playStatusLiveData.value = getCurrentPlayStatus().copy(isPlaying = false)
                }

                override fun onPlay() {
                    // 4
                    playStatusLiveData.value = getCurrentPlayStatus().copy(isPlaying = true)
                }
            },
        )
    }

    // 5
    fun pause() {
        trackPlayer.pause(trackId)
    }

    // 6
    override fun onCleared() {
        trackPlayer.release(trackId)
    }

    private fun getCurrentPlayStatus(): PlayStatus {
        return playStatusLiveData.value ?: PlayStatus(progress = 0f, isPlaying = false)
    }

    companion object {
        /*fun getViewModelFactory(trackId: String): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                // 1
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T {
                    // 2
                    val application = checkNotNull(extras[APPLICATION_KEY])

                    return TrackViewModel(
                        trackId,
                        // 3
                        (application as StudyApplication).provideTracksInteractor(),
                    ) as T
                }
            }*/
        fun getViewModelFactory(trackId: String): ViewModelProvider.Factory = viewModelFactory {
            initializer {

                val myApp = this[APPLICATION_KEY] as StudyApplication
                val interactor = myApp.provideTracksInteractor()
                val trackPlayer = myApp.provideTrackPlayer()

                TrackViewModel(
                    trackId,
                    interactor,
                    trackPlayer,
                )
            }
        }
    }
}

