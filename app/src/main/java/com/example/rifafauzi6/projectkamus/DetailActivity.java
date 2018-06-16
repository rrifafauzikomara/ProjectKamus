package com.example.rifafauzi6.projectkamus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    public static String EXTRA_WORD = "extra_word";
    public static String EXTRA_DETAIL = "extra_detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvWord = findViewById(R.id.tvWord);
        TextView tvDetail = findViewById(R.id.tvDetail);
        String word = getIntent().getStringExtra(EXTRA_WORD);
        String detail = getIntent().getStringExtra(EXTRA_DETAIL);
        String title = "Detail " + word;

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvWord.setText(word);
        tvDetail.setText(detail);
    }

    @Override
    public void onBackPressed() {
        Intent back = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(back);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent back = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(back);
        return true;
    }

}
