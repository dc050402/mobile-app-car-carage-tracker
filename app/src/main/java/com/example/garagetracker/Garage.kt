package com.example.garage

data class Garage(
    val id: Long = System.currentTimeMilis(),
    var name: String,
    var address: String,
    var phoneNumber: String
    var latitude: Double?,
    var longitude: Double?
    var rating: Double,
    var description: String
) {
    fun isValid(): Boolean {
        return name.isNotBlank() &&
                address.isNotBlank() &&
                rating in 0.0..5.0 &&
                (latitude == null || latitude in -90.0..90.0) &&
                (longitude == null || longitude in -180.0..180.0)
    }
}