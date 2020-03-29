package com.example.item.merek;

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

import com.example.item.R;
import com.example.item.RequestHandler;
import com.example.item.jenis.JenisActivity;
import com.example.item.jenis.Konfigurasi_Jenis;
import com.example.item.jenis.ProfilJenisActivity;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ProfilMerekActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextInputEditText mkode, mnama, mdeskripsi;
    private Button updateMerek, deleteMerek;
    private TextView cek;
    private String idMerek, EmailHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_merek);

        Bundle intent = getIntent().getExtras();
        idMerek = intent.getString(Konfigurasi_Merek.MEREK_ID);
        EmailHolder = intent.getString("emailuser");

        cek = findViewById(R.id.cek);
        cek.setText("Email: "+EmailHolder);

        mkode = findViewById(R.id.profilkodemerek);
        mnama = findViewById(R.id.profilnamamerek);
        mdeskripsi = findViewById(R.id.profildeskrisimerek);

        mToolbar = findViewById(R.id.toolbarmer);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("MEREK PROFILE");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getMerek();

        deleteMerek = findViewById(R.id.btndeleteMerek);
        deleteMerek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteMerek();
            }
        });

        updateMerek = findViewById(R.id.btnupdateMerek);
        updateMerek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAnMerek();
                Intent i = new Intent(ProfilMerekActivity.this, MerekActivity.class);
                i.putExtra("emailuser",String.valueOf(EmailHolder));
                startActivity(i);
                finish();
            }
        });

    }

    private void getMerek(){
        class Getanmerek extends AsyncTask<Void,Void,String> {
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
                tampilProfilMerek(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Konfigurasi_Merek.URL_GET_AN_MEREK,idMerek);
                return s;
            }
        }
        Getanmerek gm = new Getanmerek();
        gm.execute();
    }

    private void tampilProfilMerek(String json){
        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi_Merek.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);

            String kodeMerek = c.getString(Konfigurasi_Merek.TAG_MEREKKODE);
            String namaMerek = c.getString(Konfigurasi_Merek.TAG_MEREKNAMA);
            String deskripsiMerek = c.getString(Konfigurasi_Merek.TAG_MEREKDESC);

            mkode.setText(kodeMerek);
            mnama.setText(namaMerek);
            mdeskripsi.setText(deskripsiMerek);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //--------------------------------------------------SHOW ITEM FUNCTION--------------------------------------------

    //--------------------------------------------------DELETE FUNCTION--------------------------------------------
    private void deleteMerek(){
        class DeleteMerek extends AsyncTask<Void,Void,String> {
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
                Toast.makeText(ProfilMerekActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Konfigurasi_Merek.URL_DELETE_MEREK, idMerek);
                return s;
            }
        }

        DeleteMerek dm = new DeleteMerek();
        dm.execute();
    }

    private void confirmDeleteMerek(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus Item ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteMerek();
                        Intent i = new Intent(ProfilMerekActivity.this,MerekActivity.class);
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
    private void updateAnMerek(){
        final String ukode = mkode.getText().toString().trim();
        final String unama = mnama.getText().toString().trim();
        final String udeskripsi = mdeskripsi.getText().toString().trim();


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
                Toast.makeText(ProfilMerekActivity.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(Konfigurasi_Merek.KEY_MEREKID,idMerek);
                hashMap.put(Konfigurasi_Merek.KEY_MEREKKODE,ukode);
                hashMap.put(Konfigurasi_Merek.KEY_MEREKNAMA,unama);
                hashMap.put(Konfigurasi_Merek.KEY_MEREKDESC,udeskripsi);


                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Konfigurasi_Merek.URL_UPDATE_MEREK,hashMap);

                return s;
            }
        }

        UpdateMerek um = new UpdateMerek();
        um.execute();
    }
    //--------------------------------------------------UPDATE FUNCTION--------------------------------------------


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ProfilMerekActivity.this, MerekActivity.class);
        i.putExtra("emailuser",String.valueOf(EmailHolder));
        startActivity(i);
    }
}