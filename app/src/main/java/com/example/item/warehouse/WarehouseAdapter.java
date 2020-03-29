package com.example.item.warehouse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.item.Konfigurasi;
import com.example.item.ProfilActivity;
import com.example.item.R;

import java.util.ArrayList;
import java.util.HashMap;

public class WarehouseAdapter extends RecyclerView.Adapter<WarehouseAdapter.ViewHolder> {

    private ArrayList<HashMap<String, String>> listItems;

    private ArrayList<HashMap<String, String>> listItemsFull;
    private Context context;
    private String mEmailHolder;
    //private final int limit = 10;

    public WarehouseAdapter(Context context,ArrayList<HashMap<String, String>> list, String EmailHolder) {

        this.listItems = list;
        this.mEmailHolder= EmailHolder;
        this.listItemsFull = new ArrayList<>(listItems);
        listItemsFull.addAll(listItems);

        this.context = context;
    }

    @NonNull
    @Override
    public WarehouseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.single_warehouse,parent,false);
        return new WarehouseAdapter.ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull WarehouseAdapter.ViewHolder holder, final int position) {

        holder.namaitem.setText(listItems.get(position).get(Konfigurasi_Warehouse.TAG_WAREHOUSENAMA));
        holder.stockitem.setText("Stock : "+listItems.get(position).get(Konfigurasi_Warehouse.TAG_WAREHOUSESTOCK));
        holder.merekitem.setText("Merek : "+listItems.get(position).get(Konfigurasi_Warehouse.TAG_WAREHOUSEMEREK));
        holder.tglin.setText("Tanggal Masuk : "+listItems.get(position).get(Konfigurasi_Warehouse.TAG_WAREHOUSETGLIN));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {
                String idItem = listItems.get(position).get(Konfigurasi.TAG_ITEMID);

                passid(idItem);
            }
        });
    }

    private void passid(String idItem) {

        Intent intent = new Intent(context, ProfilWarehouseActivity.class);
        intent.putExtra(Konfigurasi_Warehouse.WAREHOUSE_ID,idItem);
        intent.putExtra("emailuser", mEmailHolder);
        context.startActivity(intent);

        /*Intent i = new Intent("i").putExtra(Konfigurasi.ITEM_ID,idItem);
        LocalBroadcastManager.getInstance(context).sendBroadcast(i);*/
    }


    @Override
    public int getItemCount() {

        return listItems.size();

    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView namaitem,stockitem,merekitem, tglin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();

            namaitem = itemView.findViewById(R.id.single_nama_itemwar);
            stockitem = itemView.findViewById(R.id.single_stock_itemwar);
            merekitem = itemView.findViewById(R.id.single_merek_itemwar);
            tglin = itemView.findViewById(R.id.single_tglin_itemwar);
        }

    }
}