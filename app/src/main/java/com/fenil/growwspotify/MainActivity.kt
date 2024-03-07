package com.fenil.growwspotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.fenil.growwspotify.data.model.AlbumItem
import com.fenil.growwspotify.data.model.ArtistItem
import com.fenil.growwspotify.data.model.PlaylistItem
import com.fenil.growwspotify.data.model.TrackItem
import com.fenil.growwspotify.databinding.ActivityMainBinding
import com.fenil.growwspotify.ui.fragments.HomeFragmentDirections
import com.fenil.growwspotify.ui.viewmodels.SpotifyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val spotifyViewModel by viewModels<SpotifyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeViewModels()
    }

    private fun observeViewModels() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                spotifyViewModel.navigateToDetailsPageTrigger.collect {
                    it?.let { navigateToDetailPage(it) }
                }
            }
        }
    }

    private fun navigateToDetailPage(data: Any) {
        val navController = findNavController(R.id.fragment_container_view)
        when (data) {
            is AlbumItem ->{
                val action = HomeFragmentDirections.actionHomeFragmentToAlbumFragment(data)
                navController.navigate(action)
            }
            is TrackItem -> {
                val action = HomeFragmentDirections.actionHomeFragmentToTrackFragment(data)
                navController.navigate(action)
            }
            is PlaylistItem -> {
                val action = HomeFragmentDirections.actionHomeFragmentToPlaylistFragment(data)
                navController.navigate(action)
            }
            is ArtistItem -> {
                val action = HomeFragmentDirections.actionHomeFragmentToArtistFragment(data)
                navController.navigate(action)
            }
        }
    }
}