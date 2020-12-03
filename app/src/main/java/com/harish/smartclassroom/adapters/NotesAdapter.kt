package com.harish.smartclassroom.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.harish.smartclassroom.R
import com.harish.smartclassroom.data.models.NotesResponse
import com.harish.smartclassroom.ui.Viewnotes
import kotlinx.android.synthetic.main.notes_row_layout.view.*

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>(){

    var notes = emptyList<NotesResponse.StudyMaterial>()

    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_row_layout, parent, false)
        return NotesViewHolder(view)
    }

    override fun getItemCount(): Int {

        return notes.size

    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {

        holder.itemView.apply {
            tv_notes_name . text = notes[position].title
            tv_notes_sub . text =  notes[position].subject

            setOnClickListener {
                val intent = Intent(context,Viewnotes::class.java)
                intent.putExtra("NOTES_URL",notes[position].note)
                context.startActivity(intent)
            }
        }

    }

    fun setNotesList(notesList : List<NotesResponse.StudyMaterial>){
        notes = notesList
        notifyDataSetChanged()

    }

}