package com.karunada.karunaduuvanya.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.karunada.karunaduuvanya.data.model.Alert
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertScreen() {
    var showDialog by remember { mutableStateOf(false) }
    var animalName by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    
    val alerts = remember { 
        mutableStateListOf(
            Alert("1", "Elephant Herd", "Near East Gate", System.currentTimeMillis() - 1000 * 60 * 30, "Forest Guard"),
            Alert("2", "Leopard", "South Ridge", System.currentTimeMillis() - 1000 * 60 * 120, "Villager")
        )
    }
    
    val activeAlerts = alerts.filter { System.currentTimeMillis() - it.timestamp < 6 * 3600 * 1000 }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Movement Alerts", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFC62828),
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = Color(0xFFC62828),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Report")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Text(
                "Active alerts in the last 6 hours. Stay safe!",
                modifier = Modifier.padding(16.dp),
                fontSize = 14.sp,
                color = Color.Gray
            )
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(activeAlerts) { alert ->
                    AlertItem(alert)
                }
            }
        }
        
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Report Wildlife Movement") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = animalName, 
                            onValueChange = { animalName = it }, 
                            label = { Text("What animal?") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = location, 
                            onValueChange = { location = it }, 
                            label = { Text("Where?") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (animalName.isNotBlank() && location.isNotBlank()) {
                                alerts.add(0, Alert(UUID.randomUUID().toString(), animalName, location, System.currentTimeMillis(), "Me"))
                                showDialog = false
                                animalName = ""
                                location = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC62828))
                    ) {
                        Text("Send Alert")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) { Text("Cancel") }
                }
            )
        }
    }
}

@Composable
fun AlertItem(alert: Alert) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "⚠️ ${alert.animalName}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFB71C1C)
                )
                val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
                Text(
                    text = sdf.format(Date(alert.timestamp)),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Location: ${alert.location}", fontSize = 14.sp)
            Text(text = "Reported by: ${alert.reportedBy}", fontSize = 12.sp, color = Color.Gray)
        }
    }
}
