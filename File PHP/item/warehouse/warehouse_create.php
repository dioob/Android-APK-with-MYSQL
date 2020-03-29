<?php

	if($_SERVER['REQUEST_METHOD']=='POST'){

		$kode = $_POST['kode'];
		$nama = $_POST['nama'];
		$merek = $_POST['merek'];
		$tipe = $_POST['tipe'];
		$jenis = $_POST['jenis'];
		$keterangan = $_POST['keterangan'];
		$jumlah_stock = $_POST['jumlah_stock'];
		$tgl_in = $_POST['tgl_in'];
		$tgl_update = $_POST['tgl_update'];
		$user = $_POST['user'];

		//Pembuatan Syntax SQL
		$sql = "INSERT INTO tbwarehouse (kode,nama,merek,tipe,jenis,keterangan,jumlah_stock,tgl_in,tgl_update,user)
		VALUES ('$kode','$nama','$merek','$tipe','$jenis','$keterangan','$jumlah_stock','$tgl_in','$tgl_update','$user')";

		//Import File Koneksi database
		require_once('koneksi.php');

		//Eksekusi Query database
		if(mysqli_query($con,$sql)){
			echo 'Succesfully adding Transaksi.';
		}else{
			echo 'Failed adding Transaksi.';
		}

		mysqli_close($con);
	}
?>