package com.pet.picker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
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
    private lateinit var adapter: UnsplashPhotoAdapter
    private val viewModel: SearchFragmentViewModel by lazy {
        ViewModelProvider(this).get(SearchFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindingSearch = FragmentSearchBinding.inflate(inflater, container, false)

        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL).also {
            it.setDrawable(resources.getDrawable(R.drawable.item_decoration))
        }
        bindingSearch.searchResultRoot.addItemDecoration(itemDecoration)
        return bindingSearch.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(bindingSearch) {
            adapter = getAdapter()
            searchResultRoot.adapter = adapter
            searchButton.setOnClickListener {
                searchResultRoot.visibility = View.VISIBLE
                viewModel.getSearchResultsFor(searchBar.text.toString())
            }

            viewModel.behaviorSubject.subscribe { appState ->
                when (appState) {
                    is AppState.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    is AppState.Success -> {
                        progressBar.visibility = View.INVISIBLE
                        searchResultRoot.visibility = View.VISIBLE
                        adapter.submitList(appState.searchResults)
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
        return UnsplashPhotoAdapter(object : OnItemViewClickListener {
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingSearch = null
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(linkFull: String)
    }
}