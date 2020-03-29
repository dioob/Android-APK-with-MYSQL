package com.example.item.jenis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.item.jenis.JenisAdapter;
import com.example.item.jenis.Konfigurasi_Jenis;
import com.example.item.jenis.ProfilJenisActivity;
import com.example.item.R;

import java.util.ArrayList;
import java.util.HashMap;

public class JenisAdapter extends RecyclerView.Adapter<JenisAdapter.ViewHolder> {

    private ArrayList<HashMap<String, String>> listItems;

    private ArrayList<HashMap<String, String>> listItemsFull;
    private Context context;
    //private final int limit = 10;

    public JenisAdapter(Context context,ArrayList<HashMap<String, String>> list) {

        this.listItems = list;

        this.listItemsFull = new ArrayList<>(listItems);
        listItemsFull.addAll(listItems);

        this.context = context;
    }

    @NonNull
    @Override
    public JenisAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.single_jenis,parent,false);
        return new JenisAdapter.ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull JenisAdapter.ViewHolder holder, final int position) {

        holder.namajenis.setText(listItems.get(position).get(Konfigurasi_Jenis.TAG_JENISNAMA));
        holder.kodejenis.setText("Kode : "+listItems.get(position).get(Konfigurasi_Jenis.TAG_JENISKODE));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {
                String idJenis = listItems.get(position).get(Konfigurasi_Jenis.TAG_JENISID);
                passid(idJenis);
            }
        });
    }

    private void passid(String idJenis) {

        Intent intent = new Intent(context, ProfilJenisActivity.class);
        intent.putExtra(Konfigurasi_Jenis.JENIS_ID,idJenis);
        intent.putExtra("emailuser",JenisActivity.EmailHolder);
        context.startActivity(intent);

        /*Intent i = new Intent("i").putExtra(Konfigurasi.ITEM_ID,idItem);
        LocalBroadcastManager.getInstance(context).sendBroadcast(i);*/
    }


    @Override
    public int getItemCount() {

        return listItems.size();

    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView namajenis,kodejenis;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();
            namajenis = itemView.findViewById(R.id.single_nama_jenis);
            kodejenis = itemView.findViewById(R.id.single_kode_jenis);
        }

    }
}