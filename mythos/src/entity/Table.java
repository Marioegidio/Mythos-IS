package entity;

import java.io.Serializable;
/**
 * Classe per memorizzare i dati riguardanti i tavoli e le prenotazioni.
 */
public class Table implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Metodo per restituire il numero del tavolo.
	 * @return {@link Integer} numero tavolo.
	 */
	public int getTableNumber() {
		return tableNumber;
	}

	/**
	 * Metodo per settare il numero del tavolo.
	 * @param tableNumber {@link Integer} numero tavolo.
	 */
	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}


	/**
	 * Metodo per restituire il nome del tavolo.
	 * @return {@link String} nome tavolo.
	 */
	public String getTableName() {
		if (tableName == null)
			return "";
		return tableName;
	}

	/**
	 * Metodo per settare il nome del tavolo.
	 * @param tableName {@link String} nome tavolo.
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * Metodo per restituire la capacità del tavolo.
	 * @return {@link Integer} capacità tavolo.
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Metodo per settare la capacità del tavolo.
	 * @param capacity {@link Integer} capacità tavolo.
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	/**
	 * Metodo per restituire lo stato del tavolo (assegnato o no).
	 * @return {@link Boolean} stato tavolo.
	 */
	public boolean isAssigned() {
		return isAssigned;
	}

	/**
	 * Metodo per settare lo stato del tavolo (assegnato o no).
	 * @param isAssigned {@link Boolean} stato tavolo.
	 */
	public void setAssigned(boolean isAssigned) {
		this.isAssigned = isAssigned;
	}

	/**
	 * Metodo per restituire il nome della prenotazione al tavolo.
	 * @return {@link String} nome prenotazione.
	 */
	public String getReservationName() {
		return reservationName;
	}

	/**
	 * Metodo per settare il nome della prenotazione al tavolo.
	 * @param reservationName {@link String} nome prenotazione.
	 */
	public void setReservationName(String reservationName) {
		this.reservationName = reservationName;
	}

	/**
	 * Metodo per restituire il numero di persone della prenotazione al tavolo.
	 * @return {@link Integer} numero persone della prenotazione.
	 */
	public int getPeopleNumber() {
		return peopleNumber;
	}

	/**
	 * Metodo per settare il numero di persone della prenotazione al tavolo.
	 * @param peopleNumber {@link Integer} numero persone della prenotazione.
	 */
	public void setPeopleNumber(int peopleNumber) {
		this.peopleNumber = peopleNumber;
	}

	/**
	 * Metodo per restituire il budget della prenotazione al tavolo.
	 * @return {@link Float} budget della prenotazione.
	 */
	public float getBudget() {
		return budget;
	}

	/**
	 * Metodo per settare il budget della prenotazione al tavolo.
	 * @param budget {@link Float} budget della prenotazione.
	 */
	public void setBudget(float budget) {
		this.budget = budget;
	}

	/**
	 * Metodo per restituire il tipo di tavolo.
	 * @return {@link Boolean} tipo di tavolo.
	 */
	public boolean isLuxus() {
		return isLuxus;
	}

	/**
	 * Metodo per settare il tipo di tavolo.
	 * @param isLuxus {@link Boolean} tipo di tavolo.
	 */
	public void setLuxus(boolean isLuxus) {
		this.isLuxus = isLuxus;
	}

	/**
	 * Metodo to string.
	 */
	public String toString() {
		return "Table [tableNumber=" + tableNumber + ", tableName=" + tableName + ", capacity=" + capacity
				+ ", isAssigned=" + isAssigned + ", reservationName=" + reservationName + ", peopleNumber="
				+ peopleNumber + ", budget=" + budget + ", isLuxus=" + isLuxus + "]";
	}

	/**
	 * numero del tavolo.
	 */
	private int tableNumber;
	/**
	 * nome del tavolo.
	 */
	private String tableName;
	/**
	 * capacità tavolo.
	 */
	private int capacity;
	/**
	 * stato tavolo.
	 */
	private boolean isAssigned;
	/**
	 * nome della prenotazione.
	 */
	private String reservationName;
	/**
	 * numero persone della prenotazione.
	 */
	private int peopleNumber;
	/** 
	 * budget tavolo.
	 */
	private float budget;
	/** 
	 * tipo prenotazione.
	 */
	private boolean isLuxus;
}
