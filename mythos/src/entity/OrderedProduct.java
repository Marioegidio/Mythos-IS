package entity;

import java.io.Serializable;

/**
 * Classe per memorizzare i dati riguardanti i prodotti ordinati.
 */
public class OrderedProduct implements Serializable {

	
	private static final long serialVersionUID = 1L;

	/**
	 * Metodo per restituire la quantità ordinata.
	 * @return {@link Integer} quantità ordinata.
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Metodo per settare la quantità ordinata.
	 * @param quantity {@link Integer} quantità ordinata.
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * Metodo per restituire il prezzo d'acquisto.
	 * @return {@link Float} prezzo d'acquisto.
	 */
	public float getPurchaseUnitPrice() {
		return purchaseUnitPrice;
	}

	/**
	 * Metodo per settare il prezzo d'acquisto.
	 * @param purchaseUnitPrice {@link Float} prezzo d'acquisto.
	 */
	public void setPurchaseUnitPrice(float purchaseUnitPrice) {
		this.purchaseUnitPrice = purchaseUnitPrice;
	}

	/**
	 * Metodo per restituire l'ordine.
	 * @return {@link Order} ordine.
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * Metodo per settare l'ordine.
	 * @param order {@link Order} ordine.
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	 * Metodo che restituire il prodotto.
	 * @return {@link Product} prodotto.
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * Metodo per settare il prodotto.
	 * @param product {@link Product} prodotto.
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * Metodo to string.
	 */
	@Override
	public String toString() {
		return "OrderedProduct [quantity=" + quantity + ", purchaseUnitPrice=" + purchaseUnitPrice + ", order=" + order
				+ ", product=" + product + "]";
	}

	/**
	 * quantità.
	 */
	private int quantity;
	/**
	 * prezzo d'acquisto.
	 */
	private float purchaseUnitPrice;
	/**
	 * ordine.
	 */
	private Order order;
	/**
	 * prodotto.
	 */
	private Product product;

}
