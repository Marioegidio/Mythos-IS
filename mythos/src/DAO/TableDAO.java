package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Table;
import utility.ConnectionPool;

/**
 * Classe che si occupa di prelevare i dati delle prenotazioni e dei tavoli dallo storage.
 */
public class TableDAO {

	/**
	 * Metodo che restituisce l'elenco dei tavoli.
	 * @return {@link ArrayList} elenco tavoli.
	 */
	public ArrayList<Table> doRetrieveAll() {

		try (Connection con = ConnectionPool.getConnection()) {

			PreparedStatement ps = con.prepareStatement("SELECT * " + "FROM tables ");

			ArrayList<Table> tables = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Table t = new Table();
				t.setTableNumber(rs.getInt(1));
				t.setTableName(rs.getString(2));
				t.setCapacity(rs.getInt(3));
				t.setAssigned(rs.getBoolean(4));
				t.setReservationName(rs.getString(5));
				t.setPeopleNumber(rs.getInt(6));
				t.setBudget(rs.getFloat(7));
				t.setLuxus(rs.getBoolean(8));
				tables.add(t);
			}
			if (tables.size() > 0)
				return tables;
			else
				return null;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Metodo che restituisce l'elenco dei tavoli disponibili.
	 * @return {@link ArrayList} elenco tavoli disponibili.
	 */
	public ArrayList<Table> doRetrieveNotAssigned() {

		try (Connection con = ConnectionPool.getConnection()) {

			PreparedStatement ps = con.prepareStatement("SELECT * " + "FROM tables " + "WHERE isAssigned=0 ");

			ArrayList<Table> tables = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Table t = new Table();
				t.setTableNumber(rs.getInt(1));
				t.setTableName(rs.getString(2));
				t.setCapacity(rs.getInt(3));
				t.setAssigned(rs.getBoolean(4));
				t.setReservationName(rs.getString(5));
				t.setPeopleNumber(rs.getInt(6));
				t.setBudget(rs.getFloat(7));
				t.setLuxus(rs.getBoolean(8));
				tables.add(t);
			}
			if (tables.size() > 0)
				return tables;
			else
				return null;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Metodo che restituisce i dati del tavolo sapendo il numero del tavolo.
	 * @param idTable {@link Integer} idTable!=null && idTable>0.
	 * @return {@link Table} tavolo.
	 */
	public Table doRetrieveById(int idTable) {
		Table t = null;
		try (Connection con = ConnectionPool.getConnection()) {

			PreparedStatement ps = con.prepareStatement("SELECT * " + "FROM tables " + "WHERE tableNumber=?");

			ps.setInt(1, idTable);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				t = new Table();
				t.setTableNumber(rs.getInt(1));
				t.setTableName(rs.getString(2));
				t.setCapacity(rs.getInt(3));
				t.setAssigned(rs.getBoolean(4));
				t.setReservationName(rs.getString(5));
				t.setPeopleNumber(rs.getInt(6));
				t.setBudget(rs.getFloat(7));
				t.setLuxus(rs.getBoolean(8));
			}
			return t;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Metodo che restituisce l'elenco dei tavoli non disponibili.
	 * @return {@link ArrayList} elenco tavoli non disponibili.
	 */
	public ArrayList<Table> doRetrieveAssigned() {

		try (Connection con = ConnectionPool.getConnection()) {

			PreparedStatement ps = con.prepareStatement("SELECT * " + "FROM tables " + "WHERE isAssigned=1 ");

			ArrayList<Table> tables = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Table t = new Table();
				t.setTableNumber(rs.getInt(1));
				t.setTableName(rs.getString(2));
				t.setCapacity(rs.getInt(3));
				t.setAssigned(rs.getBoolean(4));
				t.setReservationName(rs.getString(5));
				t.setPeopleNumber(rs.getInt(6));
				t.setBudget(rs.getFloat(7));
				t.setLuxus(rs.getBoolean(8));
				tables.add(t);
			}
			if (tables.size() > 0)
				return tables;
			else
				return null;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Metodo usato per aggiornare dei valori di un tavolo già memorizzato.  
	 * @param t {@link Table} t!=null indica i nuovi valori del tavolo.
	 * @return {@link Boolean} esito operazione.
	 */
	public boolean doUpdate(Table t) {

		try (Connection con = ConnectionPool.getConnection()) {

			PreparedStatement ps0 = con.prepareStatement("UPDATE tables "
					+ "SET isAssigned=?,reservationName=?,peopleNumber=?,budget=?,isLuxus=? " + "WHERE tableNumber=? ");
			ps0.setBoolean(1, t.isAssigned());
			ps0.setString(2, t.getReservationName());
			ps0.setFloat(3, t.getPeopleNumber());
			ps0.setFloat(4, t.getBudget());
			ps0.setBoolean(5, t.isLuxus());

			ps0.setInt(6, t.getTableNumber());

			if (ps0.executeUpdate() == 0)
				return false;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Metodo che setta un tavolo come "disponibile"
	 * @param table {@link Table} table!=null indica il tavolo. 
	 * @return {@link Boolean} esito operazione.
	 */
	public boolean doSetNotAssignedById(int table) {

		try (Connection con = ConnectionPool.getConnection()) {

			PreparedStatement ps0 = con
					.prepareStatement("UPDATE tables " + "SET isAssigned=0 " + "WHERE tableNumber=? ");

			ps0.setInt(1, table);

			if (ps0.executeUpdate() == 0)
				return false;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Metodo per modificare una prenotazione
	 * @param id_table {@link Integer} id_table!=null && id_table>0 indica il numero del tavolo.
	 * @param name {@link String} name!=null indica il nome della prenotazione. 
	 * @param people {@link Integer} people!=null && people>0 indica il numero di persone.
	 * @param budget {@link Float} budget!=null && budget>0 indica il budget.
	 * @return
	 */
	public boolean doEditReservation(int id_table,String name,int people, float budget) {

		try (Connection con = ConnectionPool.getConnection()) {

			PreparedStatement ps0 = con.prepareStatement("LOCK TABLES tables WRITE");
			ps0.execute();

			ps0 = con.prepareStatement("UPDATE tables " + 
										"SET reservationName=?,budget=budget+?,peopleNumber=? " + 
										"WHERE tableNumber=? ");
			
			ps0.setString(1,name);
			ps0.setFloat(2, budget);
			ps0.setInt(3, people);
			ps0.setInt(4, id_table);

			if (ps0.executeUpdate() == 0) {
				ps0 = con.prepareStatement("UNLOCK TABLES");
				ps0.execute();
				return false;
			}

			PreparedStatement psUnlock = con.prepareStatement("UNLOCK TABLES");
			psUnlock.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

	/**
	 * Metodo per azzerare i campi della tabella tables.
	 * @return {@link Boolean} esito operazione.
	 */
	public boolean doClear() {

		try (Connection con = ConnectionPool.getConnection()) {

			PreparedStatement ps0 = con
					.prepareStatement("UPDATE tables " + "SET isAssigned=?,reservationName=?,peopleNumber=?,budget=? ");
			ps0.setBoolean(1, false);
			ps0.setString(2, "");
			ps0.setInt(3, 0);
			ps0.setFloat(4, 0);

			if (ps0.executeUpdate() == 0)
				return false;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

}
