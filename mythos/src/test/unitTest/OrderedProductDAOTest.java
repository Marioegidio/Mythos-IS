package test.unitTest;

import static org.junit.Assert.assertTrue;
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
import DAO.OrderedProductDAO;
import entity.Order;
import entity.OrderedProduct;
import entity.OrdersTotals;
import entity.Product;
import entity.Table;
import utility.ConnectionPool;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderedProductDAOTest {
	
	private static OrderedProductDAO orderedpDAO;
	private static Connection con;
	private static OrderedProduct ord1,ord2;
	private static Order o1,o2;
	private static Table t1,t2;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		orderedpDAO=new OrderedProductDAO();
		
		con=ConnectionPool.getConnection();
        PreparedStatement stm=con.prepareStatement("INSERT INTO orders (tableId,userId,status) VALUES(?,?,?)");
        stm.setInt(1,2);
        stm.setString(2,"operatore2");
        stm.setInt(3,1);
		int res=stm.executeUpdate();
		assertTrue(res>0);
		
        stm=con.prepareStatement("INSERT INTO ordered_products (product,orderId) VALUES(?,?)");
        stm.setInt(1,1);
        stm.setInt(2,1);
		res=stm.executeUpdate();
		assertTrue(res>0);
		
		//lo metto qua perchè altrimenti vengono resettati ogni volta che chiamo il metodo 
		//e non si prende i parametri che setto prima di chiamare "insertOrders"
		o1=new Order();
		o2=new Order();
		t1=new Table();
		t1.setTableNumber(1);
		t2=new Table();
		t2.setTableNumber(2);
		
		o1.setTable(t1);
		o2.setTable(t2);
	}
	
	@Test
	@org.junit.jupiter.api.Order(1)
	void testDoClearTable() throws SQLException {
		
		orderedpDAO.doClearTable();
		Connection con=ConnectionPool.getConnection();
		PreparedStatement stm=con.prepareStatement("SELECT * FROM ordered_products");
		ResultSet rs=stm.executeQuery();
		int res=rs.getRow();
		assertEquals(0,res,"La tabella orders è vuota");
	}
	
	@Test
	@org.junit.jupiter.api.Order(2)
	void testDoSave() throws SQLException {
		new OrderDAO().doClearTable();
		//devo prima inserire gli ordini per poter testare i prodotti ordinati
		insertOrders();
		assertTrue(orderedpDAO.doSave(ord1));
		assertTrue(orderedpDAO.doSave(ord2));
	}

	@Test
	@org.junit.jupiter.api.Order(3)
	void testDoRetrieveAll() {
		ArrayList<OrderedProduct> ords=orderedpDAO.doRetrieveAll();
		assertTrue(ords.size()>0);
		assertTrue(ords.get(0).getOrder().getIdOrder()==ord1.getOrder().getIdOrder() &&
				ords.get(0).getProduct().getIdProduct()==ord1.getProduct().getIdProduct() &&
				ords.get(0).getPurchaseUnitPrice()==ord1.getPurchaseUnitPrice() &&
				ords.get(0).getQuantity()==ord1.getQuantity() );
				
		assertTrue(ords.get(1).getOrder().getIdOrder()==ord2.getOrder().getIdOrder() &&
				ords.get(1).getProduct().getIdProduct()==ord2.getProduct().getIdProduct() &&
				ords.get(1).getPurchaseUnitPrice()==ord2.getPurchaseUnitPrice() &&
 				ords.get(1).getQuantity()==ord2.getQuantity() );
	}

	@Test
	@org.junit.jupiter.api.Order(4)
	void testDoRetrieveByOrder() {
		ArrayList<OrderedProduct> result=orderedpDAO.doRetrieveByOrder(ord1.getOrder().getIdOrder());
		//sicuramente c'è un elemento perche ce l ho messo prima 
		assertTrue(
				result.get(0).getOrder().getIdOrder()==ord1.getOrder().getIdOrder() &&
				result.get(0).getProduct().getIdProduct()==ord1.getProduct().getIdProduct() &&
				result.get(0).getPurchaseUnitPrice()==ord1.getPurchaseUnitPrice() &&
				result.get(0).getQuantity()==ord1.getQuantity()
				);
	}

	@Test
	@org.junit.jupiter.api.Order(5)
	void testDoRetrieveTableTot1() throws SQLException {
		new OrderDAO().doClearTable();
		o1.setTable(t1);
		o2.setTable(t1);
		//chiamo prima il metodo privato che mi inserisce gli ordini che mi servono per ricavare il totale ad ogni tavol
		insertOrders();
		
		ArrayList<OrdersTotals> ordtot=orderedpDAO.doRetrieveTableTot();
		float totalExpected=(ord1.getPurchaseUnitPrice()*ord1.getQuantity()) + 
						(ord2.getPurchaseUnitPrice()*ord2.getQuantity());

		assertTrue(ordtot.get(0).getTab().getTableNumber()==o1.getTable().getTableNumber(),"tabella non uguale");
		assertTrue(ordtot.get(0).getTotal()==totalExpected,"totale non uguale a quello aspettato");
		
	}
	
	@Test
	@org.junit.jupiter.api.Order(6)
	void testDoRetrieveTableTot2() throws SQLException {
		new OrderDAO().doClearTable();
		//a differenza di prima però ora metto un ordine per ogni tavolo
		o1.setTable(t1);
		o2.setTable(t2);
		insertOrders();
		
		ArrayList<OrdersTotals> ordtot=orderedpDAO.doRetrieveTableTot();
		float totalExpected1=ord1.getPurchaseUnitPrice()*ord1.getQuantity();
		float totalExpected2=ord2.getPurchaseUnitPrice()*ord2.getQuantity();
		
		assertTrue(ordtot.get(0).getTab().getTableNumber()==t1.getTableNumber() &&
				ordtot.get(0).getTotal()==totalExpected1
				);
		
		assertTrue(ordtot.get(1).getTab().getTableNumber()==t2.getTableNumber() &&
				ordtot.get(1).getTotal()==totalExpected2
				);
		
	}
	
	private static void insertOrders() throws SQLException {
		//creo due prodotti ordinati fittizi
		ord1=new OrderedProduct();
		ord2=new OrderedProduct();
		
		o1.setIdOrder(1);
		o2.setIdOrder(2);
		o1.setStatus(1);
		o2.setStatus(1);
		
		Product p=new Product();
		
		
		ord1.setOrder(o1);
		
		p.setIdProduct(1);
		ord1.setProduct(p);
		
		ord1.setPurchaseUnitPrice(256);
		ord1.setQuantity(12);
		
		ord2.setOrder(o2);
		
		p.setIdProduct(2);
		ord2.setProduct(p);
		
		ord2.setPurchaseUnitPrice(44);
		ord2.setQuantity(28);
		
		PreparedStatement stm=con.prepareStatement("INSERT INTO orders (status,tableId) VALUES(?,?),(?,?)");
		stm.setInt(1,o1.getStatus());
		stm.setInt(2,o1.getTable().getTableNumber());
        stm.setInt(3,o2.getStatus());
        stm.setInt(4,o2.getTable().getTableNumber());
		int res=stm.executeUpdate();
		assertTrue(res>0);
	}
	
	@AfterAll
	public static void tearDown() {
//		gia è stato testato sopra e ha funzionato
		orderedpDAO.doClearTable();
		new OrderDAO().doClearTable();
	}

}
