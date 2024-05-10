package com.example.rehabilitationequipmentuserapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rehabilitationequipmentuserapp.Models.UserStatus;

import java.util.ArrayList;
import java.util.Arrays;

public class ListActivity extends AppCompatActivity {

    private MyApp App;
    private InterestAdapter interestPointsAdapter;

    private final int SUBACTIVITY_MODIFY = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView listView = (ListView) findViewById(R.id.listText);
        App = (MyApp) getApplication();
        
        interestPointsAdapter = new InterestAdapter(this, App.getPoints());
        listView.setAdapter(interestPointsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                Intent intent = new Intent(getApplicationContext(), SimulationActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, SUBACTIVITY_MODIFY);
            }
        });

        initList();
    }

    public void initList() {

        App.getFewLatestUserStatus(10, new MyApp.StatusCallback() {
            @Override
            public void onCallback(ArrayList<UserStatus> status) {
                App.initializeList(new ArrayList<ArrayList<String>>(Arrays.asList(status.get(0).toArray(), status.get(1).toArray(), status.get(2).toArray())));

                interestPointsAdapter.notifyDataSetChanged();
                Log.d("hola", "onCallback: "+ status.get(0).toArray().get(0));
            }
       });
    }


}