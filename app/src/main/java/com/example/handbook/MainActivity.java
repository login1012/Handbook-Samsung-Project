package com.example.handbook;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
private  DrawerLayout drawer;
private ListView list;
private String[] array;
private ArrayAdapter<String> adapter;
private Toolbar toolbar;
private int category_index;
private DatabaseHelper databaseHelper;
private SQLiteDatabase db;
String name;
ArrayList<String> array_1 = new ArrayList<String>();
ArrayList<String> array_2 = new ArrayList<String>();
ArrayList<String> array_3 = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.create_db();
        db = databaseHelper.open();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//загрузка экрана

        list = findViewById(R.id.listView); //нашли лист вью

        array = getResources().getStringArray(R.array.ex_array);//что высвечивается первым

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<String>(Arrays.asList(array)));
        list.setAdapter(adapter);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, Text_Content_Activity.class);
                intent.putExtra("category", category_index);
                intent.putExtra("position", i);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        toolbar.setTitle("Экзамены");
        return true;
    }

    public static String[] GetStringArray(ArrayList<String> arr)
    {
        String str[] = new String[arr.size()];
        for (int j = 0; j < arr.size(); j++) {
            str[j] = arr.get(j);
        }
        return str;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if(id == R.id.nav_html){
            toolbar.setTitle(R.string.menu_html);
            array_1.clear();
            Cursor cursor = db.rawQuery("SELECT * FROM EX1", null);
            int t = cursor.getCount();
            if (t==0){
                array_1.add("No");
            }
            else{
                cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                name=cursor.getString(0) + ". " + cursor.getString(2);
                array_1.add(name);
                cursor.moveToNext();
            }
            cursor.close(); }
            array = GetStringArray(array_1);
            adapter.clear();
            adapter.addAll(array);
            adapter.notifyDataSetChanged();
            category_index=0;
        }

        else if(id == R.id.nav_css){
            toolbar.setTitle(R.string.menu_css);
            Cursor cursor = db.rawQuery("SELECT * FROM EX2", null);
            array_2.clear();
            int t = cursor.getCount();
            if (t==0){
                array_2.add("No");
            }
            else{
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    name=cursor.getString(0) + ". " + cursor.getString(2);
                    array_2.add(name);
                    cursor.moveToNext();
                }
                cursor.close();}
            array = GetStringArray(array_2);
            adapter.clear();
            adapter.addAll(array);
            adapter.notifyDataSetChanged();
            category_index=1;
        }
        else if(id == R.id.nav_js){
            toolbar.setTitle(R.string.menu_js);
            Cursor cursor = db.rawQuery("SELECT * FROM EX3", null);
            array_3.clear();
            int t = cursor.getCount();
            if (t==0){
                array_3.add("No");
            }
            else{
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    name=cursor.getString(0) + ". " + cursor.getString(2);
                    array_3.add(name);
                    cursor.moveToNext();
                }
                cursor.close();}
            array = GetStringArray(array_3);
            adapter.clear();
            adapter.addAll(array);
            adapter.notifyDataSetChanged();
            category_index=3;
        }
        else if(id == R.id.nav_dif){
            toolbar.setTitle(R.string.menu_dif);
            array = getResources().getStringArray(R.array.dif_array);
            adapter.clear();
            adapter.addAll(array);
            adapter.notifyDataSetChanged();
            category_index=4;
        }
        else if(id == R.id.nav_res){
            toolbar.setTitle(R.string.menu_res);
            array = getResources().getStringArray(R.array.res_array);
            adapter.clear();
            adapter.addAll(array);
            adapter.notifyDataSetChanged();
            category_index=5;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
