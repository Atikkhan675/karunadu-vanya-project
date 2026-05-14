package com.karunada.karunaduuvanya.ui.screens

import android.media.MediaPlayer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.karunada.karunaduuvanya.data.model.ForestSound

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoundsScreen() {
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var currentPlayingId by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val sounds = listOf(
        ForestSound("1", "Morning Birds in Kabini", "https://www.soundjay.com/nature/sounds/forest-birds-1.mp3", "https://images.unsplash.com/photo-1444464666168-49d633b867ad?q=80&w=2069"),
        ForestSound("2", "Monsoon Rain in Malnad", "https://www.soundjay.com/nature/sounds/rain-01.mp3", "https://images.unsplash.com/photo-1534274988757-a28bf1a57c17?q=80&w=2070"),
        ForestSound("3", "Elephant Trumpet", "https://www.soundjay.com/animals/sounds/elephant-trumpet-1.mp3", "https://images.unsplash.com/photo-1581031073858-448457e5813e?q=80&w=2070"),
        ForestSound("4", "Jungle Stream", "https://www.soundjay.com/nature/sounds/river-1.mp3", "https://images.unsplash.com/photo-1437333306198-097bb66810b9?q=80&w=2070"),
        ForestSound("5", "Cricket Night", "https://www.soundjay.com/nature/sounds/cricket-chirping-1.mp3", "https://images.unsplash.com/photo-1501004318641-729e7516485b?q=80&w=2070"),
        ForestSound("6", "Tiger Roar (Simulated)", "https://www.soundjay.com/animals/sounds/lion-roar-1.mp3", "https://images.unsplash.com/photo-1501705388883-4ed8a543392c?q=80&w=2070")
    )

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.stop()
            mediaPlayer?.release()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = "https://images.unsplash.com/photo-1441974231531-c6227db76b6e?q=80&w=2071",
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        
        Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.6f)))

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text("Forest Sounds", color = Color.White, fontWeight = FontWeight.Bold) },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            }
        ) { padding ->
            Column(modifier = Modifier.padding(padding)) {
                AnimatedVisibility(visible = isLoading) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = Color(0xFF81C784))
                }
                
                LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    items(sounds) { sound ->
                        SoundItem(
                            sound = sound,
                            isPlaying = currentPlayingId == sound.id,
                            onToggle = {
                                if (currentPlayingId == sound.id) {
                                    mediaPlayer?.pause()
                                    currentPlayingId = null
                                } else {
                                    isLoading = true
                                    mediaPlayer?.release()
                                    mediaPlayer = MediaPlayer().apply {
                                        setDataSource(sound.audioUrl)
                                        prepareAsync()
                                        setOnPreparedListener { 
                                            start()
                                            isLoading = false
                                            currentPlayingId = sound.id
                                        }
                                        setOnErrorListener { _, _, _ ->
                                            isLoading = false
                                            false
                                        }
                                        setOnCompletionListener { currentPlayingId = null }
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SoundItem(sound: ForestSound, isPlaying: Boolean, onToggle: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .then(if (isPlaying) Modifier.graphicsLayer(scaleX = scale, scaleY = scale) else Modifier),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isPlaying) Color(0xFF2E7D32).copy(alpha = 0.9f) else Color.White.copy(alpha = 0.15f)
        ),
        elevation = CardDefaults.cardElevation(if (isPlaying) 12.dp else 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = sound.imageUrl,
                contentDescription = null,
                modifier = Modifier.size(70.dp).clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            
            Column(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
                Text(
                    sound.title, 
                    fontWeight = FontWeight.Bold, 
                    fontSize = 18.sp, 
                    color = Color.White
                )
                Text(
                    if (isPlaying) "Now Playing..." else "Tap to Play", 
                    fontSize = 12.sp, 
                    color = if (isPlaying) Color.LightGray else Color.Gray
                )
            }
            
            IconButton(
                onClick = onToggle,
                modifier = Modifier
                    .size(56.dp)
                    .background(if (isPlaying) Color.White else Color(0xFF2E7D32), CircleShape)
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = if (isPlaying) Color(0xFF2E7D32) else Color.White
                )
            }
        }
    }
}
