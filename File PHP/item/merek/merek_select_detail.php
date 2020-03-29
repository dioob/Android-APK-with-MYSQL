<?php
$id = $_GET['id'];

	//Importing database
	require_once('koneksi.php');

	$sql = "SELECT * FROM tbmerek WHERE id='$id'";

	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);

	//Memasukkan Hasil Kedalam Array
	$result = array();
	$row = mysqli_fetch_array($r);
	array_push($result,array(
			"kode"=>$row['kode'],
			"nama"=>$row['nama'],
			"deskripsi"=>$row['deskripsi']
		));

	//Menampilkan dalam format JSON
	echo json_encode(array('result'=>$result));

	mysqli_close($con);
