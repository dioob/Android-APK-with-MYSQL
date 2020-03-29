package com.example.item.jenis;

public class Konfigurasi_Jenis {

    public static final String URL_ADD = "http://192.168.100.4/android/item/jenis/jenis_create.php";
    public static final String URL_GET_ALL = "http://192.168.100.4/android/item/jenis/jenis_read.php";
    public static final String URL_GET_AN_JENIS = "http://192.168.100.4/android/item/jenis/jenis_select_detail.php?id=";
    public static final String URL_UPDATE_JENIS = "http://192.168.100.4/android/item/jenis/jenis_update.php";
    public static final String URL_DELETE_JENIS = "http://192.168.100.4/android/item/jenis/jenis_delete.php?id=";


    //KEY request to PHP
    public static final String KEY_JENISID = "id";
    public static final String KEY_JENISKODE = "kode";
    public static final String KEY_JENISNAMA = "nama";
    public static final String KEY_JENISDESC = "deskripsi";

    //JSON Tags
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_JENISID = "id";
    public static final String TAG_JENISKODE = "kode";
    public static final String TAG_JENISNAMA = "nama";
    public static final String TAG_JENISDESC = "deskripsi";


    public static final String JENIS_ID = "jenis_id";
}
