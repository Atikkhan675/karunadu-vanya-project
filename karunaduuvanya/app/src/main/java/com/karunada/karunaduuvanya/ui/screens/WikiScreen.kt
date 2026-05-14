package com.karunada.karunaduuvanya.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.karunada.karunaduuvanya.data.model.Wildlife

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WikiScreen(onWildlifeClick: (Wildlife) -> Unit) {
    val wildlifeList = listOf(
        Wildlife(
            "1", "Black Panther", "Panthera pardus",
            "The ghost of the forest. A melanistic color variant of leopards, frequently spotted in Kabini. They are master climbers and incredibly elusive.",
            "Dense Rainforests", "Vulnerable",
            "https://images.unsplash.com/photo-1541018939203-36eeab6d9f11?q=80&w=2070&auto=format&fit=crop",
            "Melanism is caused by a recessive allele, giving them their iconic black coat."
        ),
        Wildlife(
            "2", "Great Hornbill", "Buceros bicornis",
            "Magnificent birds with vibrant casques, essential for seed dispersal in Western Ghats. Their loud calls can be heard from miles away.",
            "Tropical Forests", "Near Threatened",
            "https://images.unsplash.com/photo-1563456843336-9769919f9791?q=80&w=2070&auto=format&fit=crop",
            "They pair for life and the female seals herself inside a tree hollow during nesting."
        ),
        Wildlife(
            "3", "Sandalwood Tree", "Santalum album",
            "The 'Pride of Karnataka'. Known for its fragrant wood and essential oils. It is a hemi-parasitic tree that draws nutrients from other plants.",
            "Deciduous Forests", "Vulnerable",
            "https://images.unsplash.com/photo-1621274220348-41039f37920f?q=80&w=2071&auto=format&fit=crop",
            "Sandalwood is the second-most expensive wood in the world."
        ),
        Wildlife(
            "4", "Asian Elephant", "Elephas maximus",
            "Gentle giants roaming the corridors of Bandipur and Nagarhole. They are highly intelligent and social animals.",
            "Grasslands & Forests", "Endangered",
            "https://images.unsplash.com/photo-1557050543-4d5f4e07ef46?q=80&w=2070&auto=format&fit=crop",
            "Elephants can recognize themselves in a mirror and show empathy for others."
        ),
        Wildlife(
            "5", "Bengal Tiger", "Panthera tigris tigris",
            "The king of the Indian jungle. Karnataka is home to one of the largest populations of tigers in the world.",
            "Varied Terrains", "Endangered",
            "https://images.unsplash.com/photo-1501705388883-4ed8a543392c?q=80&w=2070&auto=format&fit=crop",
            "No two tigers have the same stripes; they are like human fingerprints."
        ),
        Wildlife(
            "6", "King Cobra", "Ophiophagus hannah",
            "The longest venomous snake in the world. Agumbe is known as the 'Capital of King Cobras'.",
            "Rainforests", "Vulnerable",
            "https://images.unsplash.com/photo-1605869403810-746979685652?q=80&w=2070&auto=format&fit=crop",
            "They are the only snakes in the world that build nests for their eggs."
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Wildlife Wiki", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2E7D32),
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(wildlifeList) { wildlife ->
                WikiCard(wildlife, onClick = { onWildlifeClick(wildlife) })
            }
        }
    }
}

@Composable
fun WikiCard(wildlife: Wildlife, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column {
            AsyncImage(
                model = wildlife.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = wildlife.name,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E7D32)
                    )
                    Surface(
                        color = Color(0xFFFFEBEE),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = wildlife.status,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            fontSize = 12.sp,
                            color = Color(0xFFC62828),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = wildlife.description,
                    maxLines = 2,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Tap to learn more →",
                    fontSize = 12.sp,
                    color = Color(0xFF2E7D32),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
