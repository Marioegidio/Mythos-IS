package utility;

public class ParametersCheck {
	
	public static boolean checkReservationParameters(String reservationName,String peopleNumber) {
		if(reservationName==null || peopleNumber==null) return false;
		
		if(reservationName.length()==0 || reservationName.length()>50 || !reservationName.matches("[a-z A-Zàèìòù’]+"))
			throw new IllegalArgumentException("Formato nome non valido");
		
		//se la lumgheeza e zero oppure non rispetta il formato oppure il valore è maggiore di 50
		if(peopleNumber.length()==0 || !peopleNumber.matches("[0-9]+"))
			throw new IllegalArgumentException("Formato numPersone non valido");
		
		if(Integer.parseInt(peopleNumber)>50)
			throw new IllegalArgumentException("Valore numPersone troppo grande");
		
		return true;
	}

	public static boolean checkAddToProductToCartParameters(String qua) {
		if(qua==null) return false;
		
		if(qua.length()==0)
			throw new IllegalArgumentException("Inserisci una quantità");
		
		if(!qua.matches("[0-9]+"))
			throw new IllegalArgumentException("Quantità non corretta");
		
		return  true;
		
	}

	public static boolean checkEditPRoductParameters(String price_req, String warehouse_req, String galley_req,
			String bar_req) {
		if(price_req==null || warehouse_req==null || galley_req==null || bar_req==null) return false; 
		
		if(price_req.length()==0)
			throw new IllegalArgumentException("Il campo prezzo non può essere vuoto");
		if(!price_req.matches("[0-9.,]+") || price_req.charAt(0)==',')
			throw new IllegalArgumentException("Campo prezzo non valido");
		
		if(warehouse_req.length()==0)
			throw new IllegalArgumentException("Il campo qtaMagazzino non può essere vuoto");
		if(!warehouse_req.matches("[0-9]+"))
			throw new IllegalArgumentException("Campo qtaMagazzino non valido");
		
		if(galley_req.length()==0)
			throw new IllegalArgumentException("Il campo qtaCambusa non può essere vuoto");
		if(!galley_req.matches("[0-9]+"))
			throw new IllegalArgumentException("Campo qtaCambusa non valido");
		
		if(bar_req.length()==0)
			throw new IllegalArgumentException("Il campo qtaBar non può essere vuoto");
		if(!bar_req.matches("[0-9]+"))
			throw new IllegalArgumentException("Campo qtaBar non valido");
		
		return true;	
	}

	public static boolean checkEditReservationPrice(String normprice, String luxprice) {
		if(normprice==null || luxprice==null) return false; 
		
		if(normprice.length()==0)
			throw new IllegalArgumentException("Il prezzo_standard non può essere vuoto");
		if(!normprice.matches("[0-9,.]+") || normprice.charAt(0)==',')
			throw new IllegalArgumentException("Campo prezzo_standard non valido");
		
		if(luxprice.length()==0)
			throw new IllegalArgumentException("Il prezzo_luxus non può essere vuoto");
		if(!luxprice.matches("[0-9,.]+") || luxprice.charAt(0)==',')
			throw new IllegalArgumentException("Campo prezzo_luxus non valido");
		
		return true;
		
		
	}

	public static int checkLoginParameters(String username, String password) {
		if(username!=null && password!=null)
			if(username.length()==0)
				return 1;
			if(password.length()==0)
				return 2;

		return 0;
	}
	
}
