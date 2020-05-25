<?php
    /**
 * Pagina php che offre il servizio di azzeramento della lista clienti.
 */

    require_once $_SERVER["DOCUMENT_ROOT"] ."/mythos_pr/src/manager/ClientsManager.php";

    $res=clearOrders();

    echo json_encode($res);
?>