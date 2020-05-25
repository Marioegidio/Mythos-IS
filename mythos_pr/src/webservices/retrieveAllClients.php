<?php
    /**
 * Pagina php che offre il servizio che restituisce i Pr e i clienti.
 */

    require_once $_SERVER["DOCUMENT_ROOT"] ."/mythos_pr/src/manager/PrManager.php";
    require_once $_SERVER["DOCUMENT_ROOT"] ."/mythos_pr/src/manager/ClientsManager.php";

    $optionDao=new ListOptionDAO();

    $option=listStatus();
    $clientList=clientList();
    $prList=prList();
    $array = array("clients" => $clientList,"pr" => $prList,"option"=>$option);

    echo json_encode($array);
?>