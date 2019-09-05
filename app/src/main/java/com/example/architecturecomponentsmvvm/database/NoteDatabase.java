package com.example.architecturecomponentsmvvm.database;

import android.content.Context;
import android.os.AsyncTask;

import com.example.architecturecomponentsmvvm.Dao.NoteDAO;
import com.example.architecturecomponentsmvvm.Entity.Note;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Note.class, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

       private static NoteDatabase instance;

       public abstract NoteDAO noteDAO();

       // One thread at a time can access this method
       public static synchronized NoteDatabase getInstance(Context context) {

           if (instance == null) {
                 instance = Room.databaseBuilder(context.getApplicationContext(),
                         NoteDatabase.class, "note_database")
                         .fallbackToDestructiveMigration()
                         .addCallback(roomCallback)
                         .build();

           }
           return instance;
       }

       public static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
           @Override
           public void onCreate(@NonNull SupportSQLiteDatabase db) {
               super.onCreate(db);
               new populateDatabaseAsyncTask(instance).execute();
           }
       };

       // To add data to the database while creation
       private static class populateDatabaseAsyncTask extends AsyncTask<Void, Void, Void>{

           private NoteDAO noteDAO;

           private  populateDatabaseAsyncTask(NoteDatabase noteDatabase){
               noteDAO = noteDatabase.noteDAO();
           }

           @Override
           protected Void doInBackground(Void... voids) {
               noteDAO.insert(new Note("Title 1", "Description 1", 1));
               noteDAO.insert(new Note("Title 2", "Description 2", 2));
               noteDAO.insert(new Note("Title 3", "Description 3", 3));

               return null;
           }
       }
}
