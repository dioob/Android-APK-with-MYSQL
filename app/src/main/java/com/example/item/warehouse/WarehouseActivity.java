package com.example.item.warehouse;

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

public class WarehouseActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private String JSON_STRING;


    private FloatingActionButton btnaddwarehouse;

    private RecyclerView mRecyclerView;
    private ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();

    private String EmailHolder;

    private TextView cek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse);

        mRecyclerView = findViewById(R.id.recyclerViewItemWar);

        Bundle extras = getIntent().getExtras();
        EmailHolder = extras.getString("emailuser");

        cek = findViewById(R.id.cekemail);
        cek.setText(EmailHolder);

        mToolbar = findViewById(R.id.toolbarwarehouse);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("DATA WAREHOUSE");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnaddwarehouse = findViewById(R.id.btntambahwarehouse);
        btnaddwarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WarehouseActivity.this, AddWarehouseActivity.class);
                i.putExtra("emailuser",String.valueOf(EmailHolder));
                startActivity(i);
                finish();
            }
        });

        getJSON();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(WarehouseActivity.this, MainActivity.class);
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
            JSONArray result = jsonObject.getJSONArray(Konfigurasi_Warehouse.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Konfigurasi_Warehouse.TAG_WAREHOUSEID);
                String kode = jo.getString(Konfigurasi_Warehouse.TAG_WAREHOUSEKODE);
                String nama = jo.getString(Konfigurasi_Warehouse.TAG_WAREHOUSENAMA);
                String merek = jo.getString(Konfigurasi_Warehouse.TAG_WAREHOUSEMEREK);
                String tipe = jo.getString(Konfigurasi_Warehouse.TAG_WAREHOUSETIPE);
                String jenis = jo.getString(Konfigurasi_Warehouse.TAG_WAREHOUSEJENIS);
                String keterangan = jo.getString(Konfigurasi_Warehouse.TAG_WAREHOUSEKETERANGAN);
                String jumlahstock = jo.getString(Konfigurasi_Warehouse.TAG_WAREHOUSESTOCK);
                String tgl_in = jo.getString(Konfigurasi_Warehouse.TAG_WAREHOUSETGLIN);
                String tgl_update = jo.getString(Konfigurasi_Warehouse.TAG_WAREHOUSETGLUPDATE);
                String user = jo.getString(Konfigurasi_Warehouse.TAG_WAREHOUSEUSER);


                HashMap<String,String> warehouse = new HashMap<>();
                warehouse.put(Konfigurasi_Warehouse.TAG_WAREHOUSEID, id);
                warehouse.put(Konfigurasi_Warehouse.TAG_WAREHOUSEKODE, kode);
                warehouse.put(Konfigurasi_Warehouse.TAG_WAREHOUSENAMA, nama);
                warehouse.put(Konfigurasi_Warehouse.TAG_WAREHOUSEMEREK, merek);
                warehouse.put(Konfigurasi_Warehouse.TAG_WAREHOUSETIPE, tipe);
                warehouse.put(Konfigurasi_Warehouse.TAG_WAREHOUSEJENIS, jenis);
                warehouse.put(Konfigurasi_Warehouse.TAG_WAREHOUSEKETERANGAN, keterangan);
                warehouse.put(Konfigurasi_Warehouse.TAG_WAREHOUSESTOCK, jumlahstock);
                warehouse.put(Konfigurasi_Warehouse.TAG_WAREHOUSETGLIN, tgl_in);
                warehouse.put(Konfigurasi_Warehouse.TAG_WAREHOUSETGLUPDATE, tgl_update);
                warehouse.put(Konfigurasi_Warehouse.TAG_WAREHOUSEUSER, user);

                list.add(warehouse);
            }

            final WarehouseAdapter mAdapter = new WarehouseAdapter( WarehouseActivity.this,list,EmailHolder);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(WarehouseActivity.this));
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
                loading = ProgressDialog.show(WarehouseActivity.this,"Checking Data","Please wait...",false,false);
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
                String s = rh.sendGetRequest(Konfigurasi_Warehouse.URL_GET_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}
