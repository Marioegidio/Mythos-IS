<?php
    /**
 * Pagina php che permette ad un Pr di rimuovere un cliente dalla lista.
 */

    require_once $_SERVER["DOCUMENT_ROOT"] ."/mythos_pr/src/manager/ClientsManager.php";
    session_start();

    if($_SESSION["logUser"]==null)
        header("location: ../../login.html");
    else{
        $id=$_GET["id"];
        removeClientFromList($id);
    }

?>