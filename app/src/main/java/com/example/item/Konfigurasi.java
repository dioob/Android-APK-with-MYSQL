package com.example.item;

public class Konfigurasi {


    public static final String URL_ADD = "http://192.168.100.4/android/item/item_create.php";
    public static final String URL_GET_ALL = "http://192.168.100.4/android/item/item_read.php";
    public static final String URL_GET_AN_ITEM = "http://192.168.100.4/android/item/item_select_detail.php?id=";
    public static final String URL_UPDATE_ITEM = "http://192.168.100.4/android/item/item_update.php";
    public static final String URL_DELETE_ITEM = "http://192.168.100.4/android/item/item_delete.php?id=";
    public static final String URL_SEARCH_ITEM = "http://192.168.100.4/android/item/item_search.php?nama=";


    //KEY request to PHP
    public static final String KEY_ITEMID = "id";
    public static final String KEY_ITEMKODE = "kode";
    public static final String KEY_ITEMNAMA = "nama";
    public static final String KEY_ITEMMEREK = "merek";
    public static final String KEY_ITEMTIPE = "tipe";
    public static final String KEY_ITEMJENIS = "jenis";
    public static final String KEY_ITEMKETERANGAN = "keterangan"; //this is for the newest column i want to fill with image data

    //JSON Tags
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_ITEMID = "id";
    public static final String TAG_ITEMKODE = "kode";
    public static final String TAG_ITEMNAMA = "nama";
    public static final String TAG_ITEMMEREK = "merek";
    public static final String TAG_ITEMTIPE = "tipe";
    public static final String TAG_ITEMJENIS = "jenis";
    public static final String TAG_ITEMKETERANGAN = "keterangan"; //this is for the newest column i want to fill with image data


    public static final String ITEM_ID = "item_id";
}
