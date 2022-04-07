package com.jamal.ringsifysmallproject.ui.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jamal.ringsifysmallproject.GlideImage
import com.jamal.ringsifysmallproject.R
import com.jamal.ringsifysmallproject.data.RingsifyCharacter
import com.jamal.ringsifysmallproject.databinding.RowLayoutCharactersBinding

class CharacterAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<RingsifyCharacter, CharacterAdapter.ViewHolder>(
        CHARACTER_COMPARATOR
    ) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RowLayoutCharactersBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: RowLayoutCharactersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(character: RingsifyCharacter) {
            binding.apply {
                nameCharacter.text = character.name
                raceCharacter.text = character.race

                val context = itemView.context
                if (character.imageUrl!!.isNotBlank() || character.imageUrl.isNotEmpty()) {
                    GlideImage.imageHolder(context, character.imageUrl, characterImage)
                } else {
                    GlideImage.imageHolder(context, R.drawable.no_image, characterImage)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(character: RingsifyCharacter)
    }

    companion object {
        private val CHARACTER_COMPARATOR = object : DiffUtil.ItemCallback<RingsifyCharacter>() {
            override fun areItemsTheSame(oldItem: RingsifyCharacter, newItem: RingsifyCharacter) =
                oldItem._id == newItem._id

            override fun areContentsTheSame(
                oldItem: RingsifyCharacter,
                newItem: RingsifyCharacter
            ) =
                oldItem == newItem
        }
    }
}