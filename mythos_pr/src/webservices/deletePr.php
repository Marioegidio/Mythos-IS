<?php
    /**
 * Pagina php che offre il servizio di rimozione di un Pr.
 */

    require_once $_SERVER["DOCUMENT_ROOT"] ."/mythos_pr/src/manager/PrManager.php";

    session_start();
    $username=$_GET["usern"];
    $res=deletePR($username);

    echo json_encode($res);
?>