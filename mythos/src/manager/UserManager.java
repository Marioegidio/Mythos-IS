package manager;

import DAO.UserDAO;
import entity.User;

/**
 * Classe gestore che offre servizi riguardanti gli utenti.
 */
public class UserManager {

	/**
	 * Metodo per verificare le credenziali di un utente.
	 * @param username {@link String} username!=null indica l'username.
	 * @param password {@link String} password!=null indica la password.
	 * @return {@link User} l'utente cercato se le credenziali sono corrette, altrimenti null.
	 */
	public static User checkUser(String username, String password) {
		User c = null;
		c = new UserDAO().doRetrieveByUsernamePassword(username, password);
		return c;
	}
}
