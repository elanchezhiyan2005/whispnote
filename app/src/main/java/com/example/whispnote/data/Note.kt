package com.example.whispnote.data

data class Note(
    val id: Long = 0,
    val title: String,
    val content: String,
    val isPrivate: Boolean,
    val timestamp: String
)