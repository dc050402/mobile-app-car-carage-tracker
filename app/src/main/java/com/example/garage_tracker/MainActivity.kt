package com.example.garage_tracker

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val garages = listOf(
            Garage(
                id = 1,
                name = "Joe's Garage",
                address = "12 High Street",
                phoneNumber = "555-0100",
                latitude = null,
                longitude = null,
                rating = 4.5,
                description = "Friendly local garage"
            ),
            Garage(
                id = 2,
                name = "Fast Fix",
                address = "5 Mill Road",
                phoneNumber = "555-0200",
                latitude = null,
                longitude = null,
                rating = 3.8,
                description = "Quick turnaround"
            )
        )

        val services = listOf(
            Service(id = 1, garageId = 1, name = "Oil change",
                description = "Full synthetic", price = 59.99),
            Service(id = 2, garageId = 1, name = "Brake check",
                description = "Front and rear", price = 35.0),
            Service(id = 3, garageId = 2, name = "Tyre rotation",
                description = "All four wheels", price = 25.0)
        )

        val text = buildString {
            for (garage in garages.sortedByDescending { it.rating }) {
                appendLine("${garage.name} - ${garage.rating}")
                for (service in services.filter { it.garageId == garage.id }) {
                    appendLine("   ${service.name}: ${"%.2f".format(service.price)}")
                }
                appendLine()
            }
        }

        findViewById<TextView>(R.id.garageText).text = text
    }
}