package com.example.bazas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bazas.GameContract.GameTable;
import com.example.bazas.model.Game;
import com.google.gson.Gson;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Bazas.db";
    private static final int DATABASE_VERSION = 1;

    private static DatabaseHelper instance;

    private SQLiteDatabase db;

    public static synchronized DatabaseHelper getInstance(Context context){

        if (instance == null){
            instance = new DatabaseHelper(context.getApplicationContext());
        }

        return instance;

    }

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_GAMES_TABLE = "CREATE TABLE " +
                GameTable.TABLE_NAME + " ( " +
                    GameTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    GameTable.COLUMN_GAME_OBJECT_IN_JSON + " TEXT "+
                ")";

        db.execSQL(SQL_CREATE_GAMES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + GameTable.TABLE_NAME);
        onCreate(db);

    }

    public void onUpgrade(int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + GameTable.TABLE_NAME);
        onCreate(db);

    }


    public boolean addGame(Game game){

        db = getWritableDatabase();

        Gson gson = new Gson();
        String gameInJson = gson.toJson(game);

        ContentValues cv = new ContentValues();

        cv.put(GameTable.COLUMN_GAME_OBJECT_IN_JSON, gameInJson);

        long result = db.insert(GameTable.TABLE_NAME, null, cv);

        if (result == -1){
            return false;
        }
        else{

            game.setId(result);
            updateGame(game);

            return true;
        }
    }

    public ArrayList<Game> getAllGames(){
        ArrayList<Game> gameList = new ArrayList<>();
        db = getReadableDatabase();

        Cursor c = db.rawQuery( "SELECT * FROM " + GameTable.TABLE_NAME + " ORDER BY " + GameTable._ID + " DESC" , null);

        Gson gson = new Gson();

        if (c.moveToFirst()){
            do{
                Game game = gson.fromJson(c.getString(c.getColumnIndex(GameTable.COLUMN_GAME_OBJECT_IN_JSON)), Game.class);
                gameList.add(game);

            } while (c.moveToNext());
        }

        c.close();
        return gameList;

    }

    public void updateGame (Game game){

        db = this.getWritableDatabase();

        Gson gson = new Gson();
        String gameInJson = gson.toJson(game);

        ContentValues cv = new ContentValues();

        cv.put(GameTable.COLUMN_GAME_OBJECT_IN_JSON, gameInJson);
        db.update(GameTable.TABLE_NAME, cv, "_ID = ?", new String[] {String.valueOf(game.getId())} );
    }
}
