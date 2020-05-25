<?php
    /**
 * Pagina php che offre il servizio di inserimento di un Pr.
 */

    require_once $_SERVER["DOCUMENT_ROOT"] ."/mythos_pr/src/manager/PrManager.php";
    session_start();

    $res="";
    $password=$_GET["password"];
    $name=str_replace('_', ' ', ucwords($_GET["name"]));
    $lastname=str_replace('_', ' ',ucwords($_GET["lastname"]));
    $username= str_replace(' ', '', $name).str_replace(' ', '', $lastname);


    $pattern        ="/^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ']+$/u";
    $passwordPattern="/^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð0123456789 ,.'-]+$/u";
    $patternPasswordFormat="/^(?=.*?[A-Za-z])(?=.*?[0-9])(?=.*?[,.-]).{3,}\S+$/u";
    /*
     * Se voglio verificare che ci sia una lettera maiuscola, una minuscola,un numero, un carattere
     * "/^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{4,}\S+$/u";
     */

    if ( strlen($name) < 1){
        $res="Il campo nome non puo' essere vuoto";
    }
    else if ( strlen($name) > 50 ){
        $res="Il campo nome e' troppo lungo";
    }
    else if( !( preg_match($pattern,$name) ) ){
        $res="Campo nome non valido";
    }
    else if(mb_substr($name, 0, 1) == ' '){
        $res="Il campo nome non puo' iniziare con uno spazio";
    }

    else if ( strlen($lastname) < 1){
        $res="Il campo cognome non puo' essere vuoto";
    }
    else if ( strlen($lastname) > 50 ){
        $res="Il campo cognome e' troppo lungo";
    }
    else if( !( preg_match($pattern,$lastname) ) ){
        $res="Campo cognome non valido";
    }
    else if(mb_substr($lastname, 0, 1) == ' '){
        $res="Il campo cognome non puo' iniziare con uno spazio";
    }

    else if ( strlen($password) < 1){
        $res="Il campo password non puo' essere vuoto";
    }
    else if ( strlen($password) > 50 ){
        $res="Il campo password e' troppo lungo";
    }
    else if( !( preg_match($passwordPattern,$password) ) ){
        $res="Campo password non valido";
    }
    else if(!( preg_match($patternPasswordFormat,$password)) ){
        $res="Il campo password non contiene almeno una lettera, un numero e un carattere speciale";
    }

    else if (addPR($username,$password,$name,$lastname))
        $res="Pr inserito correttamente";
    else $res="Il Pr gia' e' stato memorizzato in precedenza";

    echo json_encode($res);

?>