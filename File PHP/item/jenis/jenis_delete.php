<?php

$id = $_GET['id'];

 //Import File Koneksi Database
 require_once('koneksi.php');

 //Membuat SQL Query
 $sql = "DELETE FROM tbjenis WHERE id=$id;";


 //Menghapus Nilai pada Database
 if(mysqli_query($con,$sql)){
 echo 'Berhasil Menghapus Jenis';
 }else{
 echo 'Gagal Menghapus Jenis';
 }

 mysqli_close($con);

?>