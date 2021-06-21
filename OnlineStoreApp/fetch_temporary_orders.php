<?php

$connection = new mysqli("localhost", "root", "", "online_store_db");
$sqlCommand = $connection->prepare("SELECT temporary_product_table.id,name,price,email,amount FROM temporary_product_table INNER JOIN electronic_products ON electronic_products.id=temporary_product_table.id where email=?");
$sqlCommand->bind_param("s", $_GET["email"]);
$sqlCommand->execute();



$resultArray = $sqlCommand->get_result();

$result = array();
while ($row = $resultArray->fetch_assoc()){
    array_push($result, $row);
}
echo json_encode($result);

