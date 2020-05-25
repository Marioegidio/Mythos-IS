<?php
    if($_SERVER["DOCUMENT_ROOT"]=="")
        require_once "../entity/ClientList.php";
    else
        require_once $_SERVER["DOCUMENT_ROOT"] . "/mythos_pr/src/entity/ClientList.php";

    /**
     * Classe che si occupa di prelevare i dati dei clienti in ista dallo storage.
     */
    class clientListDAO{
        /**
         * @var $connessione mysqli viene memorizzarato lo stato della connessione.
         */
        private $connessione;

        /**
         * Metodo per aprire la connessione con il database.
         */
        private function openConnection(){
            if($_SERVER["DOCUMENT_ROOT"]=="")
                require "../utility/connection.php";
            else
                require $_SERVER["DOCUMENT_ROOT"] . "/mythos_pr/src/utility/connection.php";

            $this->connessione=$connessione;
        }

        /**
         * Metodo per chiudere la connessione con il database.
         */
        private function closeConnection(){
            $this->connessione->close();
        }

        /**
         * Metodo che dato l'id di un p.r. restituisce tutti i suoi clienti in lista.
         * @param $id string indica l'id del p.r. -> $id!= null
         * @return array restituisce l'elenco dei clienti in lista del p.r. con id uguale a $id.
         */
        public function doRetrieveByPrId($id){
            $this->openConnection();
            $prDao=new PrDAO();
            $listClient = [];
            $query="SELECT *
                        FROM client_lists
                        WHERE pr=\"$id\"
                        ORDER BY surname,name";
            $result = $this->connessione->query($query);

            while($row = $result->fetch_array(MYSQLI_ASSOC))
            {
                $cList=new ClientList();
                $cList->setClient_id($row["client_id"]);
                $cList->setName($row["name"]);
                $cList->setSurname($row["surname"]);
                $cList->setEntered($row["entered"]);
                $cList->setAdded_date($row["added_date"]);+
            $cList->setPr($prDao->doRetrieveById($row["pr"]));
                $listClient[]=$cList;
            }
            $result->close();
            $this->closeConnection();

            return $listClient;
        }

        /**
         * Metodo che dato l'id di un cliente restituisce tutti i suoi dati.
         * @param $id int indica l'id del cliente -> $id!= null && $id>0
         * @return ClientList restituisce il cliente con id uguale a $id.
         */
        public function doRetrieveByClientId($id){
            $this->openConnection();
            $prDao=new PrDAO();

            $query="SELECT *
                        FROM client_lists
                        WHERE client_id='$id'";
            $result = $this->connessione->query($query);

            while($row = $result->fetch_array(MYSQLI_ASSOC))
            {
                $client=new ClientList();
                $client->setClient_id($row["client_id"]);
                $client->setName($row["name"]);
                $client->setSurname($row["surname"]);
                $client->setEntered($row["entered"]);
                $client->setAdded_date($row["added_date"]);+
            $client->setPr($prDao->doRetrieveById($row["pr"]));
            }
            $result->close();
            $this->closeConnection();

            return $client;
        }

        /**
         * Metodo che restituisce tutti i clienti in lista ordinati per Cognome,Nome.
         * @return array restituisce la lista clienti.
         */
        public function doRetrieveAll(){
            $this->openConnection();
            $prDao=new PrDAO();
            $listClient = [];
            $query="SELECT client_lists.*
                        FROM client_lists
                        ORDER BY surname,name";
            $result = $this->connessione->query($query);

            while($row = $result->fetch_array(MYSQLI_ASSOC))
            {
                $cList=new ClientList();
                $cList->setClient_id($row["client_id"]);
                $cList->setName($row["name"]);
                $cList->setSurname($row["surname"]);
                $cList->setEntered($row["entered"]);
                $cList->setAdded_date($row["added_date"]);+
                $cList->setPr($prDao->doRetrieveById($row["pr"]));
                $listClient[]=$cList;
            }
            $result->close();
            $this->closeConnection();
            return $listClient;
        }

        /**
         * Metodo che restituisce l'elenco clienti in base al nome e al cognome del cliente o p.r.
         * @param $name string $name!=null indica il nome del cliente o p.r.
         * @param $lastname string $lastname!=null indica il cognome del cliente o p.r.
         * @return array restituisce la lista clienti.
         */
        public function doRetrieveByNameAndLastname($name,$lastname){
            $this->openConnection();
            $prDao=new PrDAO();
            $listClient = [];
            $query="SELECT client_lists.*
                        FROM client_lists,pr
                        WHERE username=pr AND 
                                        ((pr.name LIKE \"%$name%\" AND pr.lastname LIKE \"%$lastname%\") OR 
                                        (client_lists.name LIKE \"%$name%\" AND client_lists.surname LIKE \"%$lastname%\"))
                        ORDER BY surname,name";
            $result = $this->connessione->query($query);

            while($row = $result->fetch_array(MYSQLI_ASSOC))
            {
                $cList=new ClientList();
                $cList->setClient_id($row["client_id"]);
                $cList->setName($row["name"]);
                $cList->setSurname($row["surname"]);
                $cList->setEntered($row["entered"]);
                $cList->setAdded_date($row["added_date"]);+
            $cList->setPr($prDao->doRetrieveById($row["pr"]));
                $listClient[]=$cList;
            }
            $result->close();
            $this->closeConnection();

            return $listClient;
        }

        /**
         * Metodo che restituisce l'ultimo cliente inserito da un p.r.
         * @param $prUsername string $prUsername !=null indica l'id del p.r.
         * @return int restituisce l'id dell'ultimo cliente inserito (-1 se non va a buon fine la query).
         */
        public function doRetrieveLastClientIdByPrId($prUsername){
            $this->openConnection();
            $id=-1;
            $prDao=new PrDAO();
            $query="SELECT MAX(client_id) as id
                        FROM client_lists
                        WHERE pr='$prUsername'";
            $result = $this->connessione->query($query);

            while($row = $result->fetch_array(MYSQLI_ASSOC))
            {
                $id=$row["id"];
            }
            $result->close();
            $this->closeConnection();

            return $id;
        }

        /**
         * Metodo per salvare un cliente nella lista.
         * @param $client ClientList $client!=null indica i dati del cliente, compreso il p.r. che lo ha inserito.
         * @return bool restituisce l'esito dell'inserimento.
         */
        public function doSave($client){
            $this->openConnection();
            $now=date("d/m/Y H:i:s");
            $stmt = $this->connessione->prepare("INSERT INTO client_lists(name,surname,added_date,pr)
                                                     VALUES(?, ?, ?, ?)");

            $name=$client->getName();
            $surname=$client->getSurname();
            $pr=$client->getPr()->getUsername();

            $stmt->bind_param("ssss",$name,$surname,$now,$pr);

            if($stmt->execute()){
                return true;
                $this->closeConnection();
            }
            else{
                return false;
                $this->closeConnection();
            }

        }

        /**
         * Matodo per aggiornare i campi di un cliente
         * @param $client ClientList $client!=null indica i dati del cliente, compreso il p.r. che lo ha inserito.
         * @return bool restituisce l'esito della modifica
         */
        public function doUpdate($client){
            $this->openConnection();
            $stmt = $this->connessione->prepare("UPDATE client_lists
                                                     SET name=?,surname=?,entered=?,added_date=?,pr=?
                                                     WHERE client_id=?");

            $name=$client->getName();
            $surname=$client->getSurname();
            $entered=$client->isEntered();
            $date=$client->getAdded_date();
            $prId=$client->getPr()->getUsername();
            $clientId=$client->getClient_id();

            $stmt->bind_param("ssssss",$name,$surname,$entered,$date,$prId,$clientId);

            if($stmt->execute())
                return true;
            else
                return false;

            $this->closeConnection();
        }

        /**
         * Metodo per eliminare un cliente dalla lista conoscendo l'id.
         * @param $id int indica l'id del cliente -> $id!= null && $id>0.
         */
        public function doDeleteById($id){
            $this->openConnection();
            $query="DELETE FROM client_lists
                        WHERE client_id=$id";

            $result = $this->connessione->query($query);

            $this->closeConnection();
        }

        /**
         * Metotdo per azzerare la tabella client_list.
         * @return bool restituisce l'esisto della cancellazione.
         */
        public function doClear(){
            $this->openConnection();
            $query="TRUNCATE TABLE client_lists";
            $result=$this->connessione->query($query);
            $this->closeConnection();
            return $result;

        }
    }
?>