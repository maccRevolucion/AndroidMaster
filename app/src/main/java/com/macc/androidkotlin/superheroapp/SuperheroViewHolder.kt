package com.macc.androidkotlin.superheroapp

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.macc.androidkotlin.databinding.ItemSuperheroBinding
import com.macc.androidkotlin.superheroapp.SuperheroItemResponse
import com.squareup.picasso.Picasso

class SuperheroViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemSuperheroBinding.bind(view)

    fun bind(superheroItemResponse: SuperheroItemResponse, onItemSelected: (String) -> Unit) {
        binding.tvSuperheroName.text = superheroItemResponse.name
        Picasso.get().load(superheroItemResponse.superheroImage.url).into(binding.ivSuperhero)
        binding.root.setOnClickListener { onItemSelected(superheroItemResponse.superheroId) }
    }
}