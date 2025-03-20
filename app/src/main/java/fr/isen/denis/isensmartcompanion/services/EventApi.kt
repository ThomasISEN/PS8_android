package fr.isen.denis.isensmartcompanion.services

import fr.isen.denis.isensmartcompanion.models.EventModel
import retrofit2.Call
import retrofit2.http.GET

interface EventApi {
    @GET("events.json")
    fun getEvents(): Call<List<EventModel>>
}
