package com.example.park_tikom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import com.bumptech.glide.Glide
import com.example.park_tikom.databinding.ActivityDetailLokasi2Binding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DetailLokasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailLokasi2Binding
    private lateinit var lokPar: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailLokasi2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        lokPar = intent.getStringExtra("lokPar").toString()
        val detailLokasi = intent.getSerializableExtra("detailLokasi") as? LokasiParkir

        binding.deskripsiLokasi.text = detailLokasi!!.deskripsi
        binding.kutoaDetailLokasi.text = "${detailLokasi.terpakai} / ${detailLokasi.kuota} Motor"
        Glide.with(binding.root).load(detailLokasi.imageUrl).into(binding.gambarDetailParkir)

        supportActionBar?.title = "Detail Lokasi Parkir"
        supportActionBar?.setBackgroundDrawable(AppCompatResources.getDrawable(this, R.drawable.header_drawable))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onStart() {
        super.onStart()
        binding.pesanButton.setOnClickListener {
            val intent = Intent(this, PesanActivity::class.java)
            intent.putExtra("lokPar", lokPar)
            startActivity(intent)
        }
    }
}