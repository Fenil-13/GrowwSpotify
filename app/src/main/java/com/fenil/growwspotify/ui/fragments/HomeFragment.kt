package com.fenil.growwspotify.ui.fragments

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.fenil.growwspotify.databinding.FragmentHomeBinding
import com.fenil.growwspotify.ui.adapters.ContentTabLayoutAdapter
import com.fenil.growwspotify.ui.viewmodels.SpotifyViewModel
import com.fenil.growwspotify.utils.Constants.ALBUMS
import com.fenil.growwspotify.utils.Constants.ARTISTS
import com.fenil.growwspotify.utils.Constants.PLAYLISTS
import com.fenil.growwspotify.utils.Constants.SONGS
import com.fenil.growwspotify.utils.NetworkHelper
import com.fenil.growwspotify.utils.showToast
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val spotifyViewModel by activityViewModels<SpotifyViewModel>()

    @Inject
    lateinit var networkHelper: NetworkHelper

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
        binding.searchView.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                (event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)
            ) {
                // Handle the search action here
                if (binding.searchView.text.isNullOrEmpty()){
                    context?.showToast("Search cannot be empty")
                }else{
                    hideKeyboard()
                    performSearch(binding.searchView.text.toString())
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
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
        if (!networkHelper.isNetworkConnected()) {
            binding.searchView.clearFocus()
            binding.searchView.setText("")
            context?.showToast("No internet connection!! you can explore local data")
        }
        spotifyViewModel.clearList()
        spotifyViewModel.searchQuery(query)
    }

    private fun hideKeyboard() {
        context?.let {
            val imm = it.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.searchView.windowToken, 0)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}