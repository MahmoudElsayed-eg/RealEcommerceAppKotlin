<?php

$connection = new mysqli("localhost", "root", "", "online_store_db");
$getproductSqlCommand = $connection->prepare("select * from electronic_products where brand = ?");
$getproductSqlCommand->bind_param("s", $_GET["brand"]);
$getproductSqlCommand->execute();

$resultArray = $getproductSqlCommand->get_result();
$product = array();

while ($row = $resultArray->fetch_assoc()){
    array_push($product, $row);
}
echo json_encode($product);