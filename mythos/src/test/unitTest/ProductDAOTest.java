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

import DAO.ProductDAO;
import entity.Product;
import utility.ConnectionPool;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductDAOTest {

	private static ProductDAO prodDao;
	private static Product p1;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		prodDao=new ProductDAO();
		
		p1=new Product();
		
		p1.setIdProduct(58);
		p1.setName("Acqua minerale");
		p1.setDescription("Mood-50cl");
		p1.setPrice(89);
		p1.setQuantityWarehouse(13);
		p1.setQuantityGalley(4);
		p1.setQuantityBar(7);
		p1.setFlagType(1);
		p1.setDeleted(false);
		
	}

	@Test
	@org.junit.jupiter.api.Order(1)
	void testDoRetrieveAll() throws SQLException {
		Connection con=ConnectionPool.getConnection();
		PreparedStatement stm=con.prepareStatement("SELECT COUNT(1) FROM products");
		ResultSet rs=stm.executeQuery();
		rs.next();
		int countRow=rs.getInt(1);
		
		ArrayList<Product> prods=prodDao.doRetrieveAll();
		
		//prima controllo se il numero di righe si trova con quello che ho calcolato io,
		//poi mi confronto la prima riga per verificare che i dati arrivino nel modo corretto
		assertEquals(countRow, prods.size());
		
		VerifyProducts(p1,prods.get(0));
			

	}

	@Test
	@org.junit.jupiter.api.Order(2)
	void testDoRetrieveGalleyAll() {
		ArrayList<Product> prods=prodDao.doRetrieveGalleyAll();
		
		//controllo che il flag sia impostato alla cambusa oppure ad entrambi
		for(Product p:prods)
			assertTrue(p.getFlagType()==2 || p.getFlagType()==3);
	}

	@Test
	@org.junit.jupiter.api.Order(3)
	void testDoRetrieveBarAll() {
		ArrayList<Product> prods=prodDao.doRetrieveBarAll();
		//controllo che il flag sia impostato al bar oppure ad entrambi
		for(Product p:prods)
//			System.out.println(p.getFlagType());
			assertTrue(p.getFlagType()==1 || p.getFlagType()==3);
	}
	
	@Test
	@org.junit.jupiter.api.Order(4)
	void testDoRetrieveById() {
		Product prod=prodDao.doRetrieveById(p1.getIdProduct());
		VerifyProducts(p1,prod);
	}

	@Test
	@org.junit.jupiter.api.Order(5)
	void testDoUpdate() {
		
		p1.setName("new name");
		p1.setDescription("new description");
		p1.setPrice(11);
		p1.setQuantityWarehouse(10);
		p1.setQuantityGalley(12);
		p1.setQuantityBar(30);
		p1.setFlagType(20);
		p1.setDeleted(false);
		
		assertTrue(prodDao.doUpdate(p1)==true);
		VerifyProducts(p1, prodDao.doRetrieveById(p1.getIdProduct()));
		
	}

	@Test
	@org.junit.jupiter.api.Order(6)
	void testDoCheckAvailabilityProduct() {
		boolean res=prodDao.doCheckAvailabilityProduct(p1,121);
		assertTrue(res!=true);
		
		res=prodDao.doCheckAvailabilityProduct(p1,12);
		assertTrue(res==true);
		
		res=prodDao.doCheckAvailabilityProduct(p1,10);
		assertTrue(res==true);
	}



	@Test
	@org.junit.jupiter.api.Order(7)
	void testDoRestoreQuantity() {
		
		assertTrue(prodDao.doRestoreQuantity(p1,100));
		Product p=prodDao.doRetrieveById(p1.getIdProduct());
		assertTrue(p1.getQuantityGalley()+100==p.getQuantityGalley());
		
	}
	
	@AfterAll
	static void after_all() {
		
		p1.setIdProduct(58);
		p1.setName("Acqua minerale");
		p1.setDescription("Mood-50cl");
		p1.setPrice(89);
		p1.setQuantityWarehouse(13);
		p1.setQuantityGalley(4);
		p1.setQuantityBar(7);
		p1.setFlagType(1);
		p1.setDeleted(false);
		
		assertTrue(prodDao.doUpdate(p1));
	}
	
	private static void VerifyProducts(Product expected,Product result) {
		assertTrue(expected.getIdProduct()==result.getIdProduct());
		assertTrue(expected.getName().equalsIgnoreCase(result.getName())==true);
		assertTrue(expected.getPrice()==result.getPrice());
		assertTrue(expected.getQuantityWarehouse()==result.getQuantityWarehouse());
		assertTrue(expected.getQuantityGalley()==result.getQuantityGalley());
		assertTrue(expected.getQuantityBar()==result.getQuantityBar());
		assertTrue(expected.isDeleted()==result.isDeleted());
	}

}
