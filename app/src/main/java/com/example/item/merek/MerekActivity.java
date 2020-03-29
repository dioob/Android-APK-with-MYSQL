package com.example.item.merek;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.example.item.MainActivity;
import com.example.item.R;
import com.example.item.RequestHandler;
import com.example.item.jenis.AddJenisActivity;
import com.example.item.jenis.JenisActivity;
import com.example.item.jenis.JenisAdapter;
import com.example.item.jenis.Konfigurasi_Jenis;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MerekActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private String JSON_STRING;
    public static String EmailHolder;

    private FloatingActionButton btnaddmerek;

    private RecyclerView mRecyclerView;
    private ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merek);

        Bundle extras = getIntent().getExtras();
        EmailHolder = extras.getString("emailuser");

        mRecyclerView = findViewById(R.id.recyclerViewMerek);

        mToolbar = findViewById(R.id.toolbarmerek);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("MEREK LIST");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnaddmerek = findViewById(R.id.btntambahmerek);
        btnaddmerek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MerekActivity.this, AddMerekActivity.class);
                i.putExtra("emailuser",String.valueOf(EmailHolder));
                startActivity(i);
            }
        });

        getJSON();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(MerekActivity.this, MainActivity.class);
        i.putExtra("mamemail",String.valueOf(EmailHolder));
        startActivity(i);
        finish();
    }

    private void tampilMerek(){
        list.clear();
        JSONObject jsonObject = null;
        //ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi_Merek.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Konfigurasi_Merek.TAG_MEREKID);
                String kode = jo.getString(Konfigurasi_Merek.TAG_MEREKKODE);
                String nama = jo.getString(Konfigurasi_Merek.TAG_MEREKNAMA);


                HashMap<String,String> item = new HashMap<>();
                item.put(Konfigurasi_Merek.TAG_MEREKNAMA,nama);
                item.put(Konfigurasi_Merek.TAG_MEREKKODE,kode);
                item.put(Konfigurasi_Merek.TAG_MEREKID,id);

                list.add(item);
            }

            final MerekAdapter mAdapter = new MerekAdapter( MerekActivity.this,list);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(MerekActivity.this));
            mRecyclerView.setAdapter(mAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MerekActivity.this,"Checking Data","Please wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                loading.dismiss();
                JSON_STRING = s;
                tampilMerek();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Konfigurasi_Merek.URL_GET_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    //----------------------------SEARCH ITEM----------------------------------

    //----------------------------SEARCH ITEM----------------------------------

}