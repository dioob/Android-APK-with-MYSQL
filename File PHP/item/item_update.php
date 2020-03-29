<?php

	if($_SERVER['REQUEST_METHOD']=='POST'){
		//MEndapatkan Nilai Dari Variable
		$id = $_POST['id'];
		$kode = $_POST['kode'];
		$nama = $_POST['nama'];
		$merek = $_POST['merek'];
		$tipe = $_POST['tipe'];
		$jenis = $_POST['jenis'];
		$keterangan = $_POST['keterangan'];

		//import file koneksi database
		require_once('koneksi.php');

		//Membuat SQL Query
		$sql = "UPDATE tbitem
		SET
		kode = '$kode',
		nama = '$nama',
		merek = '$merek',
		tipe = '$tipe',
		jenis = '$jenis',
		keterangan = '$keterangan'
		WHERE id = $id;";

		//Meng-update Database
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Update Item';
		}else{
			echo 'Gagal Update Item';
		}

		mysqli_close($con);
	}
?>