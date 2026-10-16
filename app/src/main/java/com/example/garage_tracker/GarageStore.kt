package com.example.garage_tracker

object GarageStore {

    private val garages = mutableListOf<Garage>()

    //Create
    fun add(garage: Garage) {
        garages.add(garage)
    }

    //Read
    fun getAll(): List<Garage> = garages.sortedByDescending { it.rating }

    //Delete
    fun delete(id: Long) {
        garages.removeAll { it.id == id }
    }

}