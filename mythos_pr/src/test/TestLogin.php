<?php

function sendTest($user, $pass){
    $curl = curl_init();

    curl_setopt($curl, CURLOPT_URL, "http://localhost:80/mythos_pr/src/control/login.php");
    curl_setopt($curl, CURLOPT_POST, 1);
    curl_setopt($curl, CURLOPT_POSTFIELDS,
        "user=" . $user . "&pass=" . $pass . "&prov=test");
    curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($curl, CURLOPT_HEADER, false);
    curl_setopt($curl, CURLOPT_FAILONERROR, true);
    curl_setopt($curl, CURLOPT_FOLLOWLOCATION, true);
    curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($curl, CURLOPT_SSL_VERIFYHOST, false);
    curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, false);

    $result = curl_exec($curl);

    curl_close($curl);

    echo "<br><br><br>Passo user='" . $user . "' e password='" . $pass . "' -> " . $result;
}


$user = "value1";
$pass = "value2";
sendTest($user,$pass);

$user = "";
$pass = "value2";
sendTest($user,$pass);

$user = "value1";
$pass = "";
sendTest($user,$pass);

$user = "CognomeNome";
$pass = "Ciao";
sendTest($user,$pass);

?>
