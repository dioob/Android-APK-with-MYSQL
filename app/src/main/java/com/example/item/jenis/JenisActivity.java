package com.example.item.jenis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.example.item.Konfigurasi;
import com.example.item.MainActivity;
import com.example.item.R;
import com.example.item.RequestHandler;
import com.example.item.user.Konfigurasi_Login;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class JenisActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private String JSON_STRING;

    private FloatingActionButton btnaddjenis;

    private RecyclerView mRecyclerView;
    private ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
    public static String EmailHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jenis);

        Bundle extras = getIntent().getExtras();
        EmailHolder = extras.getString("emailuser");

        mRecyclerView = findViewById(R.id.recyclerViewJenis);

        mToolbar = findViewById(R.id.toolbarjenis);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("JENIS LIST");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnaddjenis = findViewById(R.id.btntambahjenis);
        btnaddjenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(JenisActivity.this, AddJenisActivity.class);
                i.putExtra("emailuser",String.valueOf(EmailHolder));
                startActivity(i);
            }
        });

        getJSON();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(JenisActivity.this, MainActivity.class);
        i.putExtra("mamemail",String.valueOf(EmailHolder));
        startActivity(i);
        finish();
    }

    private void tampilJenis(){
        list.clear();
        JSONObject jsonObject = null;
        //ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi_Jenis.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Konfigurasi_Jenis.TAG_JENISID);
                String kode = jo.getString(Konfigurasi_Jenis.TAG_JENISKODE);
                String nama = jo.getString(Konfigurasi_Jenis.TAG_JENISNAMA);


                HashMap<String,String> item = new HashMap<>();
                item.put(Konfigurasi_Jenis.TAG_JENISNAMA,nama);
                item.put(Konfigurasi_Jenis.TAG_JENISKODE,kode);
                item.put(Konfigurasi_Jenis.TAG_JENISID,id);

                list.add(item);
            }

            final JenisAdapter mAdapter = new JenisAdapter( JenisActivity.this,list);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(JenisActivity.this));
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
                loading = ProgressDialog.show(JenisActivity.this,"Checking Data","Please wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                loading.dismiss();
                JSON_STRING = s;
                tampilJenis();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Konfigurasi_Jenis.URL_GET_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    //----------------------------SEARCH ITEM----------------------------------

    //----------------------------SEARCH ITEM----------------------------------

}