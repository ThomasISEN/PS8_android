package fr.isen.denis.isensmartcompanion.views

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import fr.isen.denis.isensmartcompanion.activities.EventDetailActivity
import fr.isen.denis.isensmartcompanion.models.EventModel

class EventView {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun EventsScreen(modifier: Modifier) {
        val context = LocalContext.current
        val events = remember { mutableStateOf<List<EventModel>>(emptyList()) }

        LaunchedEffect(Unit) {


        }

        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                TopAppBar(title = { Text("Events", color = Color.White) },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color(0xFF0047AB) // Bleu foncé
                    )
                )
            },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LazyColumn {
                        items(events.value) { event ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .clickable(onClick = {
                                        val intent =
                                            Intent(context, EventDetailActivity::class.java).apply {
                                                putExtra("event", event)
                                            }
                                        context.startActivity(intent)
                                    })
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                                    ) {
                                        Icon(Icons.Rounded.Info, contentDescription = "Event")
                                        Text(text = event.title)
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                                    ) {
                                        Icon(Icons.Rounded.DateRange, contentDescription = "Date")
                                        Text(text = event.date)
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                                    ) {
                                        Icon(Icons.Rounded.LocationOn, contentDescription = "Location")
                                        Text(text = event.location)
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                                    ) {
                                        Icon(Icons.Rounded.Menu, contentDescription = "Description")
                                        Text(text = event.description)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}