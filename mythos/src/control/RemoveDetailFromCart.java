package control;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Cart;
import entity.User;
import manager.OrdinationManager;

/**
 * Servlet implementation class DelToCart
 */
@WebServlet("/RemoveDetailFromCart")
public class RemoveDetailFromCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User u = (User) request.getSession().getAttribute("userLogged");

		// se l'utente e' loggato
		if (u != null && u.getRole().getIdRole() == 1) {

			PrintWriter out = response.getWriter();

			int id = Integer.parseInt(request.getParameter("id"));

			Cart cartList = (Cart) request.getSession().getAttribute("cartList");

			if (OrdinationManager.removeDetailFromCart(id, cartList))
				out.println("Dettaglio rimosso dal carrello");
			else
				out.println("Errore rimozione dettaglio dal carrello");

			// request.getSession().setAttribute("cartList", cartList);

		} else
			response.sendRedirect("login.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
