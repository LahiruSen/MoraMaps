package com.example.android.map;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private ListView listView;
    ArrayAdapter<String> adapter;
    private ArrayList<String> locationList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
        listView = (ListView) findViewById(R.id.list_view_locations);
        locationList = new ArrayList<>();
        locationList.addAll(getLocationList());

        adapter = new ArrayAdapter<>(SearchActivity.this,
                android.R.layout.simple_list_item_1,
                locationList);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String destination = listView.getItemAtPosition(position).toString();
                setDestination(destination);
            }
        });
    }

    private List<String> getLocationList(){
        ArrayList<String> rawData = new ArrayList<>();
        rawData.addAll(Arrays.asList(getResources().getStringArray(R.array.positions)));
        List<String> locations = new ArrayList<>();
        String[] temp;
        for (String data:rawData){
            temp = data.split("_");
            locations.add(temp[3]);
        } return locations;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search,menu);

        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // get search results
                if (locationList.contains(query)){
                    setDestination(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    public void setDestination(String location){
        Intent intent = new Intent();
        intent.putExtra("location", location);
        setResult(RESULT_OK, intent);
        finish();

    }
}
