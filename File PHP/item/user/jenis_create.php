<?php

	if($_SERVER['REQUEST_METHOD']=='POST'){

		$email = $_POST['email'];
		$password = $_POST['password'];
		$status = $_POST['status'];


		//Pembuatan Syntax SQL
		$sql = "INSERT INTO tbuser (email,password,status) VALUES ('$email','$password','$status')";

		//Import File Koneksi database
		require_once('koneksi.php');

		//Eksekusi Query database
		if(mysqli_query($con,$sql)){
			echo 'Succesfully Registration.';
		}else{
			echo 'Failed Registration.';
		}

		mysqli_close($con);
	}
?>