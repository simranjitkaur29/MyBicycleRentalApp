package com.example.bicyclerentalapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class TakeRide : AppCompatActivity() {

    private lateinit var pickDateEditText: TextView
    private lateinit var dropDateEditText: TextView
    private lateinit var totalPriceEditText: TextView
    private lateinit var priceEditText: TextView
    private lateinit var bookButton:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_ride)
        val backButton: ImageView = findViewById(R.id.back)
        backButton.setOnClickListener {
            finish()
        }
        val pic_locat:TextView=findViewById(R.id.Pick_Location_edit_text)
        pic_locat.setText(intent.getStringExtra("stop").toString())
        val drop_locat:TextView=findViewById(R.id.Drop_Location_edit_text)
        drop_locat.setText(intent.getStringExtra("stop").toString())
        priceEditText=findViewById(R.id.price_edit_text)
        priceEditText.setText(intent.getStringExtra("money").toString())
        val bikeNum:TextView=findViewById(R.id.bike_number_edit_text)
        bikeNum.setText(intent.getStringExtra("bikenum").toString())

        pickDateEditText = findViewById(R.id.PickDate_edit_text)
        dropDateEditText = findViewById(R.id.DropDate_edit_text)
        totalPriceEditText = findViewById(R.id.total_price_edit_text)

        pickDateEditText.setOnClickListener {
            showDatePicker(pickDateEditText)
        }
        dropDateEditText.setOnClickListener {
            showDatePicker(dropDateEditText)
        }
        bookButton=findViewById(R.id.bokRide)
        bookButton.setOnClickListener {
            if (areAllFieldsFilled()) {
                val db = DBHelper2(this, null)

                val pickDate = pickDateEditText.text.toString()
                val dropDate = dropDateEditText.text.toString()
                val totalPrice = totalPriceEditText.text.toString().toDouble()
                val bikeNumber = bikeNum.text.toString()

                db.addRide(pickDate, dropDate, totalPrice, bikeNumber)

                Toast.makeText(this, "Your Ride Is Booked", Toast.LENGTH_LONG).show()

                pickDateEditText.text = ""
                dropDateEditText.text = ""
                totalPriceEditText.text = ""
                bikeNum.text = ""

                Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun showDatePicker(editText: TextView) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                showTimePicker(year, month, dayOfMonth, editText)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }

    private fun showTimePicker(year: Int, month: Int, dayOfMonth: Int, editText: TextView) {
        val calendar = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                val selectedCalendar = Calendar.getInstance().apply {
                    set(year, month, dayOfMonth, hourOfDay, minute)
                }
                updateDateEditText(editText, selectedCalendar.time)
                calculateTotalPrice()
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        )
        timePickerDialog.show()
    }

    private fun updateDateEditText(editText: TextView, date: Date) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        editText.text = dateFormat.format(date)
    }

    private fun calculateTotalPrice() {
        val pickDate = pickDateEditText.text.toString()
        val dropDate = dropDateEditText.text.toString()

        if (pickDate.isNotEmpty() && dropDate.isNotEmpty()) {
            val pickDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).parse(pickDate)
            val dropDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).parse(dropDate)

            val timeDifferenceInMillis = dropDateTime.time - pickDateTime.time
            val hoursDifference = timeDifferenceInMillis / (60 * 60 * 1000)

            val pricePerHour = try {
                priceEditText.text.toString().toInt()
            } catch (e: NumberFormatException) {
                0
            }

            val totalPrice = hoursDifference * pricePerHour
            totalPriceEditText.text = totalPrice.toString()
        }
    }
    private fun areAllFieldsFilled(): Boolean {
        return pickDateEditText.text.isNotEmpty() &&
                dropDateEditText.text.isNotEmpty() &&
                totalPriceEditText.text.isNotEmpty() &&
                priceEditText.text.isNotEmpty()
    }

}