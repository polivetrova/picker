package com.pet.picker.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pet.picker.GlideApp
import com.pet.picker.databinding.SearchListItemBinding
import com.pet.picker.model.entities.UnsplashPhoto

class SearchFragmentAdapter(
    private val fragment: SearchFragment,
    private val itemClickListener: SearchFragment.OnItemViewClickListener
) :
    RecyclerView.Adapter<SearchFragmentAdapter.ViewHolder>() {

    private lateinit var binding: SearchListItemBinding
    private var photos: List<UnsplashPhoto> = listOf()

    fun setSearchResults(data: List<UnsplashPhoto>) {
        photos = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchFragmentAdapter.ViewHolder {
        binding = SearchListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SearchFragmentAdapter.ViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int = photos.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(photo: UnsplashPhoto) = with(binding) {
            likesCount.text = photo.likes.toString()
            photoInfo.text = photo.username
            GlideApp.with(fragment)
                .load(photo.linkRegular)
                .thumbnail(
                    Glide.with(fragment).load(photo.linkThumb)
                )
                .into(photoContainer)

            itemView.setOnClickListener { itemClickListener.onItemViewClick(photo.linkFull) }
        }
    }
}