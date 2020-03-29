package com.example.item;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.item.user.Konfigurasi_Login;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private String JSON_STRING;
    private TextView ikodeview, inamaview;
    private TextView cari;
    private FloatingActionButton btnadditem;
    private ImageView btncari;
    private RecyclerView mRecyclerView;
    private ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();

    public static String EmailHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        mRecyclerView = findViewById(R.id.recyclerViewItem);
        cari = findViewById(R.id.cariitem);

        Bundle extras = getIntent().getExtras();
        EmailHolder = extras.getString("emailuser");

        mToolbar = findViewById(R.id.toolbaritem);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("ITEM LIST");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnadditem = findViewById(R.id.btntambahitem);
        btnadditem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ItemActivity.this, AddActivity.class);
                i.putExtra("emailuser",String.valueOf(EmailHolder));
                startActivity(i);
            }
        });

        getJSON();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ItemActivity.this, MainActivity.class);
        i.putExtra("mamemail",String.valueOf(EmailHolder));
        startActivity(i);
        finish();
    }

    private void tampilItem(){
        list.clear();
        JSONObject jsonObject = null;
        //ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Konfigurasi.TAG_ITEMID);
                String kode = jo.getString(Konfigurasi.TAG_ITEMKODE);
                String nama = jo.getString(Konfigurasi.TAG_ITEMNAMA);
                String merek = jo.getString(Konfigurasi.TAG_ITEMMEREK);

                HashMap<String,String> item = new HashMap<>();
                item.put(Konfigurasi.TAG_ITEMNAMA,nama);
                item.put(Konfigurasi.TAG_ITEMKODE,kode);
                item.put(Konfigurasi.TAG_ITEMMEREK, merek);
                item.put(Konfigurasi.TAG_ITEMID,id);

                list.add(item);
            }

            final ItemAdapter mAdapter = new ItemAdapter( ItemActivity.this,list);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(ItemActivity.this));
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
                loading = ProgressDialog.show(ItemActivity.this,"Checking Data","Please wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                loading.dismiss();
                JSON_STRING = s;
                tampilItem();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Konfigurasi.URL_GET_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

}