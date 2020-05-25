package manager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import DAO.DetailDAO;
import DAO.OrderDAO;
import DAO.OrderedProductDAO;
import DAO.ProductDAO;
import DAO.TableDAO;

import entity.Cart;
import entity.CartProduct;
import entity.Detail;
import entity.Order;
import entity.OrderedProduct;
import entity.Product;
import entity.Table;
import entity.User;

import utility.ManageReceipt;

/**
 * Classe gestore che offre servizi riguardanti le ordinazioni.
 */
public class OrdinationManager {
	/**
	 * Oggetto usato per eseguire le operazioni di "verifica qta prodotto e conferma ordine" come una sequenza atomica.
	 */
	private static Object lock = new Object();

	/**
	 * Metodo per aggiungere un dettaglio al carrello.
	 * @param id {@link Integer} id!=null & id>0 indica l'id del dettaglio.
	 * @param cartList {@link Cart} cartList!=null indica il carrello.
	 * @return {@link Boolean} esito operazione.
	 */
	public static boolean addDetailToCart(int id, Cart cartList) {

		DetailDAO detail = new DetailDAO();
		Detail d = (Detail) detail.doRetrieveById(id);

		if (!cartList.addDetailToCart(d))
			return false;
		else
			return true;
	}

	/**
	 * Metodo per rimuovere un dettaglio dal carrello.
	 * @param id {@link Integer} id!=null & id>0 indica l'id del dettaglio.
	 * @param cartList {@link Cart} cartList!=null indica il carrello.
	 * @return {@link Boolean} esito operazione.
	 */
	public static boolean removeDetailFromCart(int id, Cart cartList) {
		return cartList.removeDetailFromCart(id);
	}

	/**
	 * Metodo per aggiungere un prodotto al carrello.
	 * @param idProd {@link Integer} idProd!=null & idProd>0 indica l'id del prodotto.
	 * @param quant {@link Integer} quant!=null & quant>0 indica la qta del prodotto.
	 * @param {@link Cart} cartList!=null indica il carrello.
	 * @return {@link Boolean} esito operazione.
	 */
	public static boolean addProductToCart(int idProd, int quant, Cart cartList) {

		Product p = getProductIfAvailable(idProd, quant);
		if (p == null)
			return false;// non è disponibile nella qta richiesta

		CartProduct cp = new CartProduct();
		cp.setProduct(p);
		cp.setQuantity(quant);

		return cartList.addProductToCart(cp);
	}

	/**
	 * Metodo per rimuovere un prodotto dal carrello.
	 * @param id {@link Integer} id!=null & id>0 indica l'id del prodotto.
	 * @param cartList {@link Cart} cartList!=null indica il carrello.
	 * @return {@link Boolean} esito operazione.
	 */
	public static boolean removeProductFromCart(int id, Cart cartList) {
		return cartList.removeProductFromCart(id);
	}

	/**
	 * Metodo per eliminare l'ordine dallo storage.
	 * @param o {@link Order} o!=null indica l'ordine.
	 */
	public static void deleteOrderFromStorage(Order o) {
		if (o != null)
			new OrderDAO().doDeleteByOrder(o);
	}

	/**
	 * Metodo per creare o aggiornare un'ordinde se già esiste.
	 * @param o {@link Order} o!=null indica l'ordine.
	 * @param u {@link User} u!=null indica l'utente che effettua l'ordine.
	 * @param table_id {@link Integer} table_id!=null & table_id>0 indica l'id del tavolo.
	 * @return
	 */
	public static Order doCreateOrUpdateOrderByUser(Order o, User u, int table_id) {
		if (o == null) {
			o = new OrderDAO().makeNewOrderByUserAndTableId(u, table_id);
			return o;
		} else {
			o.setTable(new TableDAO().doRetrieveById(table_id));

			if (new OrderDAO().doUpdate(o))
				return o;
			else
				return null;
		}

	}

	/**
	 * Metodo che restituisce l'elenco degli ordini di un tavolo.
	 * @param id {@link Integer} id!=null & id>0 indica l'id del tavolo.
	 * @return {@link ArrayList} elenco ordini.
	 */
	public static ArrayList<Order> tableOrders(int id) {
		ArrayList<Order> orders = new OrderDAO().doRetrieveByTableNumber(id);
		if (orders == null)
			return orders;

		// carico il dettaglio per ogni ordine
		OrderedProductDAO opd = new OrderedProductDAO();
		for (Order o : orders)
			o.setOrderDetails(opd.doRetrieveByOrder(o.getIdOrder()));

		return orders;
	}

	/**
	 * Metodo per confermare un'ordine di dettagli.
	 * @param cartList {@link Cart} cartList!=null indica il carrello.
	 * @param o {@link Order} o!=null indica l'ordine.
	 * @return {@link Boolean} esito operazione.
	 */
	public static boolean detailsConfirm(Cart cartList, Order o) {
		OrderDAO od = new OrderDAO();
		HashMap<Integer, Detail> details = cartList.getDetailsList();

		// mi serve per salvare nel db i dettagli di un tavolo(anch essi sono un
		// ordine(senza prodotti))
		for (Detail detail : details.values()) {

			if (o.getDetails() == null)
				o.setDetails("");

			o.setDetails(o.getDetails() + " " + detail.getDescription() + " <br>");
			Date dt = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentTime = sdf.format(dt);
			o.setDateOrder(currentTime);
			o.setMethodPay("Nessun pagamento");

			od.doUpdate(o);
		}
		// String filename="C:\\receipts\\dettagli-"+o.getUser().getUsername()+".pdf";
		return printDetailHandler(cartList, o);
	}

	/**
	 * Metodo per confermare un'ordine.
	 * @param o {@link Order} o!=null indica l'ordine.
	 * @param cart {@link Cart} cart!=null indica il carrello.
	 * @param totalOrder {@link Float} totalOrder!=null & totalOrder>0 indica il totale dell'ordine.
	 * @param methodPay {@link String} methodPay!=null indica il metodo di pagamneto.
	 * @return {@link Integer} esito operazione.
	 */
	public static int orderConfirm(Order o, Cart cart, float totalOrder, String methodPay) {

		totalOrder = Math.round(totalOrder * 100.0f) / 100.0f;
		HashMap<Integer, Detail> details = cart.getDetailsList();

		if (totalOrder < 0) {

			// System.out.println("negative total order is-->"+totalOrder);
			o.setStatus(-1);
			new OrderDAO().doUpdate(o);
			return 1;
		}

		int res = checkAvailabilityAndScaleQuantity(cart, o);
		// se ritorna un valore diverso da 1,allora vuol, dire che qualche operazione
		// non è andata a buon fine
		if (res != 1)
			return res;

		// scalo il prezzo totale dal budget solo se il prezzo rientra nel cumulabile
		Table t = o.getTable();
		float budgetCalculated = t.getBudget() - totalOrder;

		// se non ce la faccio con il cumulabile(totalOrder>cumulabile)
		if (budgetCalculated < 0) {
			o.setExtraPayments(-budgetCalculated);
			t.setBudget(0);
		} else
			t.setBudget(budgetCalculated);

		o.setMethodPay(methodPay);

		// a questo punto abbiamo il dettaglio dell'ordine,ora bisogna aggiornare
		// l'ordine
		Date dt = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(dt);
		o.setDateOrder(currentTime);
		o.setStatus(1);

		// setto i dettagli dell'ordine
		for (Detail d : details.values()) {
			o.setDetails(o.getDetails() + d.getDescription() + "<br>");
		}

		if (!new OrderDAO().doUpdate(o) || !new TableDAO().doUpdate(t)) {
			restoreProducts(o, cart);
			// System.out.println("errore ordine o tavolo-->"+o+" "+t);
			return 4;
		}

		if (!printOrderlHandler(cart, o)) {
			restoreProducts(o, cart);
			return 5;
		}

		// se sono arrivato qui,tutte le operazioni precedenti sono andate a buon fine
		return 0;
	}

	/**
	 * Metodo per ripristinare le qta dei prodotti se l'ordine non va a buon fine.
	 * @param o {@link Order} o!=null indica l'ordine.
	 * @param cart {@link Cart} cart!=null indica il carrello.
	 */
	private static void restoreProducts(Order o, Cart cart) {
		ProductDAO prodDao = new ProductDAO();

		ProductDAO.lockTable();

		for (CartProduct cp : cart.getProductList().values())
			prodDao.doRestoreQuantity(cp.getProduct(), cp.getQuantity());

		ProductDAO.unlockTable();

		new OrderDAO().doDeleteByOrder(o);
	}
	
	/**
	 * Metodo per verificare la disponibilità e scalare la qta di un prodotto.
	 * @param cart {@link Cart} cart!=null indica il carrello.
	 * @param o {@link Order} o!=null indica l'ordine.
	 * @return {@link Integer} esito operazione.
	 */
	private static int checkAvailabilityAndScaleQuantity(Cart cart, Order o) {
		ProductDAO productDao = new ProductDAO();
		OrderedProductDAO ordproductDao = new OrderedProductDAO();
		OrderedProduct op;
		Product p;

		/*
		 * se non mi ritorna null vuol dire che c'è qualche prodotto che non è
		 * disponibile in quella quantità. se mi ritorna null allora tutti i prodotti
		 * sono disponibili ritorna un arraylist di prodotti non disponibili
		 */

		synchronized (lock) {
			// prima mi assicuro che tutti i prodotti del carrello sono disponibili nelle
			// qta richieste
			for (CartProduct cp : cart.getProductList().values())
				if (!productDao.doCheckAvailabilityProduct(cp.getProduct(), cp.getQuantity()))
					// qualche prodotto non è disponibile
					return 2;

			// se sono arrivato a questo punto,allora salvo i prodotti ordinati e scalo la
			// qta richiesta dal magazzino
			for (CartProduct cp : cart.getProductList().values()) {
				p = cp.getProduct();

				op = new OrderedProduct();
				op.setOrder(o);
				op.setProduct(p);
				op.setPurchaseUnitPrice(p.getPrice());
				op.setQuantity(cp.getQuantity());

				if (!ordproductDao.doSave(op))
					return 3;// errore

				// modifico la quantità in cambusa del prodotto ordinato
				p.setQuantityGalley(p.getQuantityGalley() - cp.getQuantity());
				if (!productDao.doUpdate(op.getProduct()))
					return 3; // errore

			}

		}
		return 1; // tutto ok
	}

	/**
	 * Metodo che restituisce un prodotto se la qta richiesta è disponibile.
	 * @param idProd {@link Integer} idProd!=null & idProd>0 indica l'id del prodotto.
	 * @param qta {@link Integer} qta!=null & qta>0 indica la qta richiesta.
	 * @return {@link Product} prodotto.
	 */
	private static Product getProductIfAvailable(int idProd, int qta) {

		ProductDAO prod = new ProductDAO();
		Product p = prod.doRetrieveById(idProd);

		return qta <= p.getQuantityGalley() ? p : null;
	}

	/**
	 * Metodo per inviare la stampa di un'ordine di soli dettagli.
	 * @param cartList {@link Cart} cartList!=null indica il carrello.
	 * @param o {@link Order} o!=null indica l'ordine.
	 * @return {@link Boolean} esito operazione.
	 */
	private static boolean printDetailHandler(Cart cartList, Order o) {

		String filename = "C:\\receipts\\dettagli-" + o.getUser().getUsername() + ".pdf";
		ManageReceipt mr = new ManageReceipt();

		try {
			mr.createDetailsDocument(cartList, filename, o.getTable().getTableNumber());
			// funzione da abilitare per la stampa
			mr.printToPrinter();
			o.setStatus(2);
			new OrderDAO().doUpdate(o);
			return true;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Metodo per inviare la stampa di un'ordine.
	 * @param cartList {@link Cart} cartList!=null indica il carrello.
	 * @param o {@link Order} o!=null indica l'ordine.
	 * @return {@link Boolean} esito operazione.
	 */
	private static boolean printOrderlHandler(Cart cartList, Order o) {

		String filename = "C:\\receipts\\comanda-" + o.getUser().getUsername() + ".pdf";
		ManageReceipt mr = new ManageReceipt();

		try {
			mr.createDocument(cartList, o, filename);
			// funzione da abilitare per la stampa...se non è andata a buon fine allora
			// ripristina le quantità ed elimina le sessioni
			if (!mr.printToPrinter())
				return false;

			o.setStatus(2);
			return new OrderDAO().doUpdate(o);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
