package test.unitTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import DAO.ReservationOptionDAO;
import entity.ReservationOption;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReservationOptionDAOTest {

	private static ReservationOptionDAO roDao;
	private static ReservationOption ro;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		roDao=new ReservationOptionDAO();
		//non posso renderlo piu generico perchè altrimenti dopo non so quali sono i valori prima che il test iniziasse,
		//non posso chiamare doRetrieveConfig prima di testarlo
	}
	
	@AfterAll
	static void setUpAfterClass() throws Exception {
		ro.setTablePrice(25);
		ro.setLuxusTablePrice(30);
		roDao.doUpdate(ro);
	}

	@Test
	@org.junit.jupiter.api.Order(1)
	void testDoRetrieveConfig() {
		ro=roDao.doRetrieveConfig();
		assertTrue(25==ro.getTablePrice());
		assertTrue(30==ro.getLuxusTablePrice());
	}

	@Test
	@org.junit.jupiter.api.Order(2)
	void testDoUpdate() {
		ro.setTablePrice(10);
		ro.setLuxusTablePrice(15);
		assertTrue(roDao.doUpdate(ro));
		
		ro=roDao.doRetrieveConfig();
		assertTrue(10==ro.getTablePrice());
		assertTrue(15==ro.getLuxusTablePrice());
		
	}

}
