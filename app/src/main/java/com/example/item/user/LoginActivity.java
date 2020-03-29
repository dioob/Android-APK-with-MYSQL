package com.example.item.user;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.item.MainActivity;
import com.example.item.R;
import com.example.item.RequestHandler;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText emaillog, passwordlog;
    private TextView registrasi;
    private Button loginbtn;
    //HashMap<String, String> hashMap = new HashMap<>();
    String PasswordHolder, EmailHolder;
    Boolean CheckEditText;
    String finalResult;
    public static final String UserEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emaillog = findViewById(R.id.emaillogin);
        passwordlog = findViewById(R.id.passwordlogin);

        registrasi = findViewById(R.id.textregistrasi);
        registrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegistrasiActivity.class);
                startActivity(i);
                finish();
            }
        });

        loginbtn = findViewById(R.id.btnlogin);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckEditTextIsEmptyOrNot();
                if (CheckEditText) {
                    UserLoginFunction(EmailHolder, PasswordHolder);
                } else {
                    Toast.makeText(LoginActivity.this, "Mohon isi data akun dengan lengkap.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void CheckEditTextIsEmptyOrNot() {

        EmailHolder = emaillog.getText().toString();
        PasswordHolder = passwordlog.getText().toString();
        if (TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)) {
            CheckEditText = false;
        } else {
            CheckEditText = true;
        }
    }

    public void UserLoginFunction(final String email, final String password) {

        class UserLoginClass extends AsyncTask<String, Void, String> {

            private ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(LoginActivity.this, "Loading Data", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();

                if (httpResponseMsg.equalsIgnoreCase("Login Berhasil")) {
                    finish();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra(Konfigurasi_Login.KEY_USEREMAIL, email);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, httpResponseMsg, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> akun = new HashMap<>();
                akun.put(Konfigurasi_Login.KEY_USEREMAIL, email);
                akun.put(Konfigurasi_Login.KEY_USERPASSWORD, password);

                RequestHandler rh = new RequestHandler();
                finalResult = rh.sendPostRequest(Konfigurasi_Login.URL_GET_LOGIN, akun);

                return finalResult;
            }
        }

        UserLoginClass userLoginClass = new UserLoginClass();
        userLoginClass.execute(email, password);
    }
}