<?php

$connection = new mysqli("localhost", "root", "", "online_store_db");
$sqlCommand = $connection->prepare("select price,amount from electronic_products inner join invoice_details on invoice_details.id = electronic_products.id where invoice_details.invoice_number =?");
$sqlCommand->bind_param("i", $_GET["invoice_number"]);
$sqlCommand->execute();

$result = $sqlCommand->get_result();
$totalPrice =0;

while ($row = $result->fetch_assoc()){
    $totalPrice += ($row["price"] * $row["amount"]);
}
echo $totalPrice;

