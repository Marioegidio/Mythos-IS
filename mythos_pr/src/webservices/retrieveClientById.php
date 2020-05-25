<?php
    /**
 * Pagina php che offre il servizio che restituisce un cliente conoscendo l’id.
 */

    require_once $_SERVER["DOCUMENT_ROOT"] ."/mythos_pr/src/manager/ClientsManager.php";

    $clientId=$_GET["clientId"];
    $client=searchClient($clientId);

    echo json_encode($client);
?>