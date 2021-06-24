package com.example.sampledatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//define the table name
@Entity(tableName = "table_n")
public class DataList implements Serializable {

    //create the column id
    @PrimaryKey(autoGenerate = true)
    private int Id;

    //create the text column
    @ColumnInfo(name = "text")
    private String text;

    //create title col
    @ColumnInfo(name = "title")
    private String title;

    //getter and setter

    DataList (String title,String text)
    {
        this.title = title;
        this.text = text;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
