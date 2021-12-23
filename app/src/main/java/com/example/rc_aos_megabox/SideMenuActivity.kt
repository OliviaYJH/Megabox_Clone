package com.example.rc_aos_megabox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rc_aos_megabox.databinding.ActivitySideMenuBinding

class SideMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySideMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySideMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnClose.setOnClickListener {
            finish()
        }
    }
}