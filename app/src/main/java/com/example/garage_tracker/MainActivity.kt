package com.example.garage_tracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var list: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list = findViewById(R.id.garageList)
        list.layoutManager = LinearLayoutManager(this)

        findViewById<Button>(R.id.addButton).setOnClickListener {
            startActivity(Intent(this, AddGarageActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }

    private fun refreshList() {
        list.adapter = GarageAdapter(
            garages = GarageStore.getAll(),
            onClick = { garage ->
                val intent = Intent(this, AddGarageActivity::class.java)
                intent.putExtra("garage_id", garage.id)
                startActivity(intent)
            },
            onLongClick = { garage ->
                AlertDialog.Builder(this)
                    .setTitle("Delete garage?")
                    .setMessage("Remove ${garage.name}?")
                    .setPositiveButton("Delete") { _, _ ->
                        GarageStore.delete(garage.id)
                        refreshList()
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        )
    }
}