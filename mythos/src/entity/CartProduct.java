package entity;

import java.io.Serializable;

public class CartProduct implements Serializable {

	/**
	 * Classe per memorizzare i dati riguardanti i prodotti del carrello.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Metodo che restituisce il prodotto.
	 * @return {@link Product} prodotto nel carrello.
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * Metodo per aggiornare il prodotto.
	 * @param product {@link Product} indica il nuovo prodotto.
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * Metodo che restituisce la quantità ordinata.
	 * @return {@link Integer} qta ordinata.
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Metodo per settare la quantità ordinata
	 * @param quantity {@link Integer} indica la nuova quantità.
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * Prodotto.
	 */
	private Product product;
	/**
	 * qta.
	 */
	private int quantity;
}
