package DAO;

import java.sql.*;
import java.util.ArrayList;

import entity.Order;
import entity.User;
import utility.ConnectionPool;
/**
 * Classe che si occupa di prelevare i dati degli ordini dallo storage.
 */
public class OrderDAO {
	/**
	 * Metodo che si occupa di memorizzare un ordine conoscendo utente e id del tavolo.
	 * @param u {@link User} u!=null indica l'utente(cameriere) che effettua l'ordine.
	 * @param idTable {@link Integer} idTable!=null && idTable>0 indica il tavolo per cui fare l'ordine.
	 * @return {@link Order} ordine memorizzato.
	 */
	public synchronized Order makeNewOrderByUserAndTableId(User u, int idTable) {
		Order o = null;
		try (Connection con = ConnectionPool.getConnection()) {

			PreparedStatement ps = con.prepareStatement("INSERT INTO orders (tableId,userId) " + " VALUES(?,?) ",
					Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, idTable);
			ps.setString(2, u.getUsername());

			if (ps.executeUpdate() != 1) {
				throw new RuntimeException("INSERT error.");
			}
			// prelevo l ultimo id inserito di questo utente
			int id_ord = doRetrieveMaxIdByUser(u);

			o = new Order();
			o.setIdOrder(id_ord);
			o.setUser(u);
			o.setTable(new TableDAO().doRetrieveById(idTable));

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return o;
	}
	
	/**
	 * Metodo che restituisce l'ultimo ordine di un cameriere.
	 * @param u {@link User} u!=null indica il cameriere.
	 * @return {@link Integer} numero ordine.
	 */
	public int doRetrieveMaxIdByUser(User u) {
		try (Connection con = ConnectionPool.getConnection()) {

			PreparedStatement ps = con.prepareStatement("SELECT MAX(idOrder) " + "FROM orders " + "WHERE userId=?");

			ps.setString(1, u.getUsername());
			int orderId = 0;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				orderId = rs.getInt(1);
			}

			return orderId;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * public boolean updateOrderTableByOrderId(Order o, int idTable) {
	 * try(Connection con = ConnectionPool.getConnection()){
	 * 
	 * PreparedStatement ps = con.prepareStatement( "UPDATE orders " +
	 * "SET tableId=? " + "WHERE idOrder=? ");
	 * 
	 * ps.setInt(1,idTable); ps.setInt(2,o.getIdOrder());
	 * 
	 * if (ps.executeUpdate() == 0) return false;
	 * 
	 * return true; }catch (SQLException e) { throw new RuntimeException(e); } }
	 */
	
	/**
	 * Metodo usato per aggiornare dei valori di un ordine già memorizzato.  
	 * @param o {@link Order} o!=null indica i valori del nuovo ordine.
	 * @return {@link Boolean} esito operazione.
	 */
	public boolean doUpdate(Order o) {
		final String QUERY = "UPDATE orders SET dataOrder = ?, status = ?, tablesDetails = ?, tableId = ?,"
				+ "userId = ?, paymentMethod = ?, extraPayments = ? WHERE idOrder = ?";

		try (Connection con = ConnectionPool.getConnection()) {

			PreparedStatement ps0 = con.prepareStatement(QUERY);
			ps0.setString(1, o.getDateOrder());
			ps0.setInt(2, o.getStatus());
			ps0.setString(3, o.getDetails());
			ps0.setInt(4, o.getTable().getTableNumber());
			ps0.setString(5, o.getUser().getUsername());
			ps0.setString(6, o.getMethodPay());
			ps0.setFloat(7, o.getExtraPayments());
			ps0.setInt(8, o.getIdOrder());

			return ps0.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Metodo che restituisce un'ordine dal suo id.
	 * @param id {@link Integer} id!=null && id>0 indica l'id dell'ordine.
	 * @return {@link Order} ordine cercato.
	 */
	public Order doRetrieveById(int id) {
		try (Connection con = ConnectionPool.getConnection()) {

			PreparedStatement ps = con.prepareStatement("SELECT * " + "FROM orders " + "WHERE idOrder=?");

			ps.setInt(1, id);
			Order o = null;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				o = new Order();				
				o.setIdOrder(rs.getInt(1));
				o.setDateOrder(rs.getString(2));
				o.setStatus(rs.getInt(3));
				o.setMethodPay(rs.getString(4));
				o.setExtraPayments(rs.getFloat(5));
				o.setDetails(rs.getString(6));
				o.setUser(new UserDAO().doRetrieveByUsername(rs.getString(7)));
				o.setTable(new TableDAO().doRetrieveById(rs.getInt(8)));
			}

			return o;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Metodo che restituisce tutti gli ordini effettuati ad un tavolo.
	 * @param tn {@link Integer} tn!=null && tn>0 indica il numero del tavolo
	 * @return {@link ArrayList} elenco ordini del tavolo richiesto.
	 */
	public ArrayList<Order> doRetrieveByTableNumber(int tn) {

		try (Connection con = ConnectionPool.getConnection()) {

			PreparedStatement ps = con.prepareStatement(
					"SELECT * " + "FROM orders " + "WHERE tableId=? AND status>0 " + "ORDER BY dataOrder DESC");

			ps.setInt(1, tn);
			ArrayList<Order> orders = new ArrayList<>();
			Order o = null;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				o = new Order();
				o.setIdOrder(rs.getInt(1));
				o.setDateOrder(rs.getString(2));
				o.setStatus(rs.getInt(3));
				o.setMethodPay(rs.getString(4));
				o.setExtraPayments(rs.getFloat(5));
				o.setDetails(rs.getString(6));
				o.setUser(new UserDAO().doRetrieveByUsername(rs.getString(7)));
				o.setTable(new TableDAO().doRetrieveById(rs.getInt(8)));

				orders.add(o);
			}

			if (orders.size() > 0)
				return orders;
			else
				return null;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Metotdo per azzerare le tabella orders.
	 */
	public void doClearTable() {
		try (Connection con = ConnectionPool.getConnection()) {

			Statement stm = con.createStatement();

			String insertEmp1 = "SET FOREIGN_KEY_CHECKS = 0";
			String insertEmp2 = "TRUNCATE table orders";
			String insertEmp3 = "SET FOREIGN_KEY_CHECKS = 1";

			stm.addBatch(insertEmp1);
			stm.addBatch(insertEmp2);
			stm.addBatch(insertEmp3);

			stm.executeBatch();
			/*
			 * if (!res) { throw new RuntimeException("CLEAR error."); }
			 */

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Metodo che restituisce i pagamenti extra degli ordini confermati per un tavolo.
	 * @param table_id {@link Integer} table_id!=null && table_id>0 indica il numero del tavolo.
	 * @return {@link Float} totale pagamento extra ricavato dalla somma di tutti gli extraPagamenti degli ordini confermati al tavolo passato come parametro.
	 */
	public float doRetrieveExtraPaymentByTable(int table_id) {
		float extraPayment = 0;
		try (Connection con = ConnectionPool.getConnection()) {

			PreparedStatement ps = con.prepareStatement(
					"SELECT SUM(extraPayments) as extraPayments FROM orders WHERE tableId=? AND status>0");

			ps.setInt(1, table_id);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				extraPayment = rs.getFloat(1);
			}

			return extraPayment;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Metodo per eliminare un ordine dallo storage.
	 * @param o {@link Order} o!= null indica l'ordine da eliminare.
	 */
	public void doDeleteByOrder(Order o) {
		try (Connection con = ConnectionPool.getConnection()) {
			PreparedStatement ps0 = con.prepareStatement("DELETE FROM orders " + "WHERE idOrder=?");
			ps0.setInt(1, o.getIdOrder());
			ps0.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}