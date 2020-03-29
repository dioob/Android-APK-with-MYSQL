package com.example.item.warehouse;

public class Konfigurasi_Warehouse {

    public static final String URL_ADD = "http://192.168.100.4/android/item/warehouse/warehouse_create.php";
    public static final String URL_GET_ALL = "http://192.168.100.4/android/item/warehouse/warehouse_read.php";
    public static final String URL_GET_AN_WAREHOUSE = "http://192.168.100.4/android/item/warehouse/warehouse_select_detail.php?id=";
    public static final String URL_UPDATE_WAREHOUSE = "http://192.168.100.4/android/item/warehouse/warehouse_update.php";
    public static final String URL_DELETE_WAREHOUSE = "http://192.168.100.4/android/item/warehouse/warehouse_delete.php?id=";

    //KEY request to PHP
    public static final String KEY_WAREHOUSEID = "id";
    public static final String KEY_WAREHOUSEKODE = "kode";
    public static final String KEY_WAREHOUSENAMA = "nama";
    public static final String KEY_WAREHOUSEMEREK = "merek";
    public static final String KEY_WAREHOUSETIPE = "tipe";
    public static final String KEY_WAREHOUSEJENIS = "jenis";
    public static final String KEY_WAREHOUSEKETERANGAN = "keterangan"; //this is for the newest column i want to fill with image data
    public static final String KEY_WAREHOUSESTOCK = "jumlah_stock";
    public static final String KEY_WAREHOUSETGLIN = "tgl_in";
    public static final String KEY_WAREHOUSETGLUPDATE = "tgl_update";
    public static final String KEY_WAREHOUSEUSER = "user";


    //JSON Tags
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_WAREHOUSEID = "id";
    public static final String TAG_WAREHOUSEKODE = "kode";
    public static final String TAG_WAREHOUSENAMA = "nama";
    public static final String TAG_WAREHOUSEMEREK = "merek";
    public static final String TAG_WAREHOUSETIPE = "tipe";
    public static final String TAG_WAREHOUSEJENIS = "jenis";
    public static final String TAG_WAREHOUSEKETERANGAN = "keterangan"; //this is for the newest column i want to fill with image data
    public static final String TAG_WAREHOUSESTOCK = "jumlah_stock";
    public static final String TAG_WAREHOUSETGLIN = "tgl_in";
    public static final String TAG_WAREHOUSETGLUPDATE = "tgl_update";
    public static final String TAG_WAREHOUSEUSER = "user";


    public static final String WAREHOUSE_ID = "warehouse_id";
}
