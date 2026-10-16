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
        GarageStore.initialize(this)

        val nameInput = findViewById<EditText>(R.id.nameInput)
        val addressInput = findViewById<EditText>(R.id.addressInput)
        val phoneInput = findViewById<EditText>(R.id.phoneInput)
        val ratingInput = findViewById<EditText>(R.id.ratingInput)
        val descriptionInput = findViewById<EditText>(R.id.descriptionInput)
        val saveButton = findViewById<Button>(R.id.saveButton)

        val editId = intent.getLongExtra("garage_id", -1L)
        val existing = if (editId != -1L) GarageStore.getById(editId) else null

        if (existing != null) {
            nameInput.setText(existing.name)
            addressInput.setText(existing.address)
            phoneInput.setText(existing.phoneNumber)
            ratingInput.setText(existing.rating.toString())
            descriptionInput.setText(existing.description)
            saveButton.text = "Update"
        }

        saveButton.setOnClickListener {
            val garage = Garage(
                id = existing?.id ?: System.currentTimeMillis(),
                name = nameInput.text.toString().trim(),
                address = addressInput.text.toString().trim(),
                phoneNumber = phoneInput.text.toString().trim(),
                latitude = existing?.latitude,
                longitude = existing?.longitude,
                rating = ratingInput.text.toString().toDoubleOrNull() ?: -1.0,
                description = descriptionInput.text.toString().trim()
            )

            if (garage.isValid()) {
                if (existing != null) {
                    GarageStore.update(garage)
                } else {
                    GarageStore.add(garage)
                }
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