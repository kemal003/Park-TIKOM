package com.example.park_tikom

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class AdapterPesanan(private val listPesanan: ArrayList<Token>):
    RecyclerView.Adapter<AdapterPesanan.ListViewHolder>() {

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tanggalPesanan : TextView = itemView.findViewById(R.id.tanggalPesanan)
        val waktuPesanan : TextView = itemView.findViewById(R.id.waktuPesanan)
        val durasiPesanan : TextView = itemView.findViewById(R.id.durasiPesanan)
        val lokasiPesanan : TextView = itemView.findViewById(R.id.lokasiPesanan)
        val isCheckedIn : TextView = itemView.findViewById(R.id.isCheckedIn)
        val lihatButton : ImageView = itemView.findViewById(R.id.lihatQRButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.pesanan, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (id, tanggal, waktu, durasi, lokasi, isCheckedIn, owner) = listPesanan[position]
        holder.tanggalPesanan.text = tanggal
        holder.waktuPesanan.text = waktu
        holder.durasiPesanan.text = "(Batas Waktu: $durasi)"
        holder.lokasiPesanan.text = lokasi
        if (lokasi == "parkir1"){
            holder.lokasiPesanan.text = "Parkiran Depan (Depan Gedung E)"
        } else {
            holder.lokasiPesanan.text = "Parkiran Belakang (Belakang Gedung A)"
        }
        if (isCheckedIn) {
            holder.isCheckedIn.text = "Sudah Check-In"
        } else {
            holder.isCheckedIn.text = "Belum Check-In"
        }

        val manager = (holder.itemView.context as FragmentActivity).supportFragmentManager

        holder.lihatButton.setOnClickListener {
            val qrCode = QRCodeFragment()
            manager.beginTransaction().apply {
                replace(R.id.qrCodeShow, qrCode).addToBackStack(null)

                val data = Bundle()
                data.putString("idQR", id)
                println(data.getString("idQR"))
                qrCode.arguments = data

                commit()
            }
        }
    }

    override fun getItemCount(): Int {
        return listPesanan.size
    }
}