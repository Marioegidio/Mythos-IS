package manager;

import java.util.ArrayList;

import DAO.DetailDAO;
import DAO.ProductDAO;
import entity.Detail;
import entity.Product;

/**
 * Classe gestore che offre servizi riguardanti i prodotti in magazzino.
 */
public class WarehouseManager {

	/**
	 * Metodo che restituisce i prodotti della cambusa.
	 * @return {@link ArrayList} prodotti cambusa.
	 */
	public static ArrayList<Product> showOnlyGalleyProducts() {
		return new ProductDAO().doRetrieveGalleyAll();
	}

	/**
	 * Metodo che restituisce i prodotti del bar.
	 * @return {@link ArrayList} prodotti bar.
	 */
	public static ArrayList<Product> showOnlyBarProducts() {
		return new ProductDAO().doRetrieveBarAll();
	}

	/**
	 * Metodo che restituisce tutti i prodotti.
	 * @return {@link ArrayList} elenco prodotti.
	 */
	public static ArrayList<Product> showAllProducts() {
		return new ProductDAO().doRetrieveAll();
	}

	/**
	 * Metodo che restituisce tutti i dettagli.
	 * @return {@link ArrayList} elenco dettagli.
	 */
	public static ArrayList<Detail> showOrderDetails() {
		return new DetailDAO().doRetrieveAll();
	}

	/**
	 * Metodo che permette la modifica di un prodotto.
	 * @param id {@link Integer} id!=null & id>0 indica l'id del prodotto
	 * @param location {@link Integer} location!=null & location>0 indica la locazione.
	 * @param price {@link Float} price!=null & price>0 indica il prezzo.
	 * @param galley {@link Integer} galley!=null & galley>0 indica la qta del prodotto in cambusa.
	 * @param bar {@link Integer} bar!=null & bar>0 indica la qta del prodotto in bar.
	 * @param warehouse {@link Integer} warehouse!=null & warehouse>0 indica la qta del prodotto in magazzino.
	 */
	public static boolean editProduct(int id, int location, float price, int galley, int bar, int warehouse) {
		Product p = new ProductDAO().doRetrieveById(id);

		p.setFlagType(location);
		p.setPrice(price);
		p.setQuantityGalley(galley);
		p.setQuantityBar(bar);
		p.setQuantityWarehouse(warehouse);
		
		return new ProductDAO().doUpdate(p);

	}

}
