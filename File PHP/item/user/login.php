<?php

	require_once('koneksi.php');

	$email = $_POST['email'];
	$password = $_POST['password'];


	$sql = "select id,email,status from tbuser where email='$email' and password='$password'";

	$check = mysqli_fetch_array(mysqli_query($con,$sql));

	if(isset($check)){

		echo "Login Berhasil";
	}else{
		echo "Mohon masukan email dan password dengan benar";
	}

	mysqli_close($con);

?>