package com.example.rc_aos_megabox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rc_aos_megabox.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ibBtnClose.setOnClickListener {
            finish()
        }
    }
}