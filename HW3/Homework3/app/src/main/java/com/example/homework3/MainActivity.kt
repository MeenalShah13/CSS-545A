package com.example.homework3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.homework3.data.Note
import com.example.homework3.ui.theme.Homework3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Homework3Theme {
                Scaffold {innerpadding ->
                    NotesApp(modifier = Modifier.fillMaxSize()
                        .safeDrawingPadding()
                        .statusBarsPadding()
                        .padding(innerpadding))
                }
            }
        }
    }
}

@Composable
fun NotesApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    // Define a custom Saver to store and restore the notes
    val notesState = rememberSaveable(saver = listSaver()) { mutableStateListOf<Note>() }

    NavHost(navController = navController, startDestination = "notesList") {
        composable("notesList") {
            NotesListScreen(notesState, navController, onAddNote = {
                notesState.add(Note(
                    id = (notesState.size + 1).toString(),
                    title = "New Note",
                    content = "Enter content here..."
                ))
            })
        }
        composable(
            route = "noteDetail/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId")
            NoteDetailScreen(noteId.toString(), notesState, navController)
        }
    }
}

fun listSaver(): Saver<SnapshotStateList<Note>, List<Map<String, Any>>> {
    return Saver(
        save = { list ->
            // Convert each Note to a Map
            list.map { note ->
                mapOf(
                    "id" to note.id,
                    "title" to note.title,
                    "content" to note.content
                )
            }
        },
        restore = { savedList ->
            // Convert each Map back to a Note and create SnapshotStateList
            SnapshotStateList<Note>().apply {
                savedList.forEach { map ->
                    add(
                        Note(
                            id = (map["id"] as Int).toString(),
                            title = map["title"] as String,
                            content = map["content"] as String
                        )
                    )
                }
            }
        }
    )
}



@Composable
fun NotesListScreen(
    notes: List<Note>,
    navController: NavHostController,
    onAddNote: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNote) {
                Text("+") // Simple label for add button
            }
        }
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            items(notes) { note ->
                NoteCard(
                    title = note.title,
                    content = note.content,
                    onCardClick = { navController.navigate("noteDetail/${note.id}") }
                )
            }
        }
    }
}


@Composable
fun NoteCard(title: String, content: String, onCardClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onCardClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = content, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun NoteDetailScreen(
    noteId: String,
    notes: MutableList<Note>,
    navController: NavHostController
) {
    val note = notes.find { it.id == noteId }
    if (note == null) {
        navController.popBackStack() // Navigate back if the note is not found
        return
    }

    var title by rememberSaveable { mutableStateOf(note.title) }
    var content by rememberSaveable { mutableStateOf(note.content) }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Content") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 5
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {
                // Update note in the list and navigate back
                note.title = title
                note.content = content
                navController.popBackStack() // Navigate back after saving
            }) {
                Text("Save")
            }
            Button(onClick = {
                // Delete the note and navigate back
                notes.remove(note)
                navController.popBackStack() // Ensure we go back after deletion
            }) {
                Text("Delete")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Homework3Theme {
        NotesApp()
    }
}