package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.ReservationOption;
import utility.ConnectionPool;

/**
 * Classe che si occupa di prelevare i dati i costi e le opzioni di prenotazione dallo storage.
 */
public class ReservationOptionDAO {

	/**
	 * Metodo che restituisce le opzioni della prenoazione.
	 * @return {@link ReservationOption} opzioni prenotazione.
	 */
	public ReservationOption doRetrieveConfig() {

		try (Connection con = ConnectionPool.getConnection()) {

			PreparedStatement ps = con.prepareStatement("SELECT * " + "FROM reservation_options");

			ReservationOption ro = null;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ro = new ReservationOption();
				ro.setTablePrice(rs.getFloat(1));
				ro.setLuxusTablePrice(rs.getFloat(2));
			}

			return ro;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Metodo per aggiornare le opzioni di prenotazione.
	 * @param ro {@link ReservationOption} ro!=null nuovi valori per le opzioni di prenotazione.
	 * @return {@link Boolean} esito operazione.
	 */
	public boolean doUpdate(ReservationOption ro) {

		try (Connection con = ConnectionPool.getConnection()) {

			PreparedStatement ps0 = con.prepareStatement("UPDATE reservation_options " + "SET tablePrice=?,luxusTablePrice=?");
			ps0.setFloat(1, ro.getTablePrice());
			ps0.setFloat(2, ro.getLuxusTablePrice());

			if (ps0.executeUpdate() == 0)
				return false;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

}