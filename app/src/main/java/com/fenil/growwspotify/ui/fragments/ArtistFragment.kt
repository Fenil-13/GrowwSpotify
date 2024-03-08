package com.fenil.growwspotify.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.fenil.growwspotify.R
import com.fenil.growwspotify.data.remote.model.ArtistItem
import com.fenil.growwspotify.databinding.FragmentArtistBinding

class ArtistFragment : Fragment() {
    private var _binding: FragmentArtistBinding? = null
    private val binding get() = _binding!!

    private val args: ArtistFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArtistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val artistItem = args.artistItem
        renderUi(artistItem)
    }

    private fun renderUi(artistItem: ArtistItem) {
        binding.tvArtistName.text = artistItem.name
        binding.tvFollowers.text = "Followers: ${artistItem.followers?.total ?: 0}"
        binding.tvGenres.text = "Genres: ${artistItem.genres?.joinToString() ?: "Unknown"}"

        Glide.with(requireContext())
            .load(artistItem.images?.firstOrNull()?.url)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .into(binding.ivArtistImage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}