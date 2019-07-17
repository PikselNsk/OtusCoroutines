package info.javaway.otuscoroutines.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import info.javaway.otuscoroutines.R
import info.javaway.otuscoroutines.model.Character

class CharactersRecyclerAdapter constructor(
    val inflater:LayoutInflater,
    val items: List<Character>,
    val onCharacterClickListener: OnCharacterClickListener
): RecyclerView.Adapter<CharactersRecyclerAdapter.CharacterHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterHolder {
        val view = inflater.inflate(R.layout.item_movie, parent, false)
        return CharacterHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CharacterHolder, position: Int) {
        holder.bindItem(items[position])
    }

    inner class CharacterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var nameTv:TextView = itemView.findViewById(R.id.name_char_tv)
        var describeTv:TextView  = itemView.findViewById(R.id.describe_char_tv)
        var characterContainerLl:LinearLayout = itemView.findViewById(R.id.char_item_container)
        lateinit var idCharacter:String

        init {
            characterContainerLl.setOnClickListener {
                Log.d("M_MovieRecyclerAdapter", "$idCharacter ")
                onCharacterClickListener.clickOnMovie(idCharacter)
            }
        }

        fun bindItem(character: Character) {
            nameTv.text = character.name
            describeTv.text = character.species
            idCharacter = character.id.toString()
        }

    }

    interface OnCharacterClickListener{
        fun clickOnMovie(id:String)
    }
}