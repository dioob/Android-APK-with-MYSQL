<?php

	if($_SERVER['REQUEST_METHOD']=='POST'){

		$kode = $_POST['kode'];
		$nama = $_POST['nama'];
		$merek = $_POST['merek'];
		$tipe = $_POST['tipe'];
		$jenis = $_POST['jenis'];
		$keterangan = $_POST['keterangan'];

		//Pembuatan Syntax SQL
		$sql = "INSERT INTO tbitem (kode,nama,merek,tipe,jenis,keterangan) VALUES ('$kode','$nama','$merek','$tipe','$jenis','$keterangan')";

		//Import File Koneksi database
		require_once('koneksi.php');

		//Eksekusi Query database
		if(mysqli_query($con,$sql)){
			echo 'Succesfully adding Item.';
		}else{
			echo 'Failed adding Item.';
		}

		mysqli_close($con);
	}
?>