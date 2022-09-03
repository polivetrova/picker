package com.pet.picker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pet.picker.R
import com.pet.picker.databinding.FragmentSearchBinding
import com.pet.picker.model.AppState
import com.pet.picker.showSnackBarWithAction

const val BACKSTACK_KEY_SEARCH_SCREEN = "search screen"
const val PHOTO_KEY: String = "photo link"

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private var _bindingSearch: FragmentSearchBinding? = null
    private val bindingSearch get() = _bindingSearch!!
    private var adapter: UnsplashPhotoAdapter? = null
    private val viewModel: SearchFragmentViewModel by lazy {
        ViewModelProvider(this).get(SearchFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindingSearch = FragmentSearchBinding.inflate(inflater, container, false)
        return bindingSearch.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(bindingSearch) {
            searchResultRoot.adapter = getAdapter()
            searchButton.setOnClickListener {
                searchResultRoot.visibility = View.VISIBLE
                viewModel.getSearchResultsFor(searchBar.text.toString())
            }

            viewModel.searchResultsLiveData.observe(viewLifecycleOwner) { appState ->
                when (appState) {
                    is AppState.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    is AppState.Success -> {
                        progressBar.visibility = View.INVISIBLE
                        searchResultRoot.visibility = View.VISIBLE
                        adapter?.submitList(appState.searchResults)
                    }
                    is AppState.Error -> {
                        progressBar.visibility = View.INVISIBLE
                        searchResultRoot.visibility = View.INVISIBLE
                        view.showSnackBarWithAction(
                            "Something went wrong!",
                            "Reload?",
                            { viewModel.getSearchResultsFor(searchBar.text.toString()) }
                        )
                    }
                }
            }
        }
    }

    private fun getAdapter(): UnsplashPhotoAdapter {
        adapter = UnsplashPhotoAdapter(object : OnItemViewClickListener {
            override fun onItemViewClick(linkFull: String) {
                val manager = activity?.supportFragmentManager
                manager?.let {
                    val bundle = Bundle().apply {
                        putString(PHOTO_KEY, linkFull)
                    }
                    manager.beginTransaction()
                        .add(R.id.container, PhotoViewFragment.newInstance(bundle))
                        .addToBackStack(BACKSTACK_KEY_SEARCH_SCREEN)
                        .commit()
                }
            }
        })
        return adapter as UnsplashPhotoAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingSearch = null
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(linkFull: String)
    }
}