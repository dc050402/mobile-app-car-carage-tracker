
package com.example.garage_tracker

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var list: RecyclerView
    private lateinit var searchInput: EditText
    private var allGarages: List<Garage> = emptyList()
    private lateinit var garageAdapter: GarageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchInput = findViewById(R.id.searchInput)
        list = findViewById(R.id.garageList)
        list.layoutManager = LinearLayoutManager(this)

        findViewById<Button>(R.id.addButton).setOnClickListener {
            startActivity(Intent(this, AddGarageActivity::class.java))
        }

        refreshList()

        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence?, start: Int, before: Int, count: Int
            ) {
                val query = s.toString().trim()

                val filtered = allGarages.filter { garage ->
                    garage.name.contains(query, ignoreCase = true) ||
                            garage.address.contains(query, ignoreCase = true)
                }

                garageAdapter.updateList(filtered)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onResume() {
        super.onResume()

        if (::list.isInitialized) {
            refreshList()
        }
    }

    private fun refreshList() {
        allGarages = GarageStore.getAll()

        garageAdapter = GarageAdapter(
            garages = allGarages.toMutableList(),
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

        list.adapter = garageAdapter

        val query = searchInput.text.toString().trim()
        val filtered = allGarages.filter { garage ->
            garage.name.contains(query, ignoreCase = true) ||
                    garage.address.contains(query, ignoreCase = true)
        }

        garageAdapter.updateList(filtered)
    }
}
