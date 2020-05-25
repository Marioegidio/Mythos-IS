<?php
    /**
     * Classe per memorizzare le opzioni della lista clienti.
     */
	class ListOption implements JsonSerializable {
        /**
         * @return array|mixed
         */
        public function jsonSerialize()
		{
			return array(
				 'flagList' => $this->flagList
			);
		}

        /**
         * Metodo che restituisce lo stato delle liste.
         * @return bool
         */
        public function getFlagList() {
			return $this->flagList;
		}

        /**
         * Metodo che setta lo stato delle liste.
         * @param $flagList bool indica il nuovo stato delle liste.
         */
        public function setFlagList($flagList) {
			$this->flagList = $flagList;
		}

        /**
         * @var $flagList bool indica lo stato delle liste.
         */
        private $flagList;
	}
?>