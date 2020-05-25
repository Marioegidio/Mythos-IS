<?php
require_once $_SERVER["DOCUMENT_ROOT"] . "/mythos_pr/src/entity/Pr.php";

session_start();


function sendTest($name, $lastname)
{
    $curl = curl_init();

    curl_setopt($curl, CURLOPT_URL, "http://localhost:80/mythos_pr/src/control/AddToList.php");
    curl_setopt($curl, CURLOPT_POST, 1);
    curl_setopt($curl, CURLOPT_POSTFIELDS,
        "name=" . $name . "&lastname=" . $lastname . "&prov=test");
    curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($curl, CURLOPT_HEADER, false);
    curl_setopt($curl, CURLOPT_FAILONERROR, true);
    curl_setopt($curl, CURLOPT_FOLLOWLOCATION, true);
    curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($curl, CURLOPT_SSL_VERIFYHOST, false);
    curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, false);

    $result = curl_exec($curl);

    curl_close($curl);

    echo "<br><br><br>Passo name='" . $name . "' e lastname='" . $lastname . "' -> " . $result;
}


$name = "";
$lastname = "value2";
sendTest($name, $lastname);

$name = "value1gggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg";
$lastname = "value2";
sendTest($name, $lastname);

$name = "value1";
$lastname = "";
sendTest($name, $lastname);

$name = " value";
$lastname = "";
sendTest($name, $lastname);

$name = "value";
$lastname = "";
sendTest($name, $lastname);

$name = "value";
$lastname = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
sendTest($name, $lastname);

$name = "value";
$lastname = "2w2";
sendTest($name, $lastname);

$name = "value";
$lastname = " cognome";
sendTest($name, $lastname);

$name = "value";
$lastname = "value";
sendTest($name, $lastname);


?>
