package com.example.rifafauzi6.projectkamus;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.rifafauzi6.projectkamus.adapter.EnglishAdapter;
import com.example.rifafauzi6.projectkamus.adapter.IndonesiaAdapter;
import com.example.rifafauzi6.projectkamus.db.KamusHelper;
import com.example.rifafauzi6.projectkamus.model.KamusEngModel;
import com.example.rifafauzi6.projectkamus.model.KamusIndModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final static String SELECTED_TAG = "selected_index";
    private static int selectedIndex;
    private RecyclerView recyclerView;
    private EnglishAdapter englishAdapter;
    private ArrayList<KamusEngModel> kamusEngModels;
    private IndonesiaAdapter indonesiaAdapter;
    private ArrayList<KamusIndModel> kamusIndModels;
    private KamusHelper kamusHelper;
    Toolbar toolbar;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerview);
        kamusHelper = new KamusHelper(this);
        englishAdapter= new EnglishAdapter(this);
        indonesiaAdapter = new IndonesiaAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(englishAdapter);
        kamusHelper.open();
        kamusEngModels = kamusHelper.getAllDataENG();
        kamusIndModels = kamusHelper.getAllDataIND();
        kamusHelper.close();
        englishAdapter.addItem(kamusEngModels);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState!=null){
            navigationView.getMenu().findItem(savedInstanceState.getInt(SELECTED_TAG)).setChecked(true);
            if (savedInstanceState.getInt(SELECTED_TAG)==R.id.nav_eng){
                toolbar.setTitle(R.string.english_indonesia);
                recyclerView.setAdapter(englishAdapter);
                englishAdapter.addItem(kamusEngModels);
            } else if (savedInstanceState.getInt(SELECTED_TAG)==R.id.nav_ind){
                toolbar.setTitle(R.string.indonesia_english);
                recyclerView.setAdapter(indonesiaAdapter);
                indonesiaAdapter.addItem(kamusIndModels);
            }
            return;
        }

        selectedIndex = R.id.nav_eng;

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        final MenuItem search = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setMaxWidth(R.dimen.search_max_width);
        searchView.setQueryHint(getResources().getString(R.string.search_text_in_here));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                kamusHelper.open();
                kamusEngModels = kamusHelper.getDataByNameENG(newText);
                kamusIndModels = kamusHelper.getDataByNameIND(newText);
                kamusHelper.close();
                if (selectedIndex==R.id.nav_eng){
                    recyclerView.setAdapter(englishAdapter);
                    englishAdapter.addItem(kamusEngModels);
                } else if (selectedIndex==R.id.nav_ind){
                    recyclerView.setAdapter(indonesiaAdapter);
                    indonesiaAdapter.addItem(kamusIndModels);
                }
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_TAG, selectedIndex);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_eng:
                if (!item.isChecked()){
                    selectedIndex = R.id.nav_eng;
                    item.setChecked(true);
                    toolbar.setTitle(R.string.english_indonesia);
                    recyclerView.setAdapter(englishAdapter);
                    englishAdapter.addItem(kamusEngModels);
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_ind:
                if (!item.isChecked()){
                    selectedIndex = R.id.nav_ind;
                    item.setChecked(true);
                    toolbar.setTitle(R.string.indonesia_english);
                    recyclerView.setAdapter(indonesiaAdapter);
                    indonesiaAdapter.addItem(kamusIndModels);
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
        }
        return false;
    }
}
