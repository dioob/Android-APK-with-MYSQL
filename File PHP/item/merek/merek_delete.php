<?php

$id = $_GET['id'];

 //Import File Koneksi Database
 require_once('koneksi.php');

 //Membuat SQL Query
 $sql = "DELETE FROM tbmerek WHERE id=$id;";


 //Menghapus Nilai pada Database
 if(mysqli_query($con,$sql)){
 echo 'Berhasil Menghapus Merek';
 }else{
 echo 'Gagal Menghapus Merek';
 }

 mysqli_close($con);

?>