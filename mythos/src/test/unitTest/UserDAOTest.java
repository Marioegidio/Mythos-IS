package test.unitTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import DAO.UserDAO;
import entity.Role;
import entity.User;

class UserDAOTest {

	private static UserDAO uDao;
	private static User u;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		uDao=new UserDAO();
		u=new User();
		
		u.setUsername("operatore2");
		u.setPassword("op2");
		u.setName("");
		Role r=new Role();
		r.setIdRole(1);
		u.setRole(r);
		
	}

	@Test
	void testDoRetrieveByUsernamePassword() {
		
		assertNotNull(uDao.doRetrieveByUsernamePassword(u.getUsername(), u.getPassword()));
		
	}

	@Test
	void testDoRetrieveByUsername() {
		User uu=uDao.doRetrieveByUsername(u.getUsername());
		assertNotNull(uu);
		
		assertTrue(u.getName().equalsIgnoreCase(uu.getName()));
		assertTrue(u.getPassword().equalsIgnoreCase(uu.getPassword()));
		assertTrue(u.getUsername().equalsIgnoreCase(uu.getUsername()));
		assertTrue(u.getRole().getIdRole()==uu.getRole().getIdRole());
	}

}
