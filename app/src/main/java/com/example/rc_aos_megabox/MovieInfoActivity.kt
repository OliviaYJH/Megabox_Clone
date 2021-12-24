package com.example.rc_aos_megabox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.rc_aos_megabox.databinding.ActivityMovieInfoBinding
import com.example.rc_aos_megabox.tablayout.BoxOfficeFragment

class MovieInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieInfoBinding.inflate(layoutInflater)

        var id = Integer.parseInt(intent.getStringExtra("id"))
        binding.ivPoster.setImageResource(BoxOfficeFragment.imgArray[id!!])
        binding.tvMovieNm.text = intent.getStringExtra("title")
        setContentView(binding.root)
    }
}