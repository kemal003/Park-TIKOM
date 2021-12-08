package com.example.park_tikom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.park_tikom.databinding.ActivityDetailLokasi2Binding

class DetailLokasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailLokasi2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailLokasi2Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.pesanButton.setOnClickListener {
            val lokPar = intent.getStringExtra("lokPar")
            val intent = Intent(this, PesanActivity::class.java)
            intent.putExtra("lokPar", lokPar)
            startActivity(intent)
        }
    }
}