<?php
$nama = $_GET['nama'];
     //Import File Koneksi Database
     	require_once('koneksi.php');

     	//Membuat SQL Query
     	$sql = "SELECT * FROM tbitem where nama='$nama'";

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
     			"keterangan"=>$row['keterangan']
     		));
     	}

     	//Menampilkan Array dalam Format JSON
     	echo json_encode(array('result'=>$result));

	mysqli_close($con);

?>