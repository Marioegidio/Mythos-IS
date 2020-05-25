package test.unitTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import DAO.DetailDAO;
import entity.Detail;

class DetailDAOTest {
	public static ArrayList<Detail> details=new ArrayList<>();
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
    	Detail detail = new Detail();
    	detail.setIdDetail(1);
    	detail.setDescription("Più bicchieri");
    	details.add(detail);
    	
    	detail = new Detail();
    	detail.setIdDetail(2);
    	detail.setDescription("Pulire tavolo");
    	details.add(detail);
    	
    	detail = new Detail();
    	detail.setIdDetail(3);
    	detail.setDescription("Più Ghiaccio");
    	details.add(detail);
        
        System.out.println("quiiii-->"+details);
	}

	@Test
	void testDoRetrieveAll() {
        System.out.println("doRetriveAll");
        DetailDAO det = new DetailDAO();
        //risultato atteso
        ArrayList<Detail> expResult=details;
        ArrayList<Detail> result = det.doRetrieveAll();
        System.out.println(result);
        System.out.println("detilsss->"+expResult);
        int i=0;
        for(Detail x:details) {
        	assertEquals(x.getIdDetail(), result.get(i).getIdDetail());
        	assertEquals(x.getDescription(), result.get(i).getDescription());
        	i++;
        }
        	
	}

	@Test
	void testDoRetrieveById() {
        System.out.println("doRetriveByID");
        DetailDAO det = new DetailDAO();
        //risultato atteso
        Detail expResult=details.get(0);
        Detail result = det.doRetrieveById(1);
        System.out.println(result);
        System.out.println("detilsss->"+expResult);
        assertEquals(expResult.getIdDetail(), result.getIdDetail());
        assertEquals(expResult.getDescription(), result.getDescription());
	}

}
