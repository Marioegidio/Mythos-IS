package test.integrationTest;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;

import control.Login;

class LoginTest extends Mockito{

	private Login servlet;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	
	@BeforeEach
	public void setUp() throws ServletException {
		servlet = new Login();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		
		 MockServletContext mockServletContext = new MockServletContext(); 
	     mockServletContext.setContextPath( "direct-memory" ); 
	 
	     MockServletConfig mockServletConfig = new MockServletConfig( mockServletContext );
	     servlet.init( mockServletConfig );
	}
	
	@Test
	public void test_caseOne() throws ServletException, IOException  {
		
		request.setParameter("username","");
		request.setParameter("password","");
		
		servlet.doGet(request, response);
		String res=response.getContentAsString();
		assertEquals("Il campo username non può essere vuoto",res);
	}
	
	@Test
	public void test_caseTwo() throws ServletException, IOException  {
		
		request.setParameter("username","operatore");
		request.setParameter("password","12");
		
		servlet.doGet(request, response);
		String res=response.getContentAsString();
		assertEquals("Username o password errati",res);
	}
	
	@Test
	public void test_caseThree() throws ServletException, IOException  {
		
		request.setParameter("username","operatore");
		request.setParameter("password","");
		
		servlet.doGet(request, response);
		String res=response.getContentAsString();
		assertEquals("Il campo password non può essere vuoto",res);
	}
	
	@Test
	public void test_caseFour() throws ServletException, IOException  {
		
		request.setParameter("username","operatore");
		request.setParameter("password","233");
		
		servlet.doGet(request, response);
		String res=response.getContentAsString();
		assertEquals("Username o password errati",res);
	}
	
	@Test
	public void test_caseFive() throws ServletException, IOException  {
		
		request.setParameter("username","operatore1");
		request.setParameter("password","op1");
		
		servlet.doGet(request, response);
		String res=response.getContentAsString();
		assertEquals("Login ok",res);
	}

}
