<?php

	if($_SERVER['REQUEST_METHOD']=='POST'){

		$kode = $_POST['kode'];
		$nama = $_POST['nama'];
		$deskripsi = $_POST['deskripsi'];


		//Pembuatan Syntax SQL
		$sql = "INSERT INTO tbjenis (kode,nama,deskripsi) VALUES ('$kode','$nama','$deskripsi')";

		//Import File Koneksi database
		require_once('koneksi.php');

		//Eksekusi Query database
		if(mysqli_query($con,$sql)){
			echo 'Succesfully adding Jenis.';
		}else{
			echo 'Failed adding Jenis.';
		}

		mysqli_close($con);
	}
?>