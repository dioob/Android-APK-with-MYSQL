package com.example.item.warehouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.item.Konfigurasi;
import com.example.item.R;
import com.example.item.RequestHandler;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class ProfilWarehouseActivity extends AppCompatActivity {

    private String iditem, EmailHolder,JSON_STRING, wmerek, wtipe, wjenis, wtglupdate;
    private TextInputEditText wkode, wnama, wket, wstock, wuser, wtglin, wtglup;
    private TextView cek;
    private Button updatebtn;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_warehouse);

        Bundle intent = getIntent().getExtras();
        iditem = intent.getString(Konfigurasi_Warehouse.WAREHOUSE_ID);
        EmailHolder = intent.getString("emailuser");
        cek = findViewById(R.id.cek);
        cek.setText("Email: "+EmailHolder);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String formattedDate = df.format(c.getTime());
        wtglupdate=formattedDate.trim();

        mToolbar = findViewById(R.id.toolbarprofilwarehouse);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("DATA WAREHOUSE ITEM");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        wkode = findViewById(R.id.profilkodeitemwar);
        wnama = findViewById(R.id.profilnamaitemwar);
        wket = findViewById(R.id.profilketeranganitemwar);
        wstock = findViewById(R.id.profiljumlahstockwar);
        wuser = findViewById(R.id.profiluserwar);
        wtglin = findViewById(R.id.profiltglmasukwar);
        wtglup = findViewById(R.id.profiltglupdatewar);
        wtglup.setText(wtglupdate);


        updatebtn = findViewById(R.id.btnupdateItemWar);
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAnWarehouse();
                Intent i = new Intent(ProfilWarehouseActivity.this, WarehouseActivity.class);
                i.putExtra("emailuser",String.valueOf(EmailHolder));
                startActivity(i);
                finish();
            }
        });

        getWarehouse();
    }

    private void getWarehouse(){
        class Getandata extends AsyncTask<Void,Void,String> {
            // ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(Item_Detail_View.this,"Menampilkan data item...","Mohon tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                /*Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        loading.dismiss();
                    }
                }, 1000);*/
                tampilProfilWarehouse(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Konfigurasi_Warehouse.URL_GET_AN_WAREHOUSE,iditem);
                return s;
            }
        }
        Getandata gd = new Getandata();
        gd.execute();
    }

    private void tampilProfilWarehouse(String json){
        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi_Warehouse.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);

            String id = c.getString(Konfigurasi_Warehouse.TAG_WAREHOUSEID);
            String kode = c.getString(Konfigurasi_Warehouse.TAG_WAREHOUSEKODE);
            String nama = c.getString(Konfigurasi_Warehouse.TAG_WAREHOUSENAMA);
            String merek = c.getString(Konfigurasi_Warehouse.TAG_WAREHOUSEMEREK);
            String tipe = c.getString(Konfigurasi_Warehouse.TAG_WAREHOUSETIPE);
            String jenis = c.getString(Konfigurasi_Warehouse.TAG_WAREHOUSEJENIS);
            String keterangan = c.getString(Konfigurasi_Warehouse.TAG_WAREHOUSEKETERANGAN);
            String jumlahstock = c.getString(Konfigurasi_Warehouse.TAG_WAREHOUSESTOCK);
            String tgl_in = c.getString(Konfigurasi_Warehouse.TAG_WAREHOUSETGLIN);
            String tgl_update = c.getString(Konfigurasi_Warehouse.TAG_WAREHOUSETGLUPDATE);
            String user = c.getString(Konfigurasi_Warehouse.TAG_WAREHOUSEUSER);

            wkode.setText(kode);
            wnama.setText(nama);
            wmerek=merek;
            wtipe=tipe;
            wjenis=jenis;
            wket.setText(keterangan);
            wstock.setText(jumlahstock);
            wuser.setText(user);
            wtglin.setText(tgl_in);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------UPDATE DATA WAREHOUSE----------------------------------
    private void updateAnWarehouse(){
        final String ukode = wkode.getText().toString().trim();
        final String unama = wnama.getText().toString().trim();
        final String umerek = wmerek.trim();
        final String utipe = wtipe.trim();
        final String ujenis = wjenis.trim();
        final String uket = wket.getText().toString().trim();
        final String ustock = wstock.getText().toString().trim();
        final String utglin = wtglin.getText().toString().trim();
        final String utglupdate = wtglupdate.trim();
        final String uuser = EmailHolder.trim();


        class UpdateMerek extends AsyncTask<Void,Void,String>{
            //ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(TampilPegawai.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //loading.dismiss();
                Toast.makeText(ProfilWarehouseActivity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Konfigurasi_Warehouse.KEY_WAREHOUSEID,iditem);
                hashMap.put(Konfigurasi_Warehouse.KEY_WAREHOUSEKODE,ukode);
                hashMap.put(Konfigurasi_Warehouse.KEY_WAREHOUSENAMA,unama);
                hashMap.put(Konfigurasi_Warehouse.KEY_WAREHOUSEMEREK,umerek);
                hashMap.put(Konfigurasi_Warehouse.KEY_WAREHOUSETIPE, utipe);
                hashMap.put(Konfigurasi_Warehouse.KEY_WAREHOUSEJENIS, ujenis);
                hashMap.put(Konfigurasi_Warehouse.KEY_WAREHOUSEKETERANGAN, uket);
                hashMap.put(Konfigurasi_Warehouse.KEY_WAREHOUSESTOCK, ustock);
                hashMap.put(Konfigurasi_Warehouse.KEY_WAREHOUSETGLIN, utglin);
                hashMap.put(Konfigurasi_Warehouse.KEY_WAREHOUSETGLUPDATE, utglupdate);
                hashMap.put(Konfigurasi_Warehouse.KEY_WAREHOUSEUSER, uuser);


                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Konfigurasi_Warehouse.URL_UPDATE_WAREHOUSE,hashMap);

                return s;
            }
        }

        UpdateMerek um = new UpdateMerek();
        um.execute();
    }
    //-------------------------------------UPDATE DATA WAREHOUSE----------------------------------

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ProfilWarehouseActivity.this, WarehouseActivity.class);
        i.putExtra("emailuser",String.valueOf(EmailHolder));
        startActivity(i);
    }
}
