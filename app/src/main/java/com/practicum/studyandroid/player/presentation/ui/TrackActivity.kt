package com.practicum.studyandroid.player.presentation.ui

import android.os.Bundle
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.practicum.studyandroid.R
import com.practicum.studyandroid.databinding.ActivityTrackBinding
import com.practicum.studyandroid.player.TrackViewModel
import com.practicum.studyandroid.player.domain.PlayStatus
import com.practicum.studyandroid.player.presentation.ui.models.TrackScreenState

class TrackActivity : ComponentActivity() {

    private lateinit var viewModel: TrackViewModel
    private lateinit var binding: ActivityTrackBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackBinding.inflate(layoutInflater)
        setContentView(binding.root)



        viewModel = ViewModelProvider(this, TrackViewModel.getViewModelFactory("123"))[TrackViewModel::class.java]

        //Not official using, just my try
        binding.trackList.adapter = TrackRecyclerViewAdapter { trackModel ->
            viewModel.setState(trackModel)
        }

        /*Without LiveData
        viewModel.addLoadingObserver { isLoading ->
            changeProgressBarVisibility(isLoading)
        }*/

        viewModel.getLoadingLiveData().observe(this) { isLoading ->
            changeProgressBarVisibility(isLoading)
        }

        viewModel.getScreenStateLiveData().observe(this) { screenState ->
            // 1
            when (screenState) {
                is TrackScreenState.Content -> {
                    changeContentVisibility(loading = false)
                    //binding.picture.setImage(screenState.trackModel.pictureUrl)
                    binding.author.text = screenState.trackModel.author
                    binding.trackName.text = screenState.trackModel.name
                }
                is TrackScreenState.Loading -> {
                    changeContentVisibility(loading = true)
                }
            }
        }

        viewModel.getPlayStatusLiveData().observe(this) { playStatus ->
            changeButtonStyle(playStatus)
            //binding.seekBar.value = playStatus.progress
        }

    }

    private fun changeButtonStyle(playStatus: PlayStatus) {
        // Меняем вид кнопки проигрывания в зависимости от того, играет сейчас трек или нет
    }

    /*Without LiveData
    override fun onDestroy() {
        super.onDestroy()
        viewModel.removeLoadingObserver()
    }*/

    private fun changeContentVisibility(loading: Boolean) {
        binding.progressBar.isVisible = loading

        //binding.picture.isVisible = !loading
        //binding.author.isVisible = !loading
        //binding.trackName.isVisible = !loading
    }

    private fun changeProgressBarVisibility(visible: Boolean) {
        binding.progressBar.isVisible = visible
    }


}