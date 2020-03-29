package com.example.item;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.item.jenis.Konfigurasi_Jenis;
import com.example.item.merek.Konfigurasi_Merek;
import com.example.item.user.Konfigurasi_Login;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    private String JSON_STRING;
    private ArrayList<HashMap<String, String>> listj = new ArrayList<HashMap<String, String>>();
    private ArrayList<HashMap<String, String>> listm = new ArrayList<HashMap<String, String>>();

    private String valueNamaJenis;


    private Toolbar mToolbar;
    private TextInputEditText ikode, inama, itipe, iket;
    private TextView judulmerek, juduljenis;
    private ListView listjenis, listmerek;

    private String namajenis;

    private Button saveItem, clearItem;

    private String EmailHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Bundle extras = getIntent().getExtras();
        EmailHolder = extras.getString("emailuser");

        listjenis = findViewById(R.id.listviewjenis);
        listmerek = findViewById(R.id.listviewmerek);

        juduljenis = findViewById(R.id.textnamajenis);
        judulmerek = findViewById(R.id.textnamamerek);

        ikode = findViewById(R.id.addkodeitem);
        inama = findViewById(R.id.addnamaitem);
        itipe = findViewById(R.id.addtipeitem);

        iket = findViewById(R.id.addketeranganitem);

        mToolbar = findViewById(R.id.toolbaritem);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("ADD ITEM");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        clearItem = findViewById(R.id.btnclearItem);
        clearItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ikode.setText("");
                inama.setText("");
                itipe.setText("");
                juduljenis.setText("");
                judulmerek.setText("");
                iket.setText("");

                Toast.makeText(AddActivity.this, "Form Cleared", Toast.LENGTH_SHORT).show();

            }
        });

        saveItem = findViewById(R.id.btnsimpanItem);
        saveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String kodeitem = ikode.getText().toString().trim();
                final String namaitem = inama.getText().toString().trim();
                final String merekitem = judulmerek.getText().toString().trim();
                final String tipeitem = itipe.getText().toString().trim();
                final String jenisitem = juduljenis.getText().toString().trim();
                final String keteranganitem = iket.getText().toString().trim();

                if (!TextUtils.isEmpty(kodeitem) && !TextUtils.isEmpty(namaitem) && !TextUtils.isEmpty(merekitem) && !TextUtils.isEmpty(tipeitem)
                        && !TextUtils.isEmpty(jenisitem) && !TextUtils.isEmpty(keteranganitem)/*&& mImageUri!=null*/) {
                    class TambahItemnya extends AsyncTask<Void, Void, String> {

                        ProgressDialog loading;

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            loading = ProgressDialog.show(AddActivity.this, "Item Added", "Please wait....", false, false);
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            loading.dismiss();
                            Toast.makeText(AddActivity.this, s, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        protected String doInBackground(Void... v) {
                            HashMap<String, String> params = new HashMap<>();
                            params.put(Konfigurasi.TAG_ITEMKODE,kodeitem);
                            params.put(Konfigurasi.TAG_ITEMNAMA,namaitem);
                            params.put(Konfigurasi.TAG_ITEMMEREK,merekitem);
                            params.put(Konfigurasi.TAG_ITEMTIPE,tipeitem);
                            params.put(Konfigurasi.TAG_ITEMJENIS,jenisitem);
                            params.put(Konfigurasi.TAG_ITEMKETERANGAN,keteranganitem);

                            RequestHandler rh = new RequestHandler();
                            String res = rh.sendPostRequest(Konfigurasi.URL_ADD, params);
                            return res;

                        }
                    }

                    TambahItemnya ae = new TambahItemnya();
                    ae.execute();
                    Intent i = new Intent(AddActivity.this, ItemActivity.class);
                    i.putExtra("emailuser",String.valueOf(EmailHolder));
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(AddActivity.this, "Please fill all the form entry!", Toast.LENGTH_LONG).show();
                }
            }
        });

        getJSONJenis();
        getJSONMerek();


        listjenis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
                namajenis = map.get(Konfigurasi_Jenis.TAG_JENISNAMA).toString();
                //String deskjenis = map.get(Konfigurasi_Jenis.TAG_JENISDESC).toString();

                juduljenis.setText(namajenis);


            }
        });

        listmerek.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
                String namamerek = map.get(Konfigurasi_Merek.TAG_MEREKNAMA).toString();
                //String deskjenis = map.get(Konfigurasi_Jenis.TAG_JENISDESC).toString();

                judulmerek.setText(namamerek);

            }
        });
    }

    //----------------------------------------------Tampil Jenis ke Listview--------------------
    private void tampilJenis(){
        listj.clear();
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
                String desc = jo.getString(Konfigurasi_Jenis.TAG_JENISDESC);

                HashMap<String,String> item = new HashMap<>();
                item.put(Konfigurasi_Jenis.TAG_JENISNAMA,nama);
                item.put(Konfigurasi_Jenis.TAG_JENISKODE,kode);
                item.put(Konfigurasi_Jenis.TAG_JENISID,id);
                item.put(Konfigurasi_Jenis.TAG_JENISDESC, desc);

                listj.add(item);
            }

            ListAdapter adapter = new SimpleAdapter(
                    AddActivity.this, listj, R.layout.single_jenisview,
                    new String[]{Konfigurasi_Jenis.TAG_JENISNAMA,Konfigurasi_Jenis.TAG_JENISDESC},
                    new int[]{R.id.listjenisnama, R.id.listjenisdeskripsi});
            listjenis.setAdapter(adapter);

            /*final JenisAdapter mAdapter = new JenisAdapter( JenisActivity.this,list);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(JenisActivity.this));
            mRecyclerView.setAdapter(mAdapter);*/

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getJSONJenis(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddActivity.this,"Checking Data","Please wait...",false,false);
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
    //----------------------------------------------Tampil Jenis ke Listview--------------------


    //----------------------------------------------Tampil Merek ke Listview--------------------
    private void tampilMerek(){
        listm.clear();
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
                String desc = jo.getString(Konfigurasi_Merek.TAG_MEREKDESC);

                HashMap<String,String> item = new HashMap<>();
                item.put(Konfigurasi_Merek.TAG_MEREKNAMA,nama);
                item.put(Konfigurasi_Merek.TAG_MEREKKODE,kode);
                item.put(Konfigurasi_Merek.TAG_MEREKID,id);
                item.put(Konfigurasi_Merek.TAG_MEREKDESC, desc);

                listm.add(item);
            }

            ListAdapter adapter = new SimpleAdapter(
                    AddActivity.this, listm, R.layout.single_merekview,
                    new String[]{Konfigurasi_Merek.TAG_MEREKNAMA,Konfigurasi_Merek.TAG_MEREKDESC},
                    new int[]{R.id.listmereknama, R.id.listmerekdeskripsi});
            listmerek.setAdapter(adapter);

            /*final JenisAdapter mAdapter = new JenisAdapter( JenisActivity.this,list);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(JenisActivity.this));
            mRecyclerView.setAdapter(mAdapter);*/

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getJSONMerek(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AddActivity.this,"Checking Data","Please wait...",false,false);
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
    //----------------------------------------------Tampil Jenis ke Listview--------------------



    public void onBackPressed() {
        Intent i = new Intent(AddActivity.this,ItemActivity.class);
        i.putExtra("emailuser",String.valueOf(EmailHolder));
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}
