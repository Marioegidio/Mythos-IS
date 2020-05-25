<?php
require_once $_SERVER["DOCUMENT_ROOT"] . "/mythos_pr/src/entity/Pr.php";

session_start();


function sendTest($name, $lastname,$password)
{
    $oldName=$name;
    $lastname=str_replace(' ', '_', ucwords($lastname));
    $name=str_replace(' ', '_', ucwords($name));

    $curl = curl_init();

    curl_setopt($curl, CURLOPT_URL, "http://localhost:80/mythos_pr/src/webservices/addPr.php?name=" . $name . "&lastname=" . $lastname . "&password=".$password."&prov=test");
    curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($curl, CURLOPT_HEADER, false);
    curl_setopt($curl, CURLOPT_FAILONERROR, true);
    curl_setopt($curl, CURLOPT_FOLLOWLOCATION, true);
    curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($curl, CURLOPT_SSL_VERIFYHOST, false);
    curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, false);

    $result = curl_exec($curl);

    curl_close($curl);

    echo "<br><br><br>Passo name='" . $oldName . "', lastname='" . $lastname . "' e password='".$password."' -> " . $result;
}


$name = "";
$lastname = "value2";
$password = "value3";
sendTest($name, $lastname,$password);

$name = "ciaosnjsjjzhjxbqjxnwiqjxijwxmiwsxmwosxmwsmxwksnxknxsixoqsnxiwonxjxbsnwkxsjb";
$lastname = "value2";
$password = "value3";
sendTest($name, $lastname,$password);

$name = "value1";
$lastname = "value2";
$password = "value3";
sendTest($name, $lastname,$password);

$name = " val";
$lastname = "value2";
$password = "value3";
sendTest($name, $lastname,$password);

$name = "value";
$lastname = "";
$password = "value3";
sendTest($name, $lastname,$password);

$name = "value";
$lastname = "jcjefnefkemmcfeocmledmkmdwkcdmwkcmwdkcmmwldcnkmclwdnkldwkl";
$password = "value3";
sendTest($name, $lastname,$password);

$name = "value";
$lastname = "value2";
$password = "value3";
sendTest($name, $lastname,$password);

$name = "value";
$lastname = " value";
$password = "value3";
sendTest($name, $lastname,$password);

$name = "value";
$lastname = "value";
$password = "";
sendTest($name, $lastname,$password);

$name = "value";
$lastname = "value";
$password = "gggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg";
sendTest($name, $lastname,$password);

$name = "value";
$lastname = "value";
$password = "value3!$";
sendTest($name, $lastname,$password);

$name = "value";
$lastname = "value";
$password = "value3";
sendTest($name, $lastname,$password);

$name = "value";
$lastname = "valus";
$password = "value4-";
sendTest($name, $lastname,$password);



?>
