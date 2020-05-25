package DAO;

import java.sql.*;

import entity.User;
import utility.ConnectionPool;

/**
 * Classe che si occupa di prelevare i dati degli utenti dallo storage.
 */
public class UserDAO {

	/**
	 * Metodo per verificare le credenziali di un utente.
	 * @param username {@link String} username!=null indica l'username.
	 * @param password {@link String} password!=null indica la password.
	 * @return {@link User} utente.
	 */
	public User doRetrieveByUsernamePassword(String username, String password) {

		try (Connection con = ConnectionPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement("SELECT * " + "FROM users " + "WHERE username=? AND password=? ");

			ps.setString(1, username);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				User u = new User();
				u.setUsername(rs.getString(1));
				u.setPassword(rs.getString(2));
				u.setName(rs.getString(3));
				u.setRole(new RoleDAO().retrieveRoleByID(rs.getInt(4)));
				return u;
			}

			return null;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Metodo che restituisce un utente dall'username.
	 * @param username {@link String} username!=null indica l'username.
	 * @return User {@link User} utente.
	 * @throws SQLException
	 */
	public User doRetrieveByUsername(String username) {
		try (Connection con = ConnectionPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement("SELECT * " + "FROM users " + "WHERE username=? ");

			ps.setString(1, username);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				User u = new User();
				u.setUsername(rs.getString(1));
				u.setPassword(rs.getString(2));
				u.setName(rs.getString(3));
				u.setRole(new RoleDAO().retrieveRoleByID(rs.getInt(4)));
				return u;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}

/*
 * public void doSave(Utente utente) {
 * 
 * try(Connection con = ConnectionPool.getConnection()){
 * 
 * PreparedStatement ps = con.prepareStatement(
 * "INSERT INTO utenti (matricola, nome,cognome,email,password,id_edificio,id_ruolo) "
 * +"VALUES(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
 * 
 * ps.setString(1, utente.getMatricola()); ps.setString(2, utente.getNome());
 * ps.setString(3, utente.getCognome()); ps.setString(4, utente.getEmail());
 * ps.setString(5, utente.getPassword()); ps.setInt(6,
 * utente.getEdificio().getId_edificio()); ps.setInt(7,
 * utente.getRuolo().getId_ruolo());
 * 
 * if (ps.executeUpdate() != 1) { throw new RuntimeException("INSERT error."); }
 * 
 * } catch (SQLException e) { throw new RuntimeException(e); } }
 * 
 * private static Ruolo getRuolo(Connection con, int idRuolo) throws
 * SQLException { PreparedStatement ps = con.prepareStatement(
 * "SELECT c.id_ruolo,c.nome_ruolo " + "FROM ruoli c " + "WHERE c.id_ruolo=?");
 * 
 * ps.setInt(1, idRuolo);
 * 
 * ResultSet rs = ps.executeQuery(); Ruolo c=null; while (rs.next()) { c = new
 * Ruolo(); c.setId_ruolo(rs.getInt(1)); c.setNome_ruolo(rs.getString(2)); }
 * return c; }
 * 
 * public Utente doRetrieveByMail(String email) {
 * 
 * 
 * try(Connection con = ConnectionPool.getConnection()){
 * 
 * 
 * PreparedStatement ps = con.prepareStatement(
 * "SELECT matricola,nome,cognome,email,password,id_ruolo,id_edificio " +
 * "FROM utenti " + "WHERE email=?");
 * 
 * ps.setString(1, email);
 * 
 * 
 * ResultSet rs = ps.executeQuery();
 * 
 * if (rs.next()) { Utente u = new Utente(); u.setMatricola(rs.getString(1));
 * u.setNome(rs.getString(2)); u.setCognome(rs.getString(3));
 * u.setEmail(rs.getString(4)); u.setPassword(rs.getString(5));
 * u.setRuolo(getRuolo(con,rs.getInt(6))); u.setEdificio(new
 * EdificioDAO().doRetrieveById(rs.getInt(7))); return u; } return null; } catch
 * (SQLException e) { throw new RuntimeException(e); }
 * 
 * }
 * 
 * public Utente doRetrieveByMatricola(String matricola) {
 * 
 * try(Connection con = ConnectionPool.getConnection()){
 * 
 * PreparedStatement ps = con.prepareStatement(
 * "SELECT matricola,nome,cognome,email,password,id_ruolo,id_edificio " +
 * "FROM utenti " + "WHERE matricola=?");
 * 
 * ps.setString(1, matricola);
 * 
 * ResultSet rs = ps.executeQuery();
 * 
 * if (rs.next()) { Utente u = new Utente(); u.setMatricola(rs.getString(1));
 * u.setNome(rs.getString(2)); u.setCognome(rs.getString(3));
 * u.setEmail(rs.getString(4)); u.setPassword(rs.getString(5));
 * u.setRuolo(getRuolo(con,rs.getInt(6))); u.setEdificio(new
 * EdificioDAO().doRetrieveById(rs.getInt(7))); return u; } return null; } catch
 * (SQLException e) { throw new RuntimeException(e); }
 * 
 * }
 * 
 * public Utente doRetrieveUserBarByEdificio(Edificio ed) { try(Connection con =
 * ConnectionPool.getConnection()){
 * 
 * 
 * PreparedStatement ps = con.prepareStatement(
 * "SELECT matricola,nome,cognome,email,password,id_ruolo,id_edificio " +
 * "FROM utenti " + "WHERE id_edificio=? AND id_ruolo=?");
 * 
 * ps.setInt(1,ed.getId_edificio()); //setto che l'utente deve essere un
 * bar(id=2-->bar) ps.setInt(2,2);
 * 
 * 
 * ResultSet rs = ps.executeQuery();
 * 
 * if (rs.next()) { Utente u = new Utente(); u.setMatricola(rs.getString(1));
 * u.setNome(rs.getString(2)); u.setCognome(rs.getString(3));
 * u.setEmail(rs.getString(4)); u.setPassword(rs.getString(5));
 * u.setRuolo(getRuolo(con,rs.getInt(6))); u.setEdificio(new
 * EdificioDAO().doRetrieveById(rs.getInt(7))); return u; } return null; } catch
 * (SQLException e) { throw new RuntimeException(e); } }
 * 
 * public boolean doUpdate(Utente u) {
 * 
 * try(Connection con = ConnectionPool.getConnection()) {
 * 
 * 
 * PreparedStatement ps0 = con.prepareStatement("UPDATE utenti "
 * +"SET nome=?,cognome=?,email=?,password=?,id_ruolo=?,id_edificio=? "
 * +"WHERE matricola=? "); ps0.setString(1, u.getNome());
 * ps0.setString(2,u.getCognome()); ps0.setString(3,u.getEmail());
 * ps0.setString(4,u.getPassword()); ps0.setInt(5,u.getRuolo().getId_ruolo());
 * ps0.setInt(6,u.getEdificio().getId_edificio());
 * ps0.setString(7,u.getMatricola());
 * 
 * if(ps0.executeUpdate()==0) return false;
 * 
 * } catch (SQLException e) { e.printStackTrace(); return false; }
 * 
 * return true; } public boolean doUpdatePassword(String pass,String mat) {
 * 
 * try(Connection con = ConnectionPool.getConnection()) {
 * 
 * 
 * PreparedStatement ps0 = con.prepareStatement("UPDATE utenti "
 * +"SET password=? " +"WHERE matricola=? "); ps0.setString(1, pass);
 * ps0.setString(2, mat);
 * 
 * 
 * if(ps0.executeUpdate()==0) return false;
 * 
 * } catch (SQLException e) { e.printStackTrace(); return false; }
 * 
 * return true; }
 * 
 * //elimina l utente del bar public boolean doDeleteBar(Edificio ed) {
 * 
 * try(Connection con = ConnectionPool.getConnection()) {
 * 
 * 
 * PreparedStatement ps0 = con.prepareStatement("DELETE FROM utenti "
 * +"WHERE id_edificio=? AND id_ruolo=? "); ps0.setInt(1,ed.getId_edificio());
 * //elimino l'unico utente che ha ruolo 'bar' e id_edificio uguale a quello
 * passato ps0.setInt(2,2);
 * 
 * if(ps0.executeUpdate()==0) return false;
 * 
 * } catch (SQLException e) { e.printStackTrace(); return false; }
 * 
 * return true; }
 * 
 * public boolean doDeleteUser(Utente u) {
 * 
 * try(Connection con = ConnectionPool.getConnection()) {
 * 
 * 
 * PreparedStatement ps0 = con.prepareStatement("DELETE FROM utenti "
 * +"WHERE matricola=?  "); ps0.setString(1,u.getMatricola());
 * 
 * 
 * if(ps0.executeUpdate()==0) return false;
 * 
 * } catch (SQLException e) { e.printStackTrace(); return false; }
 * 
 * return true; }
 */
