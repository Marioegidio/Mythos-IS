package entity;

import java.util.HashMap;

/**
 * Classe per memorizzare il carrello.
 */
public class Cart {

	/**
	 * Costruttore.
	 */
	public Cart() {
		productList = new HashMap<>();
		detailsList = new HashMap<>();
	}

	/*
	 * public HashMap<Integer,CartProduct> getProductList() { return productList; }
	 * 
	 * public HashMap<Integer,Detail> getDetailsList() { return detailsList; }
	 */

	/**
	 * Metodo per aggiungere un prodotto al carrello.
	 * @param cart_product {@link CartProduct} cart_product!=null indica il prodotto da aggiungere.
	 * @return {@link Boolean} esito operazione.
	 */
	public boolean addProductToCart(CartProduct cart_product) {

		// se non esiste nel carrello lo inserisce
		if (this.productList.get(cart_product.getProduct().getIdProduct()) == null)
			this.productList.put(cart_product.getProduct().getIdProduct(), cart_product);
		else {
			CartProduct cp = this.productList.get(cart_product.getProduct().getIdProduct());
			// controllo se la qta che sto richiedendo (che sta in cart_product) piï¿½ quella
			// che giï¿½ ci sta in carrello supera quella della cambusa,
			// allora ritorna false e non fa l'inserimento
			int qtaRequired = cart_product.getQuantity() + cp.getQuantity();
			int qtaGalley = cart_product.getProduct().getQuantityGalley();
			if (qtaRequired <= qtaGalley)
				cp.setQuantity(cp.getQuantity() + cart_product.getQuantity());
			else
				return false; // ritorna false quando la qtaRichiesta supera quella disponibile in cambusas
		}
		return true;
	}

	/**
	 * Metodo per rimuovere un prodotto dal carrello.
	 * @param productId {@link Integer} productId!=null && productId>0 indica l'id del prodotto.
	 * @return {@link Boolean} esito operazione.
	 */
	public boolean removeProductFromCart(int productId) {

		if (this.productList.remove(productId) != null)
			return true;

		return false;

	}
	
	/**
	 * Metodo per ottenere il numero di articoli nel carrello.
	 * @return
	 */
	public int getProductCount() {
		return this.productList.size();
	}

	/**
	 * Metodo per ottenere il numero di dettagli nel carrello.
	 * @return
	 */
	public int getDetailCount() {
		return this.detailsList.size();
	}

	/**
	 * Metodo per verificare se un dettaglio è presente nel carrello.
	 * @param d {@link Detail} d!=null indica il dettaglio da cercare.
	 * @return {@link Boolean} vero=trovato, altrimenti false.
	 */
	public boolean containsDetail(Detail d) {
		return this.detailsList.get(d.getIdDetail()) !=null;
	}

	/**
	 * Metodo per aggiungere un dettaglio al carrello.
	 * @param detail {@link Detail} detail!=null indica il dettaglio.
	 * @return {@link Boolean} esito operazione.
	 */
	public boolean addDetailToCart(Detail detail) {
		if(!this.detailsList.containsKey(detail.getIdDetail()))
				this.detailsList.put(detail.getIdDetail(), detail);
		
		return true;
	}

	/**
	 * Metodo per rimuovere un dettaglio dal carrello.
	 * @param id {@link Integer} id!=null && id>0 indica l'id del dettaglio.
	 * @return {@link Boolean} esito operazione.
	 */
	public boolean removeDetailFromCart(int id) {
		if (this.detailsList.remove(id) != null)
			return true;
		return false;
	}

	/**
	 * Metodo per restituire la lista prodotti nel carrelo.
	 * @return {@link HashMap} lista prodotti.
	 */
	public HashMap<Integer, CartProduct> getProductList() {
		return productList;
	}

	/**
	 * Metodo per restituire la lista dettagli nel carrelo.
	 * @return {@link HashMap} lista dettagli.
	 */
	public HashMap<Integer, Detail> getDetailsList() {
		return detailsList;
	}

	/**
	 * lista prodotti.
	 */
	private HashMap<Integer, CartProduct> productList;
	/**
	 * lista dettagli.
	 */
	private HashMap<Integer, Detail> detailsList;

}
