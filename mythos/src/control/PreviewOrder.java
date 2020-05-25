package control;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Cart;
import entity.Detail;
import entity.Order;
import entity.Product;
import entity.User;
import manager.OrdinationManager;
import manager.WarehouseManager;

/**
 * Servlet implementation class Index
 */
@WebServlet("/PreviewOrder")
public class PreviewOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User u = (User) request.getSession().getAttribute("userLogged");

		if (u != null && u.getRole().getIdRole() == 1) {
			String tableId = request.getParameter("idTable");

			// logica di controllo (va benee metterlo in questa classe)
			if (tableId == null) {
				response.sendRedirect(".");
				return;
			}

			// valore intero,prima è una stringa
			int table_id = Integer.parseInt(tableId);
			Order o = (Order) request.getSession().getAttribute("order");
			Cart cart = (Cart) request.getSession().getAttribute("cartList");
			if (cart == null)
				cart = new Cart();

			// creo un nuovo ordine solo se non ne esiste un altro dello stesso user
			o = OrdinationManager.doCreateOrUpdateOrderByUser(o, u, table_id);

			ArrayList<Product> products = WarehouseManager.showOnlyGalleyProducts();
			ArrayList<Detail> details = WarehouseManager.showOrderDetails();

			request.getSession().setAttribute("order", o);
			request.getSession().setAttribute("cartList", cart);
			request.setAttribute("products", products);
			request.setAttribute("details", details);

			request.getRequestDispatcher("WEB-INF/jsp/preview_order.jsp").forward(request, response);

		} else
			response.sendRedirect("login.html");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
