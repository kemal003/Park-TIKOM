package com.example.park_tikom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class AdapterPengumuman(private val listPengumuman: ArrayList<Pengumuman>):
    RecyclerView.Adapter<AdapterPengumuman.ListViewHolder>() {

    var storageReference = Firebase.storage.reference

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val fotoParkiran : ImageView = itemView.findViewById(R.id.parkiran_foto)
        val judul : TextView = itemView.findViewById(R.id.judul)
        val tanggal : TextView = itemView.findViewById(R.id.tanggal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.pengumuman, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (foto, judul, tanggal, isi) = listPengumuman[position]
        Glide.with(holder.itemView).load(foto).into(holder.fotoParkiran)
        holder.judul.text = judul
        holder.tanggal.text = tanggal
    }

    override fun getItemCount(): Int {
        return listPengumuman.size
    }
}