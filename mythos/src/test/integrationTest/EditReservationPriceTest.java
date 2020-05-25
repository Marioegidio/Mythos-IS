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

import control.EditReservationPrice;
import entity.Role;
import entity.User;

class EditReservationPriceTest extends Mockito{

		private EditReservationPrice servlet;
		private MockHttpServletRequest request;
		private MockHttpServletResponse response;
		private MockHttpSession session;
		private User u;
		private Role r;
		
		@BeforeEach
		public void setUp() throws ServletException {
			servlet = new EditReservationPrice();
			request = new MockHttpServletRequest();
			response = new MockHttpServletResponse();
			session= new MockHttpSession();
		
			u=new User();
			r=new Role();
			u.setRole(r);
			
			r.setIdRole(3);
			session.setAttribute("userLogged", u);
			request.setSession(session);
		}
		
		@Test
		public void test_caseOne() throws ServletException, IOException  {
			
			request.setParameter("priceNormal","");
			request.setParameter("priceLuxus","12");
			
			String result="";
			try {
				servlet.doGet(request, response);
			}catch(IllegalArgumentException e) {
				result=e.getMessage();
			}
			
			assertEquals("Il prezzo_standard non può essere vuoto",result);
		}

		
		@Test
		public void test_caseTwo() throws ServletException, IOException  {
			
			request.setParameter("priceNormal","2ffrrr");
			request.setParameter("priceLuxus","12");
			
			String result="";
			try {
				servlet.doGet(request, response);
			}catch(IllegalArgumentException e) {
				result=e.getMessage();
			}
			
			assertEquals("Campo prezzo_standard non valido",result);
		}
		
		@Test
		public void test_caseThree() throws ServletException, IOException  {
			
			request.setParameter("priceNormal",",99999");
			request.setParameter("priceLuxus","12");
			
			String result="";
			try {
				servlet.doGet(request, response);
			}catch(IllegalArgumentException e) {
				result=e.getMessage();
			}
			
			assertEquals("Campo prezzo_standard non valido",result);
		}
		
		@Test
		public void test_caseFour() throws ServletException, IOException  {
			
			request.setParameter("priceNormal","20");
			request.setParameter("priceLuxus","");
			
			String result="";
			try {
				servlet.doGet(request, response);
			}catch(IllegalArgumentException e) {
				result=e.getMessage();
			}
			
			assertEquals("Il prezzo_luxus non può essere vuoto",result);
		}
		
		@Test
		public void test_caseFive() throws ServletException, IOException  {
			
			request.setParameter("priceNormal","20");
			request.setParameter("priceLuxus","ff5");
			
			String result="";
			try {
				servlet.doGet(request, response);
			}catch(IllegalArgumentException e) {
				result=e.getMessage();
			}
			
			assertEquals("Campo prezzo_luxus non valido",result);
		}
		
		@Test
		public void test_caseSix() throws ServletException, IOException  {
			
			request.setParameter("priceNormal","20");
			request.setParameter("priceLuxus",",21");
			
			String result="";
			try {
				servlet.doGet(request, response);
			}catch(IllegalArgumentException e) {
				result=e.getMessage();
			}
			
			assertEquals("Campo prezzo_luxus non valido",result);
		}
		@Test
		public void test_caseSeven() throws ServletException, IOException  {
			
			request.setParameter("priceNormal","20");
			request.setParameter("priceLuxus","25");
			
			servlet.doGet(request, response);
			String res=response.getContentAsString().trim();
			assertEquals("Modifica effettuata",res);
		}

}
