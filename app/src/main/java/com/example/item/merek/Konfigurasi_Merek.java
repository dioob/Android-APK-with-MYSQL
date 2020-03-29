package com.example.item.merek;

public class Konfigurasi_Merek {

    public static final String URL_ADD = "http://192.168.100.4/android/item/merek/merek_create.php";
    public static final String URL_GET_ALL = "http://192.168.100.4/android/item/merek/merek_read.php";
    public static final String URL_GET_AN_MEREK = "http://192.168.100.4/android/item/merek/merek_select_detail.php?id=";
    public static final String URL_UPDATE_MEREK = "http://192.168.100.4/android/item/merek/merek_update.php";
    public static final String URL_DELETE_MEREK = "http://192.168.100.4/android/item/merek/merek_delete.php?id=";


    //KEY request to PHP
    public static final String KEY_MEREKID = "id";
    public static final String KEY_MEREKKODE = "kode";
    public static final String KEY_MEREKNAMA = "nama";
    public static final String KEY_MEREKDESC = "deskripsi";

    //JSON Tags
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_MEREKID = "id";
    public static final String TAG_MEREKKODE = "kode";
    public static final String TAG_MEREKNAMA = "nama";
    public static final String TAG_MEREKDESC = "deskripsi";


    public static final String MEREK_ID = "merek_id";
}
