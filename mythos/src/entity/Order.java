package entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe per memorizzare i dati riguardanti gli ordini.
 */
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Metodo che restituisce l'id dell'ordine.
	 * @return {@link Integer} id ordine.
	 */
	public int getIdOrder() {
		return idOrder;
	}

	/**
	 * Metodo per settare l'id del dettaglio.
	 * @param idOrder {@link Integer} indica il nuovo id dell'ordine.
	 */
	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}

	/**
	 * Metodo che restituisce la data dell'ordine.
	 * @return {@link String} data ordine.
	 */
	public String getDateOrder() {
		return dateOrder;
	}

	/**
	 * Metodo per settare la data dell'ordine.
	 * @param dateOrder {@link String} data ordine.
	 */
	public void setDateOrder(String dateOrder) {
		this.dateOrder = dateOrder;
	}

	/**
	 * Metodo che restituisce lo stato dell'ordine.
	 * @return {@link Integer} stato ordine.
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Metodo per settare lo stato dell'ordine.
	 * @param status {@link Integer} indica lo stato dell'ordine.
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Metodo che restituisce l'elenco dei dettagli.
	 * @return {@link String} elenco dettagli.
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * Metodo per settare l'elenco dei dettagli.
	 * @param details {@link String} elenco dettagli.
	 */
	public void setDetails(String details) {
		this.details = details;
	}

	/**
	 * Metodo che restituisce l'utente che ha fatto l'ordine.
	 * @return {@link User} utente che ha fatto l'ordine
	 */
	public User getUser() {
		return user;
	}
	/**
	 * Metodo per settare l'utente che ha fatto l'ordine.
	 * @param user {@link User} utente che ha fatto l'ordine
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * Metodo che restituisce il tavolo a cui appartiene l'ordine.
	 * @return {@link Table} tavolo a cui appartiene l'ordine
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * Metodo per settare il tavolo a cui appartiene l'ordine.
	 * @param table {@link Table} tavolo a cui appartiene l'ordine
	 */
	public void setTable(Table table) {
		this.table = table;
	}

	/**
	 * Metodo per restiruire l'elenco dei prodotti ordinati.
	 * @return {@link ArryList} elenco dei prodotti ordinati.
	 */
	public ArrayList<OrderedProduct> getOrderDetails() {
		return orderDetails;
	}
 
	/**
	 * Metodo per settare l'elenco dei prodotti ordinati.
	 * @param orderDetails {@link ArryList} elenco dei prodotti ordinati.
	 */
	public void setOrderDetails(ArrayList<OrderedProduct> orderDetails) {
		this.orderDetails = orderDetails;
	}

	/**
	 * Metodo che restituisce il metodo di pagamento.
	 * @return {@link String} metodo di pagamento.
	 */
	public String getMethodPay() {
		return methodPay;
	}

	/**
	 * Metodo per settare il metodo di pagamento.
	 * @param methodPay {@link String} metodo di pagamento.
	 */
	public void setMethodPay(String methodPay) {
		this.methodPay = methodPay;
	}

	/**
	 * Metodo che restituisce il totale di pagamento extra.
	 * @return {@link Float} totale di pagamento extra.
	 */
	public float getExtraPayments() {
		return extraPayments;
	}

	/**
	 * Metodo per settare il totale di pagamento extra.
	 * @param extraPayments {@link Float} totale di pagamento extra.
	 */
	public void setExtraPayments(float extraPayments) {
		this.extraPayments = extraPayments;
	}

	/**
	 * Metodo to string.
	 */
	@Override
	public String toString() {
		return "Order [idOrder=" + idOrder + ", dateOrder=" + dateOrder + ", status=" + status + ", details=" + details
				+ ", user=" + user + ", table=" + table + ", orderDetails=" + orderDetails + ", methodPay=" + methodPay
				+ ", extraPayments=" + extraPayments + "]";
	}

	
	private int idOrder;
	private String dateOrder;
	private int status;
	private String details = "";
	private User user;
	private Table table;
	private ArrayList<OrderedProduct> orderDetails;
	private String methodPay;
	private float extraPayments;

}
