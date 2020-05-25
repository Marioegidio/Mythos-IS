package manager;

import java.util.ArrayList;

import DAO.OrderDAO;
import DAO.OrderedProductDAO;
import DAO.ReservationOptionDAO;
import entity.Table;
import DAO.TableDAO;
import entity.OrdersTotals;
import entity.ReservationOption;

/**
 * Classe gestore che offre servizi riguardanti le prenotazioni.
 */
public class ReservationManager {

	/**
	 * Metodo che restituisce i costi delle prenotazioni.
	 * @return {@link ReservationOption} costi prenotazioni.
	 */
	public static ReservationOption getReservationsCost() {
		return new ReservationOptionDAO().doRetrieveConfig();
	}

	/**
	 * Metodo che restituisce l'elenco dei tavoli disponibili.
	 * @return {@link ArrayList} elenco tavoli disponibili.
	 */
	public static ArrayList<Table> availableTable() {
		return new TableDAO().doRetrieveNotAssigned();
	}

	/**
	 * Metodo che restituisce l'elenco dei tavoli non disponibili.
	 * @return {@link ArrayList} elenco tavoli prenotati.
	 */
	public static ArrayList<Table> reservationsList() {
		return new TableDAO().doRetrieveAssigned();
	}


	/**
	 * Metodo che restituisce l'elenco dei tavoli.
	 * @return {@link ArrayList} elenco tavoli.
	 */
	public static ArrayList<Table> tablesList() {
		return new TableDAO().doRetrieveAll();
	}

	/**
	 * Metodo che restituisce le informazioni di un tavolo.
	 * @param idTable {@link Integer} idTable!=null & idTable>0 indica l'id del tavolo.
	 * @return {@link Table} tavolo con le informazioni.
	 */
	public static Table reservationInfo(int idTable) {

		return new TableDAO().doRetrieveById(idTable);
	}

	/**
	 * Metodo che restituisce i totali di ogni tavolo.
	 * @return {@link ArrayList} totali tavoli.
	 */
	public static ArrayList<OrdersTotals> tablesReport() {
		return new OrderedProductDAO().doRetrieveTableTot();
	}

	/**
	 * Metodo per effettuare un prenotazione ad un tavolo.
	 * @param idTable {@link Integer} idTable!=null & idTable>0 indica l'id del tavolo.
	 * @param reservationName {@link String} reservationName!=null indica il nome di chi prenota il tavolo.
	 * @param peopleNumber {@link Integer} peopleNumber!=null & peopleNumber>0 indica il numero di persone.
	 * @param budg {@link Float} budg!=null & budg>0 indica il budget.
	 * @param ro {@link ReservationOption} ro!=null indica i costi delle prenotazioni.
	 */
	public static boolean newReservation(int idTable, String reservationName, int peopleNumber, float budg,
			ReservationOption ro) {
		
		

		Table tempTable = new TableDAO().doRetrieveById(idTable);

		if (tempTable.isLuxus())
			budg = peopleNumber * ro.getLuxusTablePrice();
		else
			budg = peopleNumber * ro.getTablePrice();

		Table t = new Table();
		t.setTableNumber(idTable);
		t.setAssigned(true);
		t.setReservationName(reservationName);
		t.setPeopleNumber(peopleNumber);
		t.setBudget(budg);
		t.setLuxus(tempTable.isLuxus());

		return new TableDAO().doUpdate(t);
	}

	/**
	 * Metodo per modificare una prenotazione.
	 * @param id_table {@link Integer} id_table!=null & id_table>0 indica l'id del tavolo.
	 * @param people {@link Integer} people!=null & people>0 indica il numero di persone.
	 * @param name {@link String} name!=null indica il nome di chi prenota il tavolo.
	 * @param ro {@link ReservationOption} ro!=null indica i costi delle prenotazioni.
	 */
	public static boolean editReservation(int id_table, int people, String name, ReservationOption ro) {
		Table tempTable = new TableDAO().doRetrieveById(id_table);
		int old_people = tempTable.getPeopleNumber();
		float budget=0;
		
		int addedPeople = people - old_people;
		if (tempTable.isLuxus())
			budget = addedPeople * ro.getLuxusTablePrice();
		else
			budget = addedPeople * ro.getTablePrice();

		// questa funzione aggiunge al tavolo passato come id,il budget passato come parametro
		return new TableDAO().doEditReservation(id_table,name,people, budget);

	}

	/**
	 * Metodo per modificare il tipo di tavolo.
	 * @param id {@link Integer} id!=null & id>0 indica l'id del tavolo. 
	 * @param isLuxus {@link Boolean} isLuxus!=null indica se il tavolo è di lusso.
	 */
	public static void editTableType(int id, boolean isLuxus) {
		Table t = new TableDAO().doRetrieveById(id);
		t.setLuxus(isLuxus);

		new TableDAO().doUpdate(t);
	}

	/**
	 * Metodo per modificare i costi delle prenotazioni.
	 * @param normalTablePrice {@link Float} normalTablePrice!=null & normalTablePrice>0 indica il costo dei tavoli normali.
	 * @param luxusTablePrice {@link Float} luxusTablePrice!=null & luxusTablePrice>0 indica il costo dei tavoli di lusso.
	 */
	public static boolean editReservationPrice(float normalTablePrice, float luxusTablePrice) {
		ReservationOption ro = new ReservationOption();
		ro.setTablePrice(normalTablePrice);
		ro.setLuxusTablePrice(luxusTablePrice);
		return new ReservationOptionDAO().doUpdate(ro);
	}

	/**
	 * Metodo per cancellare una prenotazione.
	 * @param id_table {@link Integer} id_table!=null & id_table>0 indica l'id del tavolo.
	 * @return {@link Boolean} esito operazione.
	 */
	public static boolean removeReservation(int id_table) {
		return new TableDAO().doSetNotAssignedById(id_table);
	}

	/**
	 * Metodo per azzerare le prenotazioni.
	 */
	public static void clearReservations() {
		new OrderDAO().doClearTable();
		new OrderedProductDAO().doClearTable();
		new TableDAO().doClear();
	}

}
