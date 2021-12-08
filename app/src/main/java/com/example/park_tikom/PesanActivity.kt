package com.example.park_tikom

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.example.park_tikom.databinding.ActivityPesanBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.lang.Exception
import java.util.*

class PesanActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private lateinit var binding: ActivityPesanBinding
    private lateinit var databaseRef : DatabaseReference
    private lateinit var currentUser : FirebaseUser
    private lateinit var savedDate : String
    private lateinit var savedTime : String
    private lateinit var endTime : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPesanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        try {
            currentUser = FirebaseAuth.getInstance().currentUser!!
        } catch (e: Exception){
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        binding.inpDate.setOnClickListener {
            val cal : Calendar = Calendar.getInstance()
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val month = cal.get(Calendar.MONTH)
            val year = cal.get(Calendar.YEAR)
            DatePickerDialog(this, this, year, month, day).show()
        }

        binding.inpWaktu.setOnClickListener {
            val cal : Calendar = Calendar.getInstance()
            val hour = cal.get(Calendar.HOUR_OF_DAY)
            val minute = cal.get(Calendar.MINUTE)
            TimePickerDialog(this, this, hour, minute, true).show()
        }

        binding.pesanButtonFinal.setOnClickListener {
            val lokPar = intent.getStringExtra("lokPar")
            databaseRef = FirebaseDatabase.getInstance().getReference("LokasiParkir/$lokPar/token")
            val newToken = databaseRef.push()
            val newTokenID = newToken.key
            newToken.setValue(Token(newTokenID!!, savedDate, savedTime, endTime, currentUser.uid))
            println(newTokenID)
        }
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        savedDate = "$p3-$p2-$p3"
        binding.inpDate.setText("$p3-$p2-$p1")
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        savedTime = "$p1:$p2"
        endTime = "${p1+1}:$p2"
        binding.inpWaktu.setText("$p1:$p2")
    }
}