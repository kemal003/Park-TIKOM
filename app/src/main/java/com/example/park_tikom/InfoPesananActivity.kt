package com.example.park_tikom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.park_tikom.databinding.ActivityInfoPesananBinding

class InfoPesananActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInfoPesananBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoPesananBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}