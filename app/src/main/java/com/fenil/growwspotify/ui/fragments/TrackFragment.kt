package com.fenil.growwspotify.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.fenil.growwspotify.R
import com.fenil.growwspotify.data.model.TrackItem
import com.fenil.growwspotify.databinding.FragmentTrackBinding

class TrackFragment : Fragment() {
    private var _binding: FragmentTrackBinding? = null
    private val binding get() = _binding!!

    private val args: TrackFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val trackItem: TrackItem = args.trackItem
        renderUi(trackItem)
    }

    private fun renderUi(trackItem: TrackItem) {
        binding.tvTrackName.text = trackItem.name
        binding.tvAlbumName.text = trackItem.album?.name ?: "Unknown Album"
        binding.tvArtistName.text = trackItem.artists?.joinToString { it.name ?: "Unknown Artist" }
        binding.tvDuration.text = "Duration: ${formatDuration(trackItem.durationMs)}"
        binding.tvExplicit.text = if (trackItem.explicit == true) "Explicit" else "Not Explicit"

        Glide.with(requireContext())
            .load(trackItem.album?.images?.firstOrNull()?.url)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .into(binding.ivAlbumCover)
    }

    private fun formatDuration(durationMs: Int?): String {
        durationMs?.let {
            val minutes = it / 1000 / 60
            val seconds = (it / 1000) % 60
            return "$minutes:${String.format("%02d", seconds)}"
        }
        return "Unknown"
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}