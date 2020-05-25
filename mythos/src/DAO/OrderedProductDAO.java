package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entity.OrderedProduct;
import entity.OrdersTotals;
import utility.ConnectionPool;


/**
 * Classe che si occupa di prelevare i dati dei prodotti ordinati dallo storage.
 */
public class OrderedProductDAO {

	/**
	 * Metodo che permette di memorizzare un prodotto ordinato. 
	 * @param op {@link OrderedProduct} op!=null indica il prodotto da memorizzare.
	 * @return {@link Boolean} esito operazione.
	 */
	public boolean doSave(OrderedProduct op) {

		try (Connection con = ConnectionPool.getConnection()) {

			PreparedStatement ps0 = con
					.prepareStatement("INSERT INTO ordered_products (quantity,purchaseUnitPrice,orderId,product) "
							+ "VALUES (?,?,?,?)");
			
			ps0.setInt(1, op.getQuantity());
			ps0.setFloat(2, op.getPurchaseUnitPrice());
			ps0.setInt(3, op.getOrder().getIdOrder());
			ps0.setInt(4, op.getProduct().getIdProduct());

			if (ps0.executeUpdate() == 0)
				return false;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Metodo che restituisce tutti i prodotti ordinati.
	 * @return {@link ArrayList} elenco prodotti ordinati.
	 */
	public ArrayList<OrderedProduct> doRetrieveAll() {
		try (Connection con = ConnectionPool.getConnection()) {

			PreparedStatement ps = con.prepareStatement("SELECT * " + "FROM ordered_products " + "ORDER BY orderId ");

			ArrayList<OrderedProduct> orderedProducts = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				OrderedProduct op = new OrderedProduct();
				op.setQuantity(rs.getInt(2));
				op.setPurchaseUnitPrice(rs.getFloat(3));
				op.setProduct(new ProductDAO().doRetrieveById(rs.getInt(4)));
				op.setOrder(new OrderDAO().doRetrieveById(rs.getInt(5)));

				orderedProducts.add(op);
			}
			if (orderedProducts.size() > 0)
				return orderedProducts;
			else
				return null;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Metodo che restituisce tutti i prodotti ordinati di un determinato ordine.
	 * @param order {@link Order} order!=null indica l'ordine.
	 * @return {@link ArrayList} elenco prodotti ordinati.
	 */
	public ArrayList<OrderedProduct> doRetrieveByOrder(int order) {
		try (Connection con = ConnectionPool.getConnection()) {

			PreparedStatement ps = con.prepareStatement("SELECT * " + "FROM ordered_products " + "WHERE orderId=? ");
			ps.setInt(1, order);

			ArrayList<OrderedProduct> orderedProducts = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				OrderedProduct op = new OrderedProduct();
				op.setQuantity(rs.getInt(2));
				op.setPurchaseUnitPrice(rs.getFloat(3));
				op.setProduct(new ProductDAO().doRetrieveById(rs.getInt(4)));
				op.setOrder(new OrderDAO().doRetrieveById(rs.getInt(5)));

				orderedProducts.add(op);
			}
			if (orderedProducts.size() > 0)
				return orderedProducts;
			else
				return null;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Metodo per azzerare la tabella ordered_products.
	 */
	public void doClearTable() {
		try (Connection con = ConnectionPool.getConnection()) {

			Statement stm = con.createStatement();

			String insertEmp1 = "SET FOREIGN_KEY_CHECKS = 0";
			String insertEmp2 = "TRUNCATE table ordered_products";
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
	 * Metodo che restituisce i totali per tavolo.
	 * @return {@link ArrayList} elenco tavoli con relativi totali.
	 */
	public ArrayList<OrdersTotals> doRetrieveTableTot() {
		OrderDAO ordDAo=new OrderDAO();
		
		try (Connection con = ConnectionPool.getConnection()) {

			PreparedStatement ps = con.prepareStatement(
					"SELECT tableId,SUM(quantity*purchaseUnitPrice) AS tot " + "FROM orders,ordered_products "
							+ "WHERE idOrder=orderId AND orders.status>0 " + "GROUP BY tableId");

			ArrayList<OrdersTotals> totals = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				OrdersTotals tot = new OrdersTotals();
				tot.setTab(new TableDAO().doRetrieveById(rs.getInt(1)));
				tot.setTotal(rs.getFloat(2));
				//per motivi di efficienza,viene richiamato qui il metodo per ricavare l extapagamento
				tot.setExtraPayments(ordDAo.doRetrieveExtraPaymentByTable(rs.getInt(1)));
				totals.add(tot);
			}

			if (totals.size() > 0)
				return totals;
			else
				return null;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
