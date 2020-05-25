package entity;

import java.io.Serializable;

public class Detail implements Serializable {

	/**
	 * Classe per memorizzare i dati riguardanti i dettagli.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Metodo che restituisce l'id del dettaglio.
	 * @return {@link Integer} id dettaglio.
	 */
	public int getIdDetail() {
		return idDetail;
	}

	/**
	 * Metodo per settare l'id del dettaglio.
	 * @param idDetail {@link Integer} indica il nuovo id.
	 */
	public void setIdDetail(int idDetail) {
		this.idDetail = idDetail;
	}

	/**
	 * Metodo che restituisce la descrizione del dettaglio.
	 * @return {@link String} descrizione del dettaglio.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Metodo che setta la nuova descrizione del dettaglio.
	 * @param description {@link String} indica la nuova descrizione.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Metodo to string.
	 */
	public String toString() {
		return "TableDetail [idDetail=" + idDetail + ", description=" + description + " ]";
	}

	/**
	 * id dettaglio.
	 */
	private int idDetail;
	/**
	 * descrizione dettaglio.
	 */
	private String description;
	// private boolean checked;//
}
