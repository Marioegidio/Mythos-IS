<?php
    /**
 * Pagina php che offre il servizio di modifica dello stato delle prenotazioni.
 */

    require_once $_SERVER["DOCUMENT_ROOT"] ."/mythos_pr/src/manager/ClientsManager.php";
    /*
        $fp = fopen('logreservStateChange.txt', 'a');
        $curr=date("Y-m-d H:i:s.u");
        $host=$_SERVER["REMOTE_HOST"] ?: gethostbyaddr($_SERVER["REMOTE_ADDR"]);
        fwrite($fp,$curr." richiesta effettuata da $host STATO ATTUALE-->".$option->getFlagList()."\n");
        fclose($fp);
    */
    $res=enableOrDisableList();

    echo json_encode($res);
?>