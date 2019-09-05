package com.example.architecturecomponentsmvvm.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.architecturecomponentsmvvm.Dao.NoteDAO;
import com.example.architecturecomponentsmvvm.Entity.Note;
import com.example.architecturecomponentsmvvm.database.NoteDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class NoteRepository {

    private NoteDAO noteDAO;
    private LiveData<List<Note>> notes;

    public NoteRepository(Application application) {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        noteDAO = noteDatabase.noteDAO();
        notes = noteDAO.getAllNotes();
    }

    // Room doesn't allow database operations run in the Main thread

    public void insert(Note note){
        new InsetNoteAsyncTask(noteDAO).execute(note);
    }

    public void delete(Note note){
        new DeleteAsyncTask(noteDAO).execute(note);
    }

    public void update(Note note){
        new UpdateAsyncTask(noteDAO).execute(note);
    }

    public void deleteAllNotes(){
        new DeleteAllNoteAsyncTask(noteDAO).execute();
    }

    public LiveData<List<Note>> getAllNotes(){
        return notes;
    }

    // Insert AsyncTask
    private static class InsetNoteAsyncTask extends AsyncTask<Note,Void,Void> {

        private NoteDAO noteDAO;

        private InsetNoteAsyncTask(NoteDAO noteDAO){
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.insert(notes[0]);
            return null;
        }
    }

    // Delete AsyncTask
    private static class DeleteAsyncTask extends AsyncTask<Note, Void, Void>{

         private  NoteDAO noteDAO;

         private DeleteAsyncTask(NoteDAO noteDAO){
             this.noteDAO = noteDAO;
         }

        @Override
        protected Void doInBackground(Note... notes) {
             noteDAO.delete(notes[0]);
            return null;
        }
    }

    // Update AsyncTask
    private static class UpdateAsyncTask extends AsyncTask<Note, Void, Void>{

        private  NoteDAO noteDAO;

        private UpdateAsyncTask(NoteDAO noteDAO){
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.update(notes[0]);
            return null;
        }
    }

    // Delete All Notes AsyncTask
    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void,Void,Void> {

        private NoteDAO noteDAO;

        private DeleteAllNoteAsyncTask(NoteDAO noteDAO){
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDAO.deleteAll();
            return null;
        }
    }
}
