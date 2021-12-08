package com.example.park_tikom

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.example.park_tikom.databinding.ActivityPesanBinding
import java.util.*

class PesanActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private lateinit var binding: ActivityPesanBinding
    private var savedDay = 0
    private var savedMonth = 0
    private var savedYear = 0
    private var savedHour = 0
    private var savedMinute = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPesanBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        savedYear = p1
        savedMonth = p2
        savedDay = p3

        binding.inpDate.setText("$p3/$p2/$p1")
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        savedHour = p1
        savedMinute = p2

        binding.inpWaktu.setText("$p1:$p2")
    }
}