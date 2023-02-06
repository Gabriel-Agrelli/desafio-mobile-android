package com.example.marveluniverse.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marveluniverse.databinding.MainActivityBinding
import com.example.marveluniverse.viewmodel.CharactersViewModel
import com.example.marveluniverse.viewmodel.CharactersViewState
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding

    private val charactersAdapter = CharactersAdapter()

    private val highlightCharactersAdapter = HighlightCharactersAdapter()

    private val viewModel by viewModel<CharactersViewModel>()

    var offset = 0
    var pastVisibleItems = 0
    var totalItemCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupRecycler()
        observeViewState()
        viewModel.getCharacters(offset)
    }

    private fun setupRecycler() {
        binding.rvCharacters.apply {
            adapter = charactersAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy > 0) {
                        val layoutManager = recyclerView.layoutManager as LinearLayoutManager

                        totalItemCount = layoutManager.itemCount
                        pastVisibleItems = layoutManager.findLastVisibleItemPosition()

                        if (pastVisibleItems >= totalItemCount - 1) {
                            offset += 20
                            viewModel.getCharacters(offset)
                        }
                    }
                }
            })
        }
    }

    private fun observeViewState() = lifecycleScope.launchWhenResumed {
        viewModel.viewState.collect { state ->
            when (state) {
                is CharactersViewState.SowProgress -> {
                    binding.pbCharacters.visibility = View.VISIBLE
                }
                is CharactersViewState.HideProgress -> {
                    binding.pbCharacters.visibility = View.GONE
                }
                is CharactersViewState.LoadHighlightsCharacters -> {
                    binding.pager.adapter = highlightCharactersAdapter
                    TabLayoutMediator(binding.tabLayout, binding.pager) { _, _ ->
                    }.attach()
                    highlightCharactersAdapter.submitList(state.characters)
                }
                is CharactersViewState.LoadCharacters -> {
                    charactersAdapter.addData(state.characters)
                }
                is CharactersViewState.OnError -> {
                    binding.txtError.visibility = View.VISIBLE
                }
                is CharactersViewState.Default -> Unit
            }
        }
    }
}