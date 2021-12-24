package com.example.rc_aos_megabox.movieInfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rc_aos_megabox.R
import com.example.rc_aos_megabox.databinding.FragmentMovieInfoBinding

class MovieInfoFragment : Fragment() {

    private lateinit var binding: FragmentMovieInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieInfoBinding.inflate(layoutInflater)
        return binding.root
    }


}