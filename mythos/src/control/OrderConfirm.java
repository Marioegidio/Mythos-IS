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
 * Servlet implementation class OrderConfirm
 */
@WebServlet("/OrderConfirm")
public class OrderConfirm extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected synchronized void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User u = (User) request.getSession().getAttribute("userLogged");

		PrintWriter out = null;
		out = response.getWriter();
		response.setContentType("text/html;charset=UTF-8");

		if (u != null && u.getRole().getIdRole() == 1) {

			Order o = (Order) request.getSession().getAttribute("order");
			float totalOrder = Float.parseFloat(request.getParameter("tot"));
			Cart cart = (Cart) request.getSession().getAttribute("cartList");
			String methodPay = request.getParameter("methodPay");

			int res = OrdinationManager.orderConfirm(o, cart, totalOrder, methodPay);

			switch (res) {
				case 1:
					out.println("<span style=\"color:red\">Errore.Totale negativo.<br> Si prega di rifare l'ordine.</span>");
					
				case 2:
					out.println("Prodotti non disponibli");
					
				case 3:
					out.println("<span style=\"color:red\">Errore nello scalare la qta in magazzino!<br> Si prega di rifare l'ordine!</span>");
					
				case 4:
					out.println("<span style=\"color:red\">Errore!<br> Si prega di rifare l'ordine!</span>");
					
				case 5:
					out.println("<span style=\"color:red\">Errore con la generazione della stampa!<br> Si prega di rifare l'ordine!</span>");
					
				case 0:
					out.println("Ordine inviato!");

			}
			deleteAllOrderConfirmSessions(request);

		} else
			out.print("NON PUOI CONFERMRE ORDINI!");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void deleteAllOrderConfirmSessions(HttpServletRequest request) {
		// in ogni caso,elimino tutti gli attributi dalla sessione.
		request.getSession().removeAttribute("cartList");
		request.getSession().removeAttribute("order");
		request.getSession().removeAttribute("details");

		// resetto i dettagli per il nuov ordine
		// o.setDetails("");
		// response.sendRedirect(".");
	}

}
