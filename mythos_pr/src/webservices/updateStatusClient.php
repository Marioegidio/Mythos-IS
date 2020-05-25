<?php
    /**
 * Pagina php che offre il servizio di modifica dello stato di un cliente.
 */

    require_once $_SERVER["DOCUMENT_ROOT"] ."/mythos_pr/src/manager/ClientsManager.php";

    $clientId=$_GET["clientId"];

    if(editClientListStatus($clientId))
        echo json_encode("modifica ok");
    else
        echo json_encode("errore modifica");

?>