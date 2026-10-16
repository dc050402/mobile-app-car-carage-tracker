package com.example.garage_tracker

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class CompareGaragesActivity : Activity() {

    private val selectedGarageIds = mutableSetOf<Long>()
    private lateinit var garageContainer: LinearLayout
    private lateinit var compareButton: Button
    private var garages = listOf<Garage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compare_garages)

        GarageStore.initialize(this)

        garageContainer = findViewById(R.id.garageSelectionContainer)
        compareButton = findViewById(R.id.compareSelectedButton)

        garages = GarageStore.getAll()

        if (garages.isEmpty()) {
            val emptyMessage = TextView(this).apply {
                text = "No garages saved yet. Add some garages first."
                textSize = 16f
                setTextColor(android.graphics.Color.LTGRAY)
                gravity = Gravity.CENTER
                setPadding(16, 32, 16, 32)
            }
            garageContainer.addView(emptyMessage)
            compareButton.isEnabled = false
            compareButton.alpha = 0.5f
        } else {
            showGarageOptions()
        }

        compareButton.setOnClickListener {
            compareSelectedGarages()
        }
    }

    private fun showGarageOptions() {
        garageContainer.removeAllViews()

        garages.forEach { garage ->
            val checkBox = CheckBox(this).apply {
                text = "${garage.name}\n${garage.address}"
                textSize = 16f
                setTextColor(android.graphics.Color.WHITE)
                buttonTintList = android.content.res.ColorStateList.valueOf(
                    android.graphics.Color.parseColor("#8AB4F8")
                )
                setPadding(8, 12, 8, 12)
                isChecked = garage.id in selectedGarageIds
            }

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    if (selectedGarageIds.size >= 2) {
                        checkBox.isChecked = false
                        Toast.makeText(
                            this,
                            "Select only two garages.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        selectedGarageIds.add(garage.id)
                    }
                } else {
                    selectedGarageIds.remove(garage.id)
                }
            }

            garageContainer.addView(checkBox)
        }
    }

    private fun compareSelectedGarages() {
        if (selectedGarageIds.size != 2) {
            Toast.makeText(
                this,
                "Please select exactly two garages.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val selected = garages.filter { it.id in selectedGarageIds }

        if (selected.size != 2) {
            Toast.makeText(
                this,
                "Could not find both selected garages.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val first = selected[0]
        val second = selected[1]

        val result = """
            ${first.name}
            Rating: ${first.rating}/5
            Address: ${first.address}
            Phone: ${first.phoneNumber.ifBlank { "Not provided" }}

            --------------------

            ${second.name}
            Rating: ${second.rating}/5
            Address: ${second.address}
            Phone: ${second.phoneNumber.ifBlank { "Not provided" }}

            --------------------

            Higher rating: ${
            when {
                first.rating > second.rating -> first.name
                second.rating > first.rating -> second.name
                else -> "It's a tie"
            }
        }
        """.trimIndent()

        AlertDialog.Builder(this)
            .setTitle("Garage Comparison")
            .setMessage(result)
            .setPositiveButton("Done", null)
            .show()
    }
}
