package com.example.sampledatabase;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainDao {

    //insert query
    @Insert(onConflict = REPLACE)
    void insert(DataList data);

    //delete query
    @Delete
    void delete(DataList data);

    //delete all query
    @Delete
    void reset(List<DataList> data);

    //Update query
    @Query("UPDATE table_n SET text = :sText, title = :sTitle WHERE ID = :sId")
    void update(int sId,String sText,String sTitle);

    //get all data query
    @Query("SELECT * FROM table_n")
    List<DataList>getAll();
}
