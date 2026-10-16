
package com.example.garage_tracker

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

object GarageStore {

    private const val FILE_NAME = "garages.json"
    private val gson = Gson()
    private val garages = mutableListOf<Garage>()
    private var initialized = false
    private lateinit var file: File

    fun initialize(context: Context) {
        if (initialized) return

        file = File(context.filesDir, FILE_NAME)

        if (file.exists()) {
            try {
                val type = object : TypeToken<List<Garage>>() {}.type
                val savedGarages: List<Garage> =
                    gson.fromJson(file.readText(), type) ?: emptyList()

                garages.clear()
                garages.addAll(savedGarages)
            } catch (e: Exception) {
                garages.clear()
            }
        }

        initialized = true
    }

    private fun save() {
        if (initialized) {
            file.writeText(gson.toJson(garages))
        }
    }

    // Create
    fun add(garage: Garage) {
        garages.add(garage)
        save()
    }

    // Read
    fun getAll(): List<Garage> =
        garages.sortedByDescending { it.rating }

    fun getById(id: Long): Garage? =
        garages.find { it.id == id }

    // Delete
    fun delete(id: Long) {
        garages.removeAll { it.id == id }
        save()
    }

    // Update
    fun update(updated: Garage) {
        val index = garages.indexOfFirst { it.id == updated.id }

        if (index != -1) {
            garages[index] = updated
            save()
        }
    }
}
