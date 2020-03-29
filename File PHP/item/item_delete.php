<?php

$id = $_GET['id'];

 //Import File Koneksi Database
 require_once('koneksi.php');

 //Membuat SQL Query
 $sql = "DELETE FROM tbitem WHERE id=$id;";


 //Menghapus Nilai pada Database
 if(mysqli_query($con,$sql)){
 echo 'Berhasil Menghapus Item';
 }else{
 echo 'Gagal Menghapus Item';
 }

 mysqli_close($con);

?>