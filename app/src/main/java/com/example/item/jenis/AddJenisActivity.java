package com.example.item.jenis;

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

import com.example.item.AddActivity;
import com.example.item.ItemActivity;
import com.example.item.Konfigurasi;
import com.example.item.R;
import com.example.item.RequestHandler;
import com.example.item.user.Konfigurasi_Login;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

public class AddJenisActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextInputEditText jkode, jnama, jdeskripsi;
    private Button saveJenis, clearJenis;

    private String EmailHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_jenis);

        Bundle extras = getIntent().getExtras();
        EmailHolder = extras.getString("emailuser");

        jkode = findViewById(R.id.addkodejenis);
        jnama = findViewById(R.id.addnamajenis);
        jdeskripsi = findViewById(R.id.adddeskripsijenis);


        mToolbar = findViewById(R.id.toolbarjen);
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

        clearJenis = findViewById(R.id.btnclearJenis);
        clearJenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                jkode.setText("");
                jnama.setText("");
                jdeskripsi.setText("");

                Toast.makeText(AddJenisActivity.this, "Form Cleared", Toast.LENGTH_SHORT).show();

            }
        });

        saveJenis = findViewById(R.id.btnaddJenis);
        saveJenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String kodejenis = jkode.getText().toString().trim();
                final String namajenis = jnama.getText().toString().trim();
                final String deskripsijenis = jdeskripsi.getText().toString().trim();


                if (!TextUtils.isEmpty(kodejenis) && !TextUtils.isEmpty(namajenis) && !TextUtils.isEmpty(deskripsijenis)) {
                    class TambahItemnya extends AsyncTask<Void, Void, String> {

                        ProgressDialog loading;

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            loading = ProgressDialog.show(AddJenisActivity.this, "Item Added", "Please wait....", false, false);
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            loading.dismiss();
                            Toast.makeText(AddJenisActivity.this, s, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        protected String doInBackground(Void... v) {
                            HashMap<String, String> params = new HashMap<>();
                            params.put(Konfigurasi_Jenis.TAG_JENISKODE,kodejenis);
                            params.put(Konfigurasi_Jenis.TAG_JENISNAMA,namajenis);
                            params.put(Konfigurasi_Jenis.TAG_JENISDESC,deskripsijenis);

                            RequestHandler rh = new RequestHandler();
                            String res = rh.sendPostRequest(Konfigurasi_Jenis.URL_ADD, params);
                            return res;

                        }
                    }

                    TambahItemnya ae = new TambahItemnya();
                    ae.execute();
                    Intent i = new Intent(AddJenisActivity.this, JenisActivity.class);
                    i.putExtra("emailuser",String.valueOf(EmailHolder));
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(AddJenisActivity.this, "Please fill all the entry form!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void onBackPressed() {
        Intent i = new Intent(AddJenisActivity.this, JenisActivity.class);
        i.putExtra("emailuser",String.valueOf(EmailHolder));
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}
