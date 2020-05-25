package entity;

import java.io.Serializable;
/**
 * Classe per memorizzare i dati riguardanti il ruolo degli Utenti.
 */
public class Role implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Metodo per restituire l'id del ruolo.
	 * @return {@link Integer} id ruolo.
	 */
	public int getIdRole() {
		return idRole;
	}

	/**
	 * Metodo per settare l'id del ruolo.
	 * @param idRole {@link Integer} id ruolo.
	 */
	public void setIdRole(int idRole) {
		this.idRole = idRole;
	}
	
	/**
	 * Metodo per restituire il nome del ruolo.
	 * @return {@link String} nome ruolo.
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * Metodo per settare il nome del ruolo.
	 * @param roleName {@link String} nome ruolo.
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * id ruolo.
	 */
	private int idRole;
	/**
	 * nome ruolo.
	 */
	private String roleName;
}
