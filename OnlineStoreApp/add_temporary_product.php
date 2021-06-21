<?php

$connection = new mysqli("localhost", "root", "", "online_store_db");
$sqlCommand = $connection->prepare("insert into temporary_product_table values (?,?,?)");
$sqlCommand->bind_param("sii", $_GET["email"],$_GET["id"],$_GET["amount"]);
$sqlCommand->execute();


