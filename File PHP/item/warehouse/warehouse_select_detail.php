<?php
$id = $_GET['id'];

	//Importing database
	require_once('koneksi.php');

	$sql = "SELECT * FROM tbwarehouse WHERE id='$id'";

	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);

	//Memasukkan Hasil Kedalam Array
	$result = array();
	$row = mysqli_fetch_array($r);
	array_push($result,array(
	        "id"=>$row['id'],
			"kode"=>$row['kode'],
			"nama"=>$row['nama'],
			"merek"=>$row['merek'],
			"tipe"=>$row['tipe'],
			"jenis"=>$row['jenis'],
			"keterangan"=>$row['keterangan'],
			"jumlah_stock"=>$row['jumlah_stock'],
			"tgl_in"=>$row['tgl_in'],
			"tgl_update"=>$row['tgl_update'],
			"user"=>$row['user']
		));

	//Menampilkan dalam format JSON
	echo json_encode(array('result'=>$result));

	mysqli_close($con);
