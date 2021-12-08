package com.example.park_tikom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import com.example.park_tikom.databinding.ActivityPilihLokasiBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PilihLokasi : AppCompatActivity() {
    private lateinit var binding: ActivityPilihLokasiBinding
    private lateinit var databaseRef : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPilihLokasiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseRef = FirebaseDatabase.getInstance().getReference("LokasiParkir")

        supportActionBar?.title = "Pilih Lokasi Parkir"
        supportActionBar?.setBackgroundDrawable(AppCompatResources.getDrawable(this, R.drawable.header_drawable))
    }

    override fun onStart() {
        super.onStart()
        binding.parkir1.setOnClickListener {
            val intent = Intent(this, DetailLokasiActivity::class.java)
            intent.putExtra("lokPar", "parkir1")
            startActivity(intent)
        }

        binding.parkir2.setOnClickListener {
            val intent = Intent(this, DetailLokasiActivity::class.java)
            intent.putExtra("lokPar", "parkir2")
            startActivity(intent)
        }
    }
}