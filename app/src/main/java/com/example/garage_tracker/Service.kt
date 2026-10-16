package com.example.garage_tracker

data class Service(
    val id: Long = System.currentTimeMillis(),
    val garageId: Long,
    var name: String,
    var description: String,
    var price: Double
) {
    fun isValid(): Boolean {
        return name.isNotBlank() &&
                description.isNotBlank() &&
                price >= 0.0 &&
                price.isFinite()
    }
}