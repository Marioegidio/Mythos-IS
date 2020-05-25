package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Detail;
import utility.ConnectionPool;
/**
 * Classe che si occupa di prelevare i dati dei dettagli dallo storage.
 */

public class DetailDAO {
	/**
	 * Metodo che restituisce l'elenco dei dettagli.
	 * @return {@link ArrayList} elenco dettagli
	 */
	public ArrayList<Detail> doRetrieveAll() {

		try (Connection con = ConnectionPool.getConnection()) {

			PreparedStatement ps = con.prepareStatement("SELECT * " + "FROM tables_details ");

			ArrayList<Detail> details = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Detail t = new Detail();
				t.setIdDetail(rs.getInt(1));
				t.setDescription(rs.getString(2));
				details.add(t);
			}
			if (details.size() > 0)
				return details;
			else
				return null;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Metodo che restituisce un dettaglio conoscendone l'id.
	 * @param id {@link Integer} indica l'id del dettaglio.
	 * @return {@link Detail} 
	 */
	public Detail doRetrieveById(int id) {

		try (Connection con = ConnectionPool.getConnection()) {

			PreparedStatement ps = con.prepareStatement("SELECT * " + "FROM tables_details " + " WHERE idDetail=?");
			ps.setInt(1, id);

			Detail d = new Detail();

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				d.setIdDetail(rs.getInt(1));
				d.setDescription(rs.getString(2));
			}

			return d;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
