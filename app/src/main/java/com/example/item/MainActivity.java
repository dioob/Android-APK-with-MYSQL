package com.example.item;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.item.jenis.JenisActivity;
import com.example.item.merek.MerekActivity;
import com.example.item.user.Konfigurasi_Login;
import com.example.item.user.LoginActivity;
import com.example.item.warehouse.WarehouseActivity;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout r1,r2,r3;

    private ImageView iitem, ijenis, imerek, iwarehouse;
    private String EmailHolder, emailholder2;
    private Toolbar mToolbar;

    private TextView judul;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        EmailHolder = intent.getStringExtra(Konfigurasi_Login.KEY_USEREMAIL);
        emailholder2 = intent.getStringExtra("mamemail");

        judul = findViewById(R.id.judulmain);

        if (emailholder2==null){
            judul.setText("Selamat Datang!\n"+EmailHolder+"!");
        }else{
            judul.setText("Selamat Datang!\n"+emailholder2+"!");
        }



        mToolbar=findViewById(R.id.toolbarmain);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("HOME");
        mToolbar.inflateMenu(R.menu.main_menu);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch  (item.getItemId()){
                    case R.id.menu_logout:
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                    break;
                    default:
                        break;
                }

                return true;
            }
        });


        iitem = findViewById(R.id.imageitem);
        ijenis = findViewById(R.id.imagejenis);
        imerek = findViewById(R.id.imagemerek);
        iwarehouse = findViewById(R.id.imagedatawarehouse);

        iitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ItemActivity.class);
                if (emailholder2==null){
                    i.putExtra("emailuser", String.valueOf(EmailHolder));
                }else{
                    i.putExtra("emailuser", emailholder2);
                }
                startActivity(i);
                finish();
            }
        });

        ijenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, JenisActivity.class);
                if (emailholder2==null){
                    i.putExtra("emailuser", String.valueOf(EmailHolder));
                }else{
                    i.putExtra("emailuser", emailholder2);
                }
                startActivity(i);
                finish();
            }
        });

        imerek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MerekActivity.class);
                if (emailholder2==null){
                    i.putExtra("emailuser", String.valueOf(EmailHolder));
                }else{
                    i.putExtra("emailuser", emailholder2);
                }
                startActivity(i);
                finish();
            }
        });

        iwarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, WarehouseActivity.class);
                if (emailholder2==null){
                    i.putExtra("emailuser", String.valueOf(EmailHolder));
                }else{
                    i.putExtra("emailuser", emailholder2);
                }
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
