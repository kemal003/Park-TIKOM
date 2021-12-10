package com.example.park_tikom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.park_tikom.databinding.ActivityDetailPesananBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class DetailPesananActivity : AppCompatActivity() {
    private lateinit var pesananArrayList: ArrayList<Token>
    private lateinit var pesananRecyclerView: RecyclerView
    private lateinit var binding: ActivityDetailPesananBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var currentUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPesananBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentUser = Firebase.auth.currentUser!!

        pesananArrayList = arrayListOf()
        pesananRecyclerView = binding.pesananRecyclerView
        pesananRecyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )
        getListPesanan()
    }

    private fun getListPesanan(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Token")
        databaseReference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (pesananSnapshot in snapshot.children){
                        val pesanan = pesananSnapshot.getValue(Token::class.java)
                        if (pesanan!!.owner.equals(currentUser.uid)){
                            pesananArrayList.add(pesanan)
                        }
                    }
                    pesananRecyclerView.adapter = AdapterPesanan(pesananArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun goHome(){
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            this.finish()
            goHome()
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}