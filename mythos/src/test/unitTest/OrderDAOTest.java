package test.unitTest;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import DAO.OrderDAO;
import entity.Order;
import entity.User;
import utility.ConnectionPool;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderDAOTest {

	private static int maxid;
	private static OrderDAO orderDao;
	private static User u;
	private static Order o,ord;
	private static int idTable;
	private static Connection con;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		orderDao=new OrderDAO();
		u=new User();
        u.setUsername("operatore2");
        
        con=ConnectionPool.getConnection();
        
        //preparo delle righe che mi servono 
        PreparedStatement stm=con.prepareStatement("INSERT INTO orders (STATUS) VALUES (0),(1),(2),(0),(0),(0),(0)");
		int res=stm.executeUpdate();
		assertTrue(res>0);
        
	}
	
	@AfterAll
	static void after_ex() {
		//a questo punto dovreo essere certi che funziona
		orderDao.doClearTable();
	}

	@Test
	@org.junit.jupiter.api.Order(1)
	void testDoClearTable() throws SQLException {
		orderDao.doClearTable();
		
		PreparedStatement stm=con.prepareStatement("SELECT * FROM orders");
		ResultSet rs=stm.executeQuery();
		int res=rs.getRow();
		assertEquals(0,res,"La tabella orders è vuota");
	}
	
	@Test
	@org.junit.jupiter.api.Order(2)
	void testMakeNewOrderByUserAndTableId() {
        System.out.println("new Order");
        
        idTable=3;       
        Order result = orderDao.makeNewOrderByUserAndTableId(u,idTable);
        
        //prima ci assicuriamo che non sia null
        assertNotNull(result);
        //poi ci assicuriamo che l abbia creato per quell'utente a quel tavolo
        assertTrue(result.getUser().getUsername()==u.getUsername() && result.getTable().getTableNumber()==idTable);
        
        //mi serve per testare i prossimi metodi
        maxid=result.getIdOrder();
        
        o=result;
        
	}

	@Test
	@org.junit.jupiter.api.Order(2)
	void testDoRetrieveMaxIdByUser() {
		System.out.println("retrieve Max Order Id by User");
		
        int result = orderDao.doRetrieveMaxIdByUser(u);
        assertEquals(maxid,result);
	}

	
	@Test
	@org.junit.jupiter.api.Order(3)
	void testDoRetrieveById() {
		System.out.println("retrieve Order by Id");
		
		Order result=orderDao.doRetrieveById(o.getIdOrder());
		assertTrue(result.getIdOrder() == o.getIdOrder());
			
	}
	
	
	@Test
	@org.junit.jupiter.api.Order(4)
	void testDoUpdate() {
		o.setDetails("test Update");
		//o.setDateOrder("dataOrder");
		o.setExtraPayments(95f);
		o.setMethodPay("newMethod");
		o.setStatus(2);
		
		boolean res=orderDao.doUpdate(o);
		
		assertTrue(res);
		
	}

	@Test
	@org.junit.jupiter.api.Order(5)
	void testDoRetrieveByTableNumber() {
		System.out.println(" Retrieve By TableNumber");
		ArrayList<Order> result = orderDao.doRetrieveByTableNumber(idTable);
		//sicuramente c'è un ordine poichè l ho inserito prima con id=3
		assertTrue(
				result.get(0).getIdOrder()==o.getIdOrder() &&
				result.get(0).getTable().getTableNumber()==idTable
				);
		assertTrue(result.size()==1);
	}


	@Test
	@org.junit.jupiter.api.Order(6)
	void testDoRetrieveExtraPaymentByTable() {
		System.out.println(" Retrieve ExtraPayment By Table");
		//creo un altro ordine
		ord = orderDao.makeNewOrderByUserAndTableId(u,idTable);
		ord.setExtraPayments(25f);
		ord.setStatus(2);
		orderDao.doUpdate(ord);
		
		float extraPayExpected=ord.getExtraPayments()+o.getExtraPayments();
				
		float result=orderDao.doRetrieveExtraPaymentByTable(idTable);
		assertEquals(extraPayExpected,result);
		
	}

	@Test
	@org.junit.jupiter.api.Order(7)
	void testDoDeleteByOrder() {
		orderDao.doDeleteByOrder(o);
		orderDao.doDeleteByOrder(ord);
		
		//se sono null allora li ha eliminati sicuramente dal database
		assertNull(orderDao.doRetrieveById(o.getIdOrder()));
		assertNull(orderDao.doRetrieveById(ord.getIdOrder()));
		
		
	}

}
