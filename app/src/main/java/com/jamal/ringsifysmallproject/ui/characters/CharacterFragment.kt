package com.jamal.ringsifysmallproject.ui.characters

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.jamal.ringsifysmallproject.R
import com.jamal.ringsifysmallproject.databinding.FragmentCharactersBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterFragment : Fragment(R.layout.fragment_characters) {

    private val viewModel by viewModels<CharacterViewModel>()
    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCharactersBinding.bind(view)

        val characterAdapter = CharacterAdapter()

        binding.apply {
            recyclerViewCharacter.setHasFixedSize(true)
            recyclerViewCharacter.itemAnimator = null
            recyclerViewCharacter.layoutManager = LinearLayoutManager(requireContext())
            recyclerViewCharacter.adapter = characterAdapter.withLoadStateHeaderAndFooter(
                header = CharacterLoadStateAdapter { characterAdapter.retry() },
                footer = CharacterLoadStateAdapter { characterAdapter.retry() }
            )
            buttonRetryCharacters.setOnClickListener {
                characterAdapter.retry()
            }
        }

        viewModel.characters.observe(viewLifecycleOwner) {
            characterAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        characterAdapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBarCharacters.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerViewCharacter.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetryCharacters.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error
                sadIconNotLoaded.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    characterAdapter.itemCount < 1) {
                    recyclerViewCharacter.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }
            }
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_character, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.apply {
            queryHint = "Gandalf..."
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.recyclerViewCharacter.scrollToPosition(0)
                    viewModel.searchCharacter(query)
                    searchView.clearFocus()
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        // Checks if the search item expand or collapse
        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                viewModel.searchCharacter("")
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
