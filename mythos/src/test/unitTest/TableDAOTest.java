package test.unitTest;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import DAO.TableDAO;
import entity.Table;
import utility.ConnectionPool;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TableDAOTest {

	private static TableDAO tDao;
	private static Table t;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		tDao=new TableDAO();
		t=new Table();
		
		t.setTableNumber(1);
		t.setTableName("I Maschi e le Femmine");
		t.setCapacity(30);
		t.setAssigned(true);
		t.setReservationName("La porta ");
		t.setPeopleNumber(18);
		t.setBudget(0);
		t.setLuxus(false);
		
	}
	
	@Test
	@org.junit.jupiter.api.Order(-1)
	void testDoClear() {
		t.setReservationName("");
		t.setPeopleNumber(0);
		t.setBudget(0);
		t.setAssigned(false);
		
		assertTrue(tDao.doClear());
		
		VerifyTables(t,tDao.doRetrieveById(t.getTableNumber()));
	}
	
	@Test
	@org.junit.jupiter.api.Order(0)
	void testDoUpdate() {
		//posso rimettere i dati iniziali,tanti il doClear li ha azzerati
		t.setTableName("I Maschi e le Femmine");
		t.setCapacity(30);
		t.setAssigned(true);
		t.setReservationName("La porta ");
		t.setPeopleNumber(18);
		t.setBudget(0);
		t.setLuxus(false);
		
		assertTrue(tDao.doUpdate(t)==true);
		VerifyTables(t,tDao.doRetrieveById(t.getTableNumber()));
	}
	
	@Test
	@org.junit.jupiter.api.Order(1)
	void testDoRetrieveAll() throws SQLException {
		
		Connection con=ConnectionPool.getConnection();
		PreparedStatement stm=con.prepareStatement("SELECT COUNT(1) FROM tables");
		ResultSet rs=stm.executeQuery();
		rs.next();
		int countRow=rs.getInt(1);
		
		ArrayList<Table> tables=tDao.doRetrieveAll();
		
		//prima controllo se il numero di righe si trova con quello che ho calcolato io,
		//poi mi confronto la prima riga per verificare che i dati arrivino nel modo corretto
		assertEquals(countRow, tables.size());
		
		VerifyTables(t,tables.get(0));
	}

	@Test
	@org.junit.jupiter.api.Order(2)
	void testDoRetrieveNotAssigned() {
		ArrayList<Table> tables=tDao.doRetrieveNotAssigned();
		
		for (Table t:tables)
			assertTrue(!t.isAssigned());
	}

	@Test
	@org.junit.jupiter.api.Order(3)
	void testDoRetrieveById() {
		Table result=tDao.doRetrieveById(t.getTableNumber());
		
		VerifyTables(t, result);
	}

	@Test
	@org.junit.jupiter.api.Order(4)
	void testDoRetrieveAssigned() {
		
		t.setAssigned(true);
		
		ArrayList<Table> tables=tDao.doRetrieveAssigned();
		
		for (Table t:tables)
			assertTrue(t.isAssigned());
	}

	@Test
	@org.junit.jupiter.api.Order(6)
	void testDoSetNotAssignedById() {
		t.setAssigned(false);
		
		assertTrue(tDao.doSetNotAssignedById(t.getTableNumber())==true);
		
		VerifyTables(t, tDao.doRetrieveById(t.getTableNumber()));
	}

	@Test
	@org.junit.jupiter.api.Order(7)
	void testDoEditReservation() {
		
		t.setReservationName("edited reservation name");
		t.setPeopleNumber(167);
		t.setBudget(342);
		assertTrue(tDao.doEditReservation(t.getTableNumber(),t.getReservationName(),t.getPeopleNumber(), t.getBudget()));
		
		VerifyTables(t, tDao.doRetrieveById(t.getTableNumber()));
	}
	
	private static void VerifyTables(Table expected,Table result) {
		assertTrue(expected.getTableNumber()==result.getTableNumber());
		assertTrue(expected.getTableName().equalsIgnoreCase(result.getTableName()));
		assertTrue(expected.getBudget()==result.getBudget());
		assertTrue(expected.getCapacity()==result.getCapacity());
		assertTrue(expected.getPeopleNumber()==result.getPeopleNumber());
		assertTrue(expected.getReservationName().equalsIgnoreCase(result.getReservationName()));
		assertTrue(expected.isAssigned()==result.isAssigned());
		assertTrue(expected.isLuxus()==result.isLuxus());
	}

}
