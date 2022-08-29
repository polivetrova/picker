package com.pet.picker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.pet.picker.GlideApp
import com.pet.picker.databinding.FragmentPhotoViewBinding

class PhotoViewFragment : Fragment() {

    private var _binding: FragmentPhotoViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            arguments?.getString(PHOTO_KEY).let {
                GlideApp.with(this@PhotoViewFragment)
                    .load(it)
                    .into(photoView)
            }

            backButton.setOnClickListener {
                parentFragmentManager.popBackStack(
                    BACKSTACK_KEY_SEARCH_SCREEN,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
            }
        }
    }

    companion object {
        fun newInstance(bundle: Bundle): PhotoViewFragment {
            val fragment = PhotoViewFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}