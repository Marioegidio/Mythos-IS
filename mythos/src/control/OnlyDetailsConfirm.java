package control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Cart;
import entity.Order;
import entity.User;
import manager.OrdinationManager;

/**
 * Servlet implementation class OnlyDetailsConfirm
 */
@WebServlet("/OnlyDetailsConfirm")
public class OnlyDetailsConfirm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User u = (User) request.getSession().getAttribute("userLogged");

		if (u != null && u.getRole().getIdRole() == 1) {

			PrintWriter out = response.getWriter();
			Order o = (Order) request.getSession().getAttribute("order");
			Cart cartList = (Cart) request.getSession().getAttribute("cartList");

			if (OrdinationManager.detailsConfirm(cartList, o))
				out.println("Dettaglio tavolo inviato!");

			// in ogni caso,elimino tutti gli attributi dalla sessione.
			request.getSession().removeAttribute("cartList");
			request.getSession().removeAttribute("order");
			request.getSession().removeAttribute("details");
			request.getSession().removeAttribute("orderDetails");
			// resetto i dettagli per il nuov ordine
			// o.setDetails("");
			// response.sendRedirect(".");

		} else
			response.sendRedirect("login.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
