package entity;

/**
 * Classe per memorizzare i dati riguardanti il rendiconto dei tavoli.
 */
public class OrdersTotals {

	/**
	 * Metodo per restituire il tavolo.
	 * @return {@link Table} tavolo.
	 */
	public Table getTab() {
		return tab;
	}

	/**
	 * Metodo per settare il nuovo tavolo.
	 * @param tab {@link Table} tavolo.
	 */
	public void setTab(Table tab) {
		this.tab = tab;
	}

	/**
	 * Metodo per restituire il totale del tavolo.
	 * @return {@link Float} totale del tavolo.
	 */
	public float getTotal() {
		return total;
	}

	/**
	 * Metodo per settare il totale del tavolo.
	 * @param total {@link Float} totale del tavolo.
	 */
	public void setTotal(float total) {
		this.total = total;
	}

	/**
	 * Metodo per restituire il totale extra del tavolo.
	 * @return {@link Float} totale extra del tavolo.
	 */
	public float getExtraPayments() {
		return extraPayments;
	}

	/**
	 * Metodo per settare il totale extra del tavolo.
	 * @param extraPayments {@link Float} totale extra del tavolo.
	 */
	public void setExtraPayments(float extraPayments) {
		this.extraPayments = extraPayments;
	}

	/**
	 * tavolo.
	 */
	private Table tab;
	/**
	 * pagamento extra.
	 */
	private float extraPayments;
	/**
	 * totale tavolo.
	 */
	private float total;

}
