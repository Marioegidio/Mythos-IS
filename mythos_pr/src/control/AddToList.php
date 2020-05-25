<?php
    /**
     * Pagina php che permette ad un Pr di aggiungere un cliente in lista.
     */

    require_once $_SERVER["DOCUMENT_ROOT"] ."/mythos_pr/src/manager/ClientsManager.php";
    session_start();

    $prov="";
    if ( isset($_POST["prov"])) $prov=$_POST["prov"];

    if ($prov=="test"){
        $user=new Pr();
        $user->setUsername("CognomeNome");
        $user->setPassword("Ciao");
        $user->setName("Nome");
        $user->setLastname("Cognome");

        $_SESSION["logUser"]=$user;
    }

    if($_SESSION["logUser"]==null)
        header("location: ../../login.html");
    else{

            $name=$_POST["name"];
            $lastname=$_POST["lastname"];
            $prSession=$_SESSION["logUser"];
            $pattern="/^[a-zA-Zàáâäãåąčćęèéêëėįìíîïłńo'óôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃo'ÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ']+$/u";

            if ( strlen($name) < 1){
                $res="Il campo nome non puo' essere vuoto";
                if ("test"==$prov)echo json_encode($res);
            }
            else if ( strlen($name) > 50 ){
                $res="Il campo nome e' troppo lungo";
                if ("test"==$prov)echo json_encode($res);
            }
            else if( !( preg_match($pattern,$name) ) ){
                $res="Campo nome non valido";
                if ("test"==$prov)echo json_encode($res);
            }
            else if(mb_substr($name, 0, 1) == ' '){
                $res="Il campo nome non puo' iniziare con uno spazio";
                if ("test"==$prov)echo json_encode($res);
            }

            else if ( strlen($lastname) < 1){
                $res="Il campo cognome non puo' essere vuoto";
                if ("test"==$prov)echo json_encode($res);
            }
            else if ( strlen($lastname) > 50 ){
                $res="Il campo cognome e' troppo lungo";
                if ("test"==$prov)echo json_encode($res);
            }
            else if( !( preg_match($pattern,$lastname) ) ){
                $res="Campo cognome non valido";
                if ("test"==$prov)echo json_encode($res);
            }
            else if(mb_substr($lastname, 0, 1) == ' '){
                $res="Il campo cognome non puo' iniziare con uno spazio";
                if ("test"==$prov)echo json_encode($res);
            }
            else {
                $addedId = addClientToList($name, $lastname, $prSession);
                if ($addedId == -1) {
                    header("location: ../../logout.php");
                    $res="Errore con l'inserimento, ma il formato è corretto. Controlla se le liste sono abilitate";
                    if ("test"==$prov)echo json_encode($res);
                }
                else {
                    $res="Aggiunto nuovo cliente in lista";
                    if ("test"==$prov)echo json_encode($res);
                    else header("location: ../../index.php?addedId=" . $addedId);
                }
            }

        if ($prov!="test")header("location: ../../index.php?errorMessage=" . $res);
    }
?>