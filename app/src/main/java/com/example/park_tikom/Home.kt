package com.example.park_tikom

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.content.res.AppCompatResources
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.park_tikom.databinding.ActivityHomeBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase


class Home : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var namaUser: String
    private lateinit var emailUser: String
    private lateinit var id: String
    private lateinit var pp: Uri
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var pengumumanArrayList : ArrayList<Pengumuman>
    private lateinit var pengumumanRecyclerView: RecyclerView
    lateinit var toggle : ActionBarDrawerToggle
    private lateinit var viewModel : ItemViewModel
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setBackgroundDrawable(AppCompatResources.getDrawable(this, R.drawable.header_drawable))
        pengumumanArrayList = arrayListOf()

        pengumumanRecyclerView = findViewById(R.id.recyclerPengumuman)
        pengumumanRecyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false)

        googleLoginRequest()
        getListPengumuman()

        val signInAccount = GoogleSignIn.getLastSignedInAccount(this)
        if (signInAccount != null){
            namaUser = signInAccount.displayName
            emailUser = signInAccount.email
            id = signInAccount.id
            pp = signInAccount.photoUrl
        }

        //NAVBAR TOK IKI BRO
        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_logout -> logOut()
            }
            true
        }

        //VIEW MODEL
        viewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        viewModel.getSelectedItem().observe(this, {



        })
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

        binding.pesanParkir.setOnClickListener {
            val intent = Intent(this, PilihLokasi::class.java)
            startActivity(intent)
            finish()
        }

        binding.pesananList.setOnClickListener {
            val intent = Intent(this, DetailPesananActivity::class.java)
            startActivity(intent)
            finish()
        }

        val inflatedView = layoutInflater.inflate(R.layout.nav_header, null)
        val nama : TextView = inflatedView.findViewById(R.id.username)
        println(namaUser)
        nama.text = namaUser
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

                    pengumumanRecyclerView.adapter = AdapterPengumuman(pengumumanArrayList.reversed() as ArrayList<Pengumuman>)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}