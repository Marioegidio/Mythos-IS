package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.Product;
import utility.ConnectionPool;

/**
 * Classe che si occupa di prelevare i dati dei prodotti dallo storage.
 */
public class ProductDAO {

	/**
	 * Metodo che restituisce l'elenco di tutti i prodotti.
	 * @return {@link ArrayList} elenco prodotti.
	 */
	public ArrayList<Product> doRetrieveAll() {

		try (Connection con = ConnectionPool.getConnection()) {

			PreparedStatement ps = con.prepareStatement("SELECT * " + "FROM products " + "ORDER BY name,description");

			ArrayList<Product> products = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product p = new Product();
				p.setIdProduct(rs.getInt(1));
				p.setName(rs.getString(2));
				p.setDescription(rs.getString(3));
				p.setPrice(rs.getFloat(4));
				p.setQuantityWarehouse(rs.getInt(5));
				p.setQuantityGalley(rs.getInt(6));
				p.setQuantityBar(rs.getInt(7));
				p.setFlagType(rs.getInt(8));
				p.setDeleted(rs.getBoolean(9));

				products.add(p);

			}
			if (products.size() > 0)
				return products;
			else
				return null;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Metodo che restituisce l'elenco di tutti i prodotti in cambusa.
	 * @return {@link ArrayList} elenco prodotti in cambusa.
	 */
	public ArrayList<Product> doRetrieveGalleyAll() {

		try (Connection con = ConnectionPool.getConnection()) {

			PreparedStatement ps = con.prepareStatement(
					"SELECT * " + "FROM products " + "WHERE flagType=? OR flagType=? " + "ORDER BY name,description");

			ps.setInt(1, 2);
			ps.setInt(2, 3);

			ArrayList<Product> products = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product p = new Product();
				p.setIdProduct(rs.getInt(1));
				p.setName(rs.getString(2));
				p.setDescription(rs.getString(3));
				p.setPrice(rs.getFloat(4));
				p.setQuantityWarehouse(rs.getInt(5));
				p.setQuantityGalley(rs.getInt(6));
				p.setQuantityBar(rs.getInt(7));
				p.setFlagType(rs.getInt(8));
				p.setDeleted(rs.getBoolean(9));

				products.add(p);

			}
			if (products.size() > 0)
				return products;
			else
				return null;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Metodo che restituisce l'elenco un prodotto conoscendo l'id.
	 * @param id {@link Integer} id!=null && id>0 indica l'id del prodotto.
	 * @return {@link Product} prodotto cercato.
	 */
	public Product doRetrieveById(int id) {
		Product p = null;
		try (Connection con = ConnectionPool.getConnection()) {

			PreparedStatement ps = con.prepareStatement("SELECT *" + "FROM products " + "WHERE idProduct=? ");
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				p = new Product();
				p.setIdProduct(rs.getInt(1));
				p.setName(rs.getString(2));
				p.setDescription(rs.getString(3));
				p.setPrice(rs.getFloat(4));
				p.setQuantityWarehouse(rs.getInt(5));
				p.setQuantityGalley(rs.getInt(6));
				p.setQuantityBar(rs.getInt(7));
				p.setFlagType(rs.getInt(8));
				p.setDeleted(rs.getBoolean(9));
			}
			return p;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Metodo usato per aggiornare dei vaolri di un prodotto già memorizzato.
	 * @param p {@link Product} p!=null indica il prodotto.
	 * @return {@link Boolean} esito operazione.
	 */
	public boolean doUpdate(Product p) {

		try (Connection con = ConnectionPool.getConnection()) {

			PreparedStatement ps = con.prepareStatement("LOCK TABLES products WRITE");
			ps.execute();

			PreparedStatement ps0 = con.prepareStatement("UPDATE products "
					+ "SET name=?,description=?,price=?,quantityWarehouse=?,deleted=?,quantityGalley=?,quantityBar=?,flagType=? "
					+ "WHERE idProduct=? ");
			ps0.setString(1, p.getName());
			ps0.setString(2, p.getDescription());
			ps0.setFloat(3, p.getPrice());
			ps0.setInt(4, p.getQuantityWarehouse());
			ps0.setBoolean(5, p.isDeleted());
			ps0.setInt(6, p.getQuantityGalley());
			ps0.setInt(7, p.getQuantityBar());
			ps0.setInt(8, p.getFlagType());
			ps0.setInt(9, p.getIdProduct());

			if (ps0.executeUpdate() == 0) {
				ps = con.prepareStatement("UNLOCK TABLES");
				ps.execute();
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
	 * Metodo per verificare se la quantità richiesta di un prodotto è disponibile.
	 * @param prod {@link Product} prod!=null indica il prodotto.
	 * @param requestedQta {@link Integer} requestedQta!=null && requestedQta>0 indica la qta richiesta. 
	 * @return {@link Boolean} esito operazione.
	 */
	public boolean doCheckAvailabilityProduct(Product prod, int requestedQta) {
		PreparedStatement ps;
		
		try (Connection con = ConnectionPool.getConnection()) {

			ps = con.prepareStatement("SELECT * " + "FROM products " + "WHERE idProduct = ? AND quantityGalley >= ?");

			ps.setInt(1, prod.getIdProduct());
			ps.setInt(2, requestedQta);

			ResultSet rs = ps.executeQuery();
			
			if (rs.next())
				return true;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return false;
	}

	/**
	 * Metodo che restituisce l'elenco di tutti i prodotti nel bar.
	 * @return {@link ArrayList} elenco prodotti nel bar.
	 */
	public ArrayList<Product> doRetrieveBarAll() {
		try (Connection con = ConnectionPool.getConnection()) {

			PreparedStatement ps = con.prepareStatement(
					"SELECT * " + "FROM products " + "WHERE flagType=? OR flagType=? " + "ORDER BY name,description");

			ps.setInt(1, 1);
			ps.setInt(2, 3);

			ArrayList<Product> products = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product p = new Product();
				p.setIdProduct(rs.getInt(1));
				p.setName(rs.getString(2));
				p.setDescription(rs.getString(3));
				p.setPrice(rs.getFloat(4));
				p.setQuantityWarehouse(rs.getInt(5));
				p.setQuantityGalley(rs.getInt(6));
				p.setQuantityBar(rs.getInt(7));
				p.setFlagType(rs.getInt(8));
				p.setDeleted(rs.getBoolean(9));

				products.add(p);

			}
			if (products.size() > 0)
				return products;
			else
				return null;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// ripristino le quantita' in cambusa dal carrello
	/**
	 * Metodo per ripristinare le quantità in cambusa dal carrello.
	 * @param prod {@link Product} prod!=null indica il prodotto.
	 * @param qtaToAdd {@link Integer} qtaToAdd!=null && qtaToAdd>0 indica la quantità da ripristinare.
	 * @return {@link Boolean} esito operazione.
	 */
	public boolean doRestoreQuantity(Product prod, int qtaToAdd) {

		PreparedStatement ps0;

		try (Connection con = ConnectionPool.getConnection()) {

			ps0 = con.prepareStatement(
					"UPDATE products " + "SET quantityGalley = quantityGalley+? " + "WHERE idProduct=? ");

			ps0.setInt(1, qtaToAdd);
			ps0.setInt(2, prod.getIdProduct());

			if (ps0.executeUpdate() == 0)
				return false;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Metodo per bloccare la tabella products.
	 */
	public static void lockTable() {
		try (Connection con = ConnectionPool.getConnection()) {

			PreparedStatement ps0 = con.prepareStatement("LOCK TABLES products WRITE");
			ps0.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo per sbloccare la tabella products.
	 */
	public static void unlockTable() {
		try (Connection con = ConnectionPool.getConnection()) {

			PreparedStatement psUnlock = con.prepareStatement("UNLOCK TABLES");
			psUnlock.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
