package com.example.whispnote.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NoteDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "WhispNote.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NOTES = "notes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"
        private const val COLUMN_IS_PRIVATE = "is_private"
        private const val COLUMN_TIMESTAMP = "timestamp"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NOTES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT,
                $COLUMN_CONTENT TEXT,
                $COLUMN_IS_PRIVATE INTEGER,
                $COLUMN_TIMESTAMP TEXT
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NOTES")
        onCreate(db)
    }

    suspend fun addNote(note: Note): Long = withContext(Dispatchers.IO) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)
            put(COLUMN_IS_PRIVATE, if (note.isPrivate) 1 else 0)
            put(COLUMN_TIMESTAMP, note.timestamp)
        }
        db.insert(TABLE_NOTES, null, values)
    }

    suspend fun getAllNotes(): List<Note> = withContext(Dispatchers.IO) {
        val db = readableDatabase
        val cursor = db.query(TABLE_NOTES, null, null, null, null, null, "$COLUMN_TIMESTAMP DESC")
        cursorToNotes(cursor)
    }

    suspend fun getPrivateNotes(): List<Note> = withContext(Dispatchers.IO) {
        val db = readableDatabase
        val cursor = db.query(TABLE_NOTES, null, "$COLUMN_IS_PRIVATE=?", arrayOf("1"), null, null, "$COLUMN_TIMESTAMP DESC")
        cursorToNotes(cursor)
    }

    private fun cursorToNotes(cursor: Cursor): List<Note> {
        val notes = mutableListOf<Note>()
        with(cursor) {
            while (moveToNext()) {
                val note = Note(
                    id = getLong(getColumnIndexOrThrow(COLUMN_ID)),
                    title = getString(getColumnIndexOrThrow(COLUMN_TITLE)),
                    content = getString(getColumnIndexOrThrow(COLUMN_CONTENT)),
                    isPrivate = getInt(getColumnIndexOrThrow(COLUMN_IS_PRIVATE)) == 1,
                    timestamp = getString(getColumnIndexOrThrow(COLUMN_TIMESTAMP))
                )
                notes.add(note)
            }
            close()
        }
        return notes
    }
}