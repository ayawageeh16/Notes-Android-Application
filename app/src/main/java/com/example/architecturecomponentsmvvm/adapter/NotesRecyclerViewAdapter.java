package com.example.architecturecomponentsmvvm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.architecturecomponentsmvvm.Entity.Note;
import com.example.architecturecomponentsmvvm.R;
import com.example.architecturecomponentsmvvm.ViewModel.NoteViewModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

public class NotesRecyclerViewAdapter extends RecyclerView.Adapter<NotesRecyclerViewAdapter.NotesViewHolder> {

    private List<Note> notes = new ArrayList<>();
    private OnItemClickListener listener;


    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note, parent, false);
        return new NotesViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.bind(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public Note getNoteAtPosition(int position) {
        return notes.get(position);
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView noteTitle;
        TextView noteDescription;
        TextView notePriority;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.note_title);
            noteDescription = itemView.findViewById(R.id.note_description);
            notePriority = itemView.findViewById(R.id.note_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(notes.get(position));
                    }
                }
            });
        }

        public void bind(Note note) {
            noteTitle.setText(note.getTitle());
            noteDescription.setText(note.getDescription());
            notePriority.setText(String.valueOf(note.getPriority()));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }
}
