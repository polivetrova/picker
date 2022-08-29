package com.pet.picker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pet.picker.databinding.FragmentSearchBinding
import com.pet.picker.model.AppState
import com.pet.picker.showSnackBarWithAction

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private val presenter = SearchFragmentPresenter()
    private var _bindingSearch: FragmentSearchBinding? = null
    private val bindingSearch get() = _bindingSearch!!
    private val adapter = SearchFragmentAdapter(this)

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
            searchResultRoot.adapter = adapter
            searchButton.setOnClickListener {
                searchResultRoot.visibility = View.VISIBLE

                presenter.searchResultsLiveData.observe(viewLifecycleOwner) { appState ->
                    when (appState) {
                        is AppState.Loading -> {
                            progressBar.visibility = View.VISIBLE
                        }
                        is AppState.Success -> {
                            progressBar.visibility = View.INVISIBLE
                            searchResultRoot.visibility = View.VISIBLE
                            adapter.setSearchResults(appState.searchResults)
                        }
                        is AppState.Error -> {
                            progressBar.visibility = View.INVISIBLE
                            searchResultRoot.visibility = View.INVISIBLE
                            view.showSnackBarWithAction(
                                "Something went wrong!",
                                "Reload?",
                                { presenter.getSearchResultsFor(searchBar.text.toString()) }
                            )
                        }
                    }
                }
                presenter.getSearchResultsFor(searchBar.text.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingSearch = null
    }
}