package com.karunada.karunaduuvanya.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuideScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Co-existence Guide", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1565C0),
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Living in harmony with wildlife starts with understanding. Follow these safety tips.",
                fontSize = 16.sp,
                color = Color.DarkGray
            )
            
            GuideSection(
                "Elephant Encounters", 
                "Elephant corridors are their paths. Respect their space.",
                listOf(
                    "Do not throw stones or use high-decibel crackers.",
                    "Maintain a safe distance of at least 50 meters.",
                    "Avoid using bright flashlights at night near them.",
                    "If they enter your field, create noise from a distance to guide them away."
                ),
                Color(0xFFE3F2FD),
                Color(0xFF1565C0)
            )

            GuideSection(
                "Leopard Awareness", 
                "Leopards are shy but opportunistic. Be vigilant.",
                listOf(
                    "Keep your surroundings clean; leopard prey on small animals in bushes.",
                    "Ensure children are accompanied during dawn and dusk.",
                    "Install motion-sensor lights around cattle sheds.",
                    "If spotted, do not run. Face the animal and back away slowly."
                ),
                Color(0xFFFFF3E0),
                Color(0xFFE65100)
            )

            GuideSection(
                "Snake Safety", 
                "Most snakes are non-venomous and vital for the ecosystem.",
                listOf(
                    "Wear boots when walking in tall grass or at night.",
                    "Use a stick to clear your path in dense vegetation.",
                    "If bitten, stay calm and seek immediate medical help. Do not use local remedies."
                ),
                Color(0xFFE8F5E9),
                Color(0xFF2E7D32)
            )
        }
    }
}

@Composable
fun GuideSection(title: String, subtitle: String, tips: List<String>, bgColor: Color, accentColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 22.sp, color = accentColor)
            Text(subtitle, fontSize = 13.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 12.dp))
            
            tips.forEach { tip ->
                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                    Text("•", fontWeight = FontWeight.Bold, color = accentColor, modifier = Modifier.padding(end = 8.dp))
                    Text(tip, fontSize = 14.sp, color = Color.Black)
                }
            }
        }
    }
}
