package com.angel.powerverse.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.angel.powerverse.databinding.ItemSuperheroBinding
import com.example.powerverse.data.Superhero


import com.squareup.picasso.Picasso


class SuperheroAdapter(var items: List<Superhero>, val onClick: (Int) -> Unit) : Adapter<SuperheroViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperheroViewHolder {
        val binding = ItemSuperheroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SuperheroViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SuperheroViewHolder, position: Int) {
        val superhero = items[position]
        holder.render(superhero)
        holder.itemView.setOnClickListener {
            onClick(position)
        }
    }
}

class SuperheroViewHolder(val binding: ItemSuperheroBinding) : ViewHolder(binding.root) {

    fun render(superhero: Superhero) {
        binding.nameSuperheroTextView.text = superhero.name
        Picasso.get().load(superhero.image.url).into(binding.avatarImageView);
    }
}