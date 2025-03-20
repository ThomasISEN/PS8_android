package fr.isen.denis.isensmartcompanion.views

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fr.isen.denis.isensmartcompanion.R
import fr.isen.denis.isensmartcompanion.services.DatabaseService
import fr.isen.denis.isensmartcompanion.services.GeminiAPIService
import kotlinx.coroutines.launch

class MainPageView {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainPage(modifier: Modifier, navController: NavController) {
        val textFieldValue = remember { mutableStateOf("") }
        val inputHistory = remember { mutableStateOf(listOf<String>()) }
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()

        Scaffold(
            modifier = modifier
                .fillMaxSize(),
            topBar = {
                TopAppBar(title = { Text("Home", color = Color.White) },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color(0xFF0047AB) // Bleu foncé
                    )
                )
            },
            content = { innerPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.is_logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(150.dp)
                        .padding(10.dp)
                )
                Text(
                    text = "Welcome on your companion!",
                    fontSize = 22.sp,
                    color = Color(0xFF0047AB),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .padding()
                        .verticalScroll(rememberScrollState())
                ) {
                    Column {
                        inputHistory.value.forEach { input ->
                            Text(
                                text = input,
                                color = Color.Black,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }
                }
                TextField(
                    modifier = Modifier.padding(bottom = 8.dp),
                    value = textFieldValue.value,
                    onValueChange = { newValue -> textFieldValue.value = newValue },
                    label = { Text("Ask Gemini...") },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFdce5e8)
                    ),
                            trailingIcon = {
                        IconButton(
                            onClick = {
                                Log.i(TAG, "TextField content: ${textFieldValue.value}")
                                makeText(context, "Question Submitted", Toast.LENGTH_SHORT).show()
                                inputHistory.value += ("You: " + textFieldValue.value)
                                coroutineScope.launch {
                                    val response =
                                        GeminiAPIService().AskGeminAI(textFieldValue.value)
                                    inputHistory.value += ("Gemin: " + response)
                                    DatabaseService(context, coroutineScope).saveInteraction(
                                        question = textFieldValue.value,
                                        answer = response
                                    )
                                    textFieldValue.value = ""
                                }
                            }
                        ) {
                            Icon(
                                Icons.AutoMirrored.Rounded.ArrowForward,
                                contentDescription = "Next",
                            )
                        }
                    }
                )
            }
        }
        )
    }
}