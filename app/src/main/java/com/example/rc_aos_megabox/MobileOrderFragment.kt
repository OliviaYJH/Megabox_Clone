package com.example.rc_aos_megabox

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.rc_aos_megabox.databinding.FragmentMobileOrderBinding

class MobileOrderFragment : Fragment() {

    private lateinit var binding: FragmentMobileOrderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMobileOrderBinding.inflate(layoutInflater)

        (activity as StartActivity).findViewById<TextView>(R.id.tv_top).text = "모바일오더"
        return binding.root
    }


}