package com.example.park_tikom

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlin.math.sign

class Home : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var namaUser: String
    private lateinit var emailUser: String
    private lateinit var id: String
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        googleLoginRequest()

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
}