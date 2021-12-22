package com.example.rc_aos_megabox

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rc_aos_megabox.databinding.FragmentHomeBinding
import com.example.rc_aos_megabox.tablayout.BoxOfficeFragment
import com.example.rc_aos_megabox.tablayout.DollCinemaFragment
import com.example.rc_aos_megabox.tablayout.PlannedScreeningFragment

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    lateinit var tab1: BoxOfficeFragment
    lateinit var tab2: PlannedScreeningFragment
    lateinit var tab3: DollCinemaFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.ivDiary.clipToOutline = true
        binding.ivVaccine.clipToOutline = true
        binding.ivLetter.clipToOutline = true
        binding.ivCard.clipToOutline = true
        binding.ivMovie.clipToOutline = true

        tab1 = BoxOfficeFragment()
        tab2 = PlannedScreeningFragment()
        tab3 = DollCinemaFragment()

        childFragmentManager.beginTransaction().add(R.id.tab_framelayout, tab1).commit()


        return binding.root
    }

}