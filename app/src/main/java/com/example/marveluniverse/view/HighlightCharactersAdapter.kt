package com.example.marveluniverse.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marveluniverse.data.model.Character
import com.example.marveluniverse.data.model.ImageVariant
import com.example.marveluniverse.databinding.ItemHighlightCharacterBinding
import com.example.marveluniverse.util.loadImage
import com.example.marveluniverse.util.prepareImageURL

class HighlightCharactersAdapter : ListAdapter<Character, HighlightCharactersAdapter.ViewHolder>(DiffUtilCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.inflate(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount() = 5

    class ViewHolder private constructor(private val binding: ItemHighlightCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: Character) {
            val imgUrl = prepareImageURL(
                character.thumbnail.path,
                ImageVariant.LANDSCAPE_LARGE.string,
                character.thumbnail.extension
            )

            binding.imgHighlightCharacter.loadImage(imgUrl)
        }

        companion object {
            fun inflate(parent: ViewGroup) =
                ViewHolder(ItemHighlightCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    companion object DiffUtilCallback : DiffUtil.ItemCallback<Character>() {
        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }
    }
}