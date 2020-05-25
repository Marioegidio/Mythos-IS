<?php
    /**
     * Classe per memorizzare i dati riguardanti i Pr.
     */
	class Pr implements JsonSerializable {
        /**
         * @return array|mixed
         */
        public function jsonSerialize()
		{
			return array(
				 'username' => $this->username,
				 'password' => $this->password,
				 'name' => $this->name,
				 'lastname' => $this->lastname
			);
		}

        /**
         * Metodo che restituisce l'username di un p.r.
         * @return string
         */
        public function getUsername() {
			return $this->username;
		}

        /**
         * Metodo che restituisce la password del p.r.
         * @return string
         */
        public function getPassword() {
			return $this->password;
		}

        /**
         * Metodo che restituisce il nome del p.r.
         * @return string
         */
        public function getName() {
			return $this->name;
		}

        /**
         * Metodo che restituisce il cognome del p.r.
         * @return string
         */
        public function getLastname() {
			return $this->lastname;
		}

        /**
         * Metodo che setta l'username del p.r.
         * @param $username string indica il nuovo username del p.r.
         */
        public function setUsername($username) {
			$this->username = $username;
		}

        /**
         * Metodo che setta la password del p.r.
         * @param $password string indica la nuova password del p.r.
         */
        public function setPassword($password) {
			$this->password = $password;
		}

        /**
         * Metodo che setta il nome del p.r.
         * @param $name string indica il nuovo nome del p.r.
         */
        public function setName($name) {
			$this->name = $name;
		}

        /**
         * Metodo che setta il cognnome del p.r.
         * @param $lastname string indica il  nuovo cognome del p.r.
         */
        public function setLastname($lastname) {
			$this->lastname = $lastname;
		}

        /**
         * @var $username string indica l'usenrame del p.r.
         */
        private $username;
        /**
         * @var $password string indica la password del p.r.
         */
        private $password;
        /**
         * @var $name string indica il nome del p.r.
         */
        private $name;
        /**
         * @var $lastname string indica il cognome del p.r.
         */
        private $lastname;
	}
?>