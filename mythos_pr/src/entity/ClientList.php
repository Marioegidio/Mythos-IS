<?php
    /**
     * Classe per memorizzare i dati dei clienti in lista.
     * Per tutte le istanze di ClienList $client_id>0
     */
	class ClientList implements JsonSerializable {
        /**
         * @return array|mixed
         */
        public function jsonSerialize()
		{
			return array(
				 'client_id' => $this->client_id,
				 'name' => $this->name,
				 'surname' => $this->surname,
                 'entered' => $this->entered,
                 'added_date' => $this->added_date,
                 'pr' => $this->pr
			);
		}

        /**
         * Metodo che restituisce l'id del cliente.
         * @return int
         */
        public function getClient_id() {
			return $this->client_id;
		}

        /**
         * Metodo che restituisce il nome del cliente.
         * @return string
         */
        public function getName() {
			return $this->name;
        }

        /**
         * Metodo che restituisce il cognome del cliente.
         * @return string
         */
        public function getSurname() {
			return $this->surname;
        }

        /**
         * Metodo che restituisce lo stato del cliente.
         * @return bool
         */
        public function isEntered() {
			return $this->entered;
        }

        /**
         * Metodo che restituisce la data di inserimento in lista del cliente.
         * @return string
         */
        public function getAdded_date() {
			return $this->added_date;
        }

        /**
         * Metodo che restituisce il p.r. che ha inserito in lista il cliente.
         * @return Pr
         */
        public function getPr() {
			return $this->pr;
        }

        /**
         * Metodo che setta l'id del cliente.
         * @param $client_id int indica il nuovo id del cliente.
         */
        public function setClient_id($client_id) {
			$this->client_id=$client_id;
		}

        /**
         * Metodo che setta il nome del cliente.
         * @param $name string indica il nuovo nome del cliente.
         */
        public function setName($name) {
			$this->name=$name;
        }

        /**
         * Metodo che setta il cognome del cliente.
         * @param $surname string indica il nuovo cognome del cliente.
         */
        public function setSurname($surname) {
			$this->surname=$surname;
        }

        /**
         * Metodo che setta lo stato del cliente del cliente.
         * @param $entered bool indica il nuovo stato del cliente.
         */
        public function setEntered($entered) {
			$this->entered=$entered;
        }

        /**
         * Metodo che setta la data di inserimento in lista del cliente.
         * @param $added_date string indica la nuova data.
         */
        public function setAdded_date($added_date) {
			$this->added_date=$added_date;
        }

        /**
         * Metodo che setta il p.r. che ha inserito in lista il cliente.
         * @param $pr Pr indica il nuovo p.r.
         */
        public function setPr($pr) {
		    $this->pr=$pr;
		}

        /**
         * @var $client_id int indica l'id del cliente.
         */
        private $client_id;
        /**
         * @var $name string indica il nome del cliente.
         */
        private $name;
        /**
         * @var $surname indica il cognome del cliente.
         */
        private $surname;
        /**
         * @var $entered bool  indica lo stato del cliente.
         */
        private $entered;
        /**
         * @var $added_date string  indica la data di inserimento in lista del cliente.
         */
        private $added_date;
        /**
         * @var $pr Pr  indica il p.r. che ha inserito in lista il cliente.
         */
        private $pr;
	}
?>