package com.example.sampledatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//add database entities
@Database(entities = {DataList.class},version = 1,exportSchema = false)
public abstract class DBRoom extends RoomDatabase {
    //create database instance
    private static DBRoom room;

    //define database name
    private static String DATABASE_NAME = "MyDataBase";

    public synchronized static DBRoom getInstance(Context context){

        //condn check
        if(room == null)
        {
            //initialize database
            room = Room.databaseBuilder(context.getApplicationContext(),
                    DBRoom.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return room;
    }

    //Create DAO
    public abstract MainDao mainDao();
}
