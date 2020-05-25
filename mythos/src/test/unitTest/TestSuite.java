package test.unitTest;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses( { CartTest.class, DetailDAOTest.class, OrderDAOTest.class,OrderedProductDAOTest.class,
					ProductDAOTest.class,ReservationOptionDAOTest.class,RoleDAOTest.class,TableDAOTest.class,UserDAOTest.class} )
class TestSuite {

}
