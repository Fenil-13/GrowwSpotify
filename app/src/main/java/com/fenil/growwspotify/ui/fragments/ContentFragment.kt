package com.fenil.growwspotify.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.fenil.growwspotify.databinding.FragmentContentBinding
import com.fenil.growwspotify.ui.adapters.ContentAdapter
import com.fenil.growwspotify.ui.viewmodels.SpotifyViewModel
import com.fenil.growwspotify.utils.Constants
import com.fenil.growwspotify.utils.PaginationListScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContentFragment : Fragment() {
    private var _binding: FragmentContentBinding? = null
    private val binding get() = _binding!!
    private lateinit var contentAdapter : ContentAdapter
    private val spotifyViewModel by activityViewModels<SpotifyViewModel>()
    private lateinit var fragmentType:String

    companion object {
        const val TYPE = "type"
        fun getInstance(type:String): ContentFragment {
            val fragment = ContentFragment()
            val args = Bundle().apply {
                putString(TYPE,type)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentType = arguments?.getString(TYPE) ?: ""
        setupUi()
        observeViewModels()
    }

    private fun observeViewModels() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                spotifyViewModel.searchResultStateFlow.collect {
                    val dataList: List<Any>? = when (fragmentType) {
                        Constants.SONGS -> it?.tracks?.items
                        Constants.ALBUMS -> it?.albums?.items
                        Constants.PLAYLISTS -> it?.playlists?.items
                        Constants.ARTISTS -> it?.artists?.items
                        else -> null
                    }
                    dataList?.let { albumData ->
                        if (albumData.isEmpty()) {
                            binding.tvError.isVisible = true
                            binding.tvError.text = "No $fragmentType found"
                            binding.rvContent.isVisible = false
                            binding.loadingProgressBar.isVisible = false
                            binding.tvHeading.isVisible = false
                        } else {
                            binding.rvContent.isVisible = true
                            binding.tvError.isVisible = false
                            binding.loadingProgressBar.isVisible = false
                            binding.tvHeading.isVisible = false
                            contentAdapter.updateData(albumData)
                        }
                    } ?: kotlin.run {
                        contentAdapter.clearList()
                    }
                }
            }
        }

        spotifyViewModel.loadingState.observe(viewLifecycleOwner){
            if (it){
                binding.rvContent.isVisible = false
                binding.tvError.isVisible = false
                binding.tvHeading.isVisible = false
            }
            binding.loadingProgressBar.isVisible = it
        }
        spotifyViewModel.errorState.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                binding.tvHeading.isVisible = false
                binding.rvContent.isVisible = false
                binding.loadingProgressBar.isVisible = false
                binding.tvError.isVisible = true
                binding.tvError.text = it
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                spotifyViewModel.offlineMode.collect{
                    binding.tvNoInternet.isVisible = it
                }
            }
        }
    }

    private fun setupUi() {
        contentAdapter = ContentAdapter(
            mutableListOf(),
            onItemClick = {
                lifecycleScope.launch {
                    spotifyViewModel.clickEvent.postValue(it)
                }
            }
        )
        binding.tvHeading.text = "Search $fragmentType"
        binding.rvContent.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = contentAdapter
        }
        binding.rvContent.addOnScrollListener(PaginationListScrollListener{
            spotifyViewModel.fetchNextPage()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}