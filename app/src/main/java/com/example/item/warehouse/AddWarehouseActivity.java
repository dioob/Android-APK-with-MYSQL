package com.example.item.warehouse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.item.AddActivity;
import com.example.item.ItemActivity;
import com.example.item.Konfigurasi;
import com.example.item.R;
import com.example.item.RequestHandler;
import com.example.item.jenis.Konfigurasi_Jenis;
import com.example.item.user.Konfigurasi_Login;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class AddWarehouseActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private String JSON_STRING;
    private String ikode,inama,imerek, itipe,ijenis,iket, tgl_in, tgl_update, EmailHolder;
    private ListView listviewwarehouse;
    private ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();

    private TextInputEditText kode, nama, keterangan, jumstock;
    private TextView namaitemnya;

    private Button adddatawarehouse, cleardatawarehouse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_warehouse);

        Bundle extras = getIntent().getExtras();
        EmailHolder = extras.getString("emailuser");

        mToolbar = findViewById(R.id.toolbaraddwarehouse);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("ADD DATA WAREHOUSE");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String formattedDate = df.format(c.getTime());

        tgl_in = formattedDate;
        tgl_update = formattedDate;

        kode = findViewById(R.id.addkodeitemwar);
        nama = findViewById(R.id.addnamaitemwar);
        keterangan = findViewById(R.id.addketeranganitemwar);
        jumstock = findViewById(R.id.addjumlahstockwar);

        namaitemnya = findViewById(R.id.textnamaitemwar);

        adddatawarehouse = findViewById(R.id.btnsimpanItemWar);
        cleardatawarehouse = findViewById(R.id.btnclearItemWar);

        listviewwarehouse = findViewById(R.id.listviewitemwarehouse);
        listviewwarehouse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
                ikode = map.get(Konfigurasi.TAG_ITEMKODE).toString();
                inama = map.get(Konfigurasi.TAG_ITEMNAMA).toString();
                imerek = map.get(Konfigurasi.TAG_ITEMMEREK).toString();
                itipe = map.get(Konfigurasi.TAG_ITEMTIPE).toString();
                ijenis = map.get(Konfigurasi.TAG_ITEMJENIS).toString();
                iket = map.get(Konfigurasi.TAG_ITEMKETERANGAN).toString();


                kode.setText(ikode);
                nama.setText(inama);
                keterangan.setText(iket);

                namaitemnya.setText(inama);
            }
        });

        cleardatawarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nama.setText("");
                kode.setText("");
                keterangan.setText("");
                namaitemnya.setText("");
                jumstock.setText("");
                itipe.equals("");


            }
        });

        adddatawarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String kodeitem = kode.getText().toString().trim();
                final String namaitem = nama.getText().toString().trim();
                final String keteranganitem = keterangan.getText().toString().trim();
                final String jenisitem = ijenis.trim();
                final String tipeitem = itipe.trim();
                final String merekitem = imerek.trim();
                final String jumlahstock = jumstock.getText().toString().trim();
                final String tgl_masuk = tgl_in.trim();
                final String tgl_up = tgl_update.trim();
                final String wuser = String.valueOf(EmailHolder).trim();


                if (!TextUtils.isEmpty(kodeitem) && !TextUtils.isEmpty(namaitem) && !TextUtils.isEmpty(merekitem) && !TextUtils.isEmpty(tipeitem)
                        && !TextUtils.isEmpty(jenisitem) && !TextUtils.isEmpty(keteranganitem) && !TextUtils.isEmpty(jumlahstock) && !TextUtils.isEmpty(wuser)) {
                    class TambahItemnya extends AsyncTask<Void, Void, String> {

                        ProgressDialog loading;

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            loading = ProgressDialog.show(AddWarehouseActivity.this, "Item Added", "Please wait....", false, false);
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            loading.dismiss();
                            Toast.makeText(AddWarehouseActivity.this, s, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        protected String doInBackground(Void... v) {
                            HashMap<String, String> params = new HashMap<>();
                            params.put(Konfigurasi_Warehouse.TAG_WAREHOUSEKODE, kodeitem);
                            params.put(Konfigurasi_Warehouse.TAG_WAREHOUSENAMA, namaitem);
                            params.put(Konfigurasi_Warehouse.TAG_WAREHOUSEMEREK, merekitem);
                            params.put(Konfigurasi_Warehouse.TAG_WAREHOUSETIPE, tipeitem);
                            params.put(Konfigurasi_Warehouse.TAG_WAREHOUSEJENIS, jenisitem);
                            params.put(Konfigurasi_Warehouse.TAG_WAREHOUSEKETERANGAN, keteranganitem);
                            params.put(Konfigurasi_Warehouse.TAG_WAREHOUSESTOCK, jumlahstock);
                            params.put(Konfigurasi_Warehouse.TAG_WAREHOUSETGLIN,tgl_masuk);
                            params.put(Konfigurasi_Warehouse.TAG_WAREHOUSETGLUPDATE, tgl_up);
                            params.put(Konfigurasi_Warehouse.TAG_WAREHOUSEUSER, EmailHolder);

                            RequestHandler rh = new RequestHandler();
                            String res = rh.sendPostRequest(Konfigurasi_Warehouse.URL_ADD, params);
                            return res;

                        }
                    }

                    TambahItemnya ae = new TambahItemnya();
                    ae.execute();
                    Intent i = new Intent(AddWarehouseActivity.this, WarehouseActivity.class);
                    i.putExtra("emailuser",String.valueOf(EmailHolder));
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(AddWarehouseActivity.this, "Please fill all the form entry!", Toast.LENGTH_LONG).show();
                }
            }
        });

        getJSON();

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
                String tipe = jo.getString(Konfigurasi.TAG_ITEMTIPE);
                String jenis = jo.getString(Konfigurasi.TAG_ITEMJENIS);
                String keterangan = jo.getString(Konfigurasi.TAG_ITEMKETERANGAN);


                HashMap<String,String> item = new HashMap<>();
                item.put(Konfigurasi.TAG_ITEMID,id);
                item.put(Konfigurasi.TAG_ITEMNAMA,nama);
                item.put(Konfigurasi.TAG_ITEMKODE,kode);
                item.put(Konfigurasi.TAG_ITEMMEREK, merek);
                item.put(Konfigurasi.TAG_ITEMTIPE,tipe);
                item.put(Konfigurasi.TAG_ITEMJENIS, jenis);
                item.put(Konfigurasi.TAG_ITEMKETERANGAN,keterangan);


                list.add(item);
            }
            ListAdapter adapter = new SimpleAdapter(
                    AddWarehouseActivity.this, list, R.layout.single_itemlistview,
                    new String[]{Konfigurasi.TAG_ITEMNAMA,Konfigurasi.TAG_ITEMKETERANGAN},
                    new int[]{R.id.listnamaitem, R.id.listketeranganitem});
            listviewwarehouse.setAdapter(adapter);


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
                loading = ProgressDialog.show(AddWarehouseActivity.this,"Checking Data","Please wait...",false,false);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(AddWarehouseActivity.this, WarehouseActivity.class);
        i.putExtra("emailuser",String.valueOf(EmailHolder));
        startActivity(i);
    }
}
