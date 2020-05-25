package test.integrationTest;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import control.AddProductToCart;
import entity.Cart;
import entity.Role;
import entity.User;

class AddProductToCartTest extends Mockito{
	
	private AddProductToCart servlet;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private MockHttpSession session;
	private Cart c;
	private User u;
	private Role r;
	
	@BeforeEach
	public void setUp() throws ServletException {
		
		servlet = new AddProductToCart();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session= new MockHttpSession();
		c=new Cart();
		u=new User();
		r=new Role();
		u.setRole(r);
		
		r.setIdRole(1);
		
		session.setAttribute("userLogged", u);
		session.setAttribute("cartList", c);
		request.setSession(session);
		
		request.setParameter("id","1");
		response.setCharacterEncoding("UTF-8");
		
	}
	
	@Test
	public void test_caseOne() throws ServletException, IOException  {
		
		request.setParameter("quant","");
		
		/*assertThrows(IllegalArgumentException.class, () -> {
			servlet.doGet(request, response);
		});*/
		
		String result="";
		try {
			servlet.doGet(request, response);
		}catch(IllegalArgumentException e) {
			result=e.getMessage();
		}
		
		assertEquals("Inserisci una quantità",result);
	}
	
	@Test
	public void test_caseTwo() throws ServletException, IOException  {
		
		request.setParameter("quant","999");
		
		servlet.doGet(request, response);
		String res=response.getContentAsString().trim();
		assertEquals("Errore quantità richiesta",res);
	}
	
	@Test
	public void test_caseThree() throws ServletException, IOException  {
		
		request.setParameter("quant","2aa ");
		
		String result="";
		try {
			servlet.doGet(request, response);
		}catch(IllegalArgumentException e) {
			result=e.getMessage();
		}
		
		assertEquals("Quantità non corretta",result);
	}
	
	@Test
	public void test_caseFinal() throws ServletException, IOException  {
		
		request.setParameter("quant","2");
		
		servlet.doGet(request, response);
		
		String res=response.getContentAsString().trim();
		assertEquals(1,Integer.parseInt(res));
	}

}
