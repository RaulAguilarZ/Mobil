package com.example.demo2v22

import android.content.Context
import android.media.AudioManager
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.util.*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    TextToSpeechExample()
                }
            }
        }
    }
}

@Composable
fun TextToSpeechExample() {
    var text by remember { mutableStateOf("") }
    val context = LocalContext.current
    val tts = remember { TextToSpeech(context, null) }
//
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 50, 0)


    // TextToSpeech
    DisposableEffect(context) {
        tts.language = Locale("es" ,"MX")
        tts.setSpeechRate(0.1f)
        onDispose {
            tts.stop()
            tts.shutdown()
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Write Text") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (text.isNotBlank()) {
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
                } else {
                    Toast.makeText(context, "Write ..........", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text("Voice")
        }
    }
}
