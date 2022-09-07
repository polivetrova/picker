package com.pet.picker.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pet.picker.GlideApp
import com.pet.picker.databinding.SearchListItemBinding
import com.pet.picker.model.entities.UnsplashPhoto

class UnsplashPhotoAdapter(private val itemClickListener: SearchFragment.OnItemViewClickListener) :
    ListAdapter<UnsplashPhoto, UnsplashPhotoAdapter.ViewHolder>(DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<UnsplashPhoto>() {
        override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean {
            return oldItem.likes == newItem.likes
        }
    }

    private lateinit var binding: SearchListItemBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = SearchListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: SearchListItemBinding,
        private val itemClickListener: SearchFragment.OnItemViewClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: UnsplashPhoto) = with(binding) {
            likesCount.text = photo.likes.toString()
            photoInfo.text = photo.username

            GlideApp.with(itemView)
                .load(photo.linkRegular)
                .thumbnail(
                    Glide.with(itemView).load(photo.linkThumb)
                )
                .into(photoContainer)

            itemView.setOnClickListener { itemClickListener.onItemViewClick(photo.linkFull) }
        }
    }
}
