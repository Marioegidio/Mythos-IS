<?php
require_once "GenericTest.php";

class TestClientListDAO extends GenericTest
{

    private static $clientDB;
    private static $connessione;
    private static $host = "localhost";
    private static $user = "root";
    private static $password = "";
    private static $db = "mythos_pr_is";
    private static $clientEN;
    private static $clientEN2;
    private static $prEN;
    private static $stmt;
    private static $count = 0;
    private static $allClients;

    public static function setUpBeforeClass(): void
    {
        self::resetClient();

        // mi serve per il cliente.
        self::$prEN = new Pr();
        self::$prEN->setName("Nome");
        self::$prEN->setLastname("Cognome");
        self::$prEN->setPassword("Ciao");
        self::$prEN->setUsername("CognomeNome");
        self::insertPr();

        self::$clientDB = new ClientListDAO();
        self::$allClients = Array();

        // oggetto 1 cliente.
        self::$clientEN = new ClientList();
        self::$clientEN->setName("Cliente1");
        self::$clientEN->setSurname("cognomeCliente1");
        self::$clientEN->setAdded_date("21/01/2020 09:03:32");
        self::$clientEN->setEntered(0);
        self::$clientEN->setPr(self::$prEN);

        // oggetto 2 cliente.
        self::$clientEN2 = new ClientList();
        self::$clientEN2->setName("Cliente2");
        self::$clientEN2->setSurname("cognomeCliente2");
        self::$clientEN2->setAdded_date("21/01/2020 09:03:32");
        self::$clientEN2->setEntered(1);
        self::$clientEN2->setPr(self::$prEN);

    }

    function testDoSave()
    {
        self::$clientDB->doSave(self::$clientEN);

        echo "\n\n********************************************************";
        echo "\ntest doSave() ";
        echo "\n\nOutput atteso: ";
        echo "\n\t 1";
        echo "\nOutput ottenuto: ";
        echo "\n\t " . self::countClients();

        $this->assertEquals(
            1, self::countClients()
        );
    }

    public function testDoRetrieveAll()
    {
        self::$allClients = self::$clientDB->doRetrieveAll();
        $now = date("d/m/Y H:i:s");
        self::$clientEN->setAdded_date($now);

        echo "\n\n********************************************************";
        echo "\ntest doRetrieveAll() ";
        echo "\nOutput atteso: ";
        echo "\n\t " . self::$clientEN->getSurname() . " " . self::$clientEN->getName() . " " . self::$clientEN->isEntered() . " " . self::$clientEN->getAdded_date() . " " . self::$clientEN->getPr()->getUsername();
        echo "\nOutput ottenuto: ";
        echo "\n\t " . self::$allClients[0]->getSurname() . " " . self::$allClients[0]->getName() . " " . self::$allClients[0]->isEntered() . " " . self::$allClients[0]->getAdded_date() . " " . self::$allClients[0]->getPr()->getUsername();

        $this->assertEquals(
            self::$clientEN->getSurname() . " " . self::$clientEN->getName() . " " . self::$clientEN->isEntered() . " " . self::$clientEN->getAdded_date() . " " . self::$clientEN->getPr()->getUsername(),
            self::$allClients[0]->getSurname() . " " . self::$allClients[0]->getName() . " " . self::$allClients[0]->isEntered() . " " . self::$allClients[0]->getAdded_date() . " " . self::$allClients[0]->getPr()->getUsername()
        );


    }

    public function testDoRetrieveByPrId()
    {
        self::$allClients = self::$clientDB->doRetrieveByPrId(self::$prEN->getUsername());
        $now = date("d/m/Y H:i:s");
        self::$clientEN->setAdded_date($now);

        echo "\n\n********************************************************";
        echo "\ntest doRetrieveByPrId(\"" . self::$prEN->getUsername() . "\") ";
        echo "\nOutput atteso: ";
        echo "\n\t " . self::$clientEN->getSurname() . " " . self::$clientEN->getName() . " " . self::$clientEN->isEntered() . " " . self::$clientEN->getAdded_date() . " " . self::$clientEN->getPr()->getUsername();
        echo "\nOutput ottenuto: ";
        echo "\n\t " . self::$allClients[0]->getSurname() . " " . self::$allClients[0]->getName() . " " . self::$allClients[0]->isEntered() . " " . self::$allClients[0]->getAdded_date() . " " . self::$allClients[0]->getPr()->getUsername();

        $this->assertEquals(
            self::$clientEN->getSurname() . " " . self::$clientEN->getName() . " " . self::$clientEN->isEntered() . " " . self::$clientEN->getAdded_date() . " " . self::$clientEN->getPr()->getUsername(),
            self::$allClients[0]->getSurname() . " " . self::$allClients[0]->getName() . " " . self::$allClients[0]->isEntered() . " " . self::$allClients[0]->getAdded_date() . " " . self::$allClients[0]->getPr()->getUsername()
        );
    }

    public function testDoRetrieveByClientId()
    {
        $id = self::lastClientId();

        self::$allClients [] = self::$clientDB->doRetrieveByClientId($id);
        $now = date("d/m/Y H:i:s");
        self::$clientEN->setAdded_date($now);

        echo "\n\n********************************************************";
        echo "\ntest doRetrieveByClientId(\"" . $id . "\") ";
        echo "\nOutput atteso: ";
        echo "\n\t " . self::$clientEN->getSurname() . " " . self::$clientEN->getName() . " " . self::$clientEN->isEntered() . " " . self::$clientEN->getAdded_date() . " " . self::$clientEN->getPr()->getUsername();
        echo "\nOutput ottenuto: ";
        echo "\n\t " . self::$allClients[0]->getSurname() . " " . self::$allClients[0]->getName() . " " . self::$allClients[0]->isEntered() . " " . self::$allClients[0]->getAdded_date() . " " . self::$allClients[0]->getPr()->getUsername();

        $this->assertEquals(
            self::$clientEN->getSurname() . " " . self::$clientEN->getName() . " " . self::$clientEN->isEntered() . " " . self::$clientEN->getAdded_date() . " " . self::$clientEN->getPr()->getUsername(),
            self::$allClients[0]->getSurname() . " " . self::$allClients[0]->getName() . " " . self::$allClients[0]->isEntered() . " " . self::$allClients[0]->getAdded_date() . " " . self::$allClients[0]->getPr()->getUsername()
        );
    }

    public function testDoRetrieveByNameAndLastname()
    {
        self::$allClients = self::$clientDB->doRetrieveByNameAndLastname(self::$prEN->getName(), self::$prEN->getLastname());
        $now = date("d/m/Y H:i:s");
        self::$clientEN->setAdded_date($now);

        echo "\n\n********************************************************";
        echo "\ntest doRetrieveByNameAndLastname(\"" . self::$prEN->getName() . "\",\"" . self::$prEN->getLastname() . "\")";
        echo "\nOutput atteso: ";
        echo "\n\t " . self::$clientEN->getSurname() . " " . self::$clientEN->getName() . " " . self::$clientEN->isEntered() . " " . self::$clientEN->getAdded_date() . " " . self::$clientEN->getPr()->getUsername();
        echo "\nOutput ottenuto: ";
        echo "\n\t " . self::$allClients[0]->getSurname() . " " . self::$allClients[0]->getName() . " " . self::$allClients[0]->isEntered() . " " . self::$allClients[0]->getAdded_date() . " " . self::$allClients[0]->getPr()->getUsername();

        $this->assertEquals(
            self::$clientEN->getSurname() . " " . self::$clientEN->getName() . " " . self::$clientEN->isEntered() . " " . self::$clientEN->getAdded_date() . " " . self::$clientEN->getPr()->getUsername(),
            self::$allClients[0]->getSurname() . " " . self::$allClients[0]->getName() . " " . self::$allClients[0]->isEntered() . " " . self::$allClients[0]->getAdded_date() . " " . self::$allClients[0]->getPr()->getUsername()
        );
    }

    public function testDoRetrieveLastClientIdByPrId()
    {
        $lastInsertId = self::$clientDB->doRetrieveLastClientIdByPrId(self::$prEN->getUsername());
        $now = date("d/m/Y H:i:s");
        self::$clientEN->setAdded_date($now);

        echo "\n\n********************************************************";
        echo "\ntest doRetrieveLastClientIdByPrId(\"" . self::$prEN->getUsername() . "\")";
        echo "\nOutput atteso: ";
        echo "\n\t 1";
        echo "\nOutput ottenuto: ";
        echo "\n\t " . $lastInsertId;

        $this->assertEquals(
            1, $lastInsertId
        );
    }

    public function testDoUpdate()
    {
        self::$clientEN->setClient_id(self::lastClientId());
        self::$clientEN->setEntered(1);
        self::$clientDB->doUpdate(self::$clientEN);

        self::$allClients = self::$clientDB->doRetrieveAll();

        echo "\n\n********************************************************";
        echo "\ntest doUpdate()";
        echo "\nOutput atteso: ";
        echo "\n\t " . self::$clientEN->getSurname() . " " . self::$clientEN->getName() . " " . self::$clientEN->isEntered() . " " . self::$clientEN->getAdded_date() . " " . self::$clientEN->getPr()->getUsername();
        echo "\nOutput ottenuto: ";
        echo "\n\t " . self::$allClients[0]->getSurname() . " " . self::$allClients[0]->getName() . " " . self::$allClients[0]->isEntered() . " " . self::$allClients[0]->getAdded_date() . " " . self::$allClients[0]->getPr()->getUsername();

        $this->assertEquals(
            self::$clientEN->getSurname() . " " . self::$clientEN->getName() . " " . self::$clientEN->isEntered() . " " . self::$clientEN->getAdded_date() . " " . self::$clientEN->getPr()->getUsername(),
            self::$allClients[0]->getSurname() . " " . self::$allClients[0]->getName() . " " . self::$allClients[0]->isEntered() . " " . self::$allClients[0]->getAdded_date() . " " . self::$allClients[0]->getPr()->getUsername()
        );
    }

    public function testDoDeleteById()
    {

        self::$clientEN->setEntered(1);
        self::$clientDB->doDeleteById(self::$clientEN->getClient_id());

        self::countClients();

        echo "\n\n********************************************************";
        echo "\ntest doDeleteById(\"" . self::$clientEN->getClient_id() . "\")";
        echo "\nOutput atteso: ";
        echo "\n\t 0";
        echo "\nOutput ottenuto: ";
        echo "\n\t " . self::$count;

        $this->assertEquals(
            0, self::$count
        );
    }

    public function testDoClear()
    {

        self::$clientEN->setEntered(1);
        self::$clientDB->doSave(self::$clientEN);
        self::$clientDB->doSave(self::$clientEN2);

        $prima = self::countClients();
        self::$clientDB->doClear();
        self::countClients();

        echo "\n\n********************************************************";
        echo "\ntest doClear()";
        echo "\nOutput atteso: ";
        echo "\n\t Prima-> " . $prima . " Dopo->0";
        echo "\nOutput ottenuto: ";
        echo "\n\t Prima-> " . $prima . " Dopo->" . self::$count;

        $this->assertEquals(
            0, self::$count
        );
    }

    public static function tearDownAfterClass(): void
    {
        self::$clientDB->doSave(self::$clientEN);
    }

    private static function insertPr()
    {
        self::conn();
        self::$stmt = self::$connessione->prepare("INSERT INTO pr(username,password,name,lastname)
                                                 VALUES(?, ?, ?, ?)");

        $username = self::$prEN->getUsername();
        $password = self::$prEN->getPassword();
        $name = self::$prEN->getName();
        $lastname = self::$prEN->getLastname();

        self::$stmt->bind_param("ssss", $username, $password, $name, $lastname);
        self::$stmt->execute();
        self::$connessione->close();
    }

    private static function countClients()
    {
        self::conn();
        self::$count = 0;

        $query = "SELECT COUNT(*) AS totClient FROM client_lists";
        $result = self::$connessione->query($query);

        while ($row = $result->fetch_array(MYSQLI_ASSOC)) {
            self::$count = $row["totClient"];
        }

        $result->close();
        self::$connessione->close();

        return self::$count;
    }

    private static function lastClientId()
    {
        self::conn();

        //prelevo l'id inserito
        $id = 0;
        $query = "SELECT client_id 
                        FROM client_lists
                        LIMIT 1";
        $result = self::$connessione->query($query);
        while ($row = $result->fetch_array(MYSQLI_ASSOC)) {
            $id = ($row["client_id"]);
        }
        $result->close();
        self::$connessione->close();

        return $id;

    }

    private static function resetClient()
    {
        self::conn();

        $query = "SET FOREIGN_KEY_CHECKS = 0";
        self::$connessione->query($query);

        $query = "TRUNCATE TABLE client_lists";
        self::$connessione->query($query);

        $query = "SET FOREIGN_KEY_CHECKS = 1";
        self::$connessione->query($query);

        self::$connessione->close();
    }

    private static function conn()
    {
        self::$connessione = new mysqli(self::$host, self::$user, self::$password, self::$db);
        self::$connessione->set_charset("utf8");

        if (self::$connessione->connect_errno) {
            echo "Connessione fallita: " . self::$connessione->connect_error . ".";
            exit();
        }
    }

}

?>