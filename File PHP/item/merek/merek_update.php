<?php

	if($_SERVER['REQUEST_METHOD']=='POST'){
		//MEndapatkan Nilai Dari Variable
		$id = $_POST['id'];
		$kode = $_POST['kode'];
		$nama = $_POST['nama'];
		$deskripsi = $_POST['deskripsi'];

		//import file koneksi database
		require_once('koneksi.php');

		//Membuat SQL Query
		$sql = "UPDATE tbmerek
		SET
		kode = '$kode',
		nama = '$nama',
		deskripsi = '$deskripsi'
		WHERE id = $id;";

		//Meng-update Database
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Update Merek';
		}else{
			echo 'Gagal Update Merek';
		}

		mysqli_close($con);
	}
?>