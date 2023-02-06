package com.example.marveluniverse.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marveluniverse.R
import com.example.marveluniverse.data.model.ImageVariant
import com.example.marveluniverse.util.prepareImageURL
import kotlinx.android.synthetic.main.item_character.view.*
import com.example.marveluniverse.data.model.Character
import com.example.marveluniverse.util.loadImage

class CharactersAdapter : RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

    private var characters = ArrayList<Character>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)

        return CharacterViewHolder(view)
    }

    override fun getItemCount() = characters.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imgCharacter = view.imgCharacter
        private val txtCharacterName = view.txtCharacterName

        fun bind(character: Character) {
            val imgUrl = prepareImageURL(
                character.thumbnail.path,
                ImageVariant.STANDARD_LARGE.string,
                character.thumbnail.extension
            )

            imgCharacter.loadImage(imgUrl, true)
            txtCharacterName.text = character.name
        }
    }

    fun addData(characters: List<Character>) {
        this.characters.addAll(characters)
        notifyDataSetChanged()
    }
}