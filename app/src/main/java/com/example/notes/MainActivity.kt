package com.example.notes

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: NoteDatabaseHelper
    private lateinit var notesAdapter: NotesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { view, insets ->
            val topInset = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            view.setPadding(
                view.paddingLeft,
                topInset,
                view.paddingRight,
                view.paddingBottom
            )
            insets
        }

        db= NoteDatabaseHelper(this)
        notesAdapter= NotesAdapter(db.getAllNotes(), this)

        binding.notesRecyclerView.layoutManager= LinearLayoutManager(this)
        binding.notesRecyclerView.adapter=notesAdapter

        binding.addButton.setOnClickListener {
            val intent= Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onResume(){
        super.onResume()
        notesAdapter.refreshData(db.getAllNotes())
    }
}