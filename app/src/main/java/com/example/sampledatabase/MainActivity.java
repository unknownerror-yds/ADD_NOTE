package com.example.sampledatabase;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextInputEditText editText;
    TextInputEditText titleedit;
    FloatingActionButton add;
    RecyclerView recyclerView;

    List<DataList> datalist = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    DBRoom room;
    MainAdapter adapter;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        ColorDrawable cd = new ColorDrawable(Color.parseColor("#2F5D62"));
        actionBar.setBackgroundDrawable(cd);
        actionBar.setDisplayShowHomeEnabled(true);


        editText = findViewById(R.id.edit_text);
        add = (FloatingActionButton) findViewById(R.id.add);
        titleedit = findViewById(R.id.NoteTile);
        //Reset = findViewById(R.id.reset);
        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        //initialize database
        room = DBRoom.getInstance(this);
        //store database value in the data list
        datalist = room.mainDao().getAll();


        //initialize linear layout manager
        linearLayoutManager = new LinearLayoutManager(this);
        //set layout manager
        recyclerView.setLayoutManager(linearLayoutManager);
        //initialize adapter
        adapter =  new MainAdapter(MainActivity.this,datalist);
        //set adapter
        recyclerView.setAdapter(adapter);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get string from edittext box
                String sText = editText.getText().toString().trim();
                String sTitle = titleedit.getText().toString().trim();
                //check condn
                DataList data = new DataList(sTitle,sText);
                if(!sTitle.equals(""))
                {
                    data.setTitle(sTitle);
                    titleedit.setText("");

                }
                if(!sText.equals(""))
                {
                    //set text on main data
                    data.setText(sText);
                    //clear text box
                    editText.setText("");
                }

                //insert text in database
                room.mainDao().insert(data);

                //notify when data is changed
                datalist.clear();
                datalist.addAll(room.mainDao().getAll());
                adapter.notifyDataSetChanged();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.action,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.reset_but:
                Dialoginfo();
        }
        return super.onOptionsItemSelected(item);
    }

    public void Dialoginfo()
    {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.layout_dialog);
        dialog.setTitle("RESET DATABASE");

        Button but_res = (Button)dialog.findViewById(R.id.reset);

        but_res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete all data from databse
                room.mainDao().reset(datalist);
                //notify when all data is changed
                datalist.clear();;
                datalist.addAll(room.mainDao().getAll());
                adapter.notifyDataSetChanged();
            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(450,450);
    }
}