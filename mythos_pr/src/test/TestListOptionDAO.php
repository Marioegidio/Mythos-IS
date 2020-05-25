<?php
require_once "GenericTest.php";

class TestListOptionDAO extends GenericTest
{

    private static $loDAO;
    private static $connessione;
    private static $host = "localhost";
    private static $user = "root";
    private static $password = "";
    private static $db = "mythos_pr_is";
    private static $stmt;
    private static $lo;


    public static function setUpBeforeClass(): void
    {
        self::resetOption();
        self::insertListOption();
        self::$loDAO = new ListOptionDAO();
        self::$lo = new ListOption();
        self::$lo->setFlagList(1);
    }

    public function testDoRetrieveAll()
    {
        $esito = self::$loDAO->doRetrieveAll()->getFlagList();

        echo "\n\n********************************************************";
        echo "\ntest doRetrieveAll() ";
        echo "\n\nOutput atteso: ";
        echo "\n\t 0 ";
        echo "\nOutput ottenuto: ";
        echo "\n\t " . $esito;
        $this->assertEquals(
            0, $esito
        );
    }

    public function testDoUpdate()
    {
        self::$loDAO->doUpdate(self::$lo);

        echo "\n\n********************************************************";
        echo "\ntest doUpdate(\"self::\$lo\") ";
        echo "\n\nOutput atteso: ";
        echo "\n\t 1 ";
        echo "\nOutput ottenuto: ";
        echo "\n\t " . self::$loDAO->doRetrieveAll()->getFlagList();

        $this->assertEquals(
            1, self::$loDAO->doRetrieveAll()->getFlagList()
        );

    }

    public static function tearDownAfterClass(): void
    {

    }

    private static function insertListOption()
    {
        self::conn();
        self::$stmt = self::$connessione->prepare("INSERT INTO options(flagList) VALUES(?)");
        $flag = 0;
        self::$stmt->bind_param("s", $flag);
        self::$stmt->execute();
        self::$connessione->close();
    }

    private static function resetOption()
    {
        self::conn();
        $query = "TRUNCATE TABLE options";
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