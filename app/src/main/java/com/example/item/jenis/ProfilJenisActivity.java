package com.example.item.jenis;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.item.ItemActivity;
import com.example.item.Konfigurasi;
import com.example.item.ProfilActivity;
import com.example.item.R;
import com.example.item.RequestHandler;
import com.example.item.user.Konfigurasi_Login;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ProfilJenisActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextInputEditText jkode, jnama, jdeskripsi;
    private Button updateJenis, deleteJenis;
    private TextView cek;
    private String idJenis;
    public static String EmailHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_jenis);

        Bundle intent = getIntent().getExtras();
        idJenis = intent.getString(Konfigurasi_Jenis.JENIS_ID);
        EmailHolder = intent.getString("emailuser");
        cek = findViewById(R.id.cek);
        cek.setText("Email: "+EmailHolder);

        jkode = findViewById(R.id.profilkodejenis);
        jnama = findViewById(R.id.profilnamajenis);
        jdeskripsi = findViewById(R.id.profildeskrisijenis);

        mToolbar = findViewById(R.id.toolbarjen);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("JENIS PROFILE");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getJenis();

        deleteJenis = findViewById(R.id.btndeleteJenis);
        deleteJenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteEmployee();
            }
        });

        updateJenis = findViewById(R.id.btnupdateJenis);
        updateJenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAnJenis();
                Intent i = new Intent(ProfilJenisActivity.this, JenisActivity.class);
                i.putExtra("emailuser",String.valueOf(EmailHolder));
                startActivity(i);
                finish();
            }
        });

    }

    private void getJenis(){
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
                tampilProfilJenis(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Konfigurasi_Jenis.URL_GET_AN_JENIS,idJenis);
                return s;
            }
        }
        Getanitem ge = new Getanitem();
        ge.execute();
    }

    private void tampilProfilJenis(String json){
        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi_Jenis.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);

            String kodeJenis = c.getString(Konfigurasi_Jenis.TAG_JENISKODE);
            String namaJenis = c.getString(Konfigurasi_Jenis.TAG_JENISNAMA);
            String deskripsiJenis = c.getString(Konfigurasi_Jenis.TAG_JENISDESC);

            jkode.setText(kodeJenis);
            jnama.setText(namaJenis);
            jdeskripsi.setText(deskripsiJenis);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //--------------------------------------------------SHOW ITEM FUNCTION--------------------------------------------

    //--------------------------------------------------DELETE FUNCTION--------------------------------------------
    private void deleteJenis(){
        class DeleteJenis extends AsyncTask<Void,Void,String> {
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
                Toast.makeText(ProfilJenisActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Konfigurasi_Jenis.URL_DELETE_JENIS, idJenis);
                return s;
            }
        }

        DeleteJenis dj = new DeleteJenis();
        dj.execute();
    }

    private void confirmDeleteEmployee(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus Item ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteJenis();
                        Intent i = new Intent(ProfilJenisActivity.this,JenisActivity.class);
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
    private void updateAnJenis(){
        final String ukode = jkode.getText().toString().trim();
        final String unama = jnama.getText().toString().trim();
        final String udeskripsi = jdeskripsi.getText().toString().trim();


        class UpdateJenis extends AsyncTask<Void,Void,String>{
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
                Toast.makeText(ProfilJenisActivity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Konfigurasi_Jenis.KEY_JENISID,idJenis);
                hashMap.put(Konfigurasi_Jenis.KEY_JENISKODE,ukode);
                hashMap.put(Konfigurasi_Jenis.KEY_JENISNAMA,unama);
                hashMap.put(Konfigurasi_Jenis.KEY_JENISDESC,udeskripsi);


                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Konfigurasi_Jenis.URL_UPDATE_JENIS,hashMap);

                return s;
            }
        }

        UpdateJenis uj = new UpdateJenis();
        uj.execute();
    }
    //--------------------------------------------------UPDATE FUNCTION--------------------------------------------


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ProfilJenisActivity.this, JenisActivity.class);
        i.putExtra("emailuser",String.valueOf(EmailHolder));
        startActivity(i);
    }
}