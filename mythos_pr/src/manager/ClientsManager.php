<?php
    /**
 * Pagina gestore che offre servizi riguardanti la lista clienti.
 */

    require_once $_SERVER["DOCUMENT_ROOT"] . "/mythos_pr/src/DAO/ListOptionDAO.php";
    require_once $_SERVER["DOCUMENT_ROOT"] . "/mythos_pr/src/entity/ListOption.php";
    require_once $_SERVER["DOCUMENT_ROOT"] . "/mythos_pr/src/DAO/ClientListDAO.php";
    require_once $_SERVER["DOCUMENT_ROOT"] . "/mythos_pr/src/entity/ClientList.php";
    require_once $_SERVER["DOCUMENT_ROOT"] . "/mythos_pr/src/DAO/PrDAO.php";
    require_once $_SERVER["DOCUMENT_ROOT"] . "/mythos_pr/src/entity/Pr.php";

    /**
     * Servizio per abilitare o disabilitare le liste.
     * Abilita le liste se sono disabilitate.
     * Disabilita le liste se sono abilitate.
     * @return bool restituisce l'esito dell'operazione.
     */
    function enableOrDisableList(){
        $optionDao=new ListOptionDAO();
        $option=$optionDao->doRetrieveAll();
        if($option->getFlagList()==0)
            $option->setFlagList(1);
        else
            $option->setFlagList(0);
        if($optionDao->doUpdate($option))
            return 1;
        else
            return 0;
    }

    /**
     * Servizio per azzerare le liste.
     * @return bool restituisce l'esito dell'operazione.
     */
    function clearOrders(){
        $clientListDao=new ClientListDAO();
        return $clientListDao->doClear();
    }

    /**
     * Servizio per modificare lo stato di un cliente.
     * @param $clientId int $clientId=!null && $clientId>0 indica l'id del cliente
     * @return bool restituisce l'esito della modifica.
     */
    function editClientListStatus($clientId){
        $clientListDao=new ClientListDAO();
        $client=$clientListDao->doRetrieveByClientId($clientId);
        //setto che l 'utente è entrato
        $client->setEntered(1);
        return $clientListDao->doUpdate($client);
    }

    /**
     * Servizio che restituisce la lista di clienti avente nome e cognome o del p.r. o del cliente.
     * @param $prName string indica il nome.
     * @param $prLastname string indica il cognome.
     * @return array
     */
    function clientListByPr($prName,$prLastname){
        $clientListDao=new ClientListDAO();
        return $clientListDao->doRetrieveByNameAndLastname($prName,$prLastname);
    }

    /**
     * Servizio per inserire un cliente in lista.
     * @param $name string indica il nome del cliente.
     * @param $lastname string indica il cognome del cliente.
     * @param $prSession Pr incica il p.r.
     * @return int indica l'id del cliente inserito. (-1 se l'inserimento non è andato a buon fine).
     */
    function addClientToList($name,$lastname,$prSession)
    {
        //controllo che le liste non siano disabilitate
        $optionDao = new ListOptionDAO();
        $opt = $optionDao->doRetrieveAll();

        if ($opt->getFlagList()==0) {
            header("location: " . $_SERVER["DOCUMENT_ROOT"] . "/mythos_pr/logout.php");
            return -1;
            //fine controllo******************************
        } else {
            $client = new ClientList();
            $client->setName($name);
            $client->setSurname($lastname);
            $client->setPr($prSession);
            $clientDAO = new ClientListDAO();
            $clientDAO->doSave($client);
            return $clientDAO->doRetrieveLastClientIdByPrId($prSession->getUsername());
        }
    }

    /**
     * Servizio per rimuovere un cliente dalla lista.
     * @param $id int indica l'id del cliente.
     */
    function removeClientFromList($id){
        $clientDAO=new ClientListDAO();
        $clientDAO->doDeleteById($id);
    }

    /**
     * Servizio per ottere la lista clienti.
     * @return array
     */
    function clientList(){
        $clientListDao=new ClientListDAO();
        return $clientListDao->doRetrieveAll();
    }

    /**
     * Servizio per cercare un cliente in lista.
     * @param $id int indica l'id del cliente.
     * @return ClientList
     */
    function searchClient($id){
        $clientListDao=new ClientListDAO();
        $listClient[]=($clientListDao->doRetrieveByClientId($id));
        return $listClient;
    }

    /**
     * Servizio per ottenre lo stato delle liste.
     * @return ListOption|null
     */
    function listStatus(){
        $optionDao=new ListOptionDAO();
        return $optionDao->doRetrieveAll();
    }

?>