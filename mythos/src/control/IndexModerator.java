package control;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.OrdersTotals;
import entity.Product;
import entity.Table;
import entity.User;
import manager.ReservationManager;
import manager.WarehouseManager;

/**
 * Servlet implementation class IndexAdmin
 */
@WebServlet("/IndexModerator")
public class IndexModerator extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User u = (User) request.getSession().getAttribute("userLogged");

		if (u != null && u.getRole().getIdRole() == 3) {

			ArrayList<Product> productsGalley = WarehouseManager.showOnlyGalleyProducts();
			ArrayList<Product> productsBar = WarehouseManager.showOnlyBarProducts();
			ArrayList<Product> products = WarehouseManager.showAllProducts();
			ArrayList<Table> tables = ReservationManager.tablesList();
			ArrayList<OrdersTotals> totals = ReservationManager.tablesReport();

			request.setAttribute("productsGalley", productsGalley);
			request.setAttribute("productsBar", productsBar);
			request.setAttribute("products", products);
			request.setAttribute("tables", tables);
			request.setAttribute("totals", totals);

//			request.setAttribute("config",ro);

			request.getRequestDispatcher("WEB-INF/jsp/index_moderator.jsp").forward(request, response);
		} else
			response.sendRedirect("login.html");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
