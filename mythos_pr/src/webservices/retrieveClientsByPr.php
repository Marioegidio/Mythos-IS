<?php
    /**
 * Pagina php che offre il servizio che restituisce un cliente conoscendo il Pr.
 */

    require_once $_SERVER["DOCUMENT_ROOT"] ."/mythos_pr/src/manager/ClientsManager.php";

    $prName=$_GET["prName"];
    $prLastname=$_GET["prLastname"];
    $clientList=clientListByPr($prName,$prLastname);

    echo json_encode($clientList);
?>