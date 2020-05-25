package entity;

import java.io.Serializable;

/**
 * Classe per memorizzare i dati riguardanti gli utenti.
 */
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Metodo per restituire l'username dell'utente.
	 * @return {@link String} username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Metodo per settare l'username dell'utente.
	 * @param username {@link String} username.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Metodo per restituire la password dell'utente.
	 * @return {@link String} password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Metodo per settare la password dell'utente.
	 * @param password {@link String} password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Metodo per restituire il ruolo dell'utente.
	 * @return {@link Role} ruolo.
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * Metodo per settare il ruolo dell'utente.
	 * @param role {@link Role} ruolo.
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * Metodo per restituire il nome dell'utente.
	 * @return {@link String} nome.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Metodo per settare il nome dell'utente.
	 * @param name {@link String} nome.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * username.
	 */
	private String username;
	/**
	 * password.
	 */
	private String password;
	/**
	 * nome.
	 */
	private String name;
	/**
	 * ruolo.
	 */
	private Role role;
}
