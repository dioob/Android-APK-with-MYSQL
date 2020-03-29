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
		$jumlah_stock = $_POST['jumlah_stock'];
		$tgl_in = $_POST['tgl_in'];
		$tgl_update = $_POST['tgl_update'];
		$user = $_POST['user'];

		//import file koneksi database
		require_once('koneksi.php');

		//Membuat SQL Query
		$sql = "UPDATE tbwarehouse
		SET
		kode = '$kode',
		nama = '$nama',
		merek = '$merek',
		tipe = '$tipe',
		jenis = '$jenis',
		keterangan = '$keterangan',
		jumlah_stock = '$jumlah_stock',
		tgl_in = '$tgl_in',
		tgl_update = '$tgl_update',
		user = '$user'
		WHERE id = $id;";

		//Meng-update Database
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Update Data Item Warehouse';
		}else{
			echo 'Gagal Update Data Item Warehouse';
		}

		mysqli_close($con);
	}
?>