package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Role;
import utility.ConnectionPool;

/**
 * Classe che si occupa di prelevare i dati dei ruoli degli utenti dallo storage.
 */
public class RoleDAO {
	/**
	 * Metodo che restisuisce il ruolo dall'id.
	 * @param idRole {@link Integer} idRole!=null && idRole>0 indica l'id del ruolo.
	 * @return {@link Role} ruolo.
	 * @throws SQLException
	 */
	public Role retrieveRoleByID(int idRole) {
		Role r = null;
		try (Connection con = ConnectionPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement("SELECT idRole,roleName " + "FROM roles " + "WHERE idRole=? ");

			ps.setInt(1, idRole);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				r = new Role();
				r.setIdRole(rs.getInt(1));
				r.setRoleName(rs.getString(2));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r;
	}
}
