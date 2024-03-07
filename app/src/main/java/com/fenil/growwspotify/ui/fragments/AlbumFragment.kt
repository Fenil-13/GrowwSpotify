package com.fenil.growwspotify.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.fenil.growwspotify.R
import com.fenil.growwspotify.data.model.AlbumItem
import com.fenil.growwspotify.databinding.FragmentAlbumBinding

class AlbumFragment : Fragment() {
    private var _binding: FragmentAlbumBinding? = null
    private val binding get() = _binding!!

    val args: AlbumFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val albumItem = args.albumItem
        renderUi(albumItem)
    }

    private fun renderUi(albumItem: AlbumItem) {
        binding.tvAlbumName.text = albumItem.name
        binding.tvArtistNames.text = albumItem.artists?.joinToString { it.name ?: "Unknown Artist" }
        binding.tvReleaseDate.text = "Release Date: ${albumItem.releaseDate ?: "Unknown"}"
        binding.tvTotalTracks.text = "Total Tracks: ${albumItem.totalTracks ?: 0}"

        Glide.with(requireContext())
            .load(albumItem.images?.firstOrNull()?.url)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .into(binding.ivAlbumCover)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}