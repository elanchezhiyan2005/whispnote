package com.example.whispnote.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.whispnote.data.Note
import com.example.whispnote.data.NoteDatabase
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Empty for now, UI in Day 2
        }
        runBlocking {
            val db = NoteDatabase(this@MainActivity)
            db.addNote(Note(title = "Test Note", content = "This is a test", isPrivate = true, timestamp = System.currentTimeMillis().toString()))
        }
    }
}