package test.unitTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import entity.Cart;
import entity.CartProduct;
import entity.Detail;
import entity.Product;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartTest {

	private static Cart c;
	private static CartProduct cp1,cp2,cp3;
	private static Detail d;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		c=new Cart();
		cp1=new CartProduct();
		cp2=new CartProduct();
		cp3=new CartProduct();
		Product p=new Product();
		Product p2=new Product();
		d=new Detail();
		
		p.setIdProduct(1);
		p.setName("product name cart test");
		p.setDescription("product description cart test");
		p.setQuantityGalley(1200);
		
		p2.setIdProduct(2);
		p2.setName("product2 name cart test");
		p2.setDescription("product2 description cart test");
		p.setQuantityGalley(40);
		
		cp1.setProduct(p);
		cp1.setQuantity(23);
		
		cp2.setProduct(p);
		cp2.setQuantity(17);
		
		cp3.setProduct(p2);
		cp3.setQuantity(17);

		d.setIdDetail(1);
		d.setDescription("test detail");
	}

	@Test
	@org.junit.jupiter.api.Order(0)
	void testAddProductToCart1() {
		//prodotto non presente nel carrello
		assertTrue(c.addProductToCart(cp1));
		
		assertEquals(1,c.getProductCount());
	}

	@Test
	@org.junit.jupiter.api.Order(1)
	void testAddProductToCart2() {
		//prodotto gia presente nel carrello,aggiorna solo le qta
		assertTrue(c.addProductToCart(cp2));//aggiorna cp1
		
		CartProduct res=c.getProductList().get(cp2.getProduct().getIdProduct());
		assertEquals(res.getQuantity(),cp1.getQuantity()); //perche l ha messo due volte
		
		//aggiungo un altro prodotto e mi assicuro che ora sono due i prodotti nel carrello
		assertTrue(c.addProductToCart(cp3));
		assertEquals(2,c.getProductCount());
	}
	
	@Test
	@org.junit.jupiter.api.Order(2)
	void testRemoveProductFromCart() {
		assertTrue(c.removeProductFromCart(cp1.getProduct().getIdProduct()));
		
		assertEquals(c.getProductCount(),1);
	}

	@Test
	@org.junit.jupiter.api.Order(3)
	void testAddDetailToCart() {
		assertTrue(c.addDetailToCart(d));
		
		assertEquals(c.getDetailCount(),1);
		
		//riprovo l inserimento
		assertTrue(c.addDetailToCart(d));
		
		assertEquals(c.getDetailCount(),1);
	}
	
	@Test
	@org.junit.jupiter.api.Order(4)
	void testContainsDetail() {
		assertTrue(c.containsDetail(d));
	}

	@Test
	@org.junit.jupiter.api.Order(5)
	void testRemoveDetailFromCart() {
		assertTrue(c.removeDetailFromCart(d.getIdDetail()));
		
		assertEquals(c.getDetailCount(),0);
	}

}
