package com.jamal.ringsifysmallproject.ui.characters

import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.jamal.ringsifysmallproject.R
import com.jamal.ringsifysmallproject.data.RingsifyCharacter
import com.jamal.ringsifysmallproject.databinding.FragmentCharactersBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CharacterFragment : Fragment(R.layout.fragment_characters), CharacterAdapter.OnItemClickListener {

    private val viewModel by viewModels<CharacterViewModel>()
    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!
    private lateinit var avd: AnimatedVectorDrawable
    private lateinit var avd2: AnimatedVectorDrawableCompat
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val characterAdapter = CharacterAdapter(this)

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
                recyclerViewCharacter.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetryCharacters.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error
                sadIconNotLoaded.isVisible = loadState.source.refresh is LoadState.Error
                progressRingLoading.isVisible = loadState.source.refresh is LoadState.Loading

                val drawableRingLoading = progressRingLoading.drawable

                if (drawableRingLoading is AnimatedVectorDrawable) {
                    avd = drawableRingLoading
                    avd.start()
                    drawableRingLoading.registerAnimationCallback(object : Animatable2.AnimationCallback() {
                        override fun onAnimationEnd(drawable: Drawable?) {
                            super.onAnimationEnd(drawable)

                            avd.start()
                        }
                    })
                } else {
                    avd2 = drawableRingLoading as AnimatedVectorDrawableCompat
                    avd2.start()
                }

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
        searchView = searchItem.actionView as SearchView
        searchView.apply {
            queryHint = "Gandalf..."
        }

        val searchQuery = viewModel.currentQuery.value
        if (searchQuery != null && searchQuery.isNotEmpty()) {
            searchItem.expandActionView()
            searchView.setQuery(searchQuery, false)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter -> {
                viewModel.filterRaces(Races.Elves)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        searchView.setOnQueryTextListener(null)
    }

    override fun onItemClick(character: RingsifyCharacter) {
        val action = CharacterFragmentDirections.actionCharacterFragmentToDetailsFragment(character)
        findNavController().navigate(action)
    }
}
