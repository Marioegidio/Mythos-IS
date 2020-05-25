package entity;
/**
 * Classe per memorizzare i dati riguardanti le i costi e le opzioni di prenotazione.
 */
public class ReservationOption {

	/**
	 * Metodo per restituire il prezzo della prenotaione ai tavoli.
	 * @return {@link Float} prezzo prenotazione.
	 */
	public float getTablePrice() {
		return tablePrice;
	}
	
	/**
	 * Metodo per settare il prezzo della prenotaione ai tavoli.
	 * @param tablePrice {@link Float} prezzo prenotazione.
	 */
	public void setTablePrice(float tablePrice) {
		this.tablePrice = tablePrice;
	}
	
	/**
	 * Metodo per restituire il prezzo della prenotaione luxuus ai tavoli.
	 * @return {@link Float} prezzo prenotazione.
	 */
	public float getLuxusTablePrice() {
		return luxusTablePrice;
	}
	
	/**
	 * Metodo per settare il prezzo della prenotaione luxus ai tavoli.
	 * @param luxusTablePrice {@link Float} prezzo prenotazione.
	 */
	public void setLuxusTablePrice(float luxusTablePrice) {
		this.luxusTablePrice = luxusTablePrice;
	}

	/**
	 * prezzo prenotazione normale.
	 */
	private float tablePrice;
	/**
	 * prezzo prenotazione luxus.
	 */
	private float luxusTablePrice;
}
