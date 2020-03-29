<?php

	//Import File Koneksi Database
	require_once('koneksi.php');

	//Membuat SQL Query
	$sql = "SELECT * FROM tbwarehouse";

	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);

	//Membuat Array Kosong
	$result = array();

	while($row = mysqli_fetch_array($r)){

		//Memasukkan Nama dan ID kedalam Array Kosong yang telah dibuat
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
	}

	//Menampilkan Array dalam Format JSON
	echo json_encode(array('result'=>$result));

	mysqli_close($con);
?>