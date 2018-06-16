package com.example.rifafauzi6.projectkamus;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.rifafauzi6.projectkamus.db.KamusHelper;
import com.example.rifafauzi6.projectkamus.model.KamusEngModel;
import com.example.rifafauzi6.projectkamus.model.KamusIndModel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new LoadData().execute();

    }

    private class LoadData extends AsyncTask<Void, Integer, Void> {
        final String TAG = LoadData.class.getSimpleName();
        KamusHelper kamusHelper;
        AppPreference appPreference;

        @Override
        protected void onPreExecute() {
            kamusHelper = new KamusHelper (SplashScreenActivity.this);
            appPreference = new AppPreference(SplashScreenActivity.this);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Boolean firstRun = appPreference.getFirstRun();
            if (firstRun){
                ArrayList<KamusEngModel> kamusEngModels = preLoadRawEng();
                ArrayList<KamusIndModel> kamusIndModels = preLoadRawInd();
                kamusHelper.open();
                kamusHelper.beginTransaction();
                try {
                    for (KamusEngModel kamusEngModel : kamusEngModels){
                        kamusHelper.insertEng(kamusEngModel);
                    }
                    for (KamusIndModel kamusIndModel : kamusIndModels){
                        kamusHelper.insertInd(kamusIndModel);
                    }
                    kamusHelper.setTransactionSuccess();
                } catch (Exception e){
                    Log.e(TAG, "doInBackground: Exception");
                    e.printStackTrace();
                }
                kamusHelper.endTransaction();
                kamusHelper.close();
                appPreference.setFirstRun();
            } else {
                try {
                    synchronized (this){
                        this.wait(2000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public ArrayList<KamusEngModel> preLoadRawEng(){
        ArrayList<KamusEngModel> kamusEngModels = new ArrayList<>();
        String line ;
        BufferedReader bufferedReader;
        try{
            Resources resources = getResources();
            InputStream inputStream = resources.openRawResource(R.raw.english_indonesia);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int count = 0;
            do {
                line = bufferedReader.readLine();
                String[] strings = line.split("\t");
                KamusEngModel kamusEngModel;
                kamusEngModel = new KamusEngModel(strings[0], strings[1]);
                kamusEngModels.add(kamusEngModel);
                count++;
            } while (true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kamusEngModels;
    }
    public ArrayList<KamusIndModel> preLoadRawInd(){
        ArrayList<KamusIndModel> kamusIndModels = new ArrayList<>();
        String line ;
        BufferedReader bufferedReader;
        try{
            Resources resources = getResources();
            InputStream inputStream = resources.openRawResource(R.raw.indonesia_english);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int count = 0;
            do {
                line = bufferedReader.readLine();
                String[] strings = line.split("\t");
                KamusIndModel kamusIndModel;
                kamusIndModel = new KamusIndModel(strings[0], strings[1]);
                kamusIndModels.add(kamusIndModel);
                count++;
            } while (true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kamusIndModels;
    }

}
