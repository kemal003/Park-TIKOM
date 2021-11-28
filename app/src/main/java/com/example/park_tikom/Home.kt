package com.example.park_tikom

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase


class Home : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var namaUser: String
    private lateinit var emailUser: String
    private lateinit var id: String
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var pengumumanArrayList : ArrayList<Pengumuman>
    private lateinit var pengumumanRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        pengumumanArrayList = arrayListOf()

        pengumumanRecyclerView = findViewById(R.id.recyclerPengumuman)
        pengumumanRecyclerView.layoutManager = LinearLayoutManager(this)
        pengumumanRecyclerView.setHasFixedSize(true)

        googleLoginRequest()
        getListPengumuman()

        val signInAccount = GoogleSignIn.getLastSignedInAccount(this)
        if (signInAccount != null){
            namaUser = signInAccount.displayName
            emailUser = signInAccount.email
            id = signInAccount.id
        }

        findViewById<Button>(R.id.logoutButton).setOnClickListener(){
            logOut()
        }
    }

    override fun onStart() {
        super.onStart()
        database = FirebaseDatabase.getInstance().getReference("Users")
        val user = Users(id, namaUser, emailUser)
        database.child(id).setValue(user).addOnSuccessListener {
            Log.d("Create", "Succesfully")
        }.addOnFailureListener{
            Log.d("Create", "Failedd")
        }
        val id = 4
        val pengumuman = Pengumuman("iniFoto1", "iniJudul1", "iniTanggal2", "iniIsi1", id)
//        database.child("Pengumuman").child(id.toString()).setValue(pengumuman)
        database.child("Pengumuman").child("4").child("id").get().addOnSuccessListener {
            println("=====================${it.value}=====================")
        }
//        println("=====================${lastPengumumanFinder()}================")
//        println("==============================$key===========================")
    }

    private fun reload() {
        val login = Intent(this, Login::class.java)
        startActivity(login)
    }

    private fun logOut() {
        googleSignInClient.signOut().addOnCompleteListener(){
            Firebase.auth.signOut()
            reload()
            finish()
        }
    }

    private fun googleLoginRequest(){
        // Configure Google Sign IN
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("328405604706-n43pq4vj9ddfn26fano26ejckeha1v4t.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun getListPengumuman(){
        database = FirebaseDatabase.getInstance().getReference("Pengumuman")
        database.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(pengumSnapshot in snapshot.children){
                        val pengumuman = pengumSnapshot.getValue(Pengumuman::class.java)
                        pengumumanArrayList.add(pengumuman!!)
                    }

                    pengumumanRecyclerView.adapter = AdapterPengumuman(pengumumanArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

//    private fun listPengumuman() = CoroutineScope(Dispatchers.IO).launch {
//        try {
//            var fotos : Any?
//            database.child("Pengumuman").get().addOnSuccessListener {
//                fotos = it.value
//            }
//            val fotoUrls = mutableListOf<String>()
//            for (foto in fotos.)
//        } catch (e: Exception){
//            withContext(Dispatchers.Main) {
//                Toast.makeText(this@Home, e.message, Toast.LENGTH_LONG).show()
//            }
//        }
//    }

//    private fun lastPengumumanFinder() : Int{
//        var lastId = 1
//        var isLast = false
//        var tempId = "nonull"
//        do {
//            database.child("Pengumuman").child(lastId.toString()).child("id").get().addOnSuccessListener {
//                tempId = it.value.toString()
//            }
//            if (tempId.equals("null")){
//                isLast = false
//            } else {
//                lastId += 1
//            }
//            Timer().schedule(2000)
//        }while(!isLast)
//        return lastId
//    }
}