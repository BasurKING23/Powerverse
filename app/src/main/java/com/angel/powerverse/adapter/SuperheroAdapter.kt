package com.angel.powerverse.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.angel.powerverse.data.Superhero
import com.angel.powerverse.R
import com.angel.powerverse.data.SuperheroResponse
import com.squareup.picasso.Picasso

class SuperheroAdapter(var items: List<Superhero>) : RecyclerView.Adapter<SuperheroViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperheroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_superhero, parent, false)
        return SuperheroViewHolder(view)
    }

    override fun onBindViewHolder(holder: SuperheroViewHolder, position: Int) {
        val superhero = items[position]
        holder.render(superhero)
    }

    override fun getItemCount(): Int = items.size
    fun updateItems(superheroList: List<Superhero>) {

    }
}
    fun updateItems(items: List<SuperheroResponse>) {
}

class SuperheroViewHolder(view: View) : RecyclerView.ViewHolder(view) {

     val nameTextView: TextView = view.findViewById(R.id.nameSuperheroTextView)
     val avatarImageView: ImageView = view.findViewById(R.id.avatarImageView)

    fun render(superhero: Superhero) {
        nameTextView.text =superhero.name
        Picasso.get().load(superhero.image.url).into(avatarImageView);


    }
}
