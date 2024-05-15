package com.example.rehabilitationequipmentuserapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rehabilitationequipmentuserapp.Models.UserStatus;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountedCompleter;

public class ListActivity extends AppCompatActivity implements MyApp.DataInitializationListener{

    private MyApp App;
    private InterestAdapter interestPointsAdapter;
    private HashMap<String, Integer> diccImagenes = new HashMap<>();
    private final int SUBACTIVITY_MODIFY = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView listView = (ListView) findViewById(R.id.listText);
        App = (MyApp) getApplication();

        App.setDataInitializationListener(this);

        diccImagenes.put("working", R.drawable.ic_working);
        diccImagenes.put("sport", R.drawable.ic_sport);
        diccImagenes.put("maintenance", R.drawable.ic_maintenance);
        diccImagenes.put("none", R.drawable.ic_none);
        
        interestPointsAdapter = new InterestAdapter(this, App.getPoints());
        listView.setAdapter(interestPointsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                Intent intent = new Intent(ListActivity.this, StatusActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, SUBACTIVITY_MODIFY);
            }
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, SimulationActivity.class);
                startActivityForResult(intent, SUBACTIVITY_MODIFY);
            }
        });

        initList();
    }

    @Override
    public void onDataInitialized() {
        interestPointsAdapter.notifyDataSetChanged();
    }

    public void initList() {
        App.getFewLatestUserStatus(10, new MyApp.StatusCallback() {
            @Override
            public void onCallback(ArrayList<UserStatus> status) {
                ArrayList<ArrayList<String>> listOfLists = new ArrayList<>();
                ArrayList<Bitmap> images = new ArrayList<>();

                for (int i = 0; i < status.size(); i++) {
                    listOfLists.add(status.get(i).toArray());

                    Integer image = diccImagenes.get(status.get(i).getExerciseMode());
                    if (image == null) {
                        image = diccImagenes.get("none");
                    }
                    images.add(BitmapFactory.decodeResource(getResources(), image));
                }
                App.initializeList(listOfLists, images);

                interestPointsAdapter.notifyDataSetChanged();
            }
       });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            interestPointsAdapter.notifyDataSetChanged();
        }
    }
}