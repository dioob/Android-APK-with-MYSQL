package com.example.item.merek;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.item.R;
import com.example.item.merek.MerekAdapter;
import com.example.item.jenis.Konfigurasi_Jenis;
import com.example.item.jenis.ProfilJenisActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MerekAdapter extends RecyclerView.Adapter<MerekAdapter.ViewHolder> {

    private ArrayList<HashMap<String, String>> listItems;

    private ArrayList<HashMap<String, String>> listItemsFull;
    private Context context;
    //private final int limit = 10;

    public MerekAdapter(Context context,ArrayList<HashMap<String, String>> list) {

        this.listItems = list;

        this.listItemsFull = new ArrayList<>(listItems);
        listItemsFull.addAll(listItems);

        this.context = context;
    }

    @NonNull
    @Override
    public MerekAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.single_merek,parent,false);
        return new MerekAdapter.ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MerekAdapter.ViewHolder holder, final int position) {

        holder.namamerek.setText(listItems.get(position).get(Konfigurasi_Merek.TAG_MEREKNAMA));
        holder.kodemerek.setText("Kode : "+listItems.get(position).get(Konfigurasi_Merek.TAG_MEREKKODE));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {
                String idMerek = listItems.get(position).get(Konfigurasi_Merek.TAG_MEREKID);
                passid(idMerek);
            }
        });
    }

    private void passid(String idMerek) {

        Intent intent = new Intent(context, ProfilMerekActivity.class);
        intent.putExtra(Konfigurasi_Merek.MEREK_ID,idMerek);
        intent.putExtra("emailuser",MerekActivity.EmailHolder);
        context.startActivity(intent);

        /*Intent i = new Intent("i").putExtra(Konfigurasi.ITEM_ID,idItem);
        LocalBroadcastManager.getInstance(context).sendBroadcast(i);*/
    }


    @Override
    public int getItemCount() {

        return listItems.size();

    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView namamerek,kodemerek;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();
            namamerek = itemView.findViewById(R.id.single_nama_merek);
            kodemerek = itemView.findViewById(R.id.single_kode_merek);
        }

    }
}