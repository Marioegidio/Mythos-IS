<?php
    /**
 * Pagina che si occupa di gestire le connessioni al db online.
 */

    $host = "localhost";
    $user = "root";
    $password = "";
    $db = "mythos_pr_is";
    $connessione = new mysqli($host, $user, $password, $db);
    $connessione->set_charset("utf8");

    if ($connessione->connect_errno) {
        echo "Connessione fallita: ". $connessione->connect_error . ".";
        exit();
    }
?>