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

import control.EditProduct;
import entity.Role;
import entity.User;

class EditProductTest extends Mockito{

	private EditProduct servlet;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private MockHttpSession session;
	private User u;
	private Role r;
	
	@BeforeEach
	public void setUp() throws ServletException {
		servlet = new EditProduct();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session= new MockHttpSession();
	
		u=new User();
		r=new Role();
		u.setRole(r);
		
		r.setIdRole(3);
		session.setAttribute("userLogged", u);
		request.setSession(session);
		
		//setto l id del prodotto che verra modificato
		request.setParameter("id","1");
		//setto la locazione come cambusa di default
		request.setParameter("location","2");
	}
	
	@Test
	public void test_caseOne() throws ServletException, IOException  {
		
		request.setParameter("price","");
		request.setParameter("warehouse","12");
		request.setParameter("galley","");
		request.setParameter("bar","");
		
		String result="";
		try {
			servlet.doGet(request, response);
		}catch(IllegalArgumentException e) {
			result=e.getMessage();
		}
		
		assertEquals("Il campo prezzo non può essere vuoto",result);
	}
	
	@Test
	public void test_caseTwo() throws ServletException, IOException  {
		
		request.setParameter("price","2a");
		request.setParameter("warehouse","12");
		request.setParameter("galley","");
		request.setParameter("bar","");
		
		String result="";
		try {
			servlet.doGet(request, response);
		}catch(IllegalArgumentException e) {
			result=e.getMessage();
		}
		
		assertEquals("Campo prezzo non valido",result);
	}
	
	@Test
	public void test_caseThree() throws ServletException, IOException  {
		
		request.setParameter("price",",09");
		request.setParameter("warehouse","12");
		request.setParameter("galley","");
		request.setParameter("bar","");
		
		String result="";
		try {
			servlet.doGet(request, response);
		}catch(IllegalArgumentException e) {
			result=e.getMessage();
		}
		
		assertEquals("Campo prezzo non valido",result);
	}
	
	@Test
	public void test_caseFour() throws ServletException, IOException  {
		
		request.setParameter("price","90");
		request.setParameter("warehouse","");
		request.setParameter("galley","");
		request.setParameter("bar","");
		
		String result="";
		try {
			servlet.doGet(request, response);
		}catch(IllegalArgumentException e) {
			result=e.getMessage();
		}
		
		assertEquals("Il campo qtaMagazzino non può essere vuoto",result);
	}
	
	@Test
	public void test_caseFive() throws ServletException, IOException  {
		
		request.setParameter("price","90");
		request.setParameter("warehouse","12aa");
		request.setParameter("galley","");
		request.setParameter("bar","");
		
		String result="";
		try {
			servlet.doGet(request, response);
		}catch(IllegalArgumentException e) {
			result=e.getMessage();
		}
		
		assertEquals("Campo qtaMagazzino non valido",result);
	}
	
	@Test
	public void test_caseSix() throws ServletException, IOException  {
		
		request.setParameter("price","90");
		request.setParameter("warehouse","10");
		request.setParameter("galley","");
		request.setParameter("bar","");
		
		String result="";
		try {
			servlet.doGet(request, response);
		}catch(IllegalArgumentException e) {
			result=e.getMessage();
		}
		
		assertEquals("Il campo qtaCambusa non può essere vuoto",result);
	}
	@Test
	public void test_caseSeven() throws ServletException, IOException  {
		
		request.setParameter("price","90");
		request.setParameter("warehouse","12");
		request.setParameter("galley","4a");
		request.setParameter("bar","");
		
		String result="";
		try {
			servlet.doGet(request, response);
		}catch(IllegalArgumentException e) {
			result=e.getMessage();
		}
		
		assertEquals("Campo qtaCambusa non valido",result);
	}
	
	@Test
	public void test_caseEight() throws ServletException, IOException  {
		
		request.setParameter("price","90");
		request.setParameter("warehouse","10");
		request.setParameter("galley","4");
		request.setParameter("bar","");
		
		String result="";
		try {
			servlet.doGet(request, response);
		}catch(IllegalArgumentException e) {
			result=e.getMessage();
		}
		
		assertEquals("Il campo qtaBar non può essere vuoto",result);
	}
	
	@Test
	public void test_caseNine() throws ServletException, IOException  {
		
		request.setParameter("price","90");
		request.setParameter("warehouse","10");
		request.setParameter("galley","4");
		request.setParameter("bar","4'''''");
		
		String result="";
		try {
			servlet.doGet(request, response);
		}catch(IllegalArgumentException e) {
			result=e.getMessage();
		}
		
		assertEquals("Campo qtaBar non valido",result);
	}
	
	@Test
	public void test_caseTen() throws ServletException, IOException  {
		
		request.setParameter("price","90");
		request.setParameter("warehouse","10");
		request.setParameter("galley","2");
		request.setParameter("bar","2");

		servlet.doGet(request, response);
		String res=response.getContentAsString().trim();
		assertEquals("prodotto modificato",res);
	}

}
