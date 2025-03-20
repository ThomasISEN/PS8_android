package fr.isen.denis.isensmartcompanion.services

import android.content.Context
import com.google.gson.Gson
import fr.isen.denis.isensmartcompanion.models.EventModel

class JsonService {
    fun getEvents(context: Context): List<EventModel> {
        val json = context.assets.open("events.json").bufferedReader().use { it.readText() }
        return Gson().fromJson(json, Array<EventModel>::class.java).toList()
    }
}