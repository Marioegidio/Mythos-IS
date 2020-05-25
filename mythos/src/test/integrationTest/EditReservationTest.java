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
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;

import DAO.ReservationOptionDAO;
import control.EditTableReservation;
import entity.Role;
import entity.User;

class EditReservationTest extends Mockito{
	
	private EditTableReservation servlet;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private MockHttpSession session;
	private User u;
	private Role r;
	
	@BeforeEach
	public void setUp() throws ServletException {
		servlet = new EditTableReservation();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		session= new MockHttpSession();
	
		u=new User();
		r=new Role();
		u.setRole(r);
		
		r.setIdRole(2);
		session.setAttribute("userLogged", u);
		request.setSession(session);
		
		request.setParameter("id","1");
		
		 MockServletContext mockServletContext = new MockServletContext(); 
	     mockServletContext.setContextPath( "direct-memory" ); 
	 
	     MockServletConfig mockServletConfig = new MockServletConfig( mockServletContext );
	     servlet.init( mockServletConfig );
	}
    
	@Test
	public void test_caseOne() throws ServletException, IOException  {
		
		request.setParameter("name","");
		request.setParameter("people","12");
		
		/*assertThrows(IllegalArgumentException.class, () -> {
			servlet.doGet(request, response);
		});*/
		
		String result="";
		try {
			servlet.doGet(request, response);
		}catch(IllegalArgumentException e) {
			result=e.getMessage();
		}
		
		assertEquals("Formato nome non valido",result);
	}
	
	@Test
	public void test_caseTwo() throws ServletException, IOException  {
		
		request.setParameter("name","Ciaosonoiltestdelnomecheemaggioredi64Ciaosonoiltestdelnomecheemag");
		request.setParameter("people","");
		
		/*assertThrows(IllegalArgumentException.class, () -> {
			servlet.doGet(request, response);
		});*/
		
		String result="";
		try {
			servlet.doGet(request, response);
		}catch(IllegalArgumentException e) {
			result=e.getMessage();
		}
		
		assertEquals("Formato nome non valido",result);
	}
	
	@Test
	public void test_caseThree() throws ServletException, IOException  {
		
		request.setParameter("name","Pagliaro Vincenzo---");
		request.setParameter("people","");
		
		/*assertThrows(IllegalArgumentException.class, () -> {
			servlet.doGet(request, response);
		});*/
		
		String result="";
		try {
			servlet.doGet(request, response);
		}catch(IllegalArgumentException e) {
			result=e.getMessage();
		}
		
		assertEquals("Formato nome non valido",result);
	}
	
	@Test
	public void test_caseFour() throws ServletException, IOException  {
		
		request.setParameter("name","Pagliaro Vincenzo");
		request.setParameter("people","");
		
		/*assertThrows(IllegalArgumentException.class, () -> {
			servlet.doGet(request, response);
		});*/
		
		String result="";
		try {
			servlet.doGet(request, response);
		}catch(IllegalArgumentException e) {
			result=e.getMessage();
		}
		
		assertEquals("Formato numPersone non valido",result);
	}
	
	@Test
	public void test_caseFive() throws ServletException, IOException  {
		
		request.setParameter("name","Pagliaro Vincenzo");
		request.setParameter("people","55");
		
		/*assertThrows(IllegalArgumentException.class, () -> {
			servlet.doGet(request, response);
		});*/
		
		String result="";
		try {
			servlet.doGet(request, response);
		}catch(IllegalArgumentException e) {
			result=e.getMessage();
		}
		
		assertEquals("Valore numPersone troppo grande",result);
	}
	
	@Test
	public void test_caseSix() throws ServletException, IOException  {
		
		request.setParameter("name","Pagliaro Vincenzo");
		request.setParameter("people","-35a");
		
		/*assertThrows(IllegalArgumentException.class, () -> {
			servlet.doGet(request, response);
		});*/
		
		String result="";
		try {
			servlet.doGet(request, response);
		}catch(IllegalArgumentException e) {
			result=e.getMessage();
		}
		
		assertEquals("Formato numPersone non valido",result);
	}
	
	@Test
	public void test_caseFinal() throws ServletException, IOException  {
		
		request.setParameter("name","Pagliaro Vincenzo");
		request.setParameter("people","35");
		
		servlet.getServletConfig().getServletContext().setAttribute("config", new ReservationOptionDAO().doRetrieveConfig());
		servlet.doGet(request, response);
		String res=response.getContentAsString();
		assertEquals("Prenotazione modificata",res);
	}
}
