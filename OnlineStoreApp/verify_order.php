<?php

$connection = new mysqli("localhost", "root", "", "online_store_db");
$getTempProdSqlCommand = $connection->prepare("select * from temporary_product_table where email = ?");
$getTempProdSqlCommand->bind_param("s", $_GET["email"]);
$getTempProdSqlCommand->execute();

$temporaryOrdersResult = $getTempProdSqlCommand->get_result();

$populateInvoiceWithEmailCommand = $connection->prepare("insert into invoice(email) values(?)");
$populateInvoiceWithEmailCommand->bind_param("s",$_GET["email"]);
$populateInvoiceWithEmailCommand->execute();

$getLatestInvoiceNumberCommand = $connection->prepare("select max(invoice_number) as latest_invoice_number from invoice where email=?");
$getLatestInvoiceNumberCommand->bind_param("s", $_GET["email"]);
$getLatestInvoiceNumberCommand ->execute();

$LatestInvoiceNumberResult = $getLatestInvoiceNumberCommand->get_result();
$row_invoice_number = $LatestInvoiceNumberResult->fetch_assoc();

while ($row = $temporaryOrdersResult->fetch_assoc()){
    $populateInvoiceDetailsCommand = $connection->prepare("insert into invoice_details values(?,?,?)");
    $populateInvoiceDetailsCommand->bind_param("iii", $row_invoice_number["latest_invoice_number"],$row["id"],$row["amount"]);
    $populateInvoiceDetailsCommand->execute();   
}
$deleteTempOrdersCommand= $connection->prepare("delete from temporary_product_table where email=?");
$deleteTempOrdersCommand->bind_param("s", $_GET["email"]);
$deleteTempOrdersCommand->execute();

echo $row_invoice_number["latest_invoice_number"];
