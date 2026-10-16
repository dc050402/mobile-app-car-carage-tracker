package com.example.garage_tracker

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddGarageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_garage)

        findViewById<Button>(R.id.saveButton).setOnClickListener {
            val garage = Garage(
                name = findViewById<EditText>(R.id.nameInput).text.toString().trim(),
                address = findViewById<EditText>(R.id.addressInput).text.toString().trim(),
                phoneNumber = findViewById<EditText>(R.id.phoneInput).text.toString().trim(),
                latitude = null,
                longitude = null,
                rating = findViewById<EditText>(R.id.ratingInput).text.toString().toDoubleOrNull() ?: -1.0,
                description = findViewById<EditText>(R.id.descriptionInput).text.toString().trim()
            )

            if (garage.isValid()) {
                GarageStore.add(garage)
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Enter a name, an address and a rating from 0 to 5",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}