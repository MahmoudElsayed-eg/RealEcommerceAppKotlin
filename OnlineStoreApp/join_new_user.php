<?php

$connection = new mysqli("localhost", "root", "", "online_store_db");
$emailCheckSQLCommand = $connection->prepare("select * from app_users_table where email=?");
$emailCheckSQLCommand->bind_param("s", $_GET["email"]);
$emailCheckSQLCommand->execute();
$emailResult = $emailCheckSQLCommand->get_result();

if ($emailResult->num_rows == 0) {
$sqlCommand = $connection->prepare("insert into app_users_table values(?,?,?)");
$sqlCommand->bind_param("sss", $_GET["email"], $_GET["username"] , $_GET["pass"]);
$sqlCommand->execute();
echo 'congratulation,the registeration process was successful';
} else {
    echo 'A user already exists';   
}


//$sqlCommand = $connection->prepare("insert into app_users_table values(?,?,?)");
//$sqlCommand->bind_param("sss", $_GET["email"], $_GET["username"] , $_GET["pass"]);
//$sqlCommand->execute();

