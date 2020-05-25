<?php
require_once "GenericTest.php";

class TestPrDAO extends GenericTest
{

    private static $prDB;
    private static $connessione;
    private static $host = "localhost";
    private static $user = "root";
    private static $password = "";
    private static $db = "mythos_pr_is";
    private static $prEN;
    private static $prByUsernamePassword;
    private static $prByUsername;
    private static $count = 0;
    private static $allPr;

    public static function setUpBeforeClass(): void
    {
        self::resetPr();

        self::$prDB = new PrDAO();
        self::$allPr = Array();
        self::$prEN = new Pr();
        self::$prByUsername = new Pr();
        self::$allPr = Array();

        self::$prEN->setName("Nome");
        self::$prEN->setLastname("Cognome");
        self::$prEN->setPassword("Ciao");
        self::$prEN->setUsername("CognomeNome");

    }

    function testDoSave()
    {
        $this->assertTrue(self::$prDB->doSave(self::$prEN));

        echo "\n\n********************************************************";
        echo "\ntest testDoSave() ";
        echo "\n\nOutput atteso: ";
        echo "\n\t 1";
        echo "\nOutput ottenuto: ";
        echo "\n\t " . self::countPrs();

        $this->assertEquals(
            1, self::countPrs()
        );
    }

    public function testDoRetrieveByUserAndPassword()
    {
        self::$prByUsernamePassword = self::$prDB->doRetrieveByUserAndPassword(self::$prEN->getUsername(), self::$prEN->getPassword());

        echo "\n\n********************************************************";
        echo "\ntest doRetrieveByUserAndPassword(\"CognomeNome\",\"Ciao\") ";
        echo "\n\nOutput atteso: ";
        echo "\n\t " . self::$prEN->getLastname() . " " . self::$prEN->getName() . " " . self::$prEN->getUsername() . " " . self::$prEN->getPassword() . " ";
        echo "\nOutput ottenuto: ";
        echo "\n\t " . self::$prByUsernamePassword->getLastname() . " " . self::$prByUsernamePassword->getName() . " " . self::$prByUsernamePassword->getUsername() . " " . self::$prByUsernamePassword->getPassword() . " ";

        $this->assertEquals(
            self::$prEN->getLastname() . " " . self::$prEN->getName() . " " . self::$prEN->getUsername() . " " . self::$prEN->getPassword() . " ",
            self::$prByUsernamePassword->getLastname() . " " . self::$prByUsernamePassword->getName() . " " . self::$prByUsernamePassword->getUsername() . " " . self::$prByUsernamePassword->getPassword() . " "
        );
    }

    public function testDoRetrieveById()
    {
        self::$prByUsername = self::$prDB->doRetrieveById(self::$prEN->getUsername());

        echo "\n\n********************************************************";
        echo "\ntest doRetrieveById(\"CognomeNome\") ";
        echo "\n\nOutput atteso: ";
        echo "\n\t " . self::$prEN->getLastname() . " " . self::$prEN->getName() . " " . self::$prEN->getUsername() . " " . self::$prEN->getPassword() . " ";
        echo "\nOutput ottenuto: ";
        echo "\n\t " . self::$prByUsername->getLastname() . " " . self::$prByUsername->getName() . " " . self::$prByUsername->getUsername() . " " . self::$prByUsername->getPassword() . " ";

        $this->assertEquals(
            self::$prEN->getLastname() . " " . self::$prEN->getName() . " " . self::$prEN->getUsername() . " " . self::$prEN->getPassword() . " ",
            self::$prByUsername->getLastname() . " " . self::$prByUsername->getName() . " " . self::$prByUsername->getUsername() . " " . self::$prByUsername->getPassword() . " "
        );
    }

    public function testDoRetrieveAll()
    {
        self::$allPr = self::$prDB->doRetrieveAll();

        echo "\nOutput ottenuto: ";
        echo "\n\t " . self::$allPr[0]->getLastname() . " " . self::$allPr[0]->getName() . " " . self::$allPr[0]->getUsername() . " " . self::$allPr[0]->getPassword() . " ";
        echo "\n\n********************************************************";
        echo "\ntest doRetrieveAll() ";
        echo "\n\nOutput atteso: ";
        echo "\n\t " . self::$prEN->getLastname() . " " . self::$prEN->getName() . " " . self::$prEN->getUsername() . " " . self::$prEN->getPassword() . " ";

        $this->assertEquals(
        //1, sizeof(self::$allPr)
            self::$prEN->getLastname() . " " . self::$prEN->getName() . " " . self::$prEN->getUsername() . " " . self::$prEN->getPassword() . " ",
            self::$allPr[0]->getLastname() . " " . self::$allPr[0]->getName() . " " . self::$allPr[0]->getUsername() . " " . self::$allPr[0]->getPassword() . " "
        );
    }

    public function testDeleteById()
    {
        self::$prDB->doDeleteById(self::$prEN->getUsername());

        echo "\n\n********************************************************";
        echo "\ntest testDeleteById(\"CognomeNome\") ";
        echo "\n\nOutput atteso: ";
        echo "\n\t 0";
        echo "\nOutput ottenuto: ";
        echo "\n\t " . self::countPrs();

        $this->assertEquals(
            0, self::countPrs()
        );
    }

    public static function tearDownAfterClass(): void
    {

    }

    private static function countPrs()
    {
        self::conn();
        self::$count = 0;

        $query = "SELECT COUNT(*) AS totPr FROM pr";
        $result = self::$connessione->query($query);

        while ($row = $result->fetch_array(MYSQLI_ASSOC)) {
            self::$count = $row["totPr"];
        }

        $result->close();
        self::$connessione->close();

        return self::$count;
    }

    private static function resetPr()
    {
        self::conn();

        $query = "SET FOREIGN_KEY_CHECKS = 0";
        self::$connessione->query($query);

        $query = "TRUNCATE TABLE pr";
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