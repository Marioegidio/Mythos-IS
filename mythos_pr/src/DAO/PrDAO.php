<?php
    if($_SERVER["DOCUMENT_ROOT"]=="")
        require_once "../entity/Pr.php";
    else
        require_once $_SERVER["DOCUMENT_ROOT"] . "/mythos_pr/src/entity/Pr.php";

    /**
     * Classe che si occupa di prelevare i dati dei Pr dallo storage.
     */
    class PrDAO {

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
         * Metodo che restituisce un p.r. avendo username e password.
         * @param $usern string  $usern!=null indica l'username del p.r.
         * @param $pass string $pass!=null indica la password del p.r.
         * @return Pr|null restituisce il p.r. se esiste oppure null se non esiste.
         */
        public function doRetrieveByUserAndPassword($usern, $pass){
            $this->openConnection();
            $user=null;

            $query="SELECT *
                    FROM pr
                    WHERE username=\"$usern\"";
            $result = $this->connessione->query($query);

            while($row = $result->fetch_array(MYSQLI_ASSOC))
            {
                if($row["password"]==$pass){
                    $user=new Pr();
                    $user->setUsername($row["username"]);
                    $user->setPassword($pass);
                    $user->setName($row["name"]);
                    $user->setLastname($row["lastname"]);
                }
            }
            $result->close();
            $this->closeConnection();

            return $user;
        }

        /**
         * Medoto che restituisce tutti i p.r.
         * @return array restituisce l'elenco dei p.r.
         */
        public function doRetrieveAll(){
            $this->openConnection();
            $user=null;
            $listpr = [];

            $query="SELECT *
                    FROM pr";
            $result = $this->connessione->query($query);

            while($row = $result->fetch_array(MYSQLI_ASSOC))
            {
                    $user=new Pr();
                    $user->setUsername($row["username"]);
                    $user->setPassword($row["password"]);
                    $user->setName($row["name"]);
                    $user->setLastname($row["lastname"]);
                    $listpr[]=$user;
            }

            $result->close();
            $this->closeConnection();

            return $listpr;
        }

        /**
         * Metodo che restituisce un p.r. conoscendo l'username.
         * @param $id string $id=!null indica l'username del p.r.
         * @return Pr|null restituisce il p.r. con tutti i suoi dati.
         */
        public function doRetrieveById($id){
            $this->openConnection();
            $user=null;

            $query="SELECT *
                    FROM pr
                    WHERE username=\"$id\"";
            $result = $this->connessione->query($query);

            while($row = $result->fetch_array(MYSQLI_ASSOC))
            {
                    $user=new Pr();
                    $user->setUsername($row["username"]);
                    $user->setPassword($row["password"]);
                    $user->setName($row["name"]);
                    $user->setLastname($row["lastname"]);
            }
            $result->close();
            $this->closeConnection();

            return $user;
        }

        /**
         * Metodo per salvare un p.r.
         * @param $pr Pr indica il p.r. da memorizzare.
         * @return bool restituisce l'esito dell'inserimento.
         */
        public function doSave($pr){
            $this->openConnection();
            $stmt = $this->connessione->prepare("INSERT INTO pr(username,password,name,lastname)
                                                 VALUES(?, ?, ?, ?)");

            $username=$pr->getUsername();
            $password=$pr->getPassword();
            $name=$pr->getName();
            $lastname=$pr->getLastname();


            $stmt->bind_param("ssss",$username,$password,$name,$lastname);

            if($stmt->execute()){
                $this->closeConnection();
                return true;
            }
            else{
                $this->closeConnection();
                return false;
            }
        }

        /**
         * Metodo per eliminare un p.r. conoscendo l'id.
         * @param $pr string indica l'username del p.r.
         * @return bool restituisce l'esito dlla cancellazione.
         */
        public function doDeleteById($pr){
            $this->openConnection();
            $user=null;

            $query="DELETE FROM pr
                    WHERE username=\"$pr\"";

            if($this->connessione->query($query)){
                $this->closeConnection();
                return true;
            }
            else{
                $this->closeConnection();
                return false;
            }
        }
    }

?>