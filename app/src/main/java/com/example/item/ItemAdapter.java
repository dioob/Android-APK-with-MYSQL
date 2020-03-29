package com.example.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private ArrayList<HashMap<String, String>> listItems;

    private ArrayList<HashMap<String, String>> listItemsFull;
    private Context context;
    //private final int limit = 10;

    public ItemAdapter(Context context,ArrayList<HashMap<String, String>> list) {

        this.listItems = list;

        this.listItemsFull = new ArrayList<>(listItems);
        listItemsFull.addAll(listItems);

        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.single_item,parent,false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.namaitem.setText(listItems.get(position).get(Konfigurasi.TAG_ITEMNAMA));
        holder.kodeitem.setText("Kode : "+listItems.get(position).get(Konfigurasi.TAG_ITEMKODE));
        holder.merekitem.setText("Merek : "+listItems.get(position).get(Konfigurasi.TAG_ITEMMEREK));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {
                String idItem = listItems.get(position).get(Konfigurasi.TAG_ITEMID);
                passid(idItem);
            }
        });
    }

    private void passid(String idItem) {

        Intent intent = new Intent(context, ProfilActivity.class);
        intent.putExtra(Konfigurasi.ITEM_ID,idItem);
        intent.putExtra("emailuser",ItemActivity.EmailHolder);
        context.startActivity(intent);

        /*Intent i = new Intent("i").putExtra(Konfigurasi.ITEM_ID,idItem);
        LocalBroadcastManager.getInstance(context).sendBroadcast(i);*/
    }


    @Override
    public int getItemCount() {

        return listItems.size();

    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView namaitem,kodeitem,merekitem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();

            namaitem = itemView.findViewById(R.id.single_nama_item);
            kodeitem = itemView.findViewById(R.id.single_kode_item);
            merekitem = itemView.findViewById(R.id.single_merek_item);

        }

    }
}