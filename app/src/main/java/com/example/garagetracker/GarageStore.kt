package com.example.garagetracker
import org.json.JSONArray
import org.json.JSONObject

object GarageStore {
    private const val FILE = "garages.json"

    val garages = mutableListOf<Garage>()

    fun load(context: Context) {
        garages.clear()

        val text = try {
            context.openFileInput(FILE)
                .bufferedReader()
                .use { it.readText() }
        } catch (_: Exception) {
            "[]"
        }

        val array = JSONArray(text)

        for (i in 0 until array.length()) {
            val item = array.getJSONObject(i)

            garages.add(
                Garage(
                    id = item.getLong("id"),
                    name = item.getString("name"),
                    address item.getSring("address"),
                    phoneNumber = item.getString("phoneNumber"),
                    latitude = if (item.isNull("latitude")) null
                               else item.getDouble("latitude")
                    longitude = if (item.isNull("longitude")) null
                               else item.getDouble("longitude")
                    rating = item.getDouble("rating")
                    description = item.getString("description")
                )
            )
        }
    }

    fun save(context: Context) {
        val array = JSONArray()

        garages.forEach {garage ->
            array.put(
                JSONObject().apply {
                    put("id", garage.id)
                    put("name", garage.name)
                    put("address", garage.address)
                    put("phoneNumber", garage.phoneNumber)
                    put("latitude", garage.latitude ?: JSONObject.NULL)
                    put("longitude", garage.longitude ?: JSONObject.NULL)
                    put("rating", garage.rating)
                    put("description", garage.description)
                }
            )
        }

        context.openFileOutput(FILE, Context.MODE_PRIVATE)
            .bufferedWriter()
            .use { it.write(array.toString(2)) }
    }

    fun byId(id: Long): Garage? {
        return garages.find { it.id == id }
    }

    fun add(garage: Garage) {
        garages.add(garage)
    }

    fun delete(id: Long) {
        garages.removeAll {it.id == id }
    }


    }
}