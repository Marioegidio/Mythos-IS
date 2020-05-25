<?php
    /**
 * Pagina gestore che offre servizi riguardanti i Pr.
 */

    require_once $_SERVER["DOCUMENT_ROOT"] . "/mythos_pr/src/DAO/PrDAO.php";
    require_once $_SERVER["DOCUMENT_ROOT"] . "/mythos_pr/src/entity/Pr.php";

    /**
     * Servizio per aggiungere un p.r.
     * @param $username string indica l'username del p.r.
     * @param $password string indica la password del p.r.
     * @param $name string indica il nome del p.r.
     * @param $lastname string indica il cognome del p.r.
     * @return bool restituisce l'esito dell'inserimento.
     */
    function addPR($username,$password,$name,$lastname){
        $pr=new Pr();
        $pr->setUsername($username);
        $pr->setPassword($password);
        $pr->setName($name);
        $pr->setLastname($lastname);
        $prDAO=new PrDAO();

        return $prDAO->doSave($pr);
    }

    /**
     * Servizio per eliminare un p.r.
     * @param $username string indica l'username del p.r.
     * @return bool restituisce l'esito dell'eliminazione.
     */
    function deletePR($username){
        $prDAO=new PrDAO();
        return $prDAO->doDeleteById($username);
    }

    /**
     * Servizio che restituisce la lista di p.r.
     * @return array
     */
    function prList(){
        $prDao=new PrDAO();
        return $prDao->doRetrieveAll();
    }

    /**
     * Servizio per verificare le credenziali di un p.r.
     * @param $user string indica l'username del p.r.
     * @param $pass string indica la password del p.r.
     * @return Pr|null
     */
    function checkPr($user, $pass){
        $prDao=new PrDAO();
        return $prDao->doRetrieveByUserAndPassword($user,$pass);
    }
?>
