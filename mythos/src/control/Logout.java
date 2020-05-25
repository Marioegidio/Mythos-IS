package control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Order;
import manager.OrdinationManager;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Order o = (Order) request.getSession().getAttribute("order");

		// se l'utente fa il logout ma ha un ordine non confermato,allora viene
		// eliminato dal database
		OrdinationManager.deleteOrderFromStorage(o);

		request.getSession().invalidate();
		response.sendRedirect(".");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
