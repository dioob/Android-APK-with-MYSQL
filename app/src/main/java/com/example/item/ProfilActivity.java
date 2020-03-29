package com.example.item;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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

import com.example.item.jenis.Konfigurasi_Jenis;
import com.example.item.merek.Konfigurasi_Merek;
import com.example.item.user.Konfigurasi_Login;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfilActivity extends AppCompatActivity {

    private String JSON_STRING;
    private ArrayList<HashMap<String, String>> listj = new ArrayList<HashMap<String, String>>();
    private ArrayList<HashMap<String, String>> listm = new ArrayList<HashMap<String, String>>();
    private ListView listjenis, listmerek;

    private Toolbar mToolbar;
    private TextInputEditText ikode, inama, itipe, iket;
    private Button updateItem, deleteItem;
    private TextView imerek, ijenis, cek;
    private String iditem;
    private String EmailHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);


        listjenis = findViewById(R.id.listviewjenis);
        listmerek = findViewById(R.id.listviewmerek);

        Bundle intent = getIntent().getExtras();
        iditem = intent.getString(Konfigurasi.ITEM_ID);
        EmailHolder = intent.getString("emailuser");

        cek = findViewById(R.id.cek);
        cek.setText("Email: "+EmailHolder);

        ikode = findViewById(R.id.profilkodeitem);
        inama = findViewById(R.id.profilnamaitem);
        imerek = findViewById(R.id.profilnamamerek);
        itipe = findViewById(R.id.profiltipeitem);
        ijenis = findViewById(R.id.profilnamajenis);
        iket = findViewById(R.id.profilketeranganitem);

        mToolbar = findViewById(R.id.toolbaritem);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("ITEM PROFILE");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getItem();

        deleteItem = findViewById(R.id.btndeleteItem);
        deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteEmployee();
            }
        });

        updateItem = findViewById(R.id.btnupdateItem);
        updateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAnItem();
                Intent i = new Intent(ProfilActivity.this, ItemActivity.class);
                i.putExtra("emailuser",String.valueOf(EmailHolder));
                startActivity(i);
                finish();
            }
        });

        listjenis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
                String namajenis = map.get(Konfigurasi_Jenis.TAG_JENISNAMA).toString();
                //String deskjenis = map.get(Konfigurasi_Jenis.TAG_JENISDESC).toString();

                ijenis.setText(namajenis);


            }
        });

        listmerek.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
                String namamerek = map.get(Konfigurasi_Merek.TAG_MEREKNAMA).toString();
                //String deskjenis = map.get(Konfigurasi_Jenis.TAG_JENISDESC).toString();

                imerek.setText(namamerek);

            }
        });

        getJSONJenis();
        getJSONMerek();

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
                    ProfilActivity.this, listj, R.layout.single_jenisview,
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
                loading = ProgressDialog.show(ProfilActivity.this,"Checking Data","Please wait...",false,false);
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
                    ProfilActivity.this, listm, R.layout.single_merekview,
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
                loading = ProgressDialog.show(ProfilActivity.this,"Checking Data","Please wait...",false,false);
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


    private void getItem(){
        class Getanitem extends AsyncTask<Void,Void,String> {
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
                tampilProfilItem(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Konfigurasi.URL_GET_AN_ITEM,iditem);
                return s;
            }
        }
        Getanitem ge = new Getanitem();
        ge.execute();
    }

    //--------------------------------------------------SHOW ITEM FUNCTION--------------------------------------------
    private void tampilProfilItem(String json){
        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);

            String kodeItem = c.getString(Konfigurasi.TAG_ITEMKODE);
            String namaItem = c.getString(Konfigurasi.TAG_ITEMNAMA);
            String merekItem = c.getString(Konfigurasi.TAG_ITEMMEREK);
            String tipeItem = c.getString(Konfigurasi.TAG_ITEMTIPE);
            String jenisItem = c.getString(Konfigurasi.TAG_ITEMJENIS);
            String ketItem = c.getString(Konfigurasi.TAG_ITEMKETERANGAN);

            ikode.setText(kodeItem);
            inama.setText(namaItem);
            imerek.setText(merekItem);
            itipe.setText(tipeItem);
            ijenis.setText(jenisItem);
            iket.setText(ketItem);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //--------------------------------------------------SHOW ITEM FUNCTION--------------------------------------------

    //--------------------------------------------------DELETE FUNCTION--------------------------------------------
    private void deleteEmployee(){
        class DeleteEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(TampilPegawai.this, "Updating...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //loading.dismiss();
                Toast.makeText(ProfilActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Konfigurasi.URL_DELETE_ITEM, iditem);
                return s;
            }
        }

        DeleteEmployee de = new DeleteEmployee();
        de.execute();
    }

    private void confirmDeleteEmployee(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus Item ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteEmployee();
                        Intent i = new Intent(ProfilActivity.this,ItemActivity.class);
                        i.putExtra("emailuser",String.valueOf(EmailHolder));
                        startActivity(i);

                    }
                });

        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    //--------------------------------------------------DELETE FUNCTION--------------------------------------------

    //--------------------------------------------------UPDATE FUNCTION--------------------------------------------
    private void updateAnItem(){
        final String ukode = ikode.getText().toString().trim();
        final String unama = inama.getText().toString().trim();
        final String umerek = imerek.getText().toString().trim();
        final String utipe = itipe.getText().toString().trim();
        final String ujenis = ijenis.getText().toString().trim();
        final String uketerangan = iket.getText().toString().trim();

        class UpdateEmployee extends AsyncTask<Void,Void,String>{
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
                Toast.makeText(ProfilActivity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Konfigurasi.KEY_ITEMID,iditem);
                hashMap.put(Konfigurasi.KEY_ITEMKODE,ukode);
                hashMap.put(Konfigurasi.KEY_ITEMNAMA,unama);
                hashMap.put(Konfigurasi.KEY_ITEMMEREK,umerek);
                hashMap.put(Konfigurasi.KEY_ITEMTIPE,utipe);
                hashMap.put(Konfigurasi.KEY_ITEMJENIS,ujenis);
                hashMap.put(Konfigurasi.KEY_ITEMKETERANGAN,uketerangan);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Konfigurasi.URL_UPDATE_ITEM,hashMap);

                return s;
            }
        }

        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
    }
    //--------------------------------------------------UPDATE FUNCTION--------------------------------------------


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ProfilActivity.this, ItemActivity.class);
        i.putExtra("emailuser",String.valueOf(EmailHolder));
        startActivity(i);
    }
}
