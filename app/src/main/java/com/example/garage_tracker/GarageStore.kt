package com.example.garage_tracker

object GarageStore {

    private val garages = mutableListOf<Garage>()

    //Create
    fun add(garage: Garage) {
        garages.add(garage)
    }

    //Read
    fun getAll(): List<Garage> = garages.sortedByDescending { it.rating }
    fun getById(id: Long): Garage? = garages.find { it.id == id}

    //Delete
    fun delete(id: Long) {
        garages.removeAll { it.id == id }
    }

    fun update(updated: Garage) {
        val index = garages.indexOfFirst {it.id == updated.id }
        if (index != -1) {
            garages[index] = updated
        }
    }
}