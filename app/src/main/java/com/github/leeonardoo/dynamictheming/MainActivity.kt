package com.github.leeonardoo.dynamictheming

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.leeonardoo.dynamictheming.ui.theme.DesignSystemTheme
import com.github.leeonardoo.dynamictheming.utils.FakePrefs
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DesignSystemTheme {
                Content()
            }
        }
    }
}

@Composable
fun Content() {
    var text by remember { mutableStateOf("Text") }
    var state by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    var progress by remember { mutableStateOf(0f) }
    LaunchedEffect(coroutineScope) {
        while (true) {
            if (progress <= 1f) {
                progress += 0.01f
            } else {
                progress = 0f
                delay(400L)
            }

            delay(50L)
        }
    }

    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    Surface(color = MaterialTheme.colors.background) {

        Column {
            Text("Hello Android!")

            Spacer(modifier = Modifier.padding(8.dp))

            Button(onClick = {
                state = !state
            }, elevation = elevation(8.dp)) {
                Text("Switch theme")
            }

            Spacer(modifier = Modifier.padding(8.dp))

            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Label") }
            )

            Spacer(modifier = Modifier.padding(8.dp))

            LinearProgressIndicator(progress = animatedProgress)
        }

        if (state) {
            state = false
            DesignSystemTheme.colors.update(FakePrefs.update())
        }
    }
}

@Preview(showBackground = true, device = Devices.DEFAULT)
@Composable
fun DefaultPreview() {
    DesignSystemTheme {
        Content()
    }
}