<?php
    /**
 * Pagina php che permette di effettuare il login.
 */

    session_start();
    session_destroy();
    session_start();

    require_once $_SERVER["DOCUMENT_ROOT"] . "/mythos_pr/src/manager/PrManager.php";
    require_once $_SERVER["DOCUMENT_ROOT"] . "/mythos_pr/src/manager/ClientsManager.php";

    //echo $_SERVER["HTTP_REFERER"];

    if(!$_POST)  header("location: ../../login.html");
    else{
        $user=$_POST["user"];
        $pass=$_POST["pass"];
        $prov="";
        if ( isset($_POST["prov"])) $prov=$_POST["prov"];

        if ( strlen($user) < 1){
            $res="Il campo username non puo' essere vuoto";
            if ("test"==$prov)echo json_encode($res);
        }
        else if ( strlen($pass) < 1){
            $res="Il campo password non puo' essere vuoto";
            if ("test"==$prov)echo json_encode($res);
        }
        else{

            $opt=listStatus();

            if($opt->getFlagList()==0){
                $res="Liste disabilitate";
                if ("test"==$prov)echo json_encode($res);
                else header("location:  ../../disabled.html");
            }
            else {
                $pr=checkPr($user,$pass);
                if($pr!=null){
                    $_SESSION["logUser"]=$pr;
                    if ("test"==$prov)echo "Login ok";
                    else header("location: ../../index.php");
                }else
                    if ("test"==$prov)echo "Username o password errati";
                    else header("location:  ../../errati.html");
            }
        }

    }
?>