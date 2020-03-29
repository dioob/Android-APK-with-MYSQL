<?php

$id = $_GET['id'];

 //Import File Koneksi Database
 require_once('koneksi.php');

 //Membuat SQL Query
 $sql = "DELETE FROM tbwarehouse WHERE id=$id;";


 //Menghapus Nilai pada Database
 if(mysqli_query($con,$sql)){
 echo 'Berhasil Menghapus Transaksi';
 }else{
 echo 'Gagal Menghapus Transaksi';
 }

 mysqli_close($con);

?>