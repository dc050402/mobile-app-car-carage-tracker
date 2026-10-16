package com.example.garage_tracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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

        val list = findViewById<RecyclerView>(R.id.garageList)
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = GarageAdapter(garages.sortedByDescending { it.rating })
    }
}