package com.example.handbook;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import java.io.IOException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Text_Content_Activity extends AppCompatActivity {
    String name;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;
    Cursor cursor;

    private TextView text_content;
    private int category = 0;
    private int position = 0;

    private int [] array_dif = {R.string.dif};
    private int [] array_res = {R.string.res2};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.create_db();
        db = databaseHelper.open();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_content);
        text_content=findViewById(R.id.text_main_content);
        receiveIntent();
        }

    private void receiveIntent(){
        Intent i = getIntent();
        if (i != null){
            category = i.getIntExtra("category", 0);
            position = i.getIntExtra("position", 0);
        }
        switch (category){
            case  0:
                cursor = db.rawQuery("SELECT Content FROM EX1", null);
                cursor.moveToPosition(position);
                name=cursor.getString(0);
                cursor.close();
                text_content.setText(name);
                break;
            case  1:
                cursor = db.rawQuery("SELECT Content FROM EX2", null);
                cursor.moveToPosition(position);
                name=cursor.getString(0);
                cursor.close();
                text_content.setText(name);
                break;
            case  3:
                cursor = db.rawQuery("SELECT Content FROM EX3", null);
                cursor.moveToPosition(position);
                name=cursor.getString(0);
                cursor.close();
                text_content.setText(name);
                break;
            case  4:
                text_content.setText(array_dif[position]);
                break;
            case  5:
                text_content.setText(array_res[position]);
                break;
        }
    }
}
