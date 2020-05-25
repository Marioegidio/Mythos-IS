package entity;

/**
 * Classe per memorizzare i dati riguardanti i prodotti.
 */
public class Product {

	/**
	 * Metodo per restituire l'id del prodotto.
	 * @return {@link Integer} id prodotto.
	 */
	public int getIdProduct() {
		return idProduct;
	}

	/**
	 * Metodo per settare l'id del prodotto.
	 * @param idProduct {@link Integer} id prodotto.
	 */
	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}

	/**
	 * Metodo per restituire il nome del prodotto.
	 * @return {@link String} nome del prodotto.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Metodo per settare il nome del prodotto.
	 * @param name {@link String} nome del prodotto.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Metodo per restituire la descrizione del prodotto.
	 * @return {@link String} descrizione del prodotto.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Metodo per settare la descrizione del prodotto.
	 * @param description {@link String} descrizione del prodotto.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Metodo per restituire il prezzo del prodotto.
	 * @return {@link Float} prezzo del prodotto.
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * Metodo per settare il prezzo del prodotto.
	 * @param price {@link Float} prezzo del prodotto.
	 */
	public void setPrice(float price) {
		this.price = price;
	}

	/**
	 * Metodo per restituire lo stato di eliminazione del prodotto.
	 * @return {@link Boolean} stato di eliminazione del prodotto.
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * Metodo per settare lo stato di eliminazione del prodotto.
	 * @param deleted {@link Boolean} stato di eliminazione del prodotto.
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * Metodo per restituire la qta in magazzino del prodotto.
	 * @return {@link Integer} qta in magazzino del prodotto.
	 */
	public int getQuantityWarehouse() {
		return quantityWarehouse;
	}

	/**
	 * Metodo per settare la qta in magazzino del prodotto.
	 * @param quantityWarehouse {@link Integer} qta in magazzino del prodotto.
	 */
	public void setQuantityWarehouse(int quantityWarehouse) {
		this.quantityWarehouse = quantityWarehouse;
	}

	/**
	 * Metodo per restituire la qta in cambusa del prodotto.
	 * @return {@link Integer} qta in cambusa del prodotto.
	 */
	public int getQuantityGalley() {
		return quantityGalley;
	}

	/**
	 * Metodo per settare la qta in cambusa del prodotto.
	 * @param quantityGalley {@link Integer} qta in cambusa del prodotto.
	 */
	public void setQuantityGalley(int quantityGalley) {
		this.quantityGalley = quantityGalley;
	}

	/**
	 * Metodo per restituire la qta in bar del prodotto.
	 * @return {@link Integer} qta in bar del prodotto.
	 */
	public int getQuantityBar() {
		return quantityBar;
	}

	/**
	 * Metodo per settare la qta in bar del prodotto.
	 * @param quantityBar {@link Integer} qta in bar del prodotto.
	 */
	public void setQuantityBar(int quantityBar) {
		this.quantityBar = quantityBar;
	}

	/**
	 * Metodo per restituire il tipo del prodotto.
	 * @return {@link Integer} tipo del prodotto.
	 */
	public int getFlagType() {
		return flagType;
	}

	/**
	 * Metodo per settare il tipo del prodotto.
	 * @param flagType {@link Integer} tipo del prodotto.
	 */
	public void setFlagType(int flagType) {
		this.flagType = flagType;
	}

	/**
	 * Override di equals.
	 * @param obj
	 * @return {@link Boolean}
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (idProduct != other.idProduct)
			return false;
		return true;
	}

	/**
	 * id prodotto.
	 */
	private int idProduct;
	/**
	 * nome prodotto.
	 */
	private String name;
	/**
	 * descrizione prodotto.
	 */
	private String description;
	/**
	 * prezzo prodotto.
	 */
	private float price;
	/**
	 * quantita in magazzino.
	 */
	private int quantityWarehouse;
	/**
	 * stato eliminazione prodotto.
	 */
	private boolean deleted;
	/**
	 * qta in cambusa.
	 */
	private int quantityGalley;
	/**
	 * qta in bar.
	 */
	private int quantityBar;
	/**
	 * flag tipo.
	 */
	private int flagType;
}
