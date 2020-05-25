<?php
    if($_SERVER["DOCUMENT_ROOT"]=="")
        require_once "../entity/ListOption.php";
    else
        require_once $_SERVER["DOCUMENT_ROOT"] . "/mythos_pr/src/entity/ListOption.php";
    /**
     * Classe che si occupa di prelevare i dati delle opzioni della lista dallo storage.
     */
	class ListOptionDAO{

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
         * Metodo che restiruisce le opioni della lista (abilitata/disabilitata).
         * @return ListOption|null
         */
        public function doRetrieveAll(){
			$this->openConnection();
			$options=null;

			$query="SELECT flagList
					FROM options";
			$result = $this->connessione->query($query);
		   
			while($row = $result->fetch_array(MYSQLI_ASSOC))
			{   
					$options=new ListOption();
					$options->setFlagList($row["flagList"]);
			}
	
			$result->close();
			$this->closeConnection();
	
			return $options;
		}

        /**
         * Matodo per aggiornare i campi delle opzioni della lista.
         * @param $option ListOption indica le opzioni della lista.
         * @return bool restituisce l'esito della modifica.
         */
        public function doUpdate($option){
			$this->openConnection();
		   
			$stmt= $this->connessione->prepare("UPDATE options
												SET flagList=?");
			$flag=$option->getFlagList();

			$stmt->bind_param("s",$flag);
			if($stmt->execute()){
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