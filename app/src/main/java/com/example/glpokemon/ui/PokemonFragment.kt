package com.example.glpokemon.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.glpokemon.adapter.SingleViewHolderAdapter
import com.example.glpokemon.databinding.FragmentPokemonBinding
import com.example.glpokemon.network.Moves
import com.example.glpokemon.network.PokemonDetails
import com.example.glpokemon.network.Stats
import com.example.glpokemon.viewholder.MovesViewHolder
import com.example.glpokemon.viewholder.StatsViewHolder
import com.example.glpokemon.viewmodel.PokemonViewModel
import com.example.glpokemon.viewmodel.capitalizeFirst

class PokemonFragment : Fragment() {
    private var _binding: FragmentPokemonBinding? = null
    private val binding get() = _binding!!

    private lateinit var mMovesAdapter: SingleViewHolderAdapter<Moves, MovesViewHolder>
    private lateinit var mStatsAdapter: SingleViewHolderAdapter<Stats, StatsViewHolder>
    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(PokemonViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPokemonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMovesRecyclerView()
        initStatsRecyclerView()

        binding.pokemonRefresh.setOnClickListener { viewModel.fetchRandomPokemon() }
        viewModel.mFetching.observe(viewLifecycleOwner, { toggleProgressContainer(it) })
        viewModel.mPokemonDetails.observe(viewLifecycleOwner, { fillDetailsOfPokemon(it) })
            .also { viewModel.fetchRandomPokemon() }
    }

    private fun initMovesRecyclerView() {
        binding.pokemonMovesRv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.pokemonMovesRv.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        mMovesAdapter = SingleViewHolderAdapter(MovesViewHolder.LAYOUT_ID) { itemView -> MovesViewHolder(itemView) }
        binding.pokemonMovesRv.adapter = mMovesAdapter
    }

    private fun initStatsRecyclerView() {
        binding.pokemonStatsRv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.pokemonStatsRv.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        mStatsAdapter = SingleViewHolderAdapter(StatsViewHolder.LAYOUT_ID) { itemView -> StatsViewHolder(itemView) }
        binding.pokemonStatsRv.adapter = mStatsAdapter
    }

    private fun toggleProgressContainer(fetching: Boolean) {
        if (fetching) {
            binding.progressContainer.visibility = View.VISIBLE
            binding.pokemonContainer.visibility = View.GONE
        } else {
            binding.progressContainer.visibility = View.GONE
            binding.pokemonContainer.visibility = View.VISIBLE
        }
    }

    private fun fillDetailsOfPokemon(pokemon: PokemonDetails) {
        val name = pokemon.name?.capitalizeFirst() ?:""
        val moves = pokemon.moves
        val stats = pokemon.stats
        val sprites = pokemon.sprites

        binding.pokemonNameTxt.text = name ?: ""
        Toast.makeText(context, "Wild $name appeared!", Toast.LENGTH_SHORT).show()

        sprites?.let {
            Glide.with(requireContext()).load(it.front_default).into(binding.pokemonFrontImg)
            Glide.with(requireContext()).load(it.back_default).into(binding.pokemonBackImg)
        }

        mMovesAdapter.setData(moves).also { binding.pokemonMovesRv.scrollToPosition(0) }
        mStatsAdapter.setData(stats).also { binding.pokemonStatsRv.scrollToPosition(0) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}