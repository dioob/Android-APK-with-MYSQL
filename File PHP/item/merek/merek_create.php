<?php

	if($_SERVER['REQUEST_METHOD']=='POST'){

		$kode = $_POST['kode'];
		$nama = $_POST['nama'];
		$deskripsi = $_POST['deskripsi'];


		//Pembuatan Syntax SQL
		$sql = "INSERT INTO tbmerek (kode,nama,deskripsi) VALUES ('$kode','$nama','$deskripsi')";

		//Import File Koneksi database
		require_once('koneksi.php');

		//Eksekusi Query database
		if(mysqli_query($con,$sql)){
			echo 'Succesfully adding Merek.';
		}else{
			echo 'Failed adding Merek.';
		}

		mysqli_close($con);
	}
?>