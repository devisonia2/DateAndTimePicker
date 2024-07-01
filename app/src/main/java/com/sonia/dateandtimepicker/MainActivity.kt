package com.sonia.dateandtimepicker

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sonia.dateandtimepicker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding?.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding?.btndate?.setOnClickListener {
            var calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                this, { _, year, month, dateOfMonth ->
                    var simpleDateFormat = SimpleDateFormat("dd/MM/YYYY")
                    binding?.date?.setText(simpleDateFormat.format(calendar.time))
                    calendar.set(year, month, dateOfMonth)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE)
            )
            calendar.add(Calendar.DAY_OF_YEAR, -10)
            datePickerDialog.datePicker.minDate = calendar.timeInMillis
            calendar.timeInMillis = System.currentTimeMillis()
            calendar.add(Calendar.DAY_OF_YEAR, 10)
            datePickerDialog.datePicker.maxDate = calendar.timeInMillis
            datePickerDialog.show()
        }

        binding?.btntime?.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            val beginHour = 9
            val endHour = 18
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                if (hourOfDay < beginHour || minute < 0) {
                    Toast.makeText(
                        this, " InValid Time",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (hourOfDay > endHour || hourOfDay == endHour && minute >= 0) {
                    Toast.makeText(
                        this, "InValid Time",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    var simpleDateFormat = SimpleDateFormat("hh:mm a")
                    calendar.set(hourOfDay, minute)
                    binding?.time?.setText(simpleDateFormat.format(calendar.time))
                }
            }
            val timePickerDialog = TimePickerDialog(this, timeSetListener, hour, minute,
                false)
            timePickerDialog.show()
        }
    }
}