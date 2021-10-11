package com.jamal.ringsifysmallproject.ui.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jamal.ringsifysmallproject.data.Character
import com.jamal.ringsifysmallproject.databinding.RowLayoutCharactersBinding

class CharacterAdapter : PagingDataAdapter<Character, CharacterAdapter.ViewHolder>(
    CHARACTER_COMPARATOR) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowLayoutCharactersBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    class ViewHolder(private val binding: RowLayoutCharactersBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(character: Character) {
                binding.apply {
                    nameCharacter.text = character.name
                    raceCharacter.text = character.race
                }
            }

    }

    companion object {
        private val CHARACTER_COMPARATOR = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character) =
                oldItem._id == newItem._id

            override fun areContentsTheSame(oldItem: Character, newItem: Character) =
                oldItem == newItem
        }
    }
}