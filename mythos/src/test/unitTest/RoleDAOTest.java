package test.unitTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import DAO.RoleDAO;
import entity.Role;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoleDAOTest {
	
	private static RoleDAO rDao;
	private static Role ro;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		rDao=new RoleDAO();
		ro=new Role();
		
		ro.setIdRole(2);
		ro.setRoleName("tablet");
	}

	@Test
	@org.junit.jupiter.api.Order(1)
	void testRetrieveRoleByID() {
		Role r=rDao.retrieveRoleByID(ro.getIdRole());
		assertEquals(r.getIdRole(),ro.getIdRole());
		assertEquals(r.getRoleName(),ro.getRoleName());
		
	}

}
