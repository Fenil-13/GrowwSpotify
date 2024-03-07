package com.fenil.growwspotify.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.fenil.growwspotify.databinding.FragmentHomeBinding
import com.fenil.growwspotify.ui.adapters.ContentTabLayoutAdapter
import com.fenil.growwspotify.ui.viewmodels.SpotifyViewModel
import com.fenil.growwspotify.utils.Constants.ALBUMS
import com.fenil.growwspotify.utils.Constants.ARTISTS
import com.fenil.growwspotify.utils.Constants.PLAYLISTS
import com.fenil.growwspotify.utils.Constants.SONGS
import com.fenil.growwspotify.utils.showToast
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val spotifyViewModel by activityViewModels<SpotifyViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handling the search query submission
                Log.d("fenil", "onQueryTextSubmit: query $query")
                if (query.isNullOrBlank()) {
                    context?.showToast("Please enter a search query")
                }else{
                    binding.searchView.clearFocus()
                    performSearch(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        setupContentAdapter()
    }

    private fun setupContentAdapter() {
        val fragments = listOf(
            ContentFragment.getInstance(SONGS),
            ContentFragment.getInstance(ALBUMS),
            ContentFragment.getInstance(PLAYLISTS),
            ContentFragment.getInstance(ARTISTS)
        )
        binding.vpContent.adapter = ContentTabLayoutAdapter(fragments, childFragmentManager, lifecycle)
        val tabTitles = listOf(SONGS, ALBUMS, PLAYLISTS, ARTISTS)

        TabLayoutMediator(binding.tabLayout, binding.vpContent) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    private fun performSearch(query: String) {
        spotifyViewModel.clearList()
        spotifyViewModel.searchQuery(query)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}