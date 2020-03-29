package com.example.item.merek;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.item.R;
import com.example.item.RequestHandler;
import com.example.item.jenis.AddJenisActivity;
import com.google.android.material.textfield.TextInputEditText;


import java.util.HashMap;

public class AddMerekActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextInputEditText mkode, mnama, mdeskripsi;
    private Button saveMerek, clearMerek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_merek);

        mkode = findViewById(R.id.addkodemerek);
        mnama = findViewById(R.id.addnamamerek);
        mdeskripsi = findViewById(R.id.adddeskripsimerek);


        mToolbar = findViewById(R.id.toolbarmer);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("ADD JENIS");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        clearMerek = findViewById(R.id.btnclearMerek);
        clearMerek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mkode.setText("");
                mnama.setText("");
                mdeskripsi.setText("");

                Toast.makeText(AddMerekActivity.this, "Form Cleared", Toast.LENGTH_SHORT).show();

            }
        });

        saveMerek = findViewById(R.id.btnaddMerek);
        saveMerek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String kodemerek = mkode.getText().toString().trim();
                final String namamerek = mnama.getText().toString().trim();
                final String deskripsimerek = mdeskripsi.getText().toString().trim();


                if (!TextUtils.isEmpty(kodemerek) && !TextUtils.isEmpty(namamerek) && !TextUtils.isEmpty(deskripsimerek)) {
                    class TambahItemnya extends AsyncTask<Void, Void, String> {

                        ProgressDialog loading;

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            loading = ProgressDialog.show(AddMerekActivity.this, "Item Added", "Please wait....", false, false);
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            loading.dismiss();
                            Toast.makeText(AddMerekActivity.this, s, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        protected String doInBackground(Void... v) {
                            HashMap<String, String> params = new HashMap<>();
                            params.put(Konfigurasi_Merek.TAG_MEREKKODE,kodemerek);
                            params.put(Konfigurasi_Merek.TAG_MEREKNAMA,namamerek);
                            params.put(Konfigurasi_Merek.TAG_MEREKDESC,deskripsimerek);

                            RequestHandler rh = new RequestHandler();
                            String res = rh.sendPostRequest(Konfigurasi_Merek.URL_ADD, params);
                            return res;

                        }
                    }

                    TambahItemnya ae = new TambahItemnya();
                    ae.execute();
                    Intent i = new Intent(AddMerekActivity.this, MerekActivity.class);

                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(AddMerekActivity.this, "Please fill all the entry form!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void onBackPressed() {
        Intent i = new Intent(AddMerekActivity.this, MerekActivity.class);

        startActivity(i);
        finish();
        super.onBackPressed();
    }
}
